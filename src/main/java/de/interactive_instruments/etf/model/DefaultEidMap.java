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

import de.interactive_instruments.Copyable;

/**
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class DefaultEidMap<V> implements EidMap<V> {

	private final Map<EID, V> internalMap;

	public DefaultEidMap() {
		internalMap = new LinkedHashMap<>();
	}

	public DefaultEidMap(final Map<EID, V> map) {
		internalMap = map;
	}

	public EidMap<V> unmodifiable() {
		return new DefaultEidMap<>(Collections.unmodifiableMap(this));
	}

	public EidMap<V> createCopy() {
		return new DefaultEidMap(Copyable.createCopy(this.internalMap));
	}

	@Override
	public EidMap<V> getAll(final Collection<?> keys) {
		final EidMap map = new DefaultEidMap();
		for (final Object key : keys) {
			final V v = internalMap.get(key);
			if (v != null) {
				map.put(key, v);
			}
		}
		return map.isEmpty() ? null : map;
	}

	@Override
	public void removeAll(final Collection<?> keys) {
		keys.forEach(k -> remove(k));
	}

	@Override
	public int size() {
		return internalMap.size();
	}

	@Override
	public boolean isEmpty() {
		return internalMap.isEmpty();
	}

	@Override
	public boolean _internalContainsKey(Object key) {
		return internalMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return internalMap.containsValue(value);
	}

	@Override
	public V put(EID key, V value) {
		return internalMap.put(key, value);
	}

	@Override
	public V _internalRemove(Object key) {
		return internalMap.remove(key);
	}

	@Override
	public void putAll(Map<? extends EID, ? extends V> m) {
		internalMap.putAll(m);
	}

	@Override
	public void clear() {
		internalMap.clear();
	}

	@Override
	public Set<EID> keySet() {
		return internalMap.keySet();
	}

	@Override
	public Collection<V> values() {
		return internalMap.values();
	}

	@Override
	public Set<Entry<EID, V>> entrySet() {
		return internalMap.entrySet();
	}

	@Override
	public boolean equals(final Object o) {
		return internalMap.equals(o);
	}

	@Override
	public int hashCode() {
		return internalMap.hashCode();
	}

	@Override
	public V _internalGet(Object key) {
		return internalMap.get(key);
	}
}
