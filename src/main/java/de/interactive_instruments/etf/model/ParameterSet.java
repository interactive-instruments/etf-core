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
package de.interactive_instruments.etf.model;

import java.util.*;

/**
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class ParameterSet implements Parameterizable {

	private String typeName;
	private Map<String, Parameter> parameters = new LinkedHashMap<>();

	public ParameterSet() {}

	public ParameterSet(final ParameterSet other) {
		this.typeName = other.typeName;
		this.parameters = other.parameters;
	}

	public static class MutableParameter implements Parameterizable.Parameter {
		private String name;
		private String defaultValue;
		private String description;
		private String allowedValues;
		private String type;
		private boolean required;
		private Set<String> excludingParameter;

		public MutableParameter() {

		}

		public MutableParameter(final Parameter other) {
			this.name = other.getName();
			this.defaultValue = other.getDefaultValue();
			this.description = other.getDescription();
			this.allowedValues = other.getAllowedValues();
			this.type = other.getType();
			this.required = other.isRequired();
			this.excludingParameter = other.getExcludingParameter();
		}

		public MutableParameter(final String name, final String defaultValue) {
			this.name = name;
			this.defaultValue = defaultValue;
		}

		public void setName(final String name) {
			this.name = name;
		}

		public void setDefaultValue(final String defaultValue) {
			this.defaultValue = defaultValue;
		}

		public void setDescription(final String description) {
			this.description = description;
		}

		public void setAllowedValues(final String allowedValues) {
			this.allowedValues = allowedValues;
		}

		public void setType(final String type) {
			this.type = type;
		}

		public void setRequired(final boolean required) {
			this.required = required;
		}

		public void setExcludingParameter(final Set<String> excludingParameter) {
			this.excludingParameter = excludingParameter;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getDefaultValue() {
			return defaultValue;
		}

		@Override
		public String getDescription() {
			return description;
		}

		@Override
		public String getAllowedValues() {
			return allowedValues;
		}

		@Override
		public String getType() {
			return type;
		}

		@Override
		public boolean isRequired() {
			return required;
		}

		@Override
		public Set<String> getExcludingParameter() {
			return excludingParameter;
		}
	}

	public Collection<String[]> asNameDefaultValuePairs() {
		final List<String[]> pairs = new ArrayList<>(parameters.size());
		parameters.values().forEach(p -> pairs.add(new String[]{p.getName(), p.getDefaultValue()}));
		return pairs;
	}

	@Override
	public String getParamTypeName() {
		return typeName;
	}

	public Collection<Parameter> getParameters() {
		return parameters.values();
	}

	@Override
	public Parameter getParameter(final String name) {
		return parameters.get(name);
	}

	public void addParameter(final String parameterName, final String defaultValue) {
		addParameter(new MutableParameter(parameterName, defaultValue));
	}

	public void addParameter(final Parameter parameter) {
		if (parameters == null) {
			parameters = new HashMap<>();
		}
		parameters.put(parameter.getName(), parameter);
	}

	public boolean isEmpty() {
		return parameters.isEmpty();
	}

}
