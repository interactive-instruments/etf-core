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
import java.util.Objects;
import java.util.stream.Collectors;

import de.interactive_instruments.etf.dal.dto.DtoValidityCheckUtils;
import de.interactive_instruments.etf.dal.dto.IncompleteDtoException;
import de.interactive_instruments.etf.dal.dto.ModelItemTreeNode;
import de.interactive_instruments.etf.dal.dto.RepositoryItemDto;
import de.interactive_instruments.etf.dal.dto.capabilities.ComponentDto;
import de.interactive_instruments.etf.dal.dto.capabilities.TestObjectTypeDto;
import de.interactive_instruments.etf.dal.dto.translation.TranslationTemplateBundleDto;
import de.interactive_instruments.etf.model.*;
import de.interactive_instruments.properties.Properties;

public class ExecutableTestSuiteDto extends RepositoryItemDto
        implements ModelItemTreeNode<TestModelItemDto>, NestedDependencyHolder<ExecutableTestSuiteDto> {

    private ComponentDto testDriver;
    private TranslationTemplateBundleDto translationTemplateBundle;
    private List<TestObjectTypeDto> supportedTestObjectTypes;
    private List<ExecutableTestSuiteDto> dependencies;
    private List<TestObjectTypeDto> consumableResultTestObjectTypes;
    private List<TestCaseDto> parameterizedTestCases;
    private EidHolderMap<TestModelItemDto> testModules;
    private ParameterSet parameters;
    private Properties properties;

    public ExecutableTestSuiteDto() {}

    private ExecutableTestSuiteDto(final ExecutableTestSuiteDto other) {
        super(other);
        this.testDriver = other.testDriver;
        this.translationTemplateBundle = other.translationTemplateBundle;
        this.supportedTestObjectTypes = other.supportedTestObjectTypes;
        this.dependencies = other.dependencies;
        this.consumableResultTestObjectTypes = other.consumableResultTestObjectTypes;
        this.parameterizedTestCases = other.parameterizedTestCases;
        this.testModules = other.testModules.createCopy();
        this.parameters = other.getParameters();
    }

    public ComponentDto getTestDriver() {
        return testDriver;
    }

    public void setTestDriver(final ComponentDto testDriver) {
        this.testDriver = testDriver;
    }

    public TranslationTemplateBundleDto getTranslationTemplateBundle() {
        return translationTemplateBundle;
    }

    public void setTranslationTemplateBundle(final TranslationTemplateBundleDto translationTemplateBundle) {
        this.translationTemplateBundle = translationTemplateBundle;
    }

    public List<TestObjectTypeDto> getSupportedTestObjectTypes() {
        return supportedTestObjectTypes;
    }

    public void setSupportedTestObjectTypes(final List<TestObjectTypeDto> supportedTestObjectTypes) {
        this.supportedTestObjectTypes = supportedTestObjectTypes;
    }

    public void addSupportedTestObjectType(final TestObjectTypeDto supportedTestObjectType) {
        if (this.supportedTestObjectTypes == null) {
            this.supportedTestObjectTypes = new ArrayList<>(2);
        }
        this.supportedTestObjectTypes.add(supportedTestObjectType);
    }

    public void setDependencies(final List<ExecutableTestSuiteDto> dependencies) {
        this.dependencies = dependencies;
    }

    public void setDependencies(final EidHolderMap<ExecutableTestSuiteDto> dependencies) {
        this.dependencies = dependencies.asList();
    }

    public void addDependency(final ExecutableTestSuiteDto dependency) {
        if (this.dependencies == null) {
            this.dependencies = new ArrayList<>();
        }
        dependencies.add(dependency);
    }

    public Collection<ExecutableTestSuiteDto> getDependencies() {
        return dependencies;
    }

    public List<TestObjectTypeDto> getConsumableResultTestObjectTypes() {
        return consumableResultTestObjectTypes;
    }

    public void setConsumableResultTestObjectTypes(final List<TestObjectTypeDto> consumableResultTestObjectTypes) {
        this.consumableResultTestObjectTypes = consumableResultTestObjectTypes;
    }

    public List<TestCaseDto> getParameterizedTestCases() {
        return parameterizedTestCases;
    }

    public void setParameterizedTestCases(final List<TestCaseDto> parameterizedTestCases) {
        this.parameterizedTestCases = parameterizedTestCases;
    }

    public ParameterSet getParameters() {
        if (parameters.getParameter("TestsToExecute") == null) {
            parameters.addParameter(new ParameterSet.MutableParameter()
                    .setName("TestCasesToExecute")
                    .setType("choice")
                    .setDefaultValue("*")
                    .setRequired(false));
            parameters.addParameter(new ParameterSet.MutableParameter()
                    .setName("TestCasesToIgnore")
                    .setType("choice")
                    .setDefaultValue("$^")
                    .setRequired(false));
            parameters.addParameter(new ParameterSet.MutableParameter()
                    .setName("AssertionsToExecute")
                    .setType("choice")
                    .setDefaultValue("*")
                    .setRequired(false));
            parameters.addParameter(new ParameterSet.MutableParameter()
                    .setName("AssertionsToIgnore")
                    .setType("choice")
                    .setDefaultValue("$^")
                    .setRequired(false));
            parameters.addParameter(new ParameterSet.MutableParameter()
                    .setName("maxErrors")
                    .setType("int")
                    .setDefaultValue("-1")
                    .setAllowedValues("(-1|[1-9]\\d*)$")
                    .setRequired(false));
        }
        return parameters;
    }

    public void setParameters(final ParameterSet parameterSet) {
        this.parameters = parameterSet;
    }

    public Properties properties() {
        if (properties == null) {
            properties = new Properties();
        }
        return properties;
    }

    public void properties(final Properties properties) {
        this.properties = properties;
    }

    public List<TestModuleDto> getTestModules() {
        return (List<TestModuleDto>) getChildren();
    }

    public void setTestModules(final List<TestModuleDto> testModules) {
        setChildren(testModules);
    }

    public void addTestModule(final TestModuleDto testModule) {
        addChild(testModule);
    }

    @Override
    public List<? extends TestModelItemDto> getChildren() {
        return testModules != null ? testModules.values().stream().collect(Collectors.toList()) : null;
    }

    @Override
    public EidMap<? extends TestModelItemDto> getChildrenAsMap() {
        return testModules;
    }

    @Override
    public void addChild(final TestModelItemDto child) {
        if (this.testModules == null) {
            this.testModules = new DefaultEidHolderMap<>();
        }
        Objects.requireNonNull(child);
        Objects.requireNonNull(child.getId(), "Cannot add item whose ID is null");
        this.testModules.put(child.getId(), child);
    }

    @Override
    public void setChildren(final List<? extends TestModelItemDto> children) {
        if (this.testModules == null) {
            this.testModules = new DefaultEidHolderMap<>();
        }
        // use getChildrenAsMap().clear() instead to delete children
        Objects.requireNonNull(children, "Attempt to set children to null").forEach(c -> addChild(c));
    }

    // Todo move to immutable iface
    private long lowestTestLevelItemSize = -1;

    public long getLowestLevelItemSize() {
        if (lowestTestLevelItemSize == -1) {
            lowestTestLevelItemSize = 0;
            if (getTestModules() != null) {
                getTestModules().stream().filter(testModuleDto -> testModuleDto.getTestCases() != null)
                        .forEach(
                                testModuleDto -> testModuleDto.getTestCases().stream()
                                        .filter(testCaseDto -> testCaseDto.getTestSteps() != null)
                                        .forEach(testCaseDto -> testCaseDto.getTestSteps().stream()
                                                .filter(testStepDto -> testStepDto.getTestAssertions() != null)
                                                .forEach(testStepDto -> {
                                                    lowestTestLevelItemSize += testStepDto.getTestAssertions().size();
                                                })));
                if (lowestTestLevelItemSize > 0) {
                    return lowestTestLevelItemSize;
                }
                getTestModules().stream().filter(testModuleDto -> testModuleDto.getTestCases() != null)
                        .forEach(testModuleDto -> testModuleDto.getTestCases().stream()
                                .filter(testCaseDto -> testCaseDto.getTestSteps() != null).forEach(testCaseDto -> {
                                    lowestTestLevelItemSize += testCaseDto.getTestSteps().size();
                                }));
            }
        }
        return lowestTestLevelItemSize;
    }

    public void ensureBasicValidity() throws IncompleteDtoException {
        super.ensureBasicValidity();
        DtoValidityCheckUtils.ensureNotNullOrEmpty("supportedTestObjectTypes", supportedTestObjectTypes);
        DtoValidityCheckUtils.ensureNotNullAndHasId("testDriver", testDriver);
    }

    @Override
    public ExecutableTestSuiteDto createCopy() {
        return new ExecutableTestSuiteDto(this);
    }
}
