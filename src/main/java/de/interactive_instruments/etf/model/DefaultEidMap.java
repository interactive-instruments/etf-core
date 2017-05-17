/**
 * Copyright 2010-2017 interactive instruments GmbH
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
package de.interactive_instruments.etf.model;

import java.util.*;

/**
 *
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
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
