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

import de.interactive_instruments.etf.dal.dto.Dto;
import de.interactive_instruments.etf.model.item.EID;
import de.interactive_instruments.exceptions.ObjectWithIdNotFoundException;
import de.interactive_instruments.exceptions.StoreException;

import java.util.Collection;
import java.util.List;

/**
 * Data Access Object for creating, updating and deleting Data Transfer Objects
 *
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 */
public interface WriteDao<T extends Dto> extends Dao<T> {

	/**
	 * Add Dto to data storage
	 *
	 * @param dto
	 * @throws StoreException
	 */
	void add(T dto) throws StoreException;

	/**
	 * Add Dtos to data storage
	 *
	 * @param dtoCollection
	 * @throws StoreException
	 */
	void addAll(Collection<T> dtoCollection) throws StoreException;

	/**
	 * Update one Dto in data storage
	 *
	 * @param dto
	 * @throws StoreException
	 * @throws ObjectWithIdNotFoundException
	 */
	void update(T dto) throws StoreException, ObjectWithIdNotFoundException;

	/**
	 * Update multiple Dtos in data storage
	 *
	 * @param dtoCollection
	 * @throws StoreException
	 * @throws ObjectWithIdNotFoundException
	 */
	void updateAll(Collection<T> dtoCollection) throws StoreException, ObjectWithIdNotFoundException;

	/**
	 * Delete Dto by its ID
	 *
	 * @param id Dto ID
	 * @throws StoreException
	 * @throws ObjectWithIdNotFoundException
	 */
	void delete(EID id) throws StoreException, ObjectWithIdNotFoundException;

	/**
	 * Delete Dtos by their IDs
	 *
	 * @param ids ID collection
	 * @throws StoreException
	 * @throws ObjectWithIdNotFoundException
	 */
	void deleteAll(Collection<EID> ids) throws StoreException, ObjectWithIdNotFoundException;
}
