/**
 * Copyright 2017 European Union, interactive instruments GmbH
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

import java.util.ArrayList;
import java.util.List;

import de.interactive_instruments.etf.dal.dto.Arguments;
import de.interactive_instruments.etf.dal.dto.translation.TranslationArgumentCollectionDto;

public class TestAssertionResultDto extends ResultModelItemDto {

	// Optional arguments for parameterizable test assertions
	private Arguments arguments;

	private List<TranslationArgumentCollectionDto> messages;

	public TestAssertionResultDto() {}

	TestAssertionResultDto(final TestAssertionResultDto other) {
		super(other);
		this.arguments = other.arguments;
		this.messages = other.messages;
	}

	public Arguments getArguments() {
		return arguments;
	}

	public void setArguments(final Arguments argumentsDto) {
		this.arguments = argumentsDto;
	}

	public List<TranslationArgumentCollectionDto> getMessages() {
		return messages;
	}

	public void setMessages(final List<TranslationArgumentCollectionDto> messages) {
		this.messages = messages;
	}

	public void addMessage(final TranslationArgumentCollectionDto message) {
		if (this.messages == null) {
			this.messages = new ArrayList<>();
		}
		this.messages.add(message);
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("TestAssertionResultDto{");
		sb.append("arguments=").append(arguments);
		sb.append(", messages=").append(messages);
		sb.append('}');
		return sb.toString();
	}

	@Override
	public TestAssertionResultDto createCopy() {
		return new TestAssertionResultDto(this);
	}
}
