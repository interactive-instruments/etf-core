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

package de.interactive_instruments.etf.dal.dao;

import de.interactive_instruments.Releasable;
import de.interactive_instruments.etf.dal.dto.Dto;
import de.interactive_instruments.etf.dal.dto.Reference;
import de.interactive_instruments.etf.model.item.EID;

import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;

/**
 * Prepared statement for querying a collection of Data Transfer Object or for directly streaming it in the desired output format
 *
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 */
public interface PreparedDtoCollectionResult<T extends Dto> extends Streamable, Iterable<T>, Map<EID, T>, Releasable {

	default Collection<T> asCollection() {
		return values();
	}

	/**
	 * Ensures that this collection contains the specified Dto.
	 * Returns <tt>true</tt> if this collection changed as a
	 * result of the call.
	 *
	 * @param dto element whose presence in this collection is to be ensured
	 * @return <tt>true</tt> if this collection changed as a result of the
	 *         call
	 */
	boolean add(T dto);

	/**
	 * Adds all Dtos
	 *
	 * @param collection collection containing Dtos to be added to this collection
	 */
	void addAll(Collection<T> collection);
}

