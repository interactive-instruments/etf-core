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
package de.interactive_instruments.etf.dal.dao;

/**
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface FilterBuilder {

	/**
	 * The number of Dtos to skip
	 *
	 * @param offset non-negative offset as integer
	 * @return the number of Dtos which where skipped
	 */
	FilterBuilder offset(int offset);

	/**
	 * The maximum number of Dtos that should be returned
	 *
	 * @param limit non-negative limit as integer
	 * @return the maximum number of Dtos that should be returned
	 */
	FilterBuilder limit(int limit);

	/**
	 * Controls which references are included in a data storage result
	 *
	 * @param levelOfDetail level of detail filter
	 * @return Filter builder
	 */
	FilterBuilder levelOfDetail(Filter.LevelOfDetail levelOfDetail);

	interface PropertyExpression {
		Conj isLike(final String searchTerm);

		Conj isEqual(final String comparand);

		Conj isLessThan(final String comparand);

		Conj isGreaterThan(final String comparand);

		Conj exists();

		/**
		 * Property name
		 * @return property name
		 */
		String name();
	}

	interface Conj {
		PropertyExpression and();

		PropertyExpression or();
	}

	FilterBuilder fields(final String... property);

	FilterBuilder where(final PropertyExpression expression);

	enum Order {
		ASCENDING, DESCENDING
	}

	FilterBuilder orderBy(final Order order, final String... property);

	Filter build();
}
