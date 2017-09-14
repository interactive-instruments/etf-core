/**
 * Copyright 2017 European Union, interactive instruments GmbH
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

import java.io.InputStream;

import de.interactive_instruments.etf.dal.dto.Dto;
import de.interactive_instruments.exceptions.StorageException;

/**
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface StreamWriteDao<T extends Dto> extends WriteDao<T> {

	@FunctionalInterface
	interface ChangeBeforeStoreHook<T extends Dto> {
		/**
		 * Called before stored by a StreamWriteDao
		 *
		 * @param dto read and loaded Dto from InputStream
		 * @return changed Dto
		 */
		T doChangeBeforeStore(final T dto);
	}

	/**
	 * Reads, validates and adds a Type from an input stream
	 *
	 * @return created Dto
	 * @param input input stream
	 * @throws StorageException if an internal error occurs
	 */
	default T add(final InputStream input) throws StorageException {
		return add(input, null);
	}

	/**
	 * Reads, validates and adds a Type from an input stream
	 *
	 * @return created Dto
	 * @param input input stream
	 * @param hook a hook object that is called just before the Dto is persisted
	 * @throws StorageException if an internal error occurs
	 */
	T add(final InputStream input, final ChangeBeforeStoreHook<T> hook) throws StorageException;

}
