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
package de.interactive_instruments.etf.dal.dto.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.interactive_instruments.etf.model.NestedDependencyHolder;

public class TestCaseDto extends TestModelItemDto implements NestedDependencyHolder<TestCaseDto> {

    private List<TestCaseDto> dependencies;

    public TestCaseDto() {}

    private TestCaseDto(final TestCaseDto other) {
        super(other);
        this.dependencies = other.dependencies;
    }

    public List<TestStepDto> getTestSteps() {
        return (List<TestStepDto>) getChildren();
    }

    public void setTestSteps(final List<TestStepDto> testSteps) {
        setChildren(testSteps);
    }

    public void addTestStep(final TestStepDto testStep) {
        addChild(testStep);
    }

    public void setDependencies(final List<TestCaseDto> dependencies) {
        this.dependencies = dependencies;
    }

    public void addDependency(final TestCaseDto dependency) {
        if (this.dependencies == null) {
            this.dependencies = new ArrayList<>();
        }
        dependencies.add(dependency);
    }

    public Collection<TestCaseDto> getDependencies() {
        return dependencies;
    }

    @Override
    public TestCaseDto createCopy() {
        return new TestCaseDto(this);
    }
}
