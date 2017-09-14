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
package de.interactive_instruments.etf.dal.dto.result;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import de.interactive_instruments.etf.dal.dto.MetaDataItemDto;
import de.interactive_instruments.etf.dal.dto.ModelItemDto;
import de.interactive_instruments.etf.dal.dto.ModelItemTreeNode;
import de.interactive_instruments.etf.model.DefaultEidMap;
import de.interactive_instruments.etf.model.EidMap;

/**
 * Result Model Item Dto
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public abstract class ResultModelItemDto extends ModelItemDto implements ModelItemTreeNode<ResultModelItemDto> {

	// protected TestResultStatus resultStatus;
	protected String resultStatus;
	protected Date startTimestamp;
	protected long duration;
	protected MetaDataItemDto resultedFrom;
	protected EidMap<ResultModelItemDto> children;

	protected ResultModelItemDto() {}

	protected ResultModelItemDto(final ResultModelItemDto other) {
		super(other);
		this.resultStatus = other.resultStatus;
		this.startTimestamp = other.startTimestamp;
		this.duration = other.duration;
		this.resultedFrom = other.resultedFrom;
		this.children = other.children;
	}

	public TestResultStatus getResultStatus() {
		return TestResultStatus.valueOf(resultStatus);
	}

	public void setResultStatus(final TestResultStatus status) {
		this.resultStatus = status.toString();
	}

	public Date getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(Date value) {
		this.startTimestamp = value;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long value) {
		this.duration = value;
	}

	public MetaDataItemDto getResultedFrom() {
		return resultedFrom;
	}

	public void setResultedFrom(MetaDataItemDto value) {
		this.resultedFrom = value;
	}

	@Override
	public List<? extends ResultModelItemDto> getChildren() {
		return children != null ? children.values().stream().collect(Collectors.toList()) : null;

	}

	@Override
	public EidMap<? extends ResultModelItemDto> getChildrenAsMap() {
		return children;
	}

	@Override
	public void addChild(final ResultModelItemDto child) {
		if (this.children == null) {
			this.children = new DefaultEidMap<>();
		}
		Objects.requireNonNull(child);
		Objects.requireNonNull(child.getId(), "Cannot add item whose ID is null");
		child.setParent(this);
		this.children.put(child.getId(), child);
	}

	@Override
	public void setChildren(final List<? extends ResultModelItemDto> children) {
		children.forEach(c -> {
			c.setParent(this);
			addChild(c);
		});
	}

}
