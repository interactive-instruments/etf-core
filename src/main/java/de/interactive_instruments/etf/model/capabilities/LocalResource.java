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
package de.interactive_instruments.etf.model.capabilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.commons.io.IOUtils;

import de.interactive_instruments.IFile;

/**
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class LocalResource implements Resource {

    private final String name;
    protected final IFile file;

    public LocalResource(final String name, final IFile file) {
        this.name = name;
        this.file = file;
    }

    public LocalResource(LocalResource resource) {
        this.name = resource.name;
        this.file = resource.file;
    }

    /**
     * Package ctor for the {@link Resource#create(String, URI)} method
     *
     * @param name
     *            Resource name
     * @param uri
     *            file URI
     */
    LocalResource(final String name, final URI uri) {
        this.name = name;
        this.file = new IFile(uri, name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public URI getUri() {
        return file.toURI();
    }

    public IFile getFile() {
        return file;
    }

    @Override
    public long getContentLength() throws IOException {
        return file.length();
    }

    @Override
    public InputStream openStream() throws IOException {
        return file.getInputStream();
    }

    @Override
    public byte[] getBytes() throws IOException {
        return IOUtils.toByteArray(openStream());
    }

    @Override
    public boolean exists() {
        return file.exists();
    }

    @Override
    public void release() {
        // nothing to do
    }

    @Override
    public LocalResource createCopy() {
        return new LocalResource(this);
    }
}
