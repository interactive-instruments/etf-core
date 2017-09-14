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
package de.interactive_instruments.etf.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.interactive_instruments.MediaType;
import de.interactive_instruments.properties.PropertyHolder;

/**
 *
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface OutputFormat extends Comparable<OutputFormat>, Parameterizable {

	/**
	 * Identifier for the OutputFormat
	 *
	 * @return id of the output format
	 */
	EID getId();

	/**
	 * Name of the OutputFormat
	 *
	 * @return a label for the output format
	 */
	String getLabel();

	/**
	 * The media type that will be produced
	 *
	 * @return Media type
	 */
	MediaType getMediaTypeType();

	/**
	 *
	 * @param arguments transformation arguments
	 * @param inputStream the input stream to transform
	 * @param outputStreamStream the target output stream
	 * @throws IOException streaming failed
	 */
	void streamTo(final PropertyHolder arguments,
			final InputStream inputStream,
			final OutputStream outputStreamStream) throws IOException;

	@Override
	default int compareTo(final OutputFormat o) {
		return getId().compareTo(o.getId());
	}
}
