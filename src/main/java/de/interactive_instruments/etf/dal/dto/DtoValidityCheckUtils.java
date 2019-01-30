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

import java.util.Collection;
import java.util.Map;

/**
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class DtoValidityCheckUtils {

    private DtoValidityCheckUtils() {

    }

    public static void ensureNotNullOrEmpty(final String name, final Collection c) throws IncompleteDtoException {
        if (c == null) {
            throw new IncompleteDtoException("Required property '" + name + "' must be set!");
        }
        if (c.isEmpty()) {
            throw new IncompleteDtoException("Required property '" + name + "' is empty!");
        }
    }

    public static void ensureNotNullOrEmpty(final String name, final Map m) throws IncompleteDtoException {
        if (m == null) {
            throw new IncompleteDtoException("Required property '" + name + "' must be set!");
        }
        if (m.isEmpty()) {
            throw new IncompleteDtoException("Required property '" + name + "' is empty!");
        }
    }

    public static void ensureNotNullOrEmpty(final String name, final String str) throws IncompleteDtoException {
        if (str == null) {
            throw new IncompleteDtoException("Required property '" + str + "' must be set!");
        }
        if (str.trim().isEmpty()) {
            throw new IncompleteDtoException("Required property '" + str + "' is empty!");
        }
    }

    public static void ensureNotNullAndHasId(final String name, Dto dto) throws IncompleteDtoException {
        if (dto == null) {
            throw new IncompleteDtoException("Required property '" + name + "' must be set!");
        }
        if (dto.getId() == null) {
            throw new IncompleteDtoException("Required property '" + name + "' is set but does not possess an ID!");
        }
    }

    public static void ensureNotNull(final String name, Object obj) throws IncompleteDtoException {
        if (obj == null) {
            throw new IncompleteDtoException("Required property '" + name + "' must be set!");
        }
    }
}
