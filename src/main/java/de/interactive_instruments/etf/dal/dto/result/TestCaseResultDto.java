/**
 * Copyright 2017-2018 European Union, interactive instruments GmbH
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
package de.interactive_instruments.etf.dal.dto.result;

import java.util.List;

import de.interactive_instruments.etf.dal.dto.Arguments;

public class TestCaseResultDto extends ResultModelItemDto {

	// Optional arguments for parameterizable test cases
	private Arguments arguments;

	public TestCaseResultDto() {}

	private TestCaseResultDto(final TestCaseResultDto other) {
		super(other);
		this.arguments = other.arguments;
	}

	public List<TestStepResultDto> getTestStepResults() {
		return (List<TestStepResultDto>) getChildren();
	}

	public void setTestStepResults(final List<TestStepResultDto> testStepResults) {
		setChildren(testStepResults);
	}

	public void addTestStepResult(final TestStepResultDto testStepResult) {
		addChild(testStepResult);
	}

	public Arguments getArguments() {
		return arguments;
	}

	public void setArguments(final Arguments argumentsDto) {
		this.arguments = argumentsDto;
	}

	@Override
	public TestCaseResultDto createCopy() {
		return new TestCaseResultDto(this);
	}
}
