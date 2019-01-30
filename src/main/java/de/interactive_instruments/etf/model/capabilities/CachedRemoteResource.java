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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import de.interactive_instruments.UriModificationCheck;
import de.interactive_instruments.exceptions.ExcUtils;

/**
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class CachedRemoteResource implements CachedResource, RemoteResource {

    private byte[] cache;
    protected final RemoteResource wrapped;
    private final static int timeoutForRecache = 180000;

    CachedRemoteResource(final RemoteResource resource) {
        wrapped = resource.createCopy();
        if (resource instanceof CachedResource) {
            try {
                this.cache = ((CachedResource) resource).getFromCache();
            } catch (IOException ign) {
                // Ignore here, will be thrown when getFromCache() is called
                ExcUtils.suppress(ign);
            }
        }
    }

    protected CachedRemoteResource(final CachedRemoteResource other) {
        this.cache = other.cache;
        this.wrapped = other.wrapped;
    }

    public byte[] getFromCache() throws IOException {
        return getFromCache(timeoutForRecache);
    }

    public byte[] getFromCache(final int timeout) throws IOException {
        if (cache == null) {
            recache(timeout);
        }
        return cache;
    }

    public byte[] recache() throws IOException {
        return recache(timeoutForRecache);
    }

    public byte[] recache(final int timeout) throws IOException {
        cache = wrapped.getBytes(timeout);
        return cache;
    }

    @Override
    public InputStream openStream() throws IOException {
        return new ByteArrayInputStream(getFromCache());
    }

    @Override
    public String getName() {
        return wrapped.getName();
    }

    @Override
    public URI getUri() {
        return wrapped.getUri();
    }

    @Override
    public long getContentLength() throws IOException {
        return getFromCache().length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return getFromCache();
    }

    @Override
    public boolean exists() {
        return wrapped.exists();
    }

    @Override
    public InputStream openStream(final int timeout) throws IOException {
        return new ByteArrayInputStream(getFromCache(timeout));
    }

    @Override
    public byte[] getBytes(final int timeout) throws IOException {
        return getFromCache(timeout);
    }

    @Override
    public UriModificationCheck getModificationCheck() throws IOException {
        return wrapped.getModificationCheck();
    }

    @Override
    public boolean isModificationCheckInitialized() {
        return wrapped.isModificationCheckInitialized();
    }

    /**
     * If the resource was modified the cache is updated and true is returned, otherwise false is returned
     *
     * @return true if cache was modified
     * @throws IOException
     *             if the resource could not be accessed
     */
    public boolean recacheIfModified() throws IOException {
        if (wrapped.isModificationCheckInitialized()) {
            final byte[] modified = wrapped.getModificationCheck().getIfModified();
            if (modified != null) {
                cache = modified;
                return true;
            }
        } else {
            wrapped.getModificationCheck();
            return true;
        }
        return false;
    }

    @Override
    public void release() {
        cache = null;
        wrapped.release();
    }

    @Override
    public CachedRemoteResource createCopy() {
        return new CachedRemoteResource(this);
    }

}
