/**
 * Copyright 2017-2019 European Union, interactive instruments GmbH
 * Licensed under the EUPL, Version 1.2 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * This work was supported by the EU Interoperability Solutions for
 * European Public Administrations Programme (http://ec.europa.eu/isa)
 * through Action 1.17: A Reusable INSPIRE Reference Platform (ARE3NA).
 */
package de.interactive_instruments.etf.detector;

import java.util.Set;

import de.interactive_instruments.Initializable;
import de.interactive_instruments.Releasable;
import de.interactive_instruments.etf.dal.dto.capabilities.TestObjectTypeDto;
import de.interactive_instruments.etf.model.EID;
import de.interactive_instruments.etf.model.EidMap;
import de.interactive_instruments.etf.model.capabilities.Resource;

/**
 * This interface is implemented by detectors that detect the Test Object Types from a resource.
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface TestObjectTypeDetector extends Initializable, Releasable, Comparable<TestObjectTypeDetector> {

    /**
     * Verify the resource is one of the passed Test Object Types and return the detected type. Otherwise return null.
     *
     * Note: the {@link TestObjectTypeDetectorManager} may define a timeout in future versions. A TestObjectTypeDetector should analyse the content within 5 seconds.
     *
     * @param resource
     *            resource the TestObjectTypeDetector must use for retrieving and analyzing content
     * @param expectedTypes
     *            Test Object Type ids to check
     *
     * @return detected Test Object Type or null if unknown or other type
     */
    DetectedTestObjectType detectType(final Resource resource, final Set<EID> expectedTypes);

    /**
     * Detect the Test Object Type from a resource
     *
     * Note: the {@link TestObjectTypeDetectorManager} may define a timeout in future versions. A TestObjectTypeDetector should analyse the content within 15 seconds.
     *
     * @param resource
     *            resource the TestObjectTypeDetector must use for retrieving and analyzing content
     *
     * @return detected Test Object Type or null if unknown or other type
     */
    default DetectedTestObjectType detectType(final Resource resource) {
        return detectType(resource, null);
    }

    /**
     * If multiple TestObjectTypeDetectors are registered, detectors with a higher priority (lower value) are used first for detection. If two TestObjectTypeDetectors are attempting to register Test Object Types with the same EID (means it returns the types via {@link #supportedTypes()}), the TestObjectTypeDetector with the higher priority will win.
     *
     * @return priority
     */
    default int getPriority() {
        return 100;
    }

    /**
     * Get all types the TestObjectType detector can detect. This will register all supported types in the TestObjectTypeDetectorManager.
     *
     * @return supported test object types
     */
    EidMap<TestObjectTypeDto> supportedTypes();

    @Override
    default int compareTo(final TestObjectTypeDetector o) {
        return Integer.compare(getPriority(), o.getPriority());
    }
}
