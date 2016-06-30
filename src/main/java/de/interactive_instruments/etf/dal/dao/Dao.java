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

import de.interactive_instruments.Configurable;
import de.interactive_instruments.Initializable;
import de.interactive_instruments.Releasable;
import de.interactive_instruments.etf.dal.dto.Dto;
import de.interactive_instruments.etf.dal.dto.DtoCollection;
import de.interactive_instruments.etf.model.item.EID;
import de.interactive_instruments.exceptions.StoreException;

/**
 * Data Access Object which returns Data Transfer Objects
 *
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 */
public interface Dao<T extends Dto> extends PreparedItemResolver<T>, Configurable, Initializable, Releasable {

    /**
     * Implementation id of the dao
     *
     * @return id of the Dao
     */
    String getId();

    /**
     * The serving dto type
     *
     * @return class of the dto type
     */
    Class<T> getDtoType();

  /**
   * Return all Dto which this Dao serves.
   *
   * @return collection of Daos
   *
   * @throws StoreException
   */
    PreparedDtoCollectionResult<T> getAll(int offset, int limit) throws StoreException;

  /**
   * Check if Dto with ID exists
   *
   * @param id Dto ID
   * @return
   * @throws StoreException
   */
    boolean exists(EID id) throws StoreException;
}
