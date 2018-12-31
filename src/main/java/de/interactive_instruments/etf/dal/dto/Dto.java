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
package de.interactive_instruments.etf.dal.dto;

import de.interactive_instruments.Copyable;
import de.interactive_instruments.etf.model.EID;
import de.interactive_instruments.etf.model.EidHolder;

/**
 * Abstract Data Transfer Object for the ETF model which is used as a simple container
 * to carry data between the different ETF layers and processes.
 * A DTO does not contain any business logic but may implement internal consistency
 * checks and basic validations.
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public abstract class Dto implements Comparable, EidHolder, Copyable<Dto> {

	protected EID id;

	public EID getId() {
		return id;
	}

	public void setId(final EID id) {
		this.id = id;
	}

	public String getTypeName() {
		final String name = getClass().getSimpleName();
		return name.length() > 3 ? name.substring(0, name.length() - 3) : name;
	}

	/**
	 * A descriptive label for identifying  Dtos
	 * (used in error messages)
	 *
	 * @return dto label as string
	 */
	public String getDescriptiveLabel() {
		if (id == null) {
			return "'" + getClass().getSimpleName() + ".NID'";
		} else {
			return "'" + id + "'";
		}
	}

	public void ensureBasicValidity() throws IncompleteDtoException {
		if (id == null) {
			throw new IncompleteDtoException("Required property 'id' not set!");
		}
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		return id.equals(obj);
	}
}
