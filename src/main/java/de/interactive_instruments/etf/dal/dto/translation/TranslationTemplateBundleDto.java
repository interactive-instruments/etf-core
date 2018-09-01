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

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.interactive_instruments.etf.dal.dto.Dto;
import de.interactive_instruments.etf.dal.dto.ModelItemDto;

/**
 * TranslationTemplateBundleDto represents a bundle of
 * {@link LangTranslationTemplateCollectionDto} that can be accessed through its name.
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class TranslationTemplateBundleDto extends ModelItemDto<TranslationTemplateBundleDto> {

	/**
	 * Maps a named {@link LangTranslationTemplateCollectionDto#name} to a
	 * language specific collection {@link LangTranslationTemplateCollectionDto}
	 */
	private Map<String, LangTranslationTemplateCollectionDto> langTranslationTemplates;

	// Origin of the templates
	private String source;

	public TranslationTemplateBundleDto() {

	}

	private TranslationTemplateBundleDto(final TranslationTemplateBundleDto other) {
		this.id = other.id;
		this.langTranslationTemplates = other.langTranslationTemplates;
		this.source = other.source;
		this.parent = other.parent;
	}

	/**
	 * Add TranslationTemplates
	 *
	 * @param translationTemplates TranslationTemplateDtos to add
	 */
	public void addTranslationTemplates(
			final Collection<TranslationTemplateDto> translationTemplates) {
		if (langTranslationTemplates == null) {
			langTranslationTemplates = new HashMap<>();
		}
		for (final TranslationTemplateDto translationTemplate : translationTemplates) {
			final String name = translationTemplate.getName();
			final LangTranslationTemplateCollectionDto langTranslationTemplateCollection = langTranslationTemplates.get(name);
			if (langTranslationTemplateCollection != null) {
				langTranslationTemplateCollection.add(translationTemplate);
			} else {
				langTranslationTemplates.put(name,
						new LangTranslationTemplateCollectionDto(translationTemplate));
			}
		}
	}

	/**
	 * Returns a LangTranslationTemplateCollectionDto by its name
	 *
	 * @param name Language Translations Template Collections name {@link LangTranslationTemplateCollectionDto#name}
	 * @return LangTranslationTemplateCollectionDto
	 */
	public LangTranslationTemplateCollectionDto getTranslationTemplateCollection(final String name) {
		if (langTranslationTemplates != null) {
			final LangTranslationTemplateCollectionDto collection = langTranslationTemplates.get(name);
			if (collection != null) {
				return collection;
			}
			if (parent != null) {
				return parent.getTranslationTemplateCollection(name);
			}
		}
		return null;
	}

	/**
	 * Returns a TranslationTemplate by its name in a specific language
	 *
	 * @param name Translations Template name {@link TranslationTemplateDto#name}
	 * @param language Translations Template language {@link TranslationTemplateDto#language}
	 * @return TranslationTemplateDto
	 */
	public TranslationTemplateDto getTranslationTemplate(final String name, String language) {
		final LangTranslationTemplateCollectionDto langTranslationTemplateCollection = getTranslationTemplateCollection(name);
		if (langTranslationTemplateCollection != null) {
			return langTranslationTemplateCollection.getByLanguage(language);
		}
		if (parent != null) {
			return parent.getTranslationTemplate(name, language);
		}
		return null;
	}

	/**
	 * Returns the origin of the Template Bundle as URI
	 *
	 * @return the origin of the Template Bundle as URI
	 */
	public URI getSource() {
		return source != null ? URI.create(source) : null;
	}

	/**
	 * Set the origin of the Template Bundle
	 *
	 * @param source origin
	 */
	public void setSource(final URI source) {
		this.source = source.toString();
	}

	@Override
	public TranslationTemplateBundleDto createCopy() {
		return new TranslationTemplateBundleDto(this);
	}

}
