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
package de.interactive_instruments.etf.model.item;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import de.interactive_instruments.etf.model.EID;
import de.interactive_instruments.etf.model.EidFactory;

/**
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class EidFactoryTest {

	@Test
	public void testFactory() {

		final String id1Str = "No UUID";
		final String id1AsUuidStr = "a402ff87-805b-3875-bf17-eddd0bba21d9";
		final EID id1 = EidFactory.getDefault().createAndPreserveStr(id1Str);

		assertEquals(id1, id1.getId());
		assertEquals(id1AsUuidStr, id1.toUuid().toString());

		assertTrue(id1.equals(id1));
		assertTrue(id1.equals(id1.getId()));

		assertFalse(id1AsUuidStr.equals(id1));
		assertFalse(id1AsUuidStr.equals(id1.getId()));
		assertTrue(id1AsUuidStr.equals(id1.toUuid().toString()));

		assertTrue(id1.equals(UUID.fromString(id1AsUuidStr)));

		assertTrue(UUID.fromString(id1AsUuidStr).equals(id1.toUuid()));
		assertFalse(UUID.fromString(id1AsUuidStr).equals(id1));

		final String uuidAsStr2 = "99b01a1a-5423-49a9-8c22-27519a95d9bd";
		final EID id2 = EidFactory.getDefault().createAndPreserveStr(uuidAsStr2);
		assertEquals(uuidAsStr2, id2.getId());
		assertEquals(uuidAsStr2, id2.toUuid().toString());

	}
}
