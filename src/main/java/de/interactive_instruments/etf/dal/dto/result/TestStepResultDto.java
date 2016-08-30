/**
 * Copyright 2010-2016 interactive instruments GmbH
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
package de.interactive_instruments.etf.dal.dto.result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 */
public final class TestStepResultDto extends ResultModelItemDto implements AttachmentDtoHolder {

	private List<AttachmentDto> attachments;

	public TestStepResultDto() {}

	private TestStepResultDto(final TestStepResultDto other) {
		super(other);
		this.attachments = other.attachments;
	}

	public List<TestAssertionResultDto> getTestAssertionResults() {
		return (List<TestAssertionResultDto>) getChildren();
	}

	public void setTestAssertionResults(final List<TestAssertionResultDto> testAssertionResults) {
		setChildren(testAssertionResults);
	}

	public void addTestAssertionResult(final TestAssertionResultDto testAssertionResult) {
		addChild(testAssertionResult);
	}

	public List<AttachmentDto> getAttachments() {
		return attachments;
	}

	public void setAttachments(final List<AttachmentDto> attachments) {
		this.attachments = attachments;
	}

	public void addAttachment(final AttachmentDto attachment) {
		if (this.attachments == null) {
			attachments = new ArrayList<>();
		}
		attachments.add(attachment);
	}

	@Override public TestStepResultDto createCopy() {
		return new TestStepResultDto(this);
	}
}
