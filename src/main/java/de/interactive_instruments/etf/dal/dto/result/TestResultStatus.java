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
package de.interactive_instruments.etf.dal.dto.result;

public enum TestResultStatus {

	// The ordinal() function is used for faster int to enum conversion:
	// new enum constants have to be added at the END !!!

	/**
	 * PASSED, if all status values are PASSED
	 *
	 */
	PASSED,

	/**
	 * FAILED, if at least one status value is FAILED
	 *
	 *
	 */
	FAILED,

	/**
	 * SKIPPED, if at least one status value is SKIPPED because a
	 *                         test case depends on another test case which has the status FAILED
	 *
	 *
	 */
	SKIPPED,

	/**
	 * NOT_APPLICABLE if at least one status value is NOT_APPLICABLE,
	 *                         in the case the test object does not provide the capabilities for executing
	 *                         the test
	 *
	 */
	NOT_APPLICABLE,

	/**
	 * INFO, if at least one status value is INFO
	 *
	 */
	INFO,

	/**
	 * WARNING, if at least one status value is
	 *                         WARNING
	 *
	 */
	WARNING,

	/**
	 * UNDEFINED, in all other cases
	 *
	 */
	UNDEFINED,

	/**
	 * PASSED_MANUAL, if at least one status value is PASSED_MANUAL
	 *                         (if the test is not automated and the user has to validate results manually
	 *                         based on instructions in the report) and all others are values are
	 *                         PASSED
	 *
	 */
	PASSED_MANUAL;

	public int value() {
		return ordinal();
	}

	public static TestResultStatus valueOf(int i) {
		return TestResultStatus.values()[i];
	}
}
