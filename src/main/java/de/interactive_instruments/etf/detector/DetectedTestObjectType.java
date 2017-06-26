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

import de.interactive_instruments.etf.model.capabilities.TestObjectType;

/**
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface DetectedTestObjectType extends TestObjectType {
	/**
	 * The label from the detected Test Object
	 *
	 * @return label or null
	 */
	String getExtractedLabel();

	/**
	 * The description from the detected Test Object
	 *
	 * @return description or null
	 */
	String getExtractedDescription();

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
}
