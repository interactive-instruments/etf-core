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
import de.interactive_instruments.etf.component.ComponentNotLoadedException;
import de.interactive_instruments.etf.dal.dao.DataStorage;
import de.interactive_instruments.etf.dal.dto.run.TestRunDto;
import de.interactive_instruments.etf.model.EID;
import de.interactive_instruments.exceptions.InitializationException;
import de.interactive_instruments.exceptions.InvalidStateTransitionException;
import de.interactive_instruments.exceptions.ObjectWithIdNotFoundException;
import de.interactive_instruments.exceptions.config.ConfigurationException;
import de.interactive_instruments.properties.ConfigPropertyHolder;

import java.util.List;

/**
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 */
public interface TestDriverManager extends Configurable, Releasable {

	TestRun createTestRun(final TestRunDto dto) throws ComponentLoadingException, InitializationException, ComponentNotLoadedException, ConfigurationException, ObjectWithIdNotFoundException;

	List<ComponentInfo> getTestDriverInfo();

	void unload(final EID testDriverId) throws ObjectWithIdNotFoundException;

	void reload(final EID testDriverId) throws ObjectWithIdNotFoundException, ComponentLoadingException, ConfigurationException;

	void initSyncWithDataStorage(final DataStorage dataStorage);
}
