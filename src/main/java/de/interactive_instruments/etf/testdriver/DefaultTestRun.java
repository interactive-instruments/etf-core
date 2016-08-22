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

import de.interactive_instruments.IFile;
import de.interactive_instruments.TimeUtils;
import de.interactive_instruments.etf.dal.dto.run.TestRunDto;
import de.interactive_instruments.etf.model.EID;
import de.interactive_instruments.exceptions.ExcUtils;
import de.interactive_instruments.exceptions.InitializationException;
import de.interactive_instruments.exceptions.InvalidStateTransitionException;
import de.interactive_instruments.exceptions.config.ConfigurationException;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 */
public final class DefaultTestRun implements TestRun {

	private final TestRunDto testRunDto;
	private final IFile testRunAttachmentDir;
	private STATE currentState;
	private STATE oldState;
	private TestRunLogger testRunLogger;
	private List<TestTask> testTasks;
	private final List<TestRunEventListener> eventListeners = new ArrayList<>(3);
	private final static long waitTime = 1500;
	private int currentRunIndex = 0;
	private boolean initialized = false;
	private Instant startInstant;
	private Instant stopInstant;
	private Future<TestRunDto> future;
	private Progress progress = new TestRunProgress();

	private class TestRunProgress implements Progress {
		@Override public TestRunLogger getLogger() {
			return testRunLogger;
		}

		@Override public Date getStartTimestamp() {
			return Date.from(startInstant);
		}

		@Override public long getMaxSteps() {
			// TODO
			return testTasks.size();
		}

		@Override public long getCurrentStepsCompleted() {
			// TODO

			return currentRunIndex;
		}

		@Override public STATE getState() {
			return currentState;
		}
	}

	public DefaultTestRun(final TestRunDto testRunDto, final IFile testRunAttachmentDir) {
		this.testRunDto = testRunDto;
		this.testRunAttachmentDir = testRunAttachmentDir;
		this.testRunLogger = new DefaultTestRunLogger(
				testRunAttachmentDir, "tr-"+testRunDto.getTestTasks()+".log");
		testRunDto.setLogPath(testRunAttachmentDir.getAbsolutePath());
	}

	@Override public EID getId() {
		return testRunDto.getId();
	}

	@Override public void setFuture(final Future<TestRunDto> future) throws IllegalStateException {
		if (this.future != null) {
			throw new IllegalStateException(
					"The already set Future can't be changed!");
		}
		this.future = future;
	}

	public TestRunDto waitForResult() throws InterruptedException, ExecutionException {
		return this.future.get();
	}

	@Override public TestRunDto getResult() {
		return testRunDto;
	}

	@Override public String getLabel() {
		return testRunDto.getLabel();
	}

	@Override public List<TestTask> getTestTasks() {
		return testTasks;
	}

	public void setTestTasks(final List<TestTask> testTasks) {
		this.testTasks = testTasks;
	}

	@Override public void start() throws Exception {
		for (final TestTask testTask : testTasks) {
			++currentRunIndex;
			testTask.run();
			testTask.release();
		}
		fireCompleted();
	}

	@Override
	public final TestRunDto call() throws Exception {
		Thread.currentThread().setName("TestRun."+getId());
		try {
			testRunLogger.info("Starting TestRun." + getId() + " at " +
					TimeUtils.dateToIsoString(new Date(System.currentTimeMillis() + waitTime)));
			Thread.sleep(waitTime);
			start();
			return testRunDto;
		} catch (Exception e) {
			e.printStackTrace();
			if (!currentState.isCanceling()) {
				testRunLogger.info("Handling unexpected exception in TestRunTask.{}", getId());
				handleException(e);
			} else {
				fireFailed();
				testRunLogger.info("Ignoring exception thrown by TestRunTask.{} during cancellation phase.", getId());
			}
			throw e;
		} finally {
			try {
				testRunLogger.info("Duration: {}", TimeUtils.milisAsHrMins(getDuration().toMillis()));
			} catch (Exception e) {
				testRunLogger.info("Internal error: {}", e);
			}
			testRunLogger.info("TestRun finished");
			release();
		}
	}

	public final Duration getDuration() {
		if (stopInstant != null) {
			return Duration.between(startInstant, stopInstant);
		}
		return Duration.between(startInstant, Instant.now());
	}

	private final void fireFailed() {
		for (final TestRunEventListener eventListener : eventListeners) {
			// TODO notify
			eventListener.taskRunChangedEvent(this, STATE.FAILED, null);
		}
	}

	private final void fireCompleted() {
		for (final TestRunEventListener eventListener : eventListeners) {
			// TODO notify
			eventListener.taskRunChangedEvent(this, STATE.RUNNING, STATE.COMPLETED);
		}
	}

	private final void fireInitializing() {
		initialized = true;
		startInstant = Instant.now();
		for (final TestRunEventListener eventListener : eventListeners) {
			// TODO notify
			eventListener.taskRunChangedEvent(this, STATE.INITIALIZING, null);
		}
	}

	private void fireInitialized() {

	}

	protected final void handleException(Exception e) {
		fireFailed();
	}


	@Override public void addTestRunEventListener(final TestRunEventListener testRunEventListener) {

	}

	@Override public void cancel() throws InvalidStateTransitionException {

	}

	@Override public void init() throws ConfigurationException, InitializationException, InvalidStateTransitionException {
		fireInitializing();
		for (final TestTask testTask : testTasks) {
			testTask.init();
		}
		fireInitialized();
	}

	@Override public boolean isInitialized() {
		return initialized;
	}

	@Override public void release() {
		for (int i = currentRunIndex; i < testTasks.size(); i++) {
			try {
				testTasks.get(i).cancel();
			} catch (InvalidStateTransitionException e) {
				ExcUtils.suppress(e);
			}
			try {
				testTasks.get(i).release();
			} catch (Exception e) {
				ExcUtils.suppress(e);
			}
		}
	}

	@Override public Progress getTaskProgress() {
		return progress;
	}

	@Override public STATE getState() {
		return currentState;
	}

	public void addStateEventListener(final TestRunEventListener listener) {
		this.eventListeners.add(listener);
	}
}
