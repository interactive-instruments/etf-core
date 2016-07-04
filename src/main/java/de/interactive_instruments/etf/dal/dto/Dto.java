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

import de.interactive_instruments.etf.model.item.EID;

/**
 * Abstract Data Transfer Object for the ETF model which is used as a simple container
 * to carry data between the different ETF layers and processes.
 * A DTO does not contain any business logic but may implement internal consistency
 * checks and basic validations.
 *
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 */
public abstract class Dto {

    private EID id;

    public EID getId() {
        return id;
    }

    public void setId(final EID id) {
        this.id = id;
    }

}
