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
package de.interactive_instruments.etf.component.loaders;

import java.nio.file.Path;

import de.interactive_instruments.Releasable;

/**
 * A factory to create an item loader that implements a FileChangeListener interface.
 *
 * @see FileChangeListener
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface ItemFileLoaderFactory {

    /**
     * The callback interface that the ItemFileLoader uses to report changes
     *
     * The listener should implement the comparable interface which can be used to prioritize the loading process.
     */
    interface FileChangeListener extends Releasable, Comparable<FileChangeListener> {
        /**
         * File created
         */
        void eventFileCreated();

        /**
         * File deleted
         */
        void eventFileDeleted();

        /**
         * File updated
         */
        void eventFileUpdated();
    }

    /**
     * Priority of this factory
     *
     * Smaller values mean a higher priority.
     *
     * @return priority
     */
    default int getPriority() {
        return 700;
    }

    /**
     * Returns true, if the minimum criteria are fulfilled (could be based on the filename), false otherwise.
     *
     * @param path
     *            path to check
     * @return true if this factory could handle this path, false otherwise
     */
    boolean couldHandle(final Path path);

    /**
     * This will create a factory that must implement the {@link FileChangeListener} interface.
     *
     * If the file cannot be build, no exception should be thrown, but {@code null} should be returned.
     *
     * @param file
     *            file to use for loading
     * @return a factory that implements the listener or null if the build failed
     */
    FileChangeListener load(final Path file);
}
