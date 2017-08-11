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
package de.interactive_instruments.etf.detector;

import java.util.List;

import de.interactive_instruments.etf.dal.dto.capabilities.TestObjectDto;
import de.interactive_instruments.etf.dal.dto.capabilities.TestObjectTypeDto;
import de.interactive_instruments.etf.model.EID;
import de.interactive_instruments.etf.model.ExpressionType;
import de.interactive_instruments.etf.model.capabilities.MutableCachedResource;
import de.interactive_instruments.etf.model.capabilities.Resource;
import de.interactive_instruments.etf.model.capabilities.TestObjectType;

/**
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
class CachedDetectedTestObjectTypeDecorator implements DetectedTestObjectType {

	private final DetectedTestObjectType decorated;
	private final MutableCachedResource cachedResource;

	CachedDetectedTestObjectTypeDecorator(final DetectedTestObjectType decorated,
			final MutableCachedResource cachedResource) {
		this.decorated = decorated;
		this.cachedResource = cachedResource;
	}

	@Override
	public MutableCachedResource getNormalizedResource() {
		return cachedResource;
	}

	@Override
	public void enrichAndNormalize(final TestObjectDto testObject) {
		decorated.enrichAndNormalize(testObject);
	}

	@Override
	public boolean isInstanceOf(final TestObjectType testObjectType) {
		return decorated.isInstanceOf(testObjectType);
	}

	@Override
	public TestObjectTypeDto toTestObjectTypeDto() {
		return decorated.toTestObjectTypeDto();
	}

	@Override
	public String getLabel() {
		return decorated.getLabel();
	}

	@Override
	public String getDescription() {
		return decorated.getDescription();
	}

	@Override
	public TestObjectType getParent() {
		return decorated.getParent();
	}

	@Override
	public List<TestObjectTypeDto> getSubTypes() {
		return decorated.getSubTypes();
	}

	@Override
	public List<String> getFilenameExtensions() {
		return decorated.getFilenameExtensions();
	}

	@Override
	public List<String> getMimeTypes() {
		return decorated.getMimeTypes();
	}

	@Override
	public String getDetectionExpression() {
		return decorated.getDetectionExpression();
	}

	@Override
	public ExpressionType getDetectionExpressionType() {
		return decorated.getDetectionExpressionType();
	}

	@Override
	public String getLabelExpression() {
		return decorated.getLabelExpression();
	}

	@Override
	public ExpressionType getLabelExpressionType() {
		return decorated.getLabelExpressionType();
	}

	@Override
	public String getDescriptionExpression() {
		return decorated.getDescriptionExpression();
	}

	@Override
	public ExpressionType getDescriptionExpressionType() {
		return decorated.getDescriptionExpressionType();
	}

	@Override
	public EID getId() {
		return decorated.getId();
	}
}
