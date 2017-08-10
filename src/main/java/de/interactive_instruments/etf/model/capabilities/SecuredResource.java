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
import java.util.Map;

import de.interactive_instruments.Credentials;
import de.interactive_instruments.UriModificationCheck;
import de.interactive_instruments.UriUtils;

/**
 * Immutable DefaultResource which does not expose the credentials
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class SecuredResource implements Resource {

	private final String name;
	// Not exposed
	private final Credentials credentials;
	// Deny changing the domain name
	private URI uri;
	private UriModificationCheck check;

	public SecuredResource(final String name, final Credentials credentials, final URI uri) {
		this.name = name;
		this.credentials = credentials;
		this.uri = UriUtils.sortQueryParameters(uri);
	}

	public SecuredResource(final Resource other) {
		this.name = other.getName();
		this.uri = other.getUri();
		if (other instanceof SecuredResource) {
			credentials = ((SecuredResource) other).credentials;
		} else {
			credentials = null;
		}
		// do not copy check
	}

	/**
	 * Returns true if the parameter changed
	 *
	 * @param kvp Key value pair map
	 * @return true if URI changed, false otherwise
	 */
	protected boolean setQueyParameters(final Map<String, String> kvp) {
		final URI newUri = URI.create(UriUtils.setQueryParameters(uri.toString(),
				kvp, false));
		if (!newUri.equals(uri)) {
			uri = newUri;
			if (check != null) {
				try {
					check = new UriModificationCheck(uri, credentials);
				} catch (IOException e) {
					check = null;
				}
			}
			return true;
		}
		return false;
	}

	protected boolean isModificationCheckInitialized() {
		return check != null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public URI getUri() {
		return uri;
	}

	@Override
	public long getContentLength() throws IOException {
		return UriUtils.getContentLength(uri, credentials);
	}

	@Override
	public InputStream openStream() throws IOException {
		return UriUtils.openStream(uri, credentials);
	}

	@Override
	public InputStream openStream(final int timeout) throws IOException {
		return UriUtils.openStream(uri, credentials, timeout);
	}

	@Override
	public byte[] getBytes() throws IOException {
		return UriUtils.toByteArray(uri, credentials);
	}

	@Override
	public byte[] getBytes(final int timeout) throws IOException {
		return UriUtils.toByteArray(uri, credentials, timeout);
	}

	@Override
	public boolean exists() {
		return UriUtils.exists(uri, credentials);
	}

	@Override
	public UriModificationCheck getModificationCheck() throws IOException {
		if (check == null) {
			check = new UriModificationCheck(uri, credentials);
		}
		return check;
	}
}
