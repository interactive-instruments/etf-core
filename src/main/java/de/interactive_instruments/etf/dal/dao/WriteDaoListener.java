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
package de.interactive_instruments.etf.dal.dao;

import de.interactive_instruments.etf.dal.dto.Dto;
import de.interactive_instruments.etf.model.EID;

/**
 * Event Listener for write operations
 *
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 */
public interface WriteDaoListener {

	enum EventType {
		ADD, UPDATE, DELETE
	}

	/**
	 * Informs the listener about a write operation that has been performed with
	 * several IDs.
	 *
	 * @param event {@link EventType}
	 * @param ids {@link EID}
	 */
	void writeOperationPerformed(final EventType event, final EID... ids);

	/**
	 * Informs the listener about a write operation that has been performed with
	 * several DTOs.
	 *
	 * @param event {@link EventType}
	 * @param dtos {@link Dto}
	 */
	void writeOperationPerformed(final EventType event, final Dto... dtos);
}
