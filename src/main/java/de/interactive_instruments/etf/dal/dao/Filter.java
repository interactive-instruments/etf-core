/**
 * Copyright 2017-2019 European Union, interactive instruments GmbH
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
 * Filter criteria for a Dao request
 *
 * Todo: filter by tags for Executable Test Suite Controller + filter by Date for removeExpiredItemsHolder()
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface Filter {

    /**
     * The number of Dtos to skip
     *
     * @return the number of Dtos which where skipped
     */
    int offset();

    /**
     * The maximum number of Dtos that should be returned
     *
     * @return the maximum number of Dtos that should be returned
     */
    int limit();

    enum LevelOfDetail {
        /**
         * Don't include references in result
         */
        SIMPLE,
        /**
         * Include historical references to older items
         */
        HISTORY,

        /**
         * Include references -without historical references to older items
         */
        DETAILED_WITHOUT_HISTORY
    }

    /**
     * Controls which references are included in a data storage result
     *
     * @return Level of Detail
     */
    default LevelOfDetail levelOfDetail() {
        return LevelOfDetail.SIMPLE;
    }

    /**
     * List of fields that should be shown
     *
     * @return List of fields as String, separated with a comma, or *
     */
    default String fields() {
        return "*";
    }
}
