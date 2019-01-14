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
package de.interactive_instruments.etf.model.capabilities;

import java.io.IOException;

/**
 * A resource with an internal cache for faster access.
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface CachedResource extends Resource {

    /**
     * Invokes {@link CachedResource#recache()} if cache is empty.
     *
     * @return cached bytes
     * @throws IOException
     *             if {@link CachedResource#recache()} fails
     */
    byte[] getFromCache() throws IOException;

    /**
     * Rebuild cache.
     *
     * @return new fetched bytes
     * @throws IOException
     *             if fetching failed
     */
    byte[] recache() throws IOException;

    /**
     * Invokes {@link CachedResource#recache()} if the resource has changed since the last call of this method.
     *
     * @return true if resource changed, false otherwise
     * @throws IOException
     *             if {@link CachedResource#recache()} fails
     */
    boolean recacheIfModified() throws IOException;

    @Override
    CachedResource createCopy();
}
