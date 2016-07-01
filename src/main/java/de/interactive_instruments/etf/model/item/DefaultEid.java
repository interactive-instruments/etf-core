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

package de.interactive_instruments.etf.model.item;

import de.interactive_instruments.etf.EtfConstants;
import de.interactive_instruments.exceptions.ExcUtils;

import javax.xml.bind.annotation.*;
import java.util.UUID;

/**
 + The ETF id class is intended to provide identifiers in the ETF environment.

 * This implementation is based on the features of the UUID class.
 * The id can be generated randomly or deduced from a string.
 *
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 */
public class DefaultEid implements EID, Comparable {

    private String id;

    DefaultEid(final String id) {
        this.id=id;
    }

    void setId(final String id) {
        this.id = id;
    }

	/**
     * The ID which always starts with EID
     *
     * @return
     */
    public String getId() {
        return id;
    }


    /**
     * id as string
     *
     * @return A string or an UUID as string
     */
    @Override
    public String toString() {
        return getId();
    }

    /**
     * Returns the id in UUID representation if possible
     * or returns a generated UUID hash
     *
     * @return UUID from string or UUID hash from string
     */
    public UUID toUuid() {
        try{
            if(id.length()==36) {
                return UUID.fromString(id);
            }
        }catch(IllegalArgumentException e) {
            ExcUtils.suppress(e);
        }
        return UUID.nameUUIDFromBytes(id.getBytes());
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof EID) {
            return getId().compareTo(((EID)o).getId());
        }else{
            return o.toString().compareTo(o.toString());
        }
    }

  /**
   * Compare an EID, a String or an UUID against an object
   *
   * @param obj the object to compare this {@code EID} against
   * @return {@code true} if the given object represents a {@code EID}, {@code String} or {@code UUID}
   *          equivalent to this ID, {@code false} otherwise
   */
    @Override
    public boolean equals(final Object obj) {
        if(!(obj instanceof EID) && !(obj instanceof String)) {
            return obj instanceof UUID && this.toUuid().equals(obj);
        }
        return this.getId().equals(obj.toString());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override public String getInternalId() {
        return "EID"+id;
    }
}
