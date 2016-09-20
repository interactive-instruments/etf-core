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
import de.interactive_instruments.etf.dal.dto.result.TestResultStatus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;

/**
 * The TestResultCollector is used to report failures and messages
 * during a test run as well as adding attachments and logging messages
 * (info, debug, error) that are written to a log and linked to the
 * test task result.
 *
 * The TestResultCollector is exposed by a test driver, is somehow injected into a
 * test engine and consumed during a test run by a test driver adapter.
 *
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 */
public interface TestResultCollector {

	/**
	 * temporary, use {@link TestResultCollector#markAttachment} instead
	 *
	 * will be removed in version 2.0.0 release version
	 *
	 * @return
	 */
	@Deprecated
	IFile getAttachmentDir();

	/**
	 * temporary
	 *
	 * will be removed in version 2.0.0 release version
	 *
	 * @return
	 */
	@Deprecated
	IFile getResultFile();

	/**
	 * temporary, use {@link TestResultCollector#end} instead
	 *
	 * will be removed in version 2.0.0 release version
	 *
	 * @return
	 */
	@Deprecated
	void finish();

	/**
	 * Called when a test item is run
	 *
	 * @param testModelItemId Test Model Item EID
	 *
	 * @return eid of the recorded test result item
	 *
	 * @throws IllegalArgumentException if the eid is invalid or the test can't be started in the current context
	 * @throws IllegalStateException if test already has been started or ended
	 */
	String start(final String testModelItemId) throws IllegalArgumentException, IllegalStateException;

	/**
	 * Called when a test item is run
	 *
	 * @param testModelItemId Test Model Item EID
	 * @param startTimestamp start timestamp
	 *
	 * @return eid of the recorded test result item
	 *
	 * @throws IllegalArgumentException if the eid is invalid or the test can't be started in the current context
	 * @throws IllegalStateException if test already has been started or ended
	 */
	String start(final String testModelItemId, long startTimestamp) throws IllegalArgumentException, IllegalStateException;

	/**
	 * Called just after a test item has been run
	 *
	 * @param testModelItemId Test Model Item EID
	 * @param status {@link TestResultStatus} as integer
	 *
	 * @return eid of the recorded test result item
	 *
	 * @throws IllegalArgumentException if test already has been ended
	 * @throws IllegalStateException if test already has been ended or hasn't been started yet
	 */
	String end(final String testModelItemId, final int status) throws IllegalArgumentException, IllegalStateException;

	/**
	 * Called just after a test item has been run
	 *
	 * @param testModelItemId Test Model Item EID
	 * @param status {@link TestResultStatus} as integer
	 * @param stopTimestamp stop timestamp
	 *
	 * @return eid of the recorded test result item
	 *
	 * @throws IllegalArgumentException if test already has been ended
	 * @throws IllegalStateException if test already has been ended or hasn't been started yet
	 */
	String end(final String testModelItemId, final int status, long stopTimestamp) throws IllegalArgumentException, IllegalStateException;

	/**
	 * Returns the {@link TestResultStatus} of a non-parametrized Test Model Item.
	 * "Undefined" is returned if the Test Model Item has not yet been executed.
	 *
	 * @param testModelItemId Test Model Item EID
	 *
	 * @return {@link TestResultStatus}
	 *
	 * @throws IllegalArgumentException if the Test Model Item does not exist or the ID refers to a parametrized Test Model Item
	 */
	TestResultStatus status(final String testModelItemId) throws IllegalArgumentException;

	/**
	 * Returns true if the result of a non-parametrized Test Model Item is equal to the passed sequence of {@link TestResultStatus}.
	 *
	 * @param testResultStatus {@link TestResultStatus} to compare
	 * @param testModelItemId Test Model Item EID
	 * @return {@code true} if one of the passed {@link TestResultStatus} are equal to each other and {@code false} otherwise
	 *
	 * @throws IllegalArgumentException if the Test Model Item does not exist or the ID refers to a parametrized Test Model Item
	 */
	boolean statusEqualsAny(final String testModelItemId, final String... testResultStatus) throws IllegalArgumentException;

