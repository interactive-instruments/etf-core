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

import java.util.Objects;
import java.util.ServiceLoader;
import java.util.UUID;
import java.util.regex.Pattern;

import de.interactive_instruments.SUtils;
import de.interactive_instruments.exceptions.ExcUtils;

/**
 * Loads a EidFactory service provider
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
class EidFactoryLoader {

    public static final String ETF_EID_FACTORY = "etf.eid.EidFactory";

    private static final class InstanceHolder {
        static final EidFactory findEidFactoy() {
            final ServiceLoader<EidFactory> factories = ServiceLoader.load(EidFactory.class);
            final String factoryClassname = System.getProperty(ETF_EID_FACTORY);
            if (!SUtils.isNullOrEmpty(factoryClassname) && factories.iterator().hasNext()) {
                for (final EidFactory factory : factories) {
                    if (factoryClassname.equals(factory.getClass().getName())) {
                        return factory;
                    }
                }
            }
            return new DefaultEidFactory();
        }

        private static class DefaultEidFactory implements EidFactory {
            private static Pattern pattern = Pattern.compile(
                    "EID[0-9A-F]{8}-[0-9A-F]{4}-[1-5][0-9A-F]{3}-[89AB][0-9A-F]{3}-[0-9A-F]{12}", Pattern.CASE_INSENSITIVE);

            // TODO rename to createNewId(). Include a timestamp:
            // https://engineering.instagram.com/sharding-ids-at-instagram-1cf5a71e5a5c#.fgealvsj9
            @Override
            public EID createRandomId() {
                return new DefaultEid(UUID.randomUUID().toString());
            }

            @Override
            public EID createAndPreserveStr(final String str) {
                return new DefaultEid(str);
            }

            @Override
            public EID createUUID(final String uuid) {
                Objects.requireNonNull(uuid, "Cannot generate EID from null String!");
                try {
                    if (uuid.length() == 36) {
                        return new DefaultEid(UUID.fromString(uuid).toString());
                    } else if (uuid.length() == 39 && uuid.startsWith("EID")) {
                        return new DefaultEid(UUID.fromString(uuid.substring(3)).toString());
                    }
                } catch (IllegalArgumentException e) {
                    ExcUtils.suppress(e);
                }
                return new DefaultEid(UUID.nameUUIDFromBytes(uuid.getBytes()).toString());
            }

            @Override
            public EID createAndPreserveUUID(final UUID uuid) {
                return new DefaultEid(uuid.toString());
            }

            @Override
            public Pattern getPattern() {
                return pattern;
            }
        }

        static final EidFactory INSTANCE = findEidFactoy();
    }

    public static EidFactory instance() {
        return EidFactoryLoader.InstanceHolder.INSTANCE;
    }
}
