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

import java.util.Collection;

import javax.xml.bind.Marshaller;

import de.interactive_instruments.etf.dal.dto.Dto;
import de.interactive_instruments.etf.model.EID;
import de.interactive_instruments.exceptions.ObjectWithIdNotFoundException;
import de.interactive_instruments.exceptions.StoreException;

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
	 * Please note, if the Dto is of type RepositoryItemDto a reference to the updated
	 * Dto will be set in database and the updated Dto with a NEW EID will be returned.
	 *
	 * @param dto old dto
	 * @return the new dto, if the Dto is of type RepositoryItemDto its id will change!
	 *
	 * @throws StoreException
	 * @throws ObjectWithIdNotFoundException
	 */
	T update(T dto) throws StoreException, ObjectWithIdNotFoundException;

	/**
	 * Update multiple Dtos in data storage
	 *
	 * Please note, if the Dtos are of type RepositoryItemDto a reference to the updated
	 * Dtos will be set in database and the updated Dto with a NEW EID will be returned.
	 *
	 * @param dtoCollection
	 * @return collection of new dtos, if the Dto is of type RepositoryItemDto its id will change!
	 *
	 * @throws StoreException
	 * @throws ObjectWithIdNotFoundException
	 */
	Collection<T> updateAll(Collection<T> dtoCollection) throws StoreException, ObjectWithIdNotFoundException;

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

	/**
	 * Registers a {@link WriteDaoListener} to externally listen for write events of this WriteDao
	 *
	 * @param listener {@link WriteDaoListener}
	 */
	void registerListener(WriteDaoListener listener);

	/**
	 * Deregister a {@link WriteDaoListener} from this WriteDao
	 *
	 * @param listener {@link WriteDaoListener}
	 */
	void deregisterListener(WriteDaoListener listener);
}
