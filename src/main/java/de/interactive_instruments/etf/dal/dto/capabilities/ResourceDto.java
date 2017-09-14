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
package de.interactive_instruments.etf.dal.dto.capabilities;

import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class ResourceDto {

	private CredentialDto credential;
	private String name;
	private String uri;

	public ResourceDto() {

	}

	public ResourceDto(final String name, final String uri) throws URISyntaxException {
		this.name = name;
		setUri(uri);
	}

	public ResourceDto(final String name, final URI uri) {
		this.name = name;
		setUri(uri);
	}

	public ResourceDto(final String name, final String uri, final CredentialDto credential) throws URISyntaxException {
		this.credential = credential;
		this.name = name;
		setUri(uri);
	}

	public ResourceDto(final String name, final URI uri, final CredentialDto credential) {
		this.credential = credential;
		this.name = name;
		setUri(uri);
	}

	public CredentialDto getCredential() {
		return credential;
	}

	public void setCredential(final CredentialDto credential) {
		this.credential = credential;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public URI getUri() {
		return URI.create(uri);
	}

	public void setUri(final URI uri) {
		this.uri = uri.toString();
	}

	public void setUri(final String uri) throws URISyntaxException {
		this.uri = new URI(uri).toString();
	}
}
