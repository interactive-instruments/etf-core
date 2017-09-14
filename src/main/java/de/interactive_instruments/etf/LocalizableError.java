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
package de.interactive_instruments.etf;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import de.interactive_instruments.SUtils;

/**
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public abstract class LocalizableError extends Error {
	// Extending Throwable does not work with Spring, extending
	// Exception does not work with SoapUIs Groovy script engine.

	protected final String id;
	protected final Map<String, Object> arguments;

	private static Map<String, Object> toMap(final Object... arguments) {
		if (arguments == null || arguments.length == 0) {
			return Collections.EMPTY_MAP;
		} else if (arguments.length == 1) {
			return Collections.singletonMap("0", arguments[0]);
		}
		final Map<String, Object> map = new TreeMap<>();
		for (int i = 0, argumentsLength = arguments.length; i < argumentsLength; i++) {
			map.put(String.valueOf(i), arguments[i]);
		}
		return map;
	}

	protected LocalizableError(final String id) {
		super();
		this.id = id;
		arguments = null;
	}

	protected LocalizableError(final String id, final Exception e) {
		super(e);
		this.id = id;
		arguments = Collections.singletonMap("0", e.getMessage());
	}

	protected LocalizableError(final String id, final Exception e, final Object... arguments) {
		super(e);
		this.id = id;
		this.arguments = toMap(arguments);
	}

	protected LocalizableError(final String id, final Object... arguments) {
		super();
		this.id = id;
		this.arguments = toMap(arguments);
	}

	protected LocalizableError(final String id, final String... arguments) {
		super();
		this.id = id;
		this.arguments = (Map) SUtils.toStrMap(arguments);
	}

	protected LocalizableError(final Exception e) {
		super(e);
		this.id = null;
		arguments = Collections.singletonMap("0", e.getMessage());
	}

	public final String getId() {
		return id;
	}

	public final Object[] getArgumentValueArr() {
		if (arguments != null) {
			final Collection<Object> vals = arguments.values();
			return vals.toArray(new Object[vals.size()]);
		}
		return null;
	}

	public final Collection<Object> getArgumentValues() {
		return arguments != null ? Collections.unmodifiableCollection(arguments.values()) : null;
	}

	public Map<String, Object> getArguments() {
		return Collections.unmodifiableMap(arguments);
	}
}
