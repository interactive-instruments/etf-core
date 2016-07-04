
/*
 * Copyright 2016 interactive instruments GmbH
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

import de.interactive_instruments.etf.dal.dto.capabilities.TagDto;

import java.util.Date;
import java.util.List;

public abstract class RepositoryItemDto extends MetaDataItemDto
{
	protected RepositoryItemDto replacedBy;
    protected byte[] itemHash;
    protected String version;
    protected String author;
    protected Date creationDate;
    protected String lastEditor;
    protected Date lastUpdateDate;
	protected List<TagDto> tagDtos;

    public RepositoryItemDto() {
    }

    /**
     * Gets the value of the itemHash property.
     *
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getItemHash() {
        return itemHash;
    }

    /**
     * Sets the value of the itemHash property.
     *
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setItemHash(byte[] value) {
        this.itemHash = value;
    }

    /**
     * Gets the value of the version property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the author property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the value of the author property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAuthor(String value) {
        this.author = value;
    }

    /**
     * Gets the value of the creationDate property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the value of the creationDate property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCreationDate(Date value) {
        this.creationDate = value;
    }

    /**
     * Gets the value of the lastEditor property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLastEditor() {
        return lastEditor;
    }

    /**
     * Sets the value of the lastEditor property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLastEditor(String value) {
        this.lastEditor = value;
    }

    /**
     * Gets the value of the lastUpdateDate property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * Sets the value of the lastUpdateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUpdateDate(Date value) {
        this.lastUpdateDate = value;
    }

	public ModelItemDto getReplacedBy() {
		return replacedBy;
	}

	public void setReplacedBy(final RepositoryItemDto replacedBy) {
		this.replacedBy = replacedBy;
	}

	public List<TagDto> getTagDtos() {
		return tagDtos;
	}

	public void setTagDtos(final List<TagDto> tagDtos) {
		this.tagDtos = tagDtos;
	}

	@Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RepositoryItem{");
		sb.append("id=").append(getId());
		sb.append(", parent=").append(parent!=null ? parent.getId() : null);
		sb.append(", label=").append(label);
		sb.append(", description=").append(description);
		sb.append(", reference=").append(reference);

		sb.append(", version=").append(version);
        sb.append(", creationDate=").append(creationDate);
        sb.append(", lastUpdateDate=").append(lastUpdateDate);
        sb.append(", itemHash=");
        if (itemHash == null) sb.append("null");
        else {
            sb.append('[');
            for (int i = 0; i < itemHash.length; ++i)
                sb.append(i == 0 ? "" : ", ").append(itemHash[i]);
            sb.append(']');
        }
        sb.append(", author=").append(author);
        sb.append(", lastEditor=").append(lastEditor);
        sb.append('}');
        return sb.toString();
    }
}
