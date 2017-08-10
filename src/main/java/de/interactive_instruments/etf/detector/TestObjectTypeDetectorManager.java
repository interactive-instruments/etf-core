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
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.interactive_instruments.UriUtils;
import de.interactive_instruments.container.Pair;
import de.interactive_instruments.etf.dal.dto.capabilities.TestObjectTypeDto;
import de.interactive_instruments.etf.model.DefaultEidMap;
import de.interactive_instruments.etf.model.EID;
import de.interactive_instruments.etf.model.EidMap;
import de.interactive_instruments.etf.model.capabilities.MutableCachedResource;
import de.interactive_instruments.etf.model.capabilities.Resource;
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
		private final static Cache<URI, Pair<MutableCachedResource, DetectedTestObjectType>> cachedDetectedTestObjectTypes = Caffeine
				.newBuilder().maximumSize(30).expireAfterWrite(90, TimeUnit.MINUTES).build();

		private static DetectedTestObjectType addToCache(final DetectedTestObjectType testObjectType) throws IOException {
			// only remote resources are cached
			if (!UriUtils.isFile(testObjectType.getNormalizedResource().getUri())) {
				final MutableCachedResource resource;
				if (testObjectType.getNormalizedResource() instanceof MutableCachedResource) {
					resource = (MutableCachedResource) testObjectType.getNormalizedResource();
				} else {
					resource = new MutableCachedResource(testObjectType.getNormalizedResource());
				}
				// ensure modification check is created
				resource.getModificationCheck();
				cachedDetectedTestObjectTypes.put(resource.getUri(), new Pair<>(resource, testObjectType));
			}
			return testObjectType;
		}
	}

	/**
	 * Call detectors and cache the results if the resource is a remote resource
	 *
	 * @param cachedResource cached resource the Test Object Type Detector must use
	 * @return detected type
	 * @throws IOException internal error
	 * @throws TestObjectTypeNotDetected if the type was not detected
	 */
	private static DetectedTestObjectType callDetectors(final MutableCachedResource cachedResource,
			final Set<EID> expectedTypes)
			throws IOException, TestObjectTypeNotDetected {
		for (final TestObjectTypeDetector detector : InstanceHolder.detectors) {
			final DetectedTestObjectType type;
			if (expectedTypes != null) {
				type = detector.detectType(cachedResource, expectedTypes);
			} else {
				type = detector.detectType(cachedResource);
			}
			if (type != null) {
				return InstanceHolder.addToCache(type);
			}
		}
		return null;
	}

	/**
	 * Call detectors and cache the results if the resource is a remote resource
	 *
	 * @param cachedResource cached resource the Test Object Type Detector must use
	 * @return detected type
	 * @throws IOException internal error
	 * @throws TestObjectTypeNotDetected if the type was not detected
	 */
	private static DetectedTestObjectType callDetectorsExcludingTypes(
			final MutableCachedResource cachedResource, final Set<EID> excludingTypes)
			throws IOException, TestObjectTypeNotDetected {
		for (final TestObjectTypeDetector detector : InstanceHolder.detectors) {
			final Set<EID> expectedTypes = new HashSet<>(detector.supportedTypes().keySet());
			expectedTypes.removeAll(excludingTypes);
			// Wrap the mutable
			final DetectedTestObjectType type = detector.detectType(cachedResource, expectedTypes);
			if (type != null) {
				return InstanceHolder.addToCache(type);
			}
		}
		return null;
	}

	/**
	 * Get the cached type for remote resources
	 *
	 * @param resource resource the Test Object Type Detector must use
	 * @return cached type or null
	 * @throws IOException internal error accessing the resource
	 * @throws TestObjectTypeNotDetected if the type was modified but cannot be detected anymore
	 */
	private static DetectedTestObjectType getTypeFromCacheWithModificationCheck(
			final MutableCachedResource resource, final Set<EID> expectedTypes)
			throws IOException, TestObjectTypeNotDetected {
		// check cache, retrieve timestamp with head and compare timestamp
		final Pair<MutableCachedResource, DetectedTestObjectType> resAndtype = InstanceHolder.cachedDetectedTestObjectTypes
				.getIfPresent(resource.getUri());
		if (resAndtype != null) {
			if (resAndtype.getLeft().recacheIfModified()) {
				// check changed resource
				return callDetectors(resAndtype.getLeft(), expectedTypes);
			} else {
				return resAndtype.getRight();
			}
		} else {
			return null;
		}
	}

	private static MutableCachedResource toCachedResource(final Resource resource) {
		if (resource instanceof MutableCachedResource) {
			return (MutableCachedResource) resource;
		}
		return new MutableCachedResource(resource);
	}

	/**
	 * Get Test Object Types by EID (as String) from all registered Test Object Type Detectors.
	 *
	 * @param eids EIDs as String
	 * @return Test Object Type map
	 */
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

	/**
	 * Get Test Object Types by EID (as String) from all registered Test Object Type Detectors.
	 *
	 * @param eids EIDs as String
	 * @return Test Object Type map
	 */
	public static EidMap<TestObjectTypeDto> getTypes(final Collection<EID> eids) {
		final EidMap<TestObjectTypeDto> map = new DefaultEidMap<>();
		for (final EID eid : eids) {
			final TestObjectTypeDto t = InstanceHolder.types.get(eid);
			if (t == null) {
				throw new IllegalArgumentException("Test object Type with " + eid + " not found. "
						+ "Probably the correct Test Object Type Detector is not installed.");
			}
			map.put(t.getId(), t);
		}
		return map;
	}

	/**
	 * Get all Test Object Types the registered Test Object Type Detectors can detect.
	 *
	 * @return all supported test object types
	 */
	public static EidMap<TestObjectTypeDto> getSupportedTypes() {
		return InstanceHolder.types;
	}

	/**
	 * Detect a Test Object by analyzing its content.
	 *
	 * @param resource resource the TestObjectTypeDetector must use for retrieving and analyzing content
	 * @return detected Test Object Type or null if unknown
	 * @throws IOException internal error accessing the resource
	 * @throws TestObjectTypeNotDetected type could not be detected
	 */
	public static DetectedTestObjectType detect(final Resource resource) throws IOException, TestObjectTypeNotDetected {
		final MutableCachedResource cachedResource = toCachedResource(resource);
		final DetectedTestObjectType cached = getTypeFromCacheWithModificationCheck(cachedResource, null);
		if (cached != null) {
			return cached;
		}
		if (!UriUtils.isFile(resource.getUri())
				&& (cachedResource.getBytes() == null || cachedResource.getContentLength() == 0)) {
			throw new IOException("The cached response from the Test Object is empty");
		}
		final DetectedTestObjectType detectedType = callDetectors(cachedResource, null);
		if (detectedType != null) {
			return detectedType;
		} else {
			throw new TestObjectTypeNotDetected();
		}
	}

	/**
	 * Returns a {@link DetectedTestObjectType} if the resource exactly matches the Test Object Type
	 * or a sub type of the detected Test Object Type. Otherwise an exception
	 *
	 * @param resource resource the TestObjectTypeDetector must use for retrieving and analyzing content
	 * @param expectedTypes Test Object Type ids to check
	 *
	 * @return detected Test Object Type
	 *
	 * @throws TestObjectTypeNotDetected type could not be detected
	 * @throws IncompatibleTestObjectTypeException type does not match type or is no subtype
	 * @throws IOException internal error accessing the resource
	 * @throws ObjectWithIdNotFoundException Test Object Type wit ID not found
	 */
	public static DetectedTestObjectType detect(final Resource resource, final Set<EID> expectedTypes)
			throws TestObjectTypeNotDetected, IncompatibleTestObjectTypeException, IOException, ObjectWithIdNotFoundException {
		if (expectedTypes == null || expectedTypes.isEmpty()) {
			return detect(resource);
		}

		for (final EID expectedTypeId : expectedTypes) {
			if (!getSupportedTypes().containsKey(expectedTypeId)) {
				throw new ObjectWithIdNotFoundException(expectedTypeId.toString());
			}
		}
		// Try to find the type in the cache
		final MutableCachedResource cachedResource = toCachedResource(resource);
		final DetectedTestObjectType cached = getTypeFromCacheWithModificationCheck(cachedResource, expectedTypes);
		if (cached != null) {
			return cached;
		} else {
			// Detect non-cached type
			final DetectedTestObjectType detectedType = callDetectors(cachedResource, expectedTypes);
			if (detectedType != null) {
				return detectedType;
			}

			// none of the expected types could be detected, detect the type of the resource with all non-checked types
			// and check if the found type is a subtype of one of the expected types
			final DetectedTestObjectType detectedSubType = callDetectorsExcludingTypes(cachedResource, expectedTypes);
			if (detectedSubType == null) {
				throw new TestObjectTypeNotDetected();
			}
			final Collection<TestObjectTypeDto> expectedResTypes = getTypes(expectedTypes).values();
			for (final TestObjectTypeDto expectedType : expectedResTypes) {
				if (detectedSubType.isInstanceOf(expectedType)) {
					InstanceHolder.addToCache(detectedSubType);
					return detectedSubType;
				}
			}
			throw new IncompatibleTestObjectTypeException(expectedResTypes, detectedSubType);
		}
	}
}
