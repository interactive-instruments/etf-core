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

package de.interactive_instruments.etf.dal.dto.result;

import de.interactive_instruments.etf.model.item.*;

public final class TestAssertionResultDto extends ResultModelItemDtoDto implements ArgumentObjHolder, TranslatableMessageObjHolder {

	private Arguments arguments;

	private TranslatableMessages messages;

	@Override public ArgumentHolder getArguments() {
		return arguments;
	}

	@Override public TranslatableMessageHolder getTranslatableMessages() {
		return messages;
	}
}

