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
import java.io.InputStream;
import java.net.URI;

import de.interactive_instruments.Copyable;
import de.interactive_instruments.Credentials;
import de.interactive_instruments.Releasable;
import de.interactive_instruments.UriUtils;

/**
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface Resource extends Releasable, Copyable<Resource> {

    String getName();

    URI getUri();

    long getContentLength() throws IOException;

    InputStream openStream() throws IOException;

    byte[] getBytes() throws IOException;

    boolean exists();

    static Resource create(final String name, final URI uri, final Credentials credentials) {
        if (UriUtils.isFile(uri)) {
            return new LocalResource(name, uri);
        } else {
            return new SecuredRemoteResource(name, credentials, uri);
        }
    }

    static Resource create(final String name, final URI uri) {
        if (UriUtils.isFile(uri)) {
            return new LocalResource(name, uri);
        } else {
            return new StdRemoteResource(name, uri);
        }
    }

    static CachedResource toCached(final Resource resource) {
        if (resource instanceof CachedResource) {
            return (CachedResource) resource;
        } else if (resource instanceof MutableRemoteResource) {
            return toCached((MutableRemoteResource) resource);
        } else if (resource instanceof RemoteResource) {
            return toCached((RemoteResource) resource);
        } else if (resource instanceof LocalResource) {
            return toCached((LocalResource) resource);
        } else {
            return new StdCachedResource(resource);
        }
    }

    static CachedRemoteResource toCached(final RemoteResource resource) {
        if (resource instanceof CachedRemoteResource) {
            return (CachedRemoteResource) resource;
        }
        return new CachedRemoteResource(resource);
    }

    static MutableCachedRemoteResource toCached(final MutableRemoteResource resource) {
        if (resource instanceof MutableCachedRemoteResource) {
            return (MutableCachedRemoteResource) resource;
        }
        return new MutableCachedRemoteResource(resource);
    }

    static CachedLocalResource toCached(final LocalResource resource) {
        if (resource instanceof CachedLocalResource) {
            return (CachedLocalResource) resource;
        }
        return new CachedLocalResource(resource);
    }

    static MutableRemoteResource toMutable(final RemoteResource resource) {
        if (resource instanceof MutableRemoteResource) {
            return (MutableRemoteResource) resource;
        } else if (resource instanceof CachedResource) {
            return new MutableCachedRemoteResource(new MutableSecuredRemoteResource(resource));
        } else {
            return new MutableSecuredRemoteResource(resource);
        }
    }

    static Resource toImmutable(final Resource resource) {
        if (resource instanceof MutableCachedRemoteResource) {
            return new CachedRemoteResource((MutableCachedRemoteResource) resource);
        } else if (resource instanceof MutableSecuredRemoteResource) {
            return new SecuredRemoteResource(resource);
        } else if (resource instanceof MutableRemoteResource) {
            throw new IllegalArgumentException("Resource type unknown");
        } else {
            return resource;
        }
    }
}
