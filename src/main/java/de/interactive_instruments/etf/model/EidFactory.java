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

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Interface for a factory that creates etf identifier objects.
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface EidFactory {

    /**
     * Creates an EID with an internal random ID
     *
     * @return created EID
     */
    EID createRandomId();

    /**
     * The Factory ensures that:
     *
     * str equals createFromStrToUUID(str).getId();
     *
     * @param str
     *            string
     * @return created EID
     */
    EID createAndPreserveStr(String str);

    /**
     * The Factory ensures that:
     *
     * uuidStr equals createUUID(uuidStr).toUUID().toString() or that UUID.nameUUIDFromBytes(uuidStr).toString() equals createUUID(uuidStr).toUUID().toString()
     *
     * @param uuidStr
     *            a string as UUID or a simple string from which a EID is generated
     * @return created EID
     */
    EID createUUID(String uuidStr);

    /**
     * The Factory ensures that:
     *
     * uuid equals createAndPreserveUUID(uuid).toUUID();
     *
     * @param uuid
     *            UUID object
     * @return created EID
     */
    EID createAndPreserveUUID(UUID uuid);

    /**
     * Returns the pattern to check the ID
     *
     * @return pattern to check an ID
     */
    Pattern getPattern();

    /**
     * Get the default factory
     *
     * @return default factory
     */
    static EidFactory getDefault() {
        return EidFactoryLoader.instance();
    }

}
