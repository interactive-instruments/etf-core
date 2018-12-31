/**
 * Copyright 2017-2018 European Union, interactive instruments GmbH
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

import java.util.regex.Pattern;

import de.interactive_instruments.ImmutableVersion;
import de.interactive_instruments.Versionable;

/**
 * The ETF Group and Artifact ID class is intended to provide
 * identifiers for a group of objects with different versions
 * in the ETF environment.
 *
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public interface EGAID extends Versionable, Comparable {

	Pattern EGAID = Pattern.compile("egaid\\.([a-z][a-z1-9\\-.]*)\\.([a-z][a-z1-9\\-]*)");
	Pattern EGAID_WP = Pattern.compile("([a-z][a-z1-9\\-.]*)\\.([a-z][a-z1-9\\-]*)");
	Pattern EGAID_REF = Pattern
			.compile("egaid\\.([a-z][a-z1-9\\-.]*)\\.([a-z][a-z1-9\\-]*)(:([0-9]+\\.[0-9]+\\.[0-9]+(-SNAPSHOT)?|latest))?");

	String getArtifactId();

	String getGroupId();

	String getEgaId();

	default String getEgaIdRef() {
		final ImmutableVersion v = this.getVersion();
		if (v != null) {
			return getEgaId() + ":" + v;
		}
		return getEgaId();
	}

	@Override
	default int compareTo(final Object o) {
		if (o instanceof EGAID) {
			return ((EGAID) o).getEgaIdRef().compareToIgnoreCase(getEgaIdRef());
		} else if (o instanceof EgaidHolder) {
			final EGAID h = ((EgaidHolder) o).getEgaId();
			return h.getEgaIdRef().compareToIgnoreCase(getEgaIdRef());
		}
		return -1;
	}
}
