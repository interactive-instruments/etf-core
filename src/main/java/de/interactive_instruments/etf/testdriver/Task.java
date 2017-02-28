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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import de.interactive_instruments.Cancelable;
import de.interactive_instruments.Initializable;
import de.interactive_instruments.Releasable;
import de.interactive_instruments.etf.model.EID;

/**
 * Task interface which is implemented by {@link Runnable} objects
 *
 * Task objects are managed by the {@link TaskPoolRegistry}
 *
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 *
 */
public interface Task<T> extends Callable<T>, TaskState, Releasable, Cancelable, Initializable {

	/**
	 * ETF {@EID}
	 *
	 * @return
	 */
	EID getId();

	/**
	 * Get the result even if the task did not finish!
	 * Use {@waitForResult()} to block the calling thread until the Task finished.
	 *
	 * @return
	 */
	T getResult();

	/**
	 * Used by the TaskPoolRegistry to set the Future after
	 * submitting the task.
	 *
	 * @param future Future callback
	 *
	 * @throws IllegalStateException if the future is already set
	 */
	void setFuture(final Future<T> future) throws IllegalStateException;

	/**
	 * Wait for result
	 *
	 * @return T
	 *
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	T waitForResult() throws InterruptedException, ExecutionException;
}
