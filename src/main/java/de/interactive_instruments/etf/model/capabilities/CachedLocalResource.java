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
package de.interactive_instruments.etf.model.capabilities;

import java.io.IOException;
import java.net.URI;

/**
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class CachedLocalResource extends LocalResource implements CachedResource {

	private byte[] cache;
	private long timestamp;

	CachedLocalResource(final String name, final URI uri) {
		super(name, uri);
		timestamp = file.lastModified();
	}

	CachedLocalResource(final Resource resource) {
		super(resource.getName(), resource.getUri());
	}

	private CachedLocalResource(final CachedLocalResource other) {
		super(other);
		this.cache = other.cache;
		this.timestamp = other.timestamp;
	}

	@Override
	public byte[] getFromCache() {
		return cache;
	}

	@Override
	public byte[] recache() throws IOException {
		cache = super.getBytes();
		return cache;
	}

	@Override
	public boolean recacheIfModified() throws IOException {
		if (cache == null) {
			recache();
		}
		if (timestamp != file.lastModified()) {
			timestamp = file.lastModified();
			return true;
		}
		return false;
	}

	@Override
	public CachedLocalResource createCopy() {
		return new CachedLocalResource(this);
	}
}
