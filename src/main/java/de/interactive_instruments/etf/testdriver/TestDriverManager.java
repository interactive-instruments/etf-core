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

import java.util.List;

import de.interactive_instruments.Configurable;
import de.interactive_instruments.Releasable;
import de.interactive_instruments.etf.component.ComponentInfo;
import de.interactive_instruments.etf.component.ComponentLoadingException;
import de.interactive_instruments.etf.component.loaders.LoadingContext;
import de.interactive_instruments.etf.dal.dto.run.TestRunDto;
import de.interactive_instruments.etf.model.EID;
import de.interactive_instruments.exceptions.ObjectWithIdNotFoundException;
import de.interactive_instruments.exceptions.config.ConfigurationException;

/**
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface TestDriverManager extends Configurable, Releasable {

    /**
     * Create a new Test Run
     *
     * @param testRunDto
     *            Test Run Dto
     * @param collectorFactory
     *            TestResultCollectorFactory
     * @return executable TestRun object
     * @throws TestRunInitializationException
     *             if the initialization failed
     */
    TestRun createTestRun(final TestRunDto testRunDto, final TestResultCollectorFactory collectorFactory)
            throws TestRunInitializationException;

    /**
     * Create a new Test Run, use default TestResultCollectorFactory
     *
     * @param testRunDto
     *            Test Run Dto
     * @return executable TestRun object
     * @throws TestRunInitializationException
     *             if the initialization failed
     */
    default TestRun createTestRun(final TestRunDto testRunDto) throws TestRunInitializationException {
        return createTestRun(testRunDto, TestResultCollectorFactory.getDefault());
    }

    /**
     * Must be called before {@link #init()} is called
     *
     * @param context
     *            LoadingContext to resolve external dependencies
     */
    void setLoadingContext(final LoadingContext context);

    List<ComponentInfo> getTestDriverInfo();

    void loadAll() throws ComponentLoadingException, ConfigurationException;

    void load(final EID testDriverId) throws ObjectWithIdNotFoundException, ComponentLoadingException, ConfigurationException;

    void unload(final EID testDriverId) throws ObjectWithIdNotFoundException;

    void reload(final EID testDriverId) throws ObjectWithIdNotFoundException, ComponentLoadingException, ConfigurationException;

    static TestDriverManager getDefault() {
        return TestDriverManagerLoader.instance();
    }
}
