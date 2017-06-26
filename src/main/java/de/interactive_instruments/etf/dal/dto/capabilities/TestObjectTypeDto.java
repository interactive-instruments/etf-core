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
import java.util.Arrays;
import java.util.List;

import de.interactive_instruments.etf.dal.dto.MetaDataItemDto;
import de.interactive_instruments.etf.model.ExpressionType;
import de.interactive_instruments.etf.model.capabilities.TestObjectType;

/**
 * A Test Object Type describes a {@link TestObjectDto} and may possess
 * information how the type can be detected.
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class TestObjectTypeDto extends MetaDataItemDto<TestObjectTypeDto> implements TestObjectType {

	private List<TestObjectTypeDto> subTypes;

	// Optional list of supported filenameExtensions
	private List<String> filenameExtensions;

	// Optional list of supported mimetypes
	private List<String> mimeTypes;

	// Optional detection expression
	private String detectionExpression;

	// Optional detection expression type
	private ExpressionType detectionExpressionType;

	// Optional expression for extracting the Test Object label
	private String labelExpression;

	// Optional expression for extracting the Test Object label type
	private ExpressionType labelExpressionType;

	// Optional expression for extracting the Test Object description
	private String descriptionExpression;

	// Optional expression for extracting the Test Object description type
	private ExpressionType descriptionExpressionType;

	// Optional naming convention, which is used to
	// check if the label of a Test Object matches this regular expression.
	// This might be useful for labeling test data deliveries according
	// to a prescribed scheme.
	private String namingConvention;

	public TestObjectTypeDto() {}

	private TestObjectTypeDto(final TestObjectTypeDto other) {
		super(other);
		this.subTypes = other.subTypes;
		this.filenameExtensions = other.filenameExtensions;
		this.mimeTypes = other.mimeTypes;
		this.detectionExpression = other.detectionExpression;
		this.detectionExpressionType = other.detectionExpressionType;
		this.labelExpression = other.labelExpression;
		this.labelExpressionType = other.labelExpressionType;
		this.descriptionExpression = other.descriptionExpression;
		this.descriptionExpressionType = other.descriptionExpressionType;
		this.namingConvention = other.namingConvention;
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

	public void setFilenameExtensions(final String... filenameExtensions) {
		this.filenameExtensions = Arrays.asList(filenameExtensions);
	}

	public List<String> getMimeTypes() {
		return mimeTypes;
	}

	public void setMimeTypes(final List<String> mimeTypes) {
		this.mimeTypes = mimeTypes;
	}

	public void setDetectionExpression(final String detectionExpression, final ExpressionType type) {
		this.detectionExpression = detectionExpression;
		this.descriptionExpressionType = type;
	}

	public String getDetectionExpression() {
		return detectionExpression;
	}

	public ExpressionType getDetectionExpressionType() {
		return detectionExpressionType;
	}

	public void setLabelExpression(final String labelExpression, final ExpressionType type) {
		this.labelExpression = labelExpression;
		this.labelExpressionType = type;

	}

	public String getLabelExpression() {
		return labelExpression;
	}

	public ExpressionType getLabelExpressionType() {
		return labelExpressionType;
	}

	public void setDescriptionExpression(final String descriptionExpression, final ExpressionType type) {
		this.descriptionExpression = descriptionExpression;
		this.descriptionExpressionType = type;
	}

	public String getDescriptionExpression() {
		return descriptionExpression;
	}

	public ExpressionType getDescriptionExpressionType() {
		return descriptionExpressionType;
	}

	@Override
	public TestObjectTypeDto createCopy() {
		return new TestObjectTypeDto(this);
	}
}
