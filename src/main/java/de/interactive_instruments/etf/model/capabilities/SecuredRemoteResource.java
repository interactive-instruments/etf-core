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
package de.interactive_instruments.etf.model.capabilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import de.interactive_instruments.Credentials;
import de.interactive_instruments.UriModificationCheck;
import de.interactive_instruments.UriUtils;

/**
 * Immutable DefaultResource which does not expose the credentials
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class SecuredRemoteResource implements RemoteResource {

	private final String name;
	// Not exposed
	private final Credentials credentials;
	// Deny changing the domain name
	protected URI uri;
	private UriModificationCheck check;

	public SecuredRemoteResource(final String name, final Credentials credentials, final URI uri) {
		this.name = name;
		this.credentials = credentials;
		this.uri = UriUtils.sortQueryParameters(uri);
	}

	public SecuredRemoteResource(final Resource other) {
		this.name = other.getName();
		this.uri = UriUtils.sortQueryParameters(other.getUri());
		if (other instanceof SecuredRemoteResource) {
			credentials = ((SecuredRemoteResource) other).credentials;
		} else {
			credentials = null;
		}
		// do not copy check
	}

	private SecuredRemoteResource(final SecuredRemoteResource other) {
		this.name = other.name;
		this.credentials = other.credentials;
		this.uri = other.uri;
		// do not copy check
	}

	public boolean isModificationCheckInitialized() {
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

	@Override
	public void release() {
		check = null;
	}

	@Override
	public SecuredRemoteResource createCopy() {
		return new SecuredRemoteResource(this);
	}
}
