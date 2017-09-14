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
package de.interactive_instruments.etf.repository;

import java.util.List;

import de.interactive_instruments.Configurable;
import de.interactive_instruments.ImmutableVersion;
import de.interactive_instruments.Releasable;
import de.interactive_instruments.exceptions.ObjectWithIdNotFoundException;
import de.interactive_instruments.exceptions.StorageException;
import de.interactive_instruments.model.std.RetrievableItem;

/**
 * A repository that serves (remote) repository items.
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface Repository extends RetrievableItem, Configurable, Releasable {

	// repository URI configuration key
	String REPOSITORY_URI_PK = "ii.repository.uri";

	// repository Password configuration key
	String REPOSITORY_AUTH_PWD_PK = "ii.repository.pwd";

	// repository User configuration key
	String REPOSITORY_AUTH_USER_PK = "ii.repository.user";

	/**
	 * Gets a repository item from the repository by its label and version.
	 *
	 * @param label item label
	 * @param version version of the item
	 * @return found repository item
	 * @throws ObjectWithIdNotFoundException if item does not exist in repository
	 * @throws StorageException if repository is not initialized or a fetching error occurred
	 */
	RepositoryItem getItemByLabel(String label, ImmutableVersion version)
			throws ObjectWithIdNotFoundException, StorageException;

	/**
	 * Gets the latest repository item from the repository by its label.
	 *
	 * @param label item label
	 * @return found repository item
	 * @throws ObjectWithIdNotFoundException if item does not exist in repository
	 * @throws StorageException if repository is not initialized or a fetching error occurred
	 */
	RepositoryItem getLatestItemByLabel(String label) throws ObjectWithIdNotFoundException, StorageException;

	/**
	 * Checks if an item with a newer version exists in the repository.
	 *
	 * @param label item label
	 * @param version version of the item
	 * @return true if an item with a newer version exists; false otherwise
	 * @throws ObjectWithIdNotFoundException if item does not exist in repository
	 * @throws StorageException if repository is not initialized or a fetching error occurred
	 */
	boolean hasNewerItemVersion(String label, ImmutableVersion version) throws ObjectWithIdNotFoundException, StorageException;

	/**
	 * Gets all items from the repository by their label in all versions.
	 *
	 * @param label item label
	 * @return found repository item
	 * @throws StorageException if repository is not initialized or a fetching error occurred
	 */
	List<RepositoryItem> getItemsForLabel(String label) throws StorageException;

	/**
	 * Gets all items from the repository.
	 *
	 * @return list of all items in the repository.
	 * @throws StorageException if repository is not initialized or a fetching error occurred
	 */
	List<RepositoryItem> getItems() throws StorageException;
}
