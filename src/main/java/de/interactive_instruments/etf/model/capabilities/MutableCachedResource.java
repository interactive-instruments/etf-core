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
import java.net.URI;
import java.util.Map;

import de.interactive_instruments.Credentials;

/**
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class MutableCachedResource extends CachedResource {

	public MutableCachedResource(final String name, final Credentials credentials, final URI uri) {
		super(name, credentials, uri);
	}

	public MutableCachedResource(final Resource other) {
		super(other);
	}

	/**
	 * If the resource was modified the cache is updated and true is returned,
	 * otherwise false is returned
	 *
	 * @return true if cache was modified
	 * @throws IOException if the resource could not be accessed
	 */
	public boolean recacheIfModified() throws IOException {
		final byte[] modified = super.getModificationCheck().getIfModified();
		if (modified != null) {
			cache = modified;
			return true;
		}
		return false;
	}

	@Override
	public boolean setQueyParameters(final Map<String, String> kvp) {
		final boolean changed = super.setQueyParameters(kvp);
		if (changed) {
			try {
				cache = super.getBytes();
			} catch (final IOException ign) {
				cache = null;
			}
		}
		return changed;
	}
}
