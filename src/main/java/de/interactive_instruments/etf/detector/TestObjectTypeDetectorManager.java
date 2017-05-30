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

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.interactive_instruments.Credentials;
import de.interactive_instruments.etf.dal.dto.capabilities.TestObjectTypeDto;
import de.interactive_instruments.etf.model.DefaultEidMap;
import de.interactive_instruments.etf.model.EidMap;
import de.interactive_instruments.etf.model.capabilities.TestObjectType;

/**
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class TestObjectTypeDetectorManager {

	private static final class InstanceHolder {
		static final List<TestObjectTypeDetector> loadDetectors() {
			final ServiceLoader<TestObjectTypeDetector> loadedDetectors = ServiceLoader.load(TestObjectTypeDetector.class);

			final ArrayList<TestObjectTypeDetector> detectors = new ArrayList<>();

			final Logger logger = LoggerFactory.getLogger(TestObjectTypeDetectorManager.class);

			for (final TestObjectTypeDetector loadedDetector : loadedDetectors) {
				logger.debug("Adding Test Object TypeDetector " + loadedDetector.getClass().getName());
				detectors.add(loadedDetector);
			}
			if (detectors.isEmpty()) {
				throw new RuntimeException("No Test Object TypeDetector available");
			}
			Collections.sort(detectors);
			return Collections.unmodifiableList(detectors);
		}

		static final List<TestObjectTypeDetector> DETECTORS = loadDetectors();

		private static EidMap<TestObjectTypeDto> getTypes() {
			final EidMap<TestObjectTypeDto> types = new DefaultEidMap<>();
			for (final TestObjectTypeDetector detector : DETECTORS) {
				types.putAll(detector.supportedTypes());
			}
			return types;
		}

		static final EidMap<TestObjectTypeDto> TYPES = getTypes();
	}

	public static EidMap<TestObjectTypeDto> getTypes(final String... eids) {
		final EidMap<TestObjectTypeDto> map = new DefaultEidMap<>();
		for (final String eid : eids) {
			final TestObjectTypeDto t = InstanceHolder.TYPES.get(eid);
			if (t == null) {
				throw new IllegalArgumentException("Test object Type with " + eid + " not found. "
						+ "Probably the correct Test Object Type Detector is not installed.");
			}
			map.put(t.getId(), t);
		}
		return map;
	}

	public static EidMap<TestObjectTypeDto> getSupportedTypes() {
		return InstanceHolder.TYPES;
	}

	public static TestObjectType detect(final byte[] bytes) {
		for (final TestObjectTypeDetector detector : InstanceHolder.DETECTORS) {
			final TestObjectType t = detector.detect(bytes);
			if (t != null) {
				return t;
			}
		}
		return null;
	}

	public static TestObjectType detect(final URI uri, final Credentials credentials) {
		for (final TestObjectTypeDetector detector : InstanceHolder.DETECTORS) {
			final TestObjectType t = detector.detect(uri, credentials);
			if (t != null) {
				return t;
			}
		}
		return null;
	}
}
