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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import de.interactive_instruments.Credentials;
import de.interactive_instruments.Releasable;

/**
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class CachedResource extends SecuredResource implements Releasable {

	protected byte[] cache;

	public CachedResource(final String name, final Credentials credentials, final URI uri) {
		super(name, credentials, uri);
	}

	public CachedResource(final Resource other) {
		super(other);
		if (other instanceof CachedResource) {
			cache = ((CachedResource) other).cache;
		}
	}

	protected byte[] bypassCache() throws IOException {
		return super.getBytes();
	}

	private byte[] getCached() throws IOException {
		if (cache == null) {
			cache = bypassCache();
		}
		return cache;
	}

	@Override
	public InputStream openStream() throws IOException {
		return new ByteArrayInputStream(getCached());
	}

	@Override
	public InputStream openStream(final int timeout) throws IOException {
		return new ByteArrayInputStream(getCached());
	}

	@Override
	public long getContentLength() throws IOException {
		return getCached().length;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return getCached();
	}

	@Override
	public byte[] getBytes(final int timeout) throws IOException {
		return getCached();
	}

	@Override
	public void release() {
		super.release();
		cache = null;
	}
}
