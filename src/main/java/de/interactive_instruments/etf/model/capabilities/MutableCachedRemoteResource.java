/**
 * Copyright 2010-2017 interactive instruments GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.interactive_instruments.etf.model.capabilities;

import java.io.IOException;
import java.util.Map;

import de.interactive_instruments.exceptions.ExcUtils;

/**
 * @author Jon Herrmann ( herrmann aT interactive-instruments doT de )
 */
public class MutableCachedRemoteResource extends CachedRemoteResource implements MutableRemoteResource {

	MutableCachedRemoteResource(final RemoteResource resource) {
		super(!(resource instanceof MutableRemoteResource) ? new MutableSecuredRemoteResource(resource) : resource);
	}

	private MutableCachedRemoteResource(final CachedRemoteResource other) {
		super(other);
	}

	public boolean setQueyParameters(final Map<String, String> kvp) {
		final boolean changed = ((MutableRemoteResource) wrapped).setQueyParameters(kvp);
		if (changed) {
			try {
				recache();
			} catch (final IOException ign) {
				ExcUtils.suppress(ign);
			}
		}
		return changed;
	}

	@Override
	public MutableCachedRemoteResource createCopy() {
		return new MutableCachedRemoteResource(this);
	}
}
