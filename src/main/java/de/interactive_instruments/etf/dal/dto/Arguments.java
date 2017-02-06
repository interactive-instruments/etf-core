/**
 * Copyright 2010-2016 interactive instruments GmbH
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
package de.interactive_instruments.etf.dal.dto;

import java.util.*;

import de.interactive_instruments.etf.model.Parameterizable;
import de.interactive_instruments.properties.PropertyHolder;

/**
 *
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 */
public class Arguments {

	private String applicableParamTypeName;
	private Map<String, Argument> values = new HashMap<>();

	public Arguments(final PropertyHolder holder) {
		holder.forEach(p -> values.put(p.getKey(), new Argument(p.getKey(), p.getValue())));
	}

	public Arguments() {}

	public Arguments(final Map<String, String> map) {
		map.entrySet().forEach(e -> values.put(e.getKey(), new Argument(e.getKey(), e.getValue())));
	}

	public static class Argument {
		private String name;
		private String value;

		public Argument() {}

		public Argument(final String name, final String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}
	}

	public class ArgumentParameterSet {

		private final Parameterizable parameterizable;
		private final boolean applicable;

		ArgumentParameterSet(Parameterizable parameterizable) {
			this.parameterizable = parameterizable;
			applicable = applicableParamTypeName != null && applicableParamTypeName.equals(parameterizable.getParamTypeName());
			checkValidParams();
		}

		private void checkValidParams() {
			if (applicable) {
				// todo
			}
		}

		public String value(final String name) {
			if (applicable) {
				final Argument argumentValue = values.get(name);
				if ((argumentValue == null || argumentValue.getValue() == null) &&
						parameterizable.getParameter(name) != null) {
					parameterizable.getParameter(name).getDefaultValue();
				}
			}
			return values.get(name).getValue();
		}

		/**
		 * Does not return any null values
		 * @return
		 */
		public Map<String, String> values() {
			if (applicable) {
				final Map<String, String> map = new LinkedHashMap<>();
				for (final Parameterizable.Parameter parameter : parameterizable.getParameters()) {
					final String val = values.get(parameter.getName()).getValue();
					if (val == null) {
						if (parameter.getDefaultValue() != null) {
							map.put(parameter.getName(), parameter.getDefaultValue());
						}
					} else {
						map.put(parameter.getName(), val);
					}
				}
				return map;
			} else {
				final Map<String, String> vals = new HashMap<>();
				values.entrySet().forEach(e -> vals.put(e.getKey(), e.getValue().getValue()));
				return vals;
			}
		}
	}

	public void setValue(final String name, final String value) {
		this.values.put(name, new Argument(name, value));
	}

	public ArgumentParameterSet argumentParameterSet(Parameterizable parameterizable) {
		return new ArgumentParameterSet(parameterizable);
	}

	// TODO tmp
	public String value(final String name) {
		final Argument val = values.get(name);
		return val != null ? val.getValue() : null;
	}

	// TODO tmp
	public Map<String, String> values() {
		final Map<String, String> vals = new HashMap<>();
		values.entrySet().forEach(e -> vals.put(e.getKey(), e.getValue().getValue()));
		return vals;
	}

}
