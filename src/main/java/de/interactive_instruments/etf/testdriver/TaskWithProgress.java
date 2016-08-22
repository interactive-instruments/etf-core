/**
 * Copyright 2010-2016 interactive instruments GmbH
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

import de.interactive_instruments.etf.model.EID;

import java.util.concurrent.Future;

/**
 * This interface is implemented by runnable objects.
 *
 * The interface is intentionally separated from the TaskProgress interface,
 * so that a Client that uses the registry does not have direct access to
 * the thread!
 *
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 *
 * @see TaskPoolRegistry
 */
public interface TaskWithProgress<T> extends Task<T> {

	/**
	 * EID
	 *
	 * @return EID
	 */
	EID getId();

	/**
	 * The task progress
	 *
	 * @return Progress object
	 */
	Progress getTaskProgress();

	/**
	 * Used by the TaskPoolRegistry to set the Future after
	 * submitting the task.
	 *
	 * @param future Future callback
	 * @throws IllegalStateException if the future is already set
	 */
	void setFuture(final Future<T> future) throws IllegalStateException;
}
