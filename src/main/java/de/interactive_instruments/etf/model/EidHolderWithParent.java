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
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface EidHolderWithParent<T extends EidHolderWithParent> extends ModelItemWithParent<T>, EidHolder {

    /**
     * Return the ID of this tree node item and the IDs of all parent nodes
     *
     * @return set of EIDs
     */
    default Set<EID> getIdAndParentIds() {
        final Set<EID> ids = new TreeSet<>();
        ids.add(Objects.requireNonNull(getId(), "EID is null"));
        EidHolderWithParent<T> parent = getParent();
        while (parent != null) {
            ids.add(getId());
            parent = parent.getParent();
        }
        return ids;
    }

    static <T extends EidHolderWithParent> Set<EID> getAllIdsAndParentIds(final Collection<T> eidHolderTreeNodes) {
        final Set<EID> ids = new TreeSet<>();
        eidHolderTreeNodes.forEach(holder -> ids.addAll(holder.getIdAndParentIds()));
        return ids;
    }
}