	/**
	 * Add a message
	 *
	 * @param translationTemplateId Translation Template ID
	 */
	void addMessage(final String translationTemplateId);

	/**
	 * Add a message with translation parameters as token value pairs
	 *
	 * @param translationTemplateId Translation Template ID
	 * @param tokenValuePairs Translation Template message as token value pair
	 */
	void addMessage(final String translationTemplateId, final Map<String, String> tokenValuePairs);

	/**
	 * Add a message with translation parameters as token value pairs
	 *
	 * @param translationTemplateId Translation Template ID
	 * @param tokensAndValues Translation Template message as alternating tokens and values
	 *
	 * @throws IllegalArgumentException if number of tokensAndValues arguments is odd
	 */
	void addMessage(final String translationTemplateId, final String... tokensAndValues);

	/**
	 * Mark a file in the temporary directory as an attachment which will
	 * be persisted and added to the result document
	 *
	 * @param fileName filename in temporary directory (relative path)
	 * @param label Label for the attachment
	 * @param encoding encoding of the data
	 * @param mimeType mime type or null if the type should be auto detected
	 * @param type attachment type
	 *
	 * @return eid of the recorded attachment
	 *
	 * @throws IOException if attachment could not be read
	 */
	String markAttachment(
			final String fileName,
			final String label,
			final String encoding,
			final String mimeType,
			final String type) throws IOException;

	/**
	 * Mark a file in the temporary directory as an attachment which will
	 * be persisted and added to the result document
	 *
	 * @param fileName filename in temporary directory (relative path)
	 * @param label Label for the attachment
	 * @param encoding encoding of the data
	 * @param mimeType mime type or null if the type should be auto detected
	 *
	 * @return eid of the recorded attachment
	 *
	 * @throws IOException if attachment could not be read
	 */
	default String markAttachment(
			final String fileName,
			final String label,
			final String encoding,
			final String mimeType) throws IOException {
		return markAttachment(fileName,label,encoding,mimeType,null);
	}

	/**
	 * Save an attachment from a Reader
	 *
	 * @param reader reader
	 * @param label Label for the attachment
	 * @param mimeType mime type or null if the type should be auto detected
	 * @param type attachment type
	 *
	 * @return eid of the recorded attachment
	 *
	 * @throws IOException if attachment could not be written
	 */
	String saveAttachment(
			final Reader reader,
			final String label,
			final String mimeType,
			final String type) throws IOException;

	/**
	 * Save an attachment from an input stream
	 *
	 * @param inputStream input stream
	 * @param label Label for the attachment
	 * @param mimeType mime type or null if the type should be auto detected
	 * @param type attachment type
	 *
	 * @return eid of the recorded attachment
	 *
	 * @throws IOException if attachment could not be written
	 */
	String saveAttachment(
			final InputStream inputStream,
			final String label,
			final String mimeType,
			final String type) throws IOException;

	/**
	 * Returns a directory which can be used to store data temporary
	 * during the test run.
	 * The directory and its content will be deleted after the test run!
	 *
	 * @return temporary directory file
	 *
	 */
	File getTempDir();

	/**
	 * Report an internal error and abort the test
	 *
	 * @param translationTemplateId Translation Template ID
	 * @param tokenValuePairs Translation Template message as Token Value pair
	 * @param e Exception
	 */
	void internalError(
			final String translationTemplateId,
			final Map<String, String> tokenValuePairs,
			final Throwable e);

	/**
	 * Report an error and abort the test
	 *
	 * @param e Exception
	 */
	void internalError(final Throwable e);

	/**
	 * Info message which is written to the log file
	 *
	 * @param message message as String
	 */
	default void info(final String message) {
		getLogger().info(message);
	}

	/**
	 * Error message which is written to the log file
	 *
	 * @param message message as String
	 */
	default void error(final String message) {
		getLogger().error(message);
	}

	/**
	 * Debug message which is written to the log file
	 *
	 * @param message message as String
	 */
	default void debug(final String message) {
		getLogger().debug(message);
	}

	/**
	 * Returns the logger object
	 * @return
	 */
	TestRunLogger getLogger();

}
