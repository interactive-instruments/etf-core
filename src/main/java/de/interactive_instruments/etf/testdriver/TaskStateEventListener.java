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

/**
 * A Listener interface for realising the Observer pattern. Registered clients that implement this interface will be informed about state changes in Test Tasks objects ({@link TestTask}).
 *
 * <img src="TaskStateEventListener.svg" alt="Class UML">
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface TaskStateEventListener {

    /**
     * Sends the changed state to an observing object
     *
     * @param t
     *            task
     * @param actualState
     *            actual task state
     * @param oldState
     *            old task state
     */
    void taskStateChangedEvent(
            TestTask t, TaskState.STATE actualState, TaskState.STATE oldState);
}
