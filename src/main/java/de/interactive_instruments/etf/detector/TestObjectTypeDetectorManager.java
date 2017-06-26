/**
 * Copyright 2010-2017 interactive instruments GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.interactive_instruments.etf.detector;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.interactive_instruments.Credentials;
import de.interactive_instruments.UriUtils;
import de.interactive_instruments.container.Pair;
import de.interactive_instruments.etf.dal.dto.capabilities.TestObjectTypeDto;
import de.interactive_instruments.etf.model.DefaultEidMap;
import de.interactive_instruments.etf.model.EID;
import de.interactive_instruments.etf.model.EidMap;
import de.interactive_instruments.exceptions.InitializationException;
import de.interactive_instruments.exceptions.InvalidStateTransitionException;
import de.interactive_instruments.exceptions.ObjectWithIdNotFoundException;
import de.interactive_instruments.exceptions.config.ConfigurationException;

/**
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class TestObjectTypeDetectorManager {

	private static final class InstanceHolder {
		private static final List<TestObjectTypeDetector> loadDetectors() {
			final ServiceLoader<TestObjectTypeDetector> loadedDetectors = ServiceLoader.load(TestObjectTypeDetector.class);

			final ArrayList<TestObjectTypeDetector> detectors = new ArrayList<>();

			final Logger logger = LoggerFactory.getLogger(TestObjectTypeDetectorManager.class);

			for (final TestObjectTypeDetector loadedDetector : loadedDetectors) {
				logger.debug("Adding Test Object TypeDetector {}", loadedDetector.getClass().getName());
				try {
					loadedDetector.init();
					detectors.add(loadedDetector);
				} catch (InitializationException | InvalidStateTransitionException | ConfigurationException e) {
					logger.error("Failed to initialize Test Object TypeDetector {} ", loadedDetector.getClass().getName());
				}
			}
			if (detectors.isEmpty()) {
				throw new RuntimeException("No Test Object TypeDetector available");
			}
			Collections.sort(detectors);
			return Collections.unmodifiableList(detectors);
		}

		private final static List<TestObjectTypeDetector> detectors = loadDetectors();

		private static EidMap<TestObjectTypeDto> initTypes() {
			final DefaultEidMap<TestObjectTypeDto> types = new DefaultEidMap<>();
			// let TestObjectTypeDetectors with a higher priority overwrite the
			// supported types of others
			for (int i = detectors.size() - 1; i >= 0; i--) {
				types.putAll(detectors.get(i).supportedTypes());
			}
			return types.unmodifiable();
		}

		private final static EidMap<TestObjectTypeDto> types = initTypes();

		// Cache max 30 detected types, each for 90 minutes
		private final static Cache<URI, Pair<UriUtils.ModificationCheck, DetectedTestObjectType>> cachedDetectedTestObjectTypes = Caffeine
				.newBuilder().maximumSize(30).expireAfterWrite(90, TimeUnit.MINUTES).build();
	}

	public static EidMap<TestObjectTypeDto> getTypes(final String... eids) {
		final EidMap<TestObjectTypeDto> map = new DefaultEidMap<>();
		for (final String eid : eids) {
			final TestObjectTypeDto t = InstanceHolder.types.get(eid);
			if (t == null) {
				throw new IllegalArgumentException("Test object Type with " + eid + " not found. "
						+ "Probably the correct Test Object Type Detector is not installed.");
			}
			map.put(t.getId(), t);
		}
		return map;
	}

	public static EidMap<TestObjectTypeDto> getSupportedTypes() {
		return InstanceHolder.types;
	}

	/**
	 * Call detectors and cache the results
	 *
	 * @param uri URL or file URI
	 * @param credentials optional credentials
	 * @param cachedContent already cached content
	 *
	 * @return detected type
	 * @throws IOException internal error
	 * @throws TestObjectTypeNotDetected if the type was not detected
	 */
	private static DetectedTestObjectType callDetectors(final URI uri, final Credentials credentials,
			final byte[] cachedContent) throws IOException, TestObjectTypeNotDetected {
		for (final TestObjectTypeDetector detector : InstanceHolder.detectors) {
			final DetectedTestObjectType t = detector.detect(uri, credentials, cachedContent);
			if (t != null) {
				if (!UriUtils.isFile(uri)) {
					InstanceHolder.cachedDetectedTestObjectTypes.put(uri, new Pair<>(
							new UriUtils.ModificationCheck(uri, credentials), t));
				}
				return t;
			}
		}
		throw new TestObjectTypeNotDetected();
	}

	/**
	 * Only used for caching web resources

	 * @param uri URL
	 * @param credentials optional credentials
	 *
	 * @return cached type or null
	 * @throws IOException internal error accessing the resource
	 * @throws TestObjectTypeNotDetected if the type was modified but cannot be detected anymore
	 */
	private static DetectedTestObjectType getTypeFromCacheWithModificationCheck(final URI uri, final Credentials credentials)
			throws IOException, TestObjectTypeNotDetected {
		// check cache, retrieve timestamp with head and compare timestamp
		final Pair<UriUtils.ModificationCheck, DetectedTestObjectType> detType = InstanceHolder.cachedDetectedTestObjectTypes
				.getIfPresent(uri);
		if (detType != null) {
			final byte[] modifiedBytes = detType.getLeft().getModified();
			if (modifiedBytes == null) {
				// not modified
				return detType.getRight();
			} else {
				return callDetectors(uri, credentials, modifiedBytes);
			}
		} else {
			return null;
		}
	}

	/**
	 * Detect a Test Object by analyzing its content.
	 *
	 * @param uri source the TestObjectTypeDetector can use for additional analysis
	 * @param credentials optional credentials for authenticating with the source
	 * @param cachedContent the cached content retrieved from the source
	 * @return detected Test Object Type or null if unknown
	 * @throws IOException internal error accessing the resource
	 * @throws TestObjectTypeNotDetected type could not be detected
	 */
	public static DetectedTestObjectType detect(final URI uri, final Credentials credentials, final byte[] cachedContent)
			throws IOException, TestObjectTypeNotDetected {
		final DetectedTestObjectType cached = getTypeFromCacheWithModificationCheck(uri, credentials);
		if (cached != null) {
			return cached;
		}
		if (cachedContent == null || cachedContent.length == 0) {
			throw new IOException("The cached response from the Test Object is empty");
		}
		return callDetectors(uri, credentials, cachedContent);
	}

	/**
	 * Detect a Test Object by analyzing its resource and content
	 *
	 * @param uri source the TestObjectTypeDetector can use for additional analysis
	 * @param credentials optional credentials for authenticating with the source
	 * @return detected Test Object Type or null if unknown
	 * @throws IOException internal error accessing the resource
	 * @throws TestObjectTypeNotDetected type could not be detected
	 */
	public static DetectedTestObjectType detect(final URI uri, final Credentials credentials)
			throws IOException, TestObjectTypeNotDetected {
		final DetectedTestObjectType cached = getTypeFromCacheWithModificationCheck(uri, credentials);
		if (cached != null) {
			return cached;
		}
		if (InstanceHolder.detectors.size() == 1) {
			final DetectedTestObjectType t = InstanceHolder.detectors.get(0).detect(uri, credentials);
			if (t != null) {
				if (!UriUtils.isFile(uri)) {
					InstanceHolder.cachedDetectedTestObjectTypes.put(uri, new Pair<>(
							new UriUtils.ModificationCheck(uri, credentials), t));
				}
				return t;
			}
		}
		// cache content
		return callDetectors(uri, credentials, UriUtils.toByteArray(uri, credentials));
	}

	/**
	 * Returns a {@link DetectedTestObjectType} if the resource exactly matches the Test Object Type
	 * or a sub type of the detected Test Object Type. Otherwise an exception
	 *
	 * @param testObjectTypeId ID of the Test Object Type
	 * @param uri resource URI
	 * @param credentials optional credentials for authenticating with the source
	 * @return detected Test Object Type
	 * @throws TestObjectTypeNotDetected type could not be detected
	 * @throws IncompatibleTestObjectType type does not match type or is no subtype
	 * @throws IOException internal error accessing the resource
	 * @throws ObjectWithIdNotFoundException Test Object Type wit ID not found
	 */
	public static DetectedTestObjectType expectIsInstanceOf(final EID testObjectTypeId, final URI uri,
			final Credentials credentials)
			throws TestObjectTypeNotDetected, IncompatibleTestObjectType, IOException, ObjectWithIdNotFoundException {

		final TestObjectTypeDto required = getSupportedTypes().get(testObjectTypeId);
		if (required == null) {
			throw new ObjectWithIdNotFoundException(testObjectTypeId.toString());
		}

		// Get from cache cache
		final DetectedTestObjectType cached = getTypeFromCacheWithModificationCheck(uri, credentials);
		if (cached != null) {
			if (cached.isInstanceOf(required)) {
				return cached;
			} else {
				throw new IncompatibleTestObjectType(required, cached);
			}
		}

		// Check single detectors
		DetectedTestObjectType expected;
		for (final TestObjectTypeDetector detector : InstanceHolder.detectors) {
			expected = detector.detectType(testObjectTypeId, uri, credentials);
			if (expected != null) {
				if (!UriUtils.isFile(uri)) {
					InstanceHolder.cachedDetectedTestObjectTypes.put(uri, new Pair<>(
							new UriUtils.ModificationCheck(uri, credentials), expected));
				}
				return expected;
			}
		}

		// Construct error message
		final DetectedTestObjectType actual = callDetectors(uri, credentials, UriUtils.toByteArray(uri, credentials));
		if (actual == null) {
			throw new TestObjectTypeNotDetected();
		}
		throw new IncompatibleTestObjectType(required, actual);

	}
}
