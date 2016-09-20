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
import de.interactive_instruments.exceptions.ExcUtils;
import de.interactive_instruments.exceptions.ObjectWithIdNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 *
 * A client can call the pool and create a new task which implements the
 * TaskProgressInterface. The pool will return a Future to the Client, that might be
 * used for calling the result (Future get() will block and waits for the task to complete).
 *
 * The TaskProgressInterface is used by a client to monitor the progress of
 * the Thread without blocking it.
 *
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 *
 * @param <R> the the future result type
 *
 * @see Task
 */
public class TaskPoolRegistry<R, T extends Task<R>> {

	private final static long keepAliveTime = 30;
	private final ThreadPoolExecutor threadPool;

	private final ConcurrentMap<EID, T> tasks = new ConcurrentHashMap<>();

	private final ConcurrentMap<EID, Future<R>> cancelMap = new ConcurrentHashMap<>();

	public TaskPoolRegistry(final int poolSize, final int maxPoolSize) {

		final ArrayBlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<>(maxPoolSize);
		this.threadPool = new ThreadPoolExecutor(poolSize, maxPoolSize,
				keepAliveTime, TimeUnit.SECONDS, taskQueue);
	}

	/**
	 * Returns a TaskWithProgressIndication object which embodies
	 * the status of the task
	 * @param id task UUID
	 * @return TaskWithProgressIndication
	 */
	public T getTaskById(final EID id)
			throws ObjectWithIdNotFoundException {
		final T c = tasks.get(id);
		if (c == null) {
			throw new ObjectWithIdNotFoundException(
					getClass().getName(),
					id.toString());
		}
		return c;
	}

	public void removeDone() {
		final List<EID> removeTaskIds = new ArrayList<>();
		for (final T c : tasks.values()) {
			final EID id = c.getId();
			if (id != null) {
				final Future f = cancelMap.get(id);
				if (f != null && f.isDone()) {
					removeTaskIds.add(id);
				}
			}
		}
		removeTaskIds.forEach(this::release);
	}

	/**
	 * Returns all tasks
	 * @return TaskWithProgressIndication collection
	 */
	public synchronized Collection<T> getTasks() {
		return tasks.values();
	}

	/**
	 * Starts the task by submitting it to task to the pool.
	 * The progress of the task is then accessible by calling getTaskProgress()
	 * @param task
	 * @return Future which might be used to get the result of the task
	 * @throws NullPointerException if taskProgress is not set
	 * @throws IllegalStateException if the future in the task is already set
	 */
	public synchronized Future<R> submitTask(final T task) throws NullPointerException, IllegalStateException {
		final Future<R> future = threadPool.submit(task);
		task.setFuture(future);
		tasks.put(task.getId(), task);
		cancelMap.put(task.getId(), future);
		return future;
	}

	/**
	 * Releases the object (by calling the release interface) and removes
	 * the it from the TaskPoolRegistry
	 * @see de.interactive_instruments.Releasable
	 * @param id task UUID
	 */
	public synchronized void release(final EID id) {
		final T task = tasks.get(id);
		if (!task.getState().isFinalizing()) {
			tasks.get(id).release();
		}
		cancelMap.remove(id);
		tasks.remove(id);
	}

	/**
	 * Tries to cancel a running task and releases it
	 * @param id task UUID
	 */
	public synchronized void cancelTask(final EID id) {
		try {
			final Future future = cancelMap.get(id);
			if (!future.isDone()) {
				try {
					// Try to cancel gently
					tasks.get(id).cancel();
				} catch (Exception e) {
					ExcUtils.suppress(e);
				}
				Thread.sleep(1000);
				try {
					release(id);
				} catch (Exception e) {
					ExcUtils.suppress(e);
				}
				Thread.sleep(5000);
				future.cancel(true);
			}
		} catch (Exception e) {
			ExcUtils.suppress(e);
		}
		System.gc();
	}

	/**
	 * Kills all running threads
	 */
	public synchronized void killAll() {
		threadPool.shutdownNow();
	}

	/**
	 * Returns the number of running tasks
	 *
	 * @return number of tasks
	*/
	public int getActiveCount() {
		return this.threadPool.getActiveCount();
	}
}
