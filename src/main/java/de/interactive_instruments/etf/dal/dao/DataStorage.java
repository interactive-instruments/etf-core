/**
 * Copyright 2017-2018 European Union, interactive instruments GmbH
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

import java.util.List;
import java.util.Map;

import de.interactive_instruments.Configurable;
import de.interactive_instruments.Releasable;
import de.interactive_instruments.etf.dal.dto.Dto;
import de.interactive_instruments.exceptions.StorageException;

/**
 * DataStorage managed by the DataStorageManager
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface DataStorage extends Configurable, Releasable {

	/**
	 * Reset the data storage
	 *
	 * @throws StorageException if an internal error occurs
	 */
	void reset() throws StorageException;

	/**
	 * Create a data storage backup and returns the backup name
	 *
	 * @return the backup name
	 * @throws StorageException if an internal error occurs
	 */
	String createBackup() throws StorageException;

	/**
	 * List all available backup names
	 *
	 * @return list of available backups
	 */
	List<String> getBackupList();

	/**
	 * Restore a data storage backup by its backup name
	 *
	 * @param backupName name of the backup
	 * @throws StorageException if an internal error occurs
	 */
	void restoreBackup(final String backupName) throws StorageException;

	/**
	 * Returns the Data Access Object mappings for each Dto
	 *
	 * @param <T> Dto type
	 * @return Data Access Object mappings for each Dto
	 */
	<T extends Dto> Map<Class<T>, Dao<T>> getDaoMappings();

	/**
	 * Retuns a Data Access Object which serves the class or null
	 *
	 * @param dtoType Dto type
	 * @param <T> Dto type
	 * @return Dto type class
	 */
	default <T extends Dto> Dao<T> getDao(final Class<T> dtoType) {
		return (Dao<T>) getDaoMappings().get(dtoType);
	}

	/**
	 * Clean unused items and optimize data storage
	 * @throws StorageException if an internal error occurs
	 */
	void cleanAndOptimize() throws StorageException;
}
