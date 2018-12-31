/**
 * Copyright 2017-2018 European Union, interactive instruments GmbH
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
package de.interactive_instruments.etf.dal.dto.result;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class TestResultStatusTest {

	@Test
	public void testAggregateStatus() {

		assertEquals(TestResultStatus.PASSED,
				TestResultStatus.aggregateStatus(TestResultStatus.PASSED, TestResultStatus.PASSED));

		assertEquals(TestResultStatus.FAILED,
				TestResultStatus.aggregateStatus(TestResultStatus.PASSED, TestResultStatus.FAILED));

		assertEquals(TestResultStatus.FAILED,
				TestResultStatus.aggregateStatus(TestResultStatus.SKIPPED, TestResultStatus.INFO, TestResultStatus.FAILED));

		assertEquals(TestResultStatus.FAILED,
				TestResultStatus.aggregateStatus(TestResultStatus.PASSED_MANUAL, TestResultStatus.UNDEFINED,
						TestResultStatus.FAILED));

		assertEquals(TestResultStatus.SKIPPED,
				TestResultStatus.aggregateStatus(TestResultStatus.SKIPPED, TestResultStatus.PASSED));

		assertEquals(TestResultStatus.WARNING,
				TestResultStatus.aggregateStatus(TestResultStatus.WARNING, TestResultStatus.INFO, TestResultStatus.PASSED));

		assertEquals(TestResultStatus.INFO, TestResultStatus.aggregateStatus(TestResultStatus.INFO, TestResultStatus.PASSED));

		assertEquals(TestResultStatus.PASSED_MANUAL,
				TestResultStatus.aggregateStatus(TestResultStatus.PASSED_MANUAL, TestResultStatus.PASSED));

		assertEquals(TestResultStatus.UNDEFINED,
				TestResultStatus.aggregateStatus(TestResultStatus.PASSED_MANUAL, TestResultStatus.UNDEFINED));

		assertEquals(TestResultStatus.UNDEFINED, TestResultStatus.aggregateStatus(TestResultStatus.UNDEFINED));

		assertEquals(TestResultStatus.UNDEFINED, TestResultStatus.aggregateStatus((TestResultStatus[]) null));

		assertEquals(TestResultStatus.INTERNAL_ERROR,
				TestResultStatus.aggregateStatus(TestResultStatus.PASSED, TestResultStatus.INTERNAL_ERROR,
						TestResultStatus.PASSED));

		assertEquals(TestResultStatus.FAILED,
				TestResultStatus.aggregateStatus(TestResultStatus.PASSED, TestResultStatus.NOT_APPLICABLE,
						TestResultStatus.FAILED));

		assertEquals(TestResultStatus.PASSED,
				TestResultStatus.aggregateStatus(TestResultStatus.PASSED, TestResultStatus.NOT_APPLICABLE));

		assertEquals(TestResultStatus.PASSED,
				TestResultStatus.aggregateStatus(TestResultStatus.NOT_APPLICABLE, TestResultStatus.PASSED));

		assertEquals(TestResultStatus.SKIPPED,
				TestResultStatus.aggregateStatus(TestResultStatus.NOT_APPLICABLE, TestResultStatus.PASSED,
						TestResultStatus.SKIPPED));

		assertEquals(TestResultStatus.INTERNAL_ERROR,
				TestResultStatus.aggregateStatus(TestResultStatus.UNDEFINED, TestResultStatus.INTERNAL_ERROR));

		assertEquals(TestResultStatus.INTERNAL_ERROR,
				TestResultStatus.aggregateStatus(TestResultStatus.PASSED_MANUAL,
						TestResultStatus.UNDEFINED, TestResultStatus.INTERNAL_ERROR));

		assertEquals(TestResultStatus.FAILED,
				TestResultStatus.aggregateStatus(TestResultStatus.PASSED_MANUAL,
						TestResultStatus.FAILED, TestResultStatus.INTERNAL_ERROR));
	}
}
