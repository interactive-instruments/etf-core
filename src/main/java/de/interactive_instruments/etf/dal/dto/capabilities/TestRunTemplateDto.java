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
package de.interactive_instruments.etf.dal.dto.capabilities;

import java.util.*;

import de.interactive_instruments.etf.dal.dto.Dto;
import de.interactive_instruments.etf.dal.dto.DtoValidityCheckUtils;
import de.interactive_instruments.etf.dal.dto.IncompleteDtoException;
import de.interactive_instruments.etf.dal.dto.RepositoryItemDto;
import de.interactive_instruments.etf.dal.dto.test.ExecutableTestSuiteDto;
import de.interactive_instruments.etf.model.ParameterSet;
import de.interactive_instruments.etf.model.capabilities.TestObjectType;
import de.interactive_instruments.properties.Properties;

/**
 * A template to start one ore multiple Test Runs
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class TestRunTemplateDto extends RepositoryItemDto {

    private List<ExecutableTestSuiteDto> executableTestSuites;
    private List<TestObjectDto> testObjects;
    private ParameterSet parameters;
    private Properties properties;
    private Set<TestObjectType> supportedTestObjectTypes;

    public TestRunTemplateDto() {}

    private TestRunTemplateDto(final TestRunTemplateDto other) {
        super(other);
        this.executableTestSuites = other.executableTestSuites;
        this.testObjects = other.testObjects;
        this.parameters = other.parameters;
        this.properties = other.properties;
    }

    public List<ExecutableTestSuiteDto> getExecutableTestSuites() {
        return executableTestSuites;
    }

    public void setExecutableTestSuites(final List<ExecutableTestSuiteDto> executableTestSuites) {
        this.executableTestSuites = executableTestSuites;
    }

    public void addExecutableTestSuite(final ExecutableTestSuiteDto executableTestSuite) {
        if (this.executableTestSuites == null) {
            this.executableTestSuites = new ArrayList<>(8);
        }
        this.executableTestSuites.add(executableTestSuite);
    }

    public List<TestObjectDto> getTestObjects() {
        return testObjects;
    }

    public void setTestObjects(final List<TestObjectDto> testObjects) {
        this.testObjects = testObjects;
    }

    public void addTestObject(final TestObjectDto testObjectDto) {
        if (this.testObjects == null) {
            this.testObjects = new ArrayList<>(1);
        }
        this.testObjects.add(testObjectDto);
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

    public ParameterSet getParameters() {
        return parameters;
    }

    public void setParameters(final ParameterSet parameters) {
        this.parameters = parameters;
    }

    /**
     * Get all supported Test Object Types that can be used with this template.
     *
     * If the
     *
     * @return
     */
    public Collection<TestObjectType> getSupportedTestObjectTypes() {
        if (this.supportedTestObjectTypes == null && this.executableTestSuites != null) {
            this.supportedTestObjectTypes = new LinkedHashSet<>();
            for (final ExecutableTestSuiteDto ets : this.executableTestSuites) {
                if (ets.getSupportedTestObjectTypes() != null) {
                    this.supportedTestObjectTypes.addAll(ets.getSupportedTestObjectTypes());
                }
            }
        }
        return this.supportedTestObjectTypes;
    }

    /**
     * Restrict the Test Object Types that can be used with this template.
     *
     * Note: The passed Test Object Types must be compatible with the Executable Test Suites and the Test Object
     *
     * @param supportedTestObjectTypes
     *            collection of Test Object Types
     * @throws IllegalArgumentException
     *             if incompatible Test Object Types are used
     */
    public void setSupportedTestObjectTypes(final Collection<? extends TestObjectType> supportedTestObjectTypes) {
        if (this.executableTestSuites != null) {
            for (final TestObjectType to : supportedTestObjectTypes) {
                for (final ExecutableTestSuiteDto ets : this.executableTestSuites) {
                    if (!to.isInstanceOf(ets.getSupportedTestObjectTypes())) {
                        throw new IllegalArgumentException("Test Object Type '"
                                + to.getLabel() +
                                "'  not supported by the Executable Test Suite '"
                                + ets.getLabel() + "'");
                    }
                }
            }
        }
        this.supportedTestObjectTypes = new LinkedHashSet<>(supportedTestObjectTypes);
    }

    public void ensureBasicValidity() throws IncompleteDtoException {
        super.ensureBasicValidity();
        DtoValidityCheckUtils.ensureNotNullOrEmpty("executableTestSuites", executableTestSuites);
        DtoValidityCheckUtils.ensureNotNullOrEmpty("supportedTestObjectTypes", getSupportedTestObjectTypes());
        for (final TestObjectType to : this.supportedTestObjectTypes) {
            for (final ExecutableTestSuiteDto ets : this.executableTestSuites) {
                if (!to.isInstanceOf(ets.getSupportedTestObjectTypes())) {
                    throw new IllegalArgumentException("Test Object Type '"
                            + to.getLabel() +
                            "'  not supported by the Executable Test Suite '"
                            + ets.getLabel() + "'");
                }
            }
            if (this.testObjects != null) {
                for (final TestObjectDto testObject : this.testObjects) {
                    if (!to.isInstanceOf(testObject.getTestObjectTypes())) {
                        throw new IllegalArgumentException("Test Object Type '"
                                + to.getLabel() +
                                "'  not supported by the specified Test Object '"
                                + testObject.getLabel() + "'");
                    }
                }
            }
        }
    }

    @Override
    public Dto createCopy() {
        return new TestRunTemplateDto(this);
    }
}
