/**
 * Copyright 2010-2017 interactive instruments GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
		return new CachedRemoteResource(resource);
	}

	static MutableCachedRemoteResource toCached(final MutableRemoteResource resource) {
		return new MutableCachedRemoteResource(resource);
	}

	static CachedLocalResource toCached(final LocalResource resource) {
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
