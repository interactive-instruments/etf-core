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
package de.interactive_instruments.etf.component.loaders;

import java.util.Collection;
import java.util.Collections;

import de.interactive_instruments.etf.dal.dto.Dto;
import de.interactive_instruments.etf.model.DefaultEidHolderMap;
import de.interactive_instruments.etf.model.EID;
import de.interactive_instruments.etf.model.EidHolder;
import de.interactive_instruments.etf.model.EidHolderMap;

/**
 * Realizes the Null Object pattern
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public final class NullItemRegistry implements ItemRegistry {

    private static NullItemRegistry instance = new NullItemRegistry();

    private NullItemRegistry() {}

    public static ItemRegistry instance() {
        return instance;
    }

    @Override
    public void register(final Collection<? extends Dto> items) {
        // null object pattern
    }

    @Override
    public void deregister(final Collection<? extends EidHolder> items) {
        // null object pattern
    }

    @Override
    public void deregisterCallback(final DependencyChangeListener listener) {
        // null object pattern
    }

    @Override
    public void update(final Collection<? extends Dto> items) {
        // null object pattern
    }

    @Override
    public EidHolderMap<? extends Dto> lookupDependency(final Collection<EID> dependencies,
            final DependencyChangeListener callbackListener) {
        return new DefaultEidHolderMap(Collections.EMPTY_MAP);
    }

    @Override
    public EidHolderMap<? extends Dto> lookup(final Collection<EID> items) {
        return new DefaultEidHolderMap(Collections.EMPTY_MAP);
    }
}
