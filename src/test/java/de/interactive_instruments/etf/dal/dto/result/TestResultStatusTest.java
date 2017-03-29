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

package de.interactive_instruments.etf.dal.dto.result;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 */
public class TestResultStatusTest {

	@Test
	public void testAggregateStatus() {
		assertEquals(TestResultStatus.FAILED, TestResultStatus.aggregateStatus(TestResultStatus.SKIPPED, TestResultStatus.INFO, TestResultStatus.FAILED));
		assertEquals(TestResultStatus.SKIPPED, TestResultStatus.aggregateStatus(TestResultStatus.SKIPPED, TestResultStatus.PASSED));
		assertEquals(TestResultStatus.WARNING, TestResultStatus.aggregateStatus(TestResultStatus.WARNING, TestResultStatus.INFO, TestResultStatus.PASSED));
		assertEquals(TestResultStatus.INFO, TestResultStatus.aggregateStatus(TestResultStatus.INFO, TestResultStatus.PASSED));
		assertEquals(TestResultStatus.PASSED_MANUAL, TestResultStatus.aggregateStatus(TestResultStatus.PASSED_MANUAL, TestResultStatus.PASSED));
		assertEquals(TestResultStatus.UNDEFINED, TestResultStatus.aggregateStatus(TestResultStatus.PASSED_MANUAL, TestResultStatus.UNDEFINED));
		assertEquals(TestResultStatus.UNDEFINED, TestResultStatus.aggregateStatus(TestResultStatus.UNDEFINED));
		assertEquals(TestResultStatus.UNDEFINED, TestResultStatus.aggregateStatus(null));
	}
}
