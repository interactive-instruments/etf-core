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

import java.util.Set;

import de.interactive_instruments.etf.dal.dto.Dto;
import de.interactive_instruments.etf.model.EID;
import de.interactive_instruments.exceptions.ObjectWithIdNotFoundException;
import de.interactive_instruments.exceptions.StorageException;

/**
 * PreparedDtoResolver
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface PreparedDtoResolver<T extends Dto> {

    /**
     * Return a PreparedDto to query a DTO
     *
     * @param id
     *            Dto ID
     *
     * @return prepared Dto
     * @throws StorageException
     *             internal Store exception
     * @throws ObjectWithIdNotFoundException
     *             Invalid ID provided
     */
    default PreparedDto<T> getById(final EID id) throws StorageException, ObjectWithIdNotFoundException {
        return getById(id, null);
    }

    /**
     * Return a PreparedDto to query a filtered DTO
     *
     * @param id
     *            Dto ID
     * @param filter
     *            Filter
     *
     * @return prepared Dto
     * @throws StorageException
     *             internal Store exception
     * @throws ObjectWithIdNotFoundException
     *             Invalid ID provided
     */
    PreparedDto<T> getById(final EID id, final Filter filter) throws StorageException, ObjectWithIdNotFoundException;

    /**
     * Return a PreparedDtoCollection to query a collection of DTOs
     *
     * @param id
     *            Dto ID
     *
     * @return prepared Dto collection
     * @throws StorageException
     *             internal Store exception
     * @throws ObjectWithIdNotFoundException
     *             Invalid ID provided
     */
    default PreparedDtoCollection<T> getByIds(final Set<EID> id) throws StorageException, ObjectWithIdNotFoundException {
        return getByIds(id, null);
    }

    /**
     * Return a PreparedDtoCollection to query a filtered collection of DTOs
     *
     * @param id
     *            Dto ID
     * @param filter
     *            Filter
     *
     * @return prepared Dto collection
     * @throws StorageException
     *             internal Store exception
     * @throws ObjectWithIdNotFoundException
     *             Invalid ID provided
     */
    PreparedDtoCollection<T> getByIds(final Set<EID> id, final Filter filter)
            throws StorageException, ObjectWithIdNotFoundException;
}
