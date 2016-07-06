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
import de.interactive_instruments.Releasable;
import de.interactive_instruments.exceptions.StoreException;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * DataStorage managed by the DataStorageManager
 *
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 */
public interface DataStorage extends Configurable, Releasable {

	/**
	 * Reset the data storage
	 */
	void reset() throws StoreException;

	/**
	 * Creates a backup and returns its name
	 *
	 * @return backup name
	 * @throws StoreException if backup fails
	 */
	String createBackup() throws StoreException;

	/**
	 * Get a list of backups
	 *
	 * @return
	 */
	List<String> getBackupList();

	/**
	 * Restore a backup
	 * @param name of the backup
	 * @throws StoreException if backup fails
	 */
	void restoreBackup(String name) throws StoreException;

	/**
	 * Get mapping: which class is managed by which Dao
	 *
	 * @return class, dao mapping
	 */
	Map<Class, Dao> getDaoMappings();

	/**
	 * Clean unused items and optimize data storage
	 */
	void cleanAndOptimize();
}
