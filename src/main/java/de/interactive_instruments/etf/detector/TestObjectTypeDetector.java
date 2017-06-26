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

import de.interactive_instruments.Credentials;
import de.interactive_instruments.Initializable;
import de.interactive_instruments.Releasable;
import de.interactive_instruments.etf.dal.dto.capabilities.TestObjectTypeDto;
import de.interactive_instruments.etf.model.EID;
import de.interactive_instruments.etf.model.EidMap;

/**
 * A detector for Test Object Types.
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface TestObjectTypeDetector extends Initializable, Releasable, Comparable<TestObjectTypeDetector> {

	/**
	 * Check directly from the source. Used if only one TestObjectTypeDetector is
	 * registered and the content does not need to be cached.
	 *
	 * Note: the {@link TestObjectTypeDetectorManager} may define a timeout in future versions.
	 * A TestObjectTypeDetector should analyse the content within 5 seconds.
	 *
	 * @param source source the TestObjectTypeDetector must use for retrieving and analyzing content
	 * @param credentials optional credentials for authenticating with the source
	 * @return detected Test Object Type or null if unknown
	 */
	DetectedTestObjectType detect(final URI source, final Credentials credentials);

	/**
	 * Called if multiple TestObjectTypeDetectors are registered.
	 * The implementing TestObjectTypeDetector shall use the cachedContent
	 *
	 * Note: the {@link TestObjectTypeDetectorManager} may define a timeout in future versions.
	 * A TestObjectTypeDetector should analyse the content within 5 seconds.
	 *
	 * @param source source the TestObjectTypeDetector can use for additional analysis
	 * @param credentials optional credentials for authenticating with the source
	 * @param cachedContent the cached content retrieved from the source
	 * @return detected Test Object Type or null if unknown
	 */
	DetectedTestObjectType detect(final URI source, final Credentials credentials, final byte[] cachedContent);

	/**
	 * Verify the resource is a specific Test Object Type and return it. Otherwise return null.
	 *
	 * @param testObjectTypeId Test Object Type id to check
	 * @param source source the TestObjectTypeDetector can use for additional analysis
	 * @param credentials optional credentials for authenticating with the source
	 * @return detected Test Object Type or null if unknown or other type
	 */
	DetectedTestObjectType detectType(final EID testObjectTypeId, final URI source, final Credentials credentials);

	/**
	 * If multiple TestObjectTypeDetectors are registered, detectors with a higher priority are
	 * used first for detection. If two TestObjectTypeDetectors are attempting to register
	 * Test Object Types with the same EID (means it returns the types via {@link #supportedTypes()}),
	 * the TestObjectTypeDetector with the higher priority will win.
	 *
	 * @return priority
	 */
	default int getPriority() {
		return 100;
	}

	/**
	 * Get all types the TestObjectType detector can detect. This will register all supported
	 * types in the TestObjectTypeDetectorManager.
	 *
	 * @return supported test object types
	 */
	EidMap<TestObjectTypeDto> supportedTypes();

	@Override
	default int compareTo(final TestObjectTypeDetector o) {
		return Integer.compare(getPriority(), o.getPriority());
	}
}
