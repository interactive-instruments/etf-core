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

import java.util.Collection;
import java.util.List;
import java.util.Set;

import de.interactive_instruments.Copyable;
import de.interactive_instruments.SUtils;

/**
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface EidSet<V extends EidHolder> extends Set<V>, Copyable<EidSet<V>> {

    EidSet<V> unmodifiable();

    EidMap<V> toMap();

    List<V> toList();

    default boolean contains(final Object o) {
        if (o instanceof String) {
            return internalContains(new SUtils.StrEqContainer(o));
        }
        return internalContains(o);
    }

    boolean internalContains(Object o);

    default boolean remove(final Object o) {
        if (o instanceof String) {
            return internalRemove(new SUtils.StrEqContainer(o));
        }
        return internalRemove(o);
    }

    boolean internalRemove(Object o);

    default boolean containsAll(final Collection<?> c) {
        if (c != null && c.iterator().next() instanceof String) {
            return internalContainsAll(SUtils.StrEqContainer.createSet(c));
        }
        return internalContainsAll(c);
    }

    boolean internalContainsAll(Collection<?> c);

    default boolean retainAll(final Collection<?> c) {
        if (c != null && c.iterator().next() instanceof String) {
            return internalRetainAll(SUtils.StrEqContainer.createSet(c));
        }
        return internalRetainAll(c);
    }

    boolean internalRetainAll(Collection<?> c);

    default boolean removeAll(final Collection<?> c) {
        if (c != null && c.iterator().next() instanceof String) {
            return internalRemoveAll(SUtils.StrEqContainer.createSet(c));
        }
        return internalRemoveAll(c);
    }

    boolean internalRemoveAll(Collection<?> c);
}
