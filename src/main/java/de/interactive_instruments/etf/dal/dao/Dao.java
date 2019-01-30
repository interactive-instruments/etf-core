/**
 * Copyright 2017-2019 European Union, interactive instruments GmbH
 * Licensed under the EUPL, Version 1.2 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * This work was supported by the EU Interoperability Solutions for
 * European Public Administrations Programme (http://ec.europa.eu/isa)
 * through Action 1.17: A Reusable INSPIRE Reference Platform (ARE3NA).
 */
package de.interactive_instruments.etf.dal.dao;

import de.interactive_instruments.Configurable;
import de.interactive_instruments.Initializable;
import de.interactive_instruments.Releasable;
import de.interactive_instruments.etf.dal.dto.Dto;
import de.interactive_instruments.etf.model.Disableable;
import de.interactive_instruments.etf.model.EID;
import de.interactive_instruments.etf.model.EidMap;
import de.interactive_instruments.etf.model.OutputFormat;
import de.interactive_instruments.exceptions.StorageException;

/**
 * Data Access Object which returns Data Transfer Objects
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
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
     * Items that are an instance of {@link Disableable} and are disabled, are not listed directly in the collection but may be pulled in as references.
     *
     * @param filter
     *            criteria
     *
     * @return a PrepatedDtoCollection
     *
     * @throws StorageException
     *             if an internal error occurs
     */
    PreparedDtoCollection<T> getAll(final Filter filter) throws StorageException;

    /**
     * Check if Dto with ID exists
     *
     * @param id
     *            Dto ID
     * @return true if Dto with ID exists
     */
    boolean exists(final EID id);

    /**
     * Check if Dto with ID exists and is disabled
     *
     * If the item is no instance of {@link Disableable}, false is always returned.
     *
     * @param id
     *            Dto ID
     *
     * @return true if Dto with ID exists and is disabled, false otherwise
     */
    boolean isDisabled(final EID id);

    /**
     * An item is available if it exists ({@link #exists(EID)}) and is not disabled {@link #isDisabled(EID)}.
     *
     * @param id
     *            Dto ID
     * @return true if existent and not disabled
     */
    default boolean available(final EID id) {
        return exists(id) && !isDisabled(id);
    }

    /**
     * Returns supported OutputFormats which can be used in {@link PreparedDto } or {@link PreparedDtoCollection } for streaming to
     *
     * @return EidMap of Output Formats
     */
    EidMap<OutputFormat> getOutputFormats();

    /**
     * Last modification (update/insert) date
     *
     * @return last modification date as long
     */
    long getLastModificationDate();

    // Todo
    // FilterBuilder getFilterBuilder();
}
