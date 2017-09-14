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
package de.interactive_instruments.etf.dal.dto.capabilities;

import java.net.URI;
import java.util.*;

import de.interactive_instruments.etf.dal.dto.IncompleteDtoException;
import de.interactive_instruments.etf.dal.dto.RepositoryItemDto;
import de.interactive_instruments.properties.Properties;

/**
 * A Test Object is an asset under test which possesses one or multiple
 * references to resources and may consist of one or multiple Test Object Types
 * {@link TestObjectTypeDto}.
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class TestObjectDto extends RepositoryItemDto {

	private Map<String, ResourceDto> resources;

	private List<TestObjectTypeDto> testObjectTypes;

	private Properties properties;

	public TestObjectDto() {}

	private TestObjectDto(final TestObjectDto other) {
		super(other);
		this.resources = other.resources;
		this.testObjectTypes = other.testObjectTypes;
		this.properties = other.properties;
	}

	public List<TestObjectTypeDto> getTestObjectTypes() {
		return testObjectTypes;
	}

	public void setTestObjectTypes(final List<TestObjectTypeDto> testObjectTypes) {
		this.testObjectTypes = testObjectTypes;
	}

	public void setTestObjectType(final TestObjectTypeDto testObjectType) {
		this.testObjectTypes = new ArrayList<TestObjectTypeDto>() {
			{
				add(testObjectType);
			}
		};
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

	public Set<String> getResourceNames() {
		return resources.keySet();
	}

	public Collection<ResourceDto> getResourceCollection() {
		return resources.values();
	}

	public Map<String, ResourceDto> getResources() {
		return resources;
	}

	public int getResourcesSize() {
		return resources != null ? resources.size() : 0;
	}

	public URI getResourceByName(final String name) {
		if (resources == null) {
			return null;
		}
		final ResourceDto r = resources.get(name);
		return r != null ? r.getUri() : null;
	}

	public void setResources(final Map<String, ResourceDto> resources) {
		this.resources = resources;
	}

	public void addResource(final ResourceDto resource) {
		if (this.resources == null) {
			this.resources = new HashMap<>();
		}
		this.resources.put(resource.getName(), resource);
	}

	public void addTestObjectType(final TestObjectTypeDto testObjectType) {
		if (this.testObjectTypes == null) {
			this.testObjectTypes = new ArrayList<>();
		}
		this.testObjectTypes.add(testObjectType);
	}

	public void ensureBasicValidity() throws IncompleteDtoException {
		super.ensureBasicValidity();
		if (resources == null) {
			throw new IllegalStateException("Required property 'resources' must be set!");
		}
		if (testObjectTypes == null) {
			throw new IllegalStateException("Required property 'testObjectTypes' must be set!");
		}
	}

	@Override
	public TestObjectDto createCopy() {
		return new TestObjectDto(this);
	}
}
