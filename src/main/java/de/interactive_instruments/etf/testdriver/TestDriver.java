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

import java.util.Collection;

import de.interactive_instruments.Configurable;
import de.interactive_instruments.Releasable;
import de.interactive_instruments.etf.component.ComponentInfo;
import de.interactive_instruments.etf.component.loaders.LoadingContext;
import de.interactive_instruments.etf.dal.dto.capabilities.TestObjectTypeDto;
import de.interactive_instruments.etf.dal.dto.run.TestTaskDto;
import de.interactive_instruments.etf.dal.dto.test.ExecutableTestSuiteDto;

/**
 * Test Driver
 *
 * Encapsulates a test engine, manages test engine specific Executable Test Suites and defines the main entry point for executing Test Suites against an Test Object.
 *
 * <img src="TestDriver.svg" alt="Class UML">
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface TestDriver extends Configurable, Releasable {

    /**
     * Returns the Test Driver's component information
     *
     * @return Test Driver component information
     */
    ComponentInfo getInfo();

    /**
     * Uused to synchronize Ets cross-Test Driver dependencies and metadata.
     *
     * Must be called before {@link #init()}.
     *
     * @param context
     *            LoadingContext to resolve external dependencies
     */
    void setLoadingContext(final LoadingContext context);

    /**
     * Returns a collection of all Executable Test Suits
     *
     * @return collection of Executable Test Suits
     */
    Collection<ExecutableTestSuiteDto> getExecutableTestSuites();

    /**
     * Returns a collection of supported Test Object Types
     *
     * @return collection of Test Object Types
     */
    Collection<TestObjectTypeDto> getTestObjectTypes();

    /**
     * Creates a new Test Task
     *
     * @param testTaskDto
     *            Test Task Dto
     * @return {@link TestTask} a Test Task object that can be submitted to a {@link TaskPoolRegistry}
     * @throws TestTaskInitializationException
     *             if the Test Task initialization failed
     */
    TestTask createTestTask(final TestTaskDto testTaskDto) throws TestTaskInitializationException;
}
