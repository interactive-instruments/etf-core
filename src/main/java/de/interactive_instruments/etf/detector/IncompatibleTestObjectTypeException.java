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

import java.util.Collection;
import java.util.Iterator;

import de.interactive_instruments.etf.model.capabilities.TestObjectType;

/**
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class IncompatibleTestObjectTypeException extends Exception {

    private final DetectedTestObjectType detectedTestObjectType;

    private static String labelFor(final DetectedTestObjectType detectedTestObjectType) {
        final StringBuilder sb = new StringBuilder();
        sb.append("'");
        sb.append(detectedTestObjectType.getLabel());
        sb.append("'");
        return sb.toString();
    }

    private static String labelFor(final Collection<? extends TestObjectType> expected) {
        final StringBuilder builder = new StringBuilder();
        for (final Iterator<? extends TestObjectType> it = expected.iterator();;) {
            builder.append('\'');
            builder.append(it.next().getLabel());
            builder.append('\'');
            if (it.hasNext()) {
                builder.append(", ");
            } else {
                break;
            }
        }
        return builder.toString();
    }

    private static String errorText(final Collection<? extends TestObjectType> expected,
            final DetectedTestObjectType detectedTestObjectType) {
        if (expected.size() > 1) {
            return "Expected was a test object of one of the types " + labelFor(expected) +
                    " but detected another type '" + labelFor(detectedTestObjectType)
                    + "' which is also not a subtype of it";
        } else {
            return "Expected a Test Object of type '" +
                    expected.iterator().next().getLabel() + "' but detected another type '" +
                    labelFor(detectedTestObjectType)
                    + "' which is also not a subtype of it";
        }
    }

    public IncompatibleTestObjectTypeException(final TestObjectType expected,
            final DetectedTestObjectType detectedTestObjectType) {
        super("Expected a Test Object of type '" +
                expected.getLabel() + "' but detected another type '" + labelFor(detectedTestObjectType)
                + "' which is also not a subtype of it");
        this.detectedTestObjectType = detectedTestObjectType;
    }

    public IncompatibleTestObjectTypeException(final Collection<? extends TestObjectType> expected,
            final DetectedTestObjectType detectedTestObjectType) {
        super(errorText(expected, detectedTestObjectType));
        this.detectedTestObjectType = detectedTestObjectType;
    }

    public TestObjectType getDetectedTestObjectType() {
        return detectedTestObjectType.toTestObjectTypeDto();
    }
}
