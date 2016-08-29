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

package de.interactive_instruments.etf.testdriver;

import de.interactive_instruments.etf.dal.dto.Dto;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * Walks through a directory and creates files with associated {@link TypeBuilder}s.
 *
 * Realizes the Chain of responsibility pattern.
 *
 * @author herrmann@interactive-instruments.de.
 */
public final class TypeBuildingFileVisitor implements FileVisitor<Path> {

	@FunctionalInterface
	public interface TypeBuilder<T extends Dto> {
		/**
		 * Try to build the type and return the type on success
		 *
		 * @param file to process
		 * @return Type if responsible, null otherwise
		 */
		T process(final Path file);
	}

	private final List<TypeBuilder> builders;
	// path -> Dto
	private final Map<String, Dto> propagatedFiles = new LinkedHashMap<>();
	// EID -> path
	private final Map<String, String> duplicateCheck = new LinkedHashMap<>();

	public TypeBuildingFileVisitor(final List<TypeBuilder> builders) {
		this.builders = Objects.requireNonNull(builders);
	}

	public Map<String, Dto> getPropagatedFiles() {
		return Collections.unmodifiableMap(propagatedFiles);
	}

	@Override
	public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(final Path file, BasicFileAttributes attrs) throws IOException {
		for (final TypeBuilder builder : builders) {
			final Dto dto = builder.process(file);
			if(dto!=null) {
				final String existingPath = duplicateCheck.get(dto.getId().getId());
				if(existingPath!=null && !file.toString().equals(existingPath)) {
					throw new IllegalStateException("Type with ID \""+dto.getId().getId()+
							"\" has already been created during this refresh run of the Test Driver. "
							+ "Types with duplicate Ids created from file \""+
							file+"\" and file \""+existingPath+"\" !");
				}
				propagatedFiles.put(file.toString(), dto);
				duplicateCheck.put(dto.getId().getId(), file.toString());
				break;
			}
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(final Path file, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(final Path dir, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}
}
