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
package de.interactive_instruments.etf.dal.dto.report;

import java.util.List;

import de.interactive_instruments.etf.dal.dto.RepositoryItemDto;

public class StatisticalReportTableTypeDto extends RepositoryItemDto {

	private List<String> columnHeaderLabels;
	private List<String> columnExpressions;

	public StatisticalReportTableTypeDto() {}

	private StatisticalReportTableTypeDto(final StatisticalReportTableTypeDto other) {
		super(other);
		this.columnHeaderLabels = other.columnHeaderLabels;
		this.columnExpressions = other.columnExpressions;
	}

	public List<String> getColumnHeaderLabels() {
		return columnHeaderLabels;
	}

	public void setColumnHeaderLabels(final List<String> columnHeaderLabels) {
		this.columnHeaderLabels = columnHeaderLabels;
	}

	public List<String> getColumnExpressions() {
		return columnExpressions;
	}

	public void setColumnExpressions(final List<String> columnExpressions) {
		this.columnExpressions = columnExpressions;
	}

	@Override
	public StatisticalReportTableTypeDto createCopy() {
		return new StatisticalReportTableTypeDto(this);
	}
}
