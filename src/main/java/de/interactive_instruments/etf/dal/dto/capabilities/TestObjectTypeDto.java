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
package de.interactive_instruments.etf.dal.dto.capabilities;

import java.util.ArrayList;
import java.util.List;

import de.interactive_instruments.etf.dal.dto.MetaDataItemDto;

public class TestObjectTypeDto extends MetaDataItemDto {

	private List<TestObjectTypeDto> subTypes;

	// Optional list of supported filenameExtensions
	private List<String> filenameExtensions;

	// Optional list of supported mimetypes
	private List<String> mimeTypes;

	// Optional detection expression (as Xpath)
	private String detectionExpression;

	// Optional expression for extracting the Test Object label (as Xpath)
	private String labelExpression;

	// Optional expression for extracting the Test Object description (as Xpath)
	private String descriptionExpression;

	// Optional naming convention for the associated Test Objects as regex
	private String namingConvention;

	public TestObjectTypeDto() {}

	private TestObjectTypeDto(final TestObjectTypeDto other) {
		super(other);
		this.subTypes = other.subTypes;
		this.namingConvention = other.namingConvention;
		this.filenameExtensions = other.filenameExtensions;
		this.mimeTypes = other.mimeTypes;
		this.detectionExpression = other.detectionExpression;
		this.labelExpression = other.labelExpression;
		this.descriptionExpression = other.descriptionExpression;
	}

	public List<TestObjectTypeDto> getSubTypes() {
		return subTypes;
	}

	public void setSubTypes(final List<TestObjectTypeDto> subTypes) {
		this.subTypes = subTypes;
	}

	public void addSubType(final TestObjectTypeDto subType) {
		if (this.subTypes == null) {
			subTypes = new ArrayList<>();
		}
		subTypes.add(subType);
	}

	public String getNamingConvention() {
		return namingConvention;
	}

	public void setNamingConvention(final String namingConvention) {
		this.namingConvention = namingConvention;
	}

	public List<String> getFilenameExtensions() {
		return filenameExtensions;
	}

	public void setFilenameExtensions(final List<String> filenameExtensions) {
		this.filenameExtensions = filenameExtensions;
	}

	public List<String> getMimeTypes() {
		return mimeTypes;
	}

	public void setMimeTypes(final List<String> mimeTypes) {
		this.mimeTypes = mimeTypes;
	}

	public String getDetectionExpression() {
		return detectionExpression;
	}

	public void setDetectionExpression(final String detectionExpression) {
		this.detectionExpression = detectionExpression;
	}

	public String getLabelExpression() {
		return labelExpression;
	}

	public void setLabelExpression(final String labelExpression) {
		this.labelExpression = labelExpression;
	}

	public String getDescriptionExpression() {
		return descriptionExpression;
	}

	public void setDescriptionExpression(final String descriptionExpression) {
		this.descriptionExpression = descriptionExpression;
	}

	@Override
	public TestObjectTypeDto createCopy() {
		return new TestObjectTypeDto(this);
	}
}
