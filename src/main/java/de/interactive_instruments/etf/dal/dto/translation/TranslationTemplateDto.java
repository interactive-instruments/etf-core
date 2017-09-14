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
package de.interactive_instruments.etf.dal.dto.translation;

import java.util.Objects;

/**
 * A named object that holds a string which may contain one or multiple
 * tokens in a specific language. A Translation Template is used to
 * translate messages.
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class TranslationTemplateDto {

	private String name;
	private String language;
	private String strWithTokens;
	private static String IDENTIFIER_PREFIX = "TR.";

	/**
	 * Private Ctor for JAXB
	 */
	TranslationTemplateDto() {}

	public TranslationTemplateDto(final String name, final String language, final String strWithTokens) {
		if (Objects.requireNonNull(name).startsWith(IDENTIFIER_PREFIX)) {
			this.name = name;
		} else {
			this.name = IDENTIFIER_PREFIX + name;
		}
		this.language = language;
		this.strWithTokens = strWithTokens;
	}

	/**
	 * Returns the name of this template
	 *
	 * @return the name of this template
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the language of the message with tokens
	 *
	 * @return the language of the message with tokens
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Returns a message which may contain tokens, in this form "Error, object has invalid ID: {$ID_TOKEN}"
	 *
	 * @return a message which may contain tokens
	 */
	public String getStrWithTokens() {
		return strWithTokens;
	}
}
