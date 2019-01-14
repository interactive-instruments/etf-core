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
import java.util.Set;
import java.util.TreeSet;

/**
 * Interface for objects that possess an ETF ID
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface EidHolder extends Comparable {
    EID getId();

    @Override
    default int compareTo(final Object o) {
        if (o instanceof EidHolder) {
            return ((EidHolder) o).getId().compareTo(this.getId());
        } else if (o instanceof String) {
            return ((String) o).compareTo(this.getId().getId());
        }
        throw new IllegalArgumentException("Invalid object type comparison: " +
                o.getClass().getName() + " can not be compared with an EidHolder.");
    }

    static Set<EID> getAllIds(final Collection<? extends EidHolder> holders) {
        final Set<EID> eids = new TreeSet<>();
        holders.forEach(e -> eids.add(e.getId()));
        return eids;
    }
}
