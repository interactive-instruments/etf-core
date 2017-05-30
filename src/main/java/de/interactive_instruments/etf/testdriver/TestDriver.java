/**
 * Copyright 2010-2017 interactive instruments GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.interactive_instruments.etf.testdriver;

import java.util.Collection;

import de.interactive_instruments.Configurable;
import de.interactive_instruments.Releasable;
import de.interactive_instruments.etf.component.ComponentInfo;
import de.interactive_instruments.etf.dal.dto.capabilities.TestObjectTypeDto;
import de.interactive_instruments.etf.dal.dto.run.TestTaskDto;
import de.interactive_instruments.etf.dal.dto.test.ExecutableTestSuiteDto;
import de.interactive_instruments.exceptions.InitializationException;
import de.interactive_instruments.exceptions.InvalidStateTransitionException;
import de.interactive_instruments.exceptions.config.ConfigurationException;

/**
 * Test Driver
 *
 * Encapsulates a test engine, manages test engine specific Executable Test Suites
 * and defines the main entry point for executing Test Suites against an
 * Test Object.
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
	 * Used by the Test Driver to report Executable Test Suite life cycle changes
	 * and to observe changes in other Test Drivers that are loaded in parallel.
	 *
	 * The mechanism is used to synchronize Ets cross-Test Driver dependencies. Must be called
	 * before {@link #init()}
	 *
	 * @param mediator Mediator object
	 */
	void setLifeCycleMediator(final ExecutableTestSuiteLifeCycleListenerMediator mediator);

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
	 * Requests information about unknown ETS from the Test Driver
	 *
	 * @param etsLookupRequest ETS holding object
	 */
	void lookupExecutableTestSuites(final EtsLookupRequest etsLookupRequest);


	/**
	 * Creates a new Test Task
	 *
	 * @param testTaskDto Test Task Dto
	 * @return {@link TestTask} a Test Task object that can be submitted to a {@link TaskPoolRegistry}
	 * @throws TestTaskInitializationException if the Test Task initialization failed
	 */
	TestTask createTestTask(final TestTaskDto testTaskDto) throws TestTaskInitializationException;
}
