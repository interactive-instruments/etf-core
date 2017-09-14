/**
 * Copyright 2017 European Union, interactive instruments GmbH
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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.interactive_instruments.etf.dal.dto.test.ExecutableTestSuiteDto;

/**
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class EgaidTest {

	@Test
	public void testSetEgaid() {
		final ExecutableTestSuiteDto dto = new ExecutableTestSuiteDto();
		dto.setEgaId("egaid.eu.inspire.validation.ets.downloadservices.atompredefined");
		assertEquals("eu.inspire.validation.ets.downloadservices", dto.getEgaId().getGroupId());
		assertEquals("atompredefined", dto.getEgaId().getArtifactId());
		assertEquals(null, dto.getEgaId().getVersion());
		assertEquals(null, dto.getVersion());
	}

	@Test
	public void testSetEgaidWithVersion1() {
		final ExecutableTestSuiteDto dto = new ExecutableTestSuiteDto();
		dto.setEgaId("egaid.eu.inspire.validation.ets.downloadservices.atompredefined:1.0.0");
		assertEquals("eu.inspire.validation.ets.downloadservices", dto.getEgaId().getGroupId());
		assertEquals("atompredefined", dto.getEgaId().getArtifactId());
		assertEquals("1.0.0", dto.getEgaId().getVersion().getAsString());
		assertEquals("1.0.0", dto.getVersion().getAsString());
	}

	@Test
	public void testSetEgaidNotOverwriteVersion() {
		final ExecutableTestSuiteDto dto = new ExecutableTestSuiteDto();
		dto.setVersionFromStr("1.0.2");
		dto.setEgaId("egaid.eu.inspire.validation.ets.downloadservices.atompredefined");
		assertEquals("eu.inspire.validation.ets.downloadservices", dto.getEgaId().getGroupId());
		assertEquals("atompredefined", dto.getEgaId().getArtifactId());
		assertEquals("1.0.2", dto.getEgaId().getVersion().getAsString());
		assertEquals("1.0.2", dto.getVersion().getAsString());
	}

	@Test
	public void testEgaidRef() {
		final ExecutableTestSuiteDto dto = new ExecutableTestSuiteDto();
		dto.setVersionFromStr("1.0.2");
		dto.setEgaId("egaid.eu.inspire.validation.ets.downloadservices.atompredefined");
		assertEquals("eu.inspire.validation.ets.downloadservices.atompredefined:1.0.2", dto.getEgaId().getEgaIdRef());
	}
}
