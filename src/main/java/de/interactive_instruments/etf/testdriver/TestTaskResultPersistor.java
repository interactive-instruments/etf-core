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

import java.io.InputStream;

import de.interactive_instruments.etf.dal.dto.result.TestTaskResultDto;
import de.interactive_instruments.exceptions.StorageException;

/**
 * The TestTaskResultPersistor provides a abstract layer for persisting
 * the results of a Test Task.
 *
 * There are three ways to persist a result:
 * - collect results with a {@link TestResultCollector} object
 * - save a file that already is in the ETF Test Task result XML format
 * - Persist a Test Task result object
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface TestTaskResultPersistor {

	/**
	 * Get a {@link TestResultCollector} object for persisting the results
	 *
	 * @return a TestResultCollector object for persisting the result
	 */
	TestResultCollector getResultCollector();

	/**
	 * Stream the result of Test Task
	 *
	 * @param resultStream the XML result stream
	 * @throws IllegalStateException if result has already bean persisted
	 * @throws StorageException if result can not be persisted
	 */
	void streamResult(final InputStream resultStream) throws IllegalStateException, StorageException;

	/**
	 * Set the result of Test Task
	 *
	 * @param testTaskResultDto the result Test Task Result
	 * @throws IllegalStateException if result has already bean persisted
	 * @throws StorageException if result can not be persisted
	 */
	void setResult(final TestTaskResultDto testTaskResultDto) throws IllegalStateException, StorageException;

	/**
	 * Check if result has already benn persisted
	 *
	 * @return true if result has already bean persisted, false otherwise
	 */
	boolean resultPersisted();
}
