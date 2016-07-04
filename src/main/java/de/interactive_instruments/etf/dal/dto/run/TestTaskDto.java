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

package de.interactive_instruments.etf.dal.dto.run;

import de.interactive_instruments.etf.dal.dto.ModelItemDto;
import de.interactive_instruments.etf.dal.dto.capabilities.ResultStyleDto;
import de.interactive_instruments.etf.dal.dto.capabilities.TestObjectDto;
import de.interactive_instruments.etf.dal.dto.result.TestTaskResultDto;
import de.interactive_instruments.etf.dal.dto.test.ExecutableTestSuiteDto;
import de.interactive_instruments.etf.model.item.ArgumentHolder;
import de.interactive_instruments.etf.model.item.ArgumentObjHolder;
import de.interactive_instruments.etf.model.item.Arguments;

/**
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 */
public class TestTaskDto extends ModelItemDto implements ArgumentObjHolder {

	private ExecutableTestSuiteDto executableTestSuiteDto;
	private TestObjectDto testObjectDto;
	private ResultStyleDto resultStyleDto;
	private TestTaskResultDto testTaskResultDto;
	private Arguments arguments;

	@Override public ArgumentHolder getArguments() {
		return arguments;
	}
}
