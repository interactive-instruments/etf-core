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
import java.util.Objects;
import java.util.Set;

import de.interactive_instruments.SUtils;

/**
 * A named collection which holds TranslationTemplates {@link TranslationTemplateDto}
 * of one message type in different languages.
 *
 * A template can be accessed through its language:
 * {@link LangTranslationTemplateCollectionDto#getByLanguage(String)}
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class LangTranslationTemplateCollectionDto {

	/**
	 * Name of this collection
	 */
	private String name;

	/**
	 * Maps a language {@link TranslationTemplateDto#language} to a {@link TranslationTemplateDto}
	 */
	private Map<String, TranslationTemplateDto> translationTemplates;

	/**
	 * Private Ctor for JAXB
	 */
	private LangTranslationTemplateCollectionDto() {}

	/**
	 * Create a LangTranslationTemplateCollectionDto and add one initial TranslationTemplateDto
	 *
	 * @param translationTemplate
	 */
	LangTranslationTemplateCollectionDto(final TranslationTemplateDto translationTemplate) {
		Objects.requireNonNull(translationTemplate);
		this.name = SUtils.requireNonNullOrEmpty(translationTemplate.getName(),
				"TranslationTemplate name is null or empty");
		this.translationTemplates = new HashMap<>();
		this.translationTemplates.put(translationTemplate.getLanguage(), translationTemplate);
	}

	/**
	 * Returns the name of this collection
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Add a template by its name to the collection
	 *
	 * @param templateDto TranslationTemplateDto to add
	 */
	void add(final TranslationTemplateDto templateDto) {
		translationTemplates.put(
				SUtils.requireNonNullOrEmpty(templateDto.getLanguage(), "TranslationTemplate name is null or empty"),
				templateDto);
	}

	/**
	 * Return a TranslationTemplate by its language
	 *
	 * @param language ISO language code
	 *
	 * @return TranslationTemplateDto
	 */
	public TranslationTemplateDto getByLanguage(final String language) {
		return translationTemplates.get(language);
	}

	/**
	 * Returns all available languages
	 *
	 * @return Set of Strings
	 */
	public Set<String> getLanguages() {
		return translationTemplates.keySet();
	}
}
