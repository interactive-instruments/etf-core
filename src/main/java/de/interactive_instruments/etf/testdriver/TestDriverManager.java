/*
 * Copyright ${year} interactive instruments GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.interactive_instruments.etf.testdriver;

import de.interactive_instruments.Configurable;
import de.interactive_instruments.Releasable;
import de.interactive_instruments.etf.component.ComponentInfo;
import de.interactive_instruments.etf.dal.dto.run.TestRunDto;
import de.interactive_instruments.etf.model.EID;
import de.interactive_instruments.exceptions.ObjectWithIdNotFoundException;
import de.interactive_instruments.exceptions.config.ConfigurationException;

import java.util.List;

/**
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 */
public interface TestDriverManager extends Configurable, Releasable {

	/**
	 * Create a new Test Run
	 *
	 * @param testRunDto
	 * @return
	 * @throws TestRunInitializationException
	 */
	TestRun createTestRun(final TestRunDto testRunDto, final TestResultCollectorFactory collectorFactory) throws TestRunInitializationException;

	/**
	 * Create a new Test Run, use default TestResultCollectorFactory
	 *
	 * @param testRunDto
	 * @return
	 * @throws TestRunInitializationException
	 */
	default TestRun createTestRun(final TestRunDto testRunDto) throws TestRunInitializationException {
		return createTestRun(testRunDto, TestResultCollectorFactory.getDefault());
	}

	List<ComponentInfo> getTestDriverInfo();

	void unload(final EID testDriverId) throws ObjectWithIdNotFoundException;

	void reload(final EID testDriverId) throws ObjectWithIdNotFoundException, ComponentLoadingException, ConfigurationException;

	static TestDriverManager getDefault() {
		return TestDriverManagerLoader.instance();
	}
}
