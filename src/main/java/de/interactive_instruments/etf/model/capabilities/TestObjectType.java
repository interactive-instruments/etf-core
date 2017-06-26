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
package de.interactive_instruments.etf.model.capabilities;

import java.util.List;

import de.interactive_instruments.etf.dal.dto.capabilities.TestObjectDto;
import de.interactive_instruments.etf.dal.dto.capabilities.TestObjectTypeDto;
import de.interactive_instruments.etf.model.EidHolder;
import de.interactive_instruments.etf.model.ExpressionType;

/**
 * A Test Object Type describes a {@link TestObjectDto} and may possess
 * information how the type can be detected.
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface TestObjectType extends EidHolder {

	String getLabel();

	String getDescription();

	TestObjectType getParent();

	List<TestObjectTypeDto> getSubTypes();

	String getNamingConvention();

	List<String> getFilenameExtensions();

	List<String> getMimeTypes();

	String getDetectionExpression();

	ExpressionType getDetectionExpressionType();

	String getLabelExpression();

	ExpressionType getLabelExpressionType();

	String getDescriptionExpression();

	ExpressionType getDescriptionExpressionType();
}
