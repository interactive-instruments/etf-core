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

import de.interactive_instruments.etf.dal.dto.run.TestTaskDto;
import de.interactive_instruments.etf.dal.dto.test.ExecutableTestSuiteDto;
import de.interactive_instruments.etf.model.EID;
import de.interactive_instruments.exceptions.ObjectWithIdNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Realizes the chain of responsibility pattern. Responsible TestDrivers
 * add known Executable Test Suites and their dependencies.
 *
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 */
final class EtsLookupSet {

	// TestDriver ID -> list of unkown ETS
	private final Map<EID, Set<EID>> unknownEts = new LinkedHashMap<>();
	private final Set<ExecutableTestSuiteDto> knownEts = new HashSet<>();
	private final Logger logger = LoggerFactory.getLogger(EtsLookupSet.class);

	public EtsLookupSet(final TestTaskDto testTaskDto) {
		testTaskDto.ensureValid();
		final EID testDriverId = testTaskDto.getExecutableTestSuite().getTestDriver().getId();
		final EID etsId = testTaskDto.getExecutableTestSuite().getId();
		addEts(testDriverId, etsId);
	}

	private static class LookupRequest implements EtsLookupRequest {

		private final EtsLookupSet etsLookupSet;
		private final EID testDriverId;

		LookupRequest(final EtsLookupSet etsLookupSet, final EID testDriverId) {
			this.etsLookupSet = etsLookupSet;
			this.testDriverId = testDriverId;
		}

		public Set<EID> getUnknownEts() {
			return etsLookupSet.unknownEts.get(testDriverId);
		}

		public void addKnownEts(final Set<ExecutableTestSuiteDto> knownEts) {
			this.etsLookupSet.addKnownEts(testDriverId, knownEts);
		}

	}

	public EtsLookupRequest etsResolver(final EID testDriverId) {
		return new LookupRequest(this,testDriverId);
	}

	private void addEts(final EID testDriverId, final EID etsId) {
		Set<EID> etsIds = unknownEts.get(testDriverId);
		if(etsIds==null) {
			etsIds = new HashSet<>();
		}
		unknownEts.put(testDriverId, etsIds);
	}

	private void addKnownEts(final EID testDriverId, final Set<ExecutableTestSuiteDto> knownEts) {
		final Set<EID> unknownEtsSet = unknownEts.get(testDriverId);
		this.knownEts.addAll(knownEts);
		if(unknownEtsSet!=null) {
			unknownEtsSet.removeAll(knownEts);
		}
	}

	public EID getNextTestDriverId() {
		return unknownEts.entrySet().iterator().next().getKey();
	}

	public boolean hasUnknownEts() {
		return unknownEts.isEmpty();
	}

	public Set<ExecutableTestSuiteDto> getResolved() throws ObjectWithIdNotFoundException {
		if(!unknownEts.isEmpty()) {
			final Set<EID> allUnkownIds = new HashSet<>();
			unknownEts.entrySet().forEach(e -> {
				String str="";
				for (final EID eid : e.getValue()) {
					str+=eid.getId()+" ";
				}
				logger.error("Executable Test Suites for driver \""+e.getKey()+"\" not found: "+str);
				allUnkownIds.addAll(e.getValue());
			});
			throw new ObjectWithIdNotFoundException(allUnkownIds);
		}
		return Collections.unmodifiableSet(knownEts);
	}

}