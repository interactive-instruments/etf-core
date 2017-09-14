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

import java.io.IOException;
import java.nio.file.Path;

import de.interactive_instruments.IFile;
import de.interactive_instruments.Releasable;

/**
 * An interface for an item in a remote repository.
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface RemoteRepositoryItem extends RepositoryItem, Releasable {

	/**
	 * Gets the remote item and deploys it on the local system.
	 *
	 * @param path remote path
	 * @return path on the local system.
	 * @throws IOException if download fails
	 */
	IFile makeAvailable(final Path path) throws IOException;

	/**
	 * Get the fetched path on the local system.
	 *
	 * @return path on local system; null if isAvailable() returns false;
	 */
	IFile getLocal();

	/**
	 * Checks if the item is available on the local system.
	 *
	 * @return true if item is available on local system; false otherwise
	 */
	boolean isAvailable();

	/**
	 * Removes the item from the local system.
	 *
	 * @throws IOException if removing fails
	 */
	void remove() throws IOException;
}
