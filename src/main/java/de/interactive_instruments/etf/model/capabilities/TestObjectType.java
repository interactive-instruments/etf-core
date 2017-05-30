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

import de.interactive_instruments.etf.dal.dto.Dto;
import de.interactive_instruments.etf.dal.dto.capabilities.TestObjectTypeDto;
import de.interactive_instruments.etf.model.EID;
import de.interactive_instruments.etf.model.EidHolder;

/**
 * TODO remove DTO and use this type
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface TestObjectType extends EidHolder {

	TestObjectTypeDto getDto();

	@Override
	default EID getId() {
		return getDto().getId();
	}

	@Override
	default int compareTo(final Object o) {
		if (o instanceof TestObjectType) {
			return getDto().compareTo(((TestObjectType) o).getDto());
		}
		return getDto().compareTo(o);
	}
}
