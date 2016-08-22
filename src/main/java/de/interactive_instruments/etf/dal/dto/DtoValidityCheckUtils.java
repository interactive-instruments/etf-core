/**
 * Copyright 2010-2016 interactive instruments GmbH
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
package de.interactive_instruments.etf.dal.dto;

import de.interactive_instruments.etf.dal.dto.result.TestTaskResultDto;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * @author J. Herrmann ( herrmann <aT) interactive-instruments (doT> de )
 */
public class DtoValidityCheckUtils {

	private DtoValidityCheckUtils() {

	}

	public static void ensureNotNullOrEmpty(final String name, final Collection c) {
		if (c == null) {
			throw new IllegalStateException("Required property '" + name + "' (" + c.getClass().getName() + ") must be set!");
		}
		if (c.isEmpty()) {
			throw new IllegalStateException("Required property '" + name + "' (" + c.getClass().getName() + ") is empty!");
		}
	}

	public static void ensureNotNullOrEmpty(final String name, final Map m) {
		if (m == null) {
			throw new IllegalStateException("Required property '" + name + "' (" + m.getClass().getName() + ") must be set!");
		}
		if (m.isEmpty()) {
			throw new IllegalStateException("Required property '" + name + "' (" + m.getClass().getName() + ") is empty!");
		}
	}

	public static void ensureNotNullOrEmpty(final String name, final String str) {
		if (str == null) {
			throw new IllegalStateException("Required property '" + str + "' must be set!");
		}
		if (str.trim().isEmpty()) {
			throw new IllegalStateException("Required property '" + str + "' is empty!");
		}
	}

	public static void ensureNotNullAndHasId(final String name, Dto dto) {
		if (dto == null) {
			throw new IllegalStateException("Required property '" + name + "' (" + dto.getClass().getName() + ") must be set!");
		}
		if (dto.getId() == null) {
			throw new IllegalStateException("Required property '" + name + "' (" + dto.getClass().getName() + ") is set but does not possess an ID!");
		}
	}

	public static void ensureNotNull(final String name, Object obj) {
		if (obj == null) {
			throw new IllegalStateException("Required property '" + name + "' must be set!");
		}
	}
}
