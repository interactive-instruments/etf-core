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
package de.interactive_instruments.etf.dal.dto.translation;

import java.util.HashMap;
import java.util.Map;

import de.interactive_instruments.etf.dal.dto.Dto;

/**
 * Collection of token/value pairs that are used to replace tokens
 * in TranslationTemplates
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class TranslationArgumentCollectionDto extends Dto {

	private String translationTemplateName;

	private Map<String, Argument> arguments;

	public TranslationArgumentCollectionDto() {}

	private TranslationArgumentCollectionDto(final TranslationArgumentCollectionDto other) {
		this.translationTemplateName = other.translationTemplateName;
		this.arguments = other.arguments;
	}

	public static class Argument {
		String token;
		String value;

		public Argument() {

		}

		public Argument(final String token, final String value) {
			this.token = token;
			this.value = value;
		}

		public String getToken() {
			return token;
		}

		public String getValue() {
			return value;
		}
	}

	public Map<String, Argument> getTokenValues() {
		return arguments;
	}

	public void addTokenValue(final String token, String value) {
		if (arguments == null) {
			arguments = new HashMap<>();
		}
		arguments.put(token, new Argument(token, value));
	}

	/**
	 * Name of the Translation Template for which the token replacements are applied
	 *
	 * @return String reference name
	 */
	public String getRefTemplateName() {
		return translationTemplateName;
	}

	public void setRefTemplateName(final String translationTemplateName) {
		this.translationTemplateName = translationTemplateName;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("TranslationArgumentCollectionDto{");
		sb.append("translationTemplateName='").append(translationTemplateName).append('\'');
		sb.append(", arguments=").append(arguments);
		sb.append('}');
		return sb.toString();
	}

	@Override
	public TranslationArgumentCollectionDto createCopy() {
		return new TranslationArgumentCollectionDto(this);
	}
}
