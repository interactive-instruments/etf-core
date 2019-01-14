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
package de.interactive_instruments.etf.testdriver;

import java.util.ServiceLoader;

import de.interactive_instruments.SUtils;

/**
 * Loads a EidFactory service provider
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
class TestDriverManagerLoader {

    public static final String ETF_TESTDRIVER_MANAGER = "etf.testdriver.manager";

    private static final class InstanceHolder {
        static final TestDriverManager findTestDriverManager() {
            final ServiceLoader<TestDriverManager> managers = ServiceLoader.load(TestDriverManager.class);
            final String managerClassname = System.getProperty(ETF_TESTDRIVER_MANAGER);
            if (SUtils.isNullOrEmpty(managerClassname)) {
                return managers.iterator().next();
            } else {
                for (final TestDriverManager manager : managers) {
                    if (managerClassname.equals(manager.getClass().getName())) {
                        return manager;
                    }
                }
            }
            throw new RuntimeException("Can not load Test Driver Manager " + managerClassname);
        }

        static final TestDriverManager INSTANCE = findTestDriverManager();
    }

    public static TestDriverManager instance() {
        return TestDriverManagerLoader.InstanceHolder.INSTANCE;
    }
}
