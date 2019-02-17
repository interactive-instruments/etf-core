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
package de.interactive_instruments.etf.model.capabilities;

import java.util.Collection;
import java.util.List;

import de.interactive_instruments.etf.dal.dto.capabilities.TestObjectDto;
import de.interactive_instruments.etf.dal.dto.capabilities.TestObjectTypeDto;
import de.interactive_instruments.etf.model.EidHolder;
import de.interactive_instruments.etf.model.EidHolderWithParent;
import de.interactive_instruments.etf.model.ExpressionType;

/**
 * A Test Object Type describes a {@link TestObjectDto} and may possess information how the type can be detected.
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface TestObjectType extends EidHolder, EidHolderWithParent<TestObjectType> {

    String getLabel();

    String getDescription();

    TestObjectType getParent();

    List<TestObjectTypeDto> getSubTypes();

    List<String> getFilenameExtensions();

    List<String> getMimeTypes();

    String getDetectionExpression();

    ExpressionType getDetectionExpressionType();

    String getLabelExpression();

    ExpressionType getLabelExpressionType();

    String getDescriptionExpression();

    ExpressionType getDescriptionExpressionType();

    /**
     * Check if a Test Object Type equals the passed Test Object Type or is a subtype of it
     *
     * @param testObjectType
     *            Test Object Type
     * @return true if Test Object Type equals or is a subtype of it
     */
    default boolean isInstanceOf(final TestObjectType testObjectType) {
        if (testObjectType.getId().equals(getId())) {
            return true;
        }
        for (TestObjectType parent = getParent(); parent != null; parent = parent.getParent()) {
            if (testObjectType.getId().equals(parent.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the provided Test Object Type collection contains the Test Object Types or it is a subtype of it.
     *
     * @param testObjectTypes
     *            list of Test Object Types
     * @return true if list contains the Test Object Type or the Test Object Type is a subtype of one of the types, false otherwise
     */
    default boolean isInstanceOf(final Collection<? extends TestObjectType> testObjectTypes) {
        for (final TestObjectType testObjectType : testObjectTypes) {
            if (testObjectType.isInstanceOf(this)) {
                return true;
            }
        }
        return false;

    }
}
