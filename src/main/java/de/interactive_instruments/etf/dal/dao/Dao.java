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

import de.interactive_instruments.Configurable;
import de.interactive_instruments.Initializable;
import de.interactive_instruments.Releasable;
import de.interactive_instruments.etf.dal.dto.Dto;
import de.interactive_instruments.etf.model.EID;
import de.interactive_instruments.etf.model.EidMap;
import de.interactive_instruments.etf.model.OutputFormat;
import de.interactive_instruments.exceptions.StorageException;

/**
 * Data Access Object which returns Data Transfer Objects
 *
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 */
public interface Dao<T extends Dto> extends PreparedDtoResolver<T>, Configurable, Initializable, Releasable {

	/**
	 * Implementation id of the dao
	 *
	 * @return id of the Dao
	 */
	String getId();

	/**
	 * The dto type this dao serves
	 *
	 * @return class of the dto type
	 */
	Class<T> getDtoType();

	/**
	 * Returns a collection of Dtos as PrepatedDtoCollection
	 *
	 * @param filter criteria
	 *
	 * @return a PrepatedDtoCollection
	 *
	 * @throws StorageException
	 */
	PreparedDtoCollection<T> getAll(final Filter filter) throws StorageException;

	/**
	 * Check if Dto with ID exists
	 *
	 * @param id Dto ID
	 * @return true if Dto with ID exists
	 */
	boolean exists(EID id);

	/**
	 * Returns supported OutputFormats which can be used in
	 * {@link PreparedDto } or {@link PreparedDtoCollection } for streaming to
	 *
	 * @return EidMap of Output Formats
	 */
	EidMap<OutputFormat> getOutputFormats();
}
