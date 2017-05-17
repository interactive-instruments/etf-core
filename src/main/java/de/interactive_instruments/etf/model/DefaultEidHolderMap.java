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
public class DefaultEidHolderMap<V extends EidHolder> extends DefaultEidMap<V> implements EidHolderMap<V> {

	public DefaultEidHolderMap() {
		super();
	}

	public DefaultEidHolderMap(final Map<EID, V> map) {
		super(map);
	}

	public DefaultEidHolderMap(final Collection<V> collection) {
		super();
		collection.forEach(e -> put(e.getId(), e));
	}

	public V add(V v) {
		return put(v.getId(), v);
	}

	public Set<EID> ids() {
		return keySet();
	}

	public EidHolderMap<V> unmodifiable() {
		return new DefaultEidHolderMap<>(Collections.unmodifiableMap(this));
	}
}
