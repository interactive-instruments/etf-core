/**
 * Copyright 2017-2019 European Union, interactive instruments GmbH
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
package de.interactive_instruments.etf.dal.dto;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import de.interactive_instruments.SUtils;
import de.interactive_instruments.etf.model.Parameterizable;
import de.interactive_instruments.properties.PropertyHolder;

/**
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
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
         *
         * @return key value pairs
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

    public boolean isEmpty() {
        if (!values().isEmpty()) {
            return values.size() == 1 && SUtils.isNullOrEmpty(
                    values.values().iterator().next().getName());
        }
        return true;
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Arguments{");
        sb.append("applicableParamTypeName='").append(applicableParamTypeName).append('\'');
        sb.append(", values={");
        for (final Map.Entry<String, String> entry : values().entrySet()) {
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            sb.append(' ');
        }
        sb.append("}}");
        return sb.toString();
    }
}
