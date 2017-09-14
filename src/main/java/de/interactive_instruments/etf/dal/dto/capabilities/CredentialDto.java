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

import de.interactive_instruments.etf.dal.dto.ModelItemDto;
import de.interactive_instruments.etf.dal.dto.RepositoryItemDto;

/**
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class CredentialDto extends RepositoryItemDto {

	private ModelItemDto applicableTo;
	private String applicableUri;
	private byte[] cipher;

	public CredentialDto() {}

	private CredentialDto(final CredentialDto other) {
		super(other);
		this.applicableTo = other.applicableTo;
		this.applicableUri = other.applicableUri;
		this.cipher = other.cipher;
	}

	public ModelItemDto getApplicableTo() {
		return applicableTo;
	}

	public void setApplicableTo(final ModelItemDto applicableTo) {
		this.applicableTo = applicableTo;
	}

	public String getApplicableUri() {
		return applicableUri;
	}

	public void setApplicableUri(final String applicableUri) {
		this.applicableUri = applicableUri;
	}

	public byte[] getCipher() {
		return cipher;
	}

	public void setCipher(final byte[] cipher) {
		this.cipher = cipher;
	}

	@Override
	public CredentialDto createCopy() {
		return new CredentialDto(this);
	}
}
