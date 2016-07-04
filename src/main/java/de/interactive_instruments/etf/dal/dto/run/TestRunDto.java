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

package de.interactive_instruments.etf.dal.dto.run;


import de.interactive_instruments.etf.dal.dto.Dto;
import de.interactive_instruments.etf.dal.dto.result.TestResultStatus;
import de.interactive_instruments.etf.model.item.EID;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import java.util.List;

/**
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 */
public class TestRunDto extends Dto {

    private String label;
    private String defaultLang;
    private Date startTimestamp;
    private String startedBy;
    private List<TestTaskDto> testTaskDto;

    // Test RUN status is queried from the test task objects!

    private TestResultStatus testResultStatus;

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public String getDefaultLang() {
        return defaultLang;
    }

    public void setDefaultLang(final String defaultLang) {
        this.defaultLang = defaultLang;
    }

    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(final Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public String getStartedBy() {
        return startedBy;
    }

    public void setStartedBy(final String startedBy) {
        this.startedBy = startedBy;
    }

    public List<TestTaskDto> getTestTaskDto() {
        return testTaskDto;
    }

    public void setTestTaskDto(final List<TestTaskDto> testTaskDto) {
        this.testTaskDto = testTaskDto;
    }

    public TestResultStatus getTestResultStatus() {
        return testResultStatus;
    }

    public void setTestResultStatus(final TestResultStatus testResultStatus) {
        this.testResultStatus = testResultStatus;
    }
}
