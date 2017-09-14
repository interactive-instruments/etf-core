/**
 * Copyright 2017 European Union, interactive instruments GmbH
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

import java.util.NoSuchElementException;
import java.util.ServiceLoader;

import de.interactive_instruments.SUtils;
import de.interactive_instruments.exceptions.ExcUtils;

/**
 * Loads a EidFactory service provider
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
class TestResultCollectorFactoryLoader {

	public static final String ETF_TESTDRIVER_RESULT_COLLECTOR_FACTORY = "etf.testdriver.resultcollectorfactory";

	private static final class InstanceHolder {
		static final TestResultCollectorFactory findResultCollectorFactory() {
			final ServiceLoader<TestResultCollectorFactory> collectorFactories = ServiceLoader
					.load(TestResultCollectorFactory.class);
			final String collectorFactoryClassname = System.getProperty(ETF_TESTDRIVER_RESULT_COLLECTOR_FACTORY);
			try {
				if (SUtils.isNullOrEmpty(collectorFactoryClassname)) {
					return collectorFactories.iterator().next();
				} else {
					for (final TestResultCollectorFactory collectorFactory : collectorFactories) {
						if (collectorFactoryClassname.equals(collectorFactory.getClass().getName())) {
							return collectorFactory;
						}
					}
				}
			} catch (NoSuchElementException e) {
				ExcUtils.suppress(e);
			}
			throw new RuntimeException("Can not load Result Collector Factory " +
					collectorFactoryClassname != null ? collectorFactoryClassname : "");
		}

		static final TestResultCollectorFactory INSTANCE = findResultCollectorFactory();
	}

	public static TestResultCollectorFactory instance() {
		return TestResultCollectorFactoryLoader.InstanceHolder.INSTANCE;
	}
}
