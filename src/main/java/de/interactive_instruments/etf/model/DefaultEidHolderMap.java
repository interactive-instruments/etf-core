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
package de.interactive_instruments.etf.model;

import java.util.*;
import java.util.stream.Collectors;

import de.interactive_instruments.Copyable;

/**
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class DefaultEidHolderMap<V extends EidHolder> extends DefaultEidMap<V> implements EidHolderMap<V> {

    public DefaultEidHolderMap() {
        super();
    }

    public DefaultEidHolderMap(final Map<EID, V> map) {
        super(map);
    }

    public DefaultEidHolderMap(final Collection<V> collection) {
        super(collection.stream().collect(
                Collectors.toMap(i -> i.getId(), i -> i, (i1, i2) -> i1, LinkedHashMap::new)));

    }

    public DefaultEidHolderMap(final V[] array) {
        super(Arrays.stream(array).collect(
                Collectors.toMap(i -> i.getId(), i -> i, (i1, i2) -> i1, LinkedHashMap::new)));
    }

    public DefaultEidHolderMap(final V singleItem) {
        super(Collections.singletonMap(singleItem.getId(), singleItem));
    }

    public EidHolderMap<V> unmodifiable() {
        return new DefaultEidHolderMap<>(Collections.unmodifiableMap(this));
    }

    public EidHolderMap<V> createCopy() {
        return new DefaultEidHolderMap<>(Copyable.createCopy(this));
    }

    public EidSet<V> toSet() {
        return new DefaultEidSet<>(values());
    }

    @Override
    public EidHolderMap<V> getAll(final Collection<?> keys) {
        final EidHolderMap map = new DefaultEidHolderMap();
        for (final Object key : keys) {
            final V v = get(key);
            if (v != null) {
                map.put(key, v);
            }
        }
        return map.isEmpty() ? null : map;
    }

    public V add(V v) {
        return put(v.getId(), v);
    }

    public void addAll(Collection<V> values) {
        for (final V value : values) {
            put(value.getId(), value);
        }
    }

    /**
     * Unmodifiable
     *
     * @param singleItem
     *            single item
     * @param <V>
     *            type
     * @return an EID holding object
     */
    public static <V extends EidHolder> EidHolderMap<V> singleton(final V singleItem) {
        return new DefaultEidHolderMap<>(Collections.singleton(singleItem));
    }
}
