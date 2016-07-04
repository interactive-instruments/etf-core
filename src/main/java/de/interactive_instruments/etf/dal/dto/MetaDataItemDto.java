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

package de.interactive_instruments.etf.dal.dto;

/**
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 */
public abstract class MetaDataItemDto extends ModelItemDto {

	protected String label;
	protected String description;
	protected String reference;

	public MetaDataItemDto() {
	}

	/**
	 * Gets the value of the label property.
	 *
	 * @return
	 *     possible object is
	 *     {@link String }
	 *
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the value of the label property.
	 *
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *
	 */
	public void setLabel(String value) {
		this.label = value;
	}

	/**
	 * Gets the value of the description property.
	 *
	 * @return
	 *     possible object is
	 *     {@link String }
	 *
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the value of the description property.
	 *
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *
	 */
	public void setDescription(String value) {
		this.description = value;
	}

	/**
	 * Gets the value of the reference property.
	 *
	 * @return
	 *     possible object is
	 *     {@link String }
	 *
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * Sets the value of the reference property.
	 *
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *
	 */
	public void setReference(String value) {
		this.reference = value;
	}

	@Override public String toString() {
		final StringBuffer sb = new StringBuffer("MetaDataItem{");
		sb.append("id=").append(getId());
		sb.append(", parent=").append(parent!=null ? parent.getId() : null);
		sb.append(", label=").append(label);
		sb.append(", description=").append(description);
		sb.append(", reference=").append(reference);
		sb.append('}');
		return sb.toString();
	}
}
