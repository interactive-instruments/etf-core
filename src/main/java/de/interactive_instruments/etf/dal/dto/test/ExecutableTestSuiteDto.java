
/*
 * Copyright ${year} interactive instruments GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.interactive_instruments.etf.dal.dto.test;

import de.interactive_instruments.etf.dal.dto.RepositoryItemDto;
import de.interactive_instruments.etf.dal.dto.capabilities.ComponentDto;
import de.interactive_instruments.etf.dal.dto.capabilities.TestObjectTypeDto;
import de.interactive_instruments.etf.model.item.*;

import java.util.List;

public class ExecutableTestSuiteDto extends RepositoryItemDto implements ParameterObjHolder {

	private ComponentDto testDriver;
	private TranslationTemplateDto translationTemplate;
	private List<TestObjectTypeDto> supportedTestObjectTypes;
	private List<DependencyDto> dependencies;
	private List<TestObjectTypeDto> consumableResultTestObjectTypes;
	private List<TestCaseDto> parameterizedTestCases;
	private List<TestModuleDto> testModules;
	private Parameters parameters;

	@Override public ParameterHolder getParameters() {
		return parameters;
	}
}
