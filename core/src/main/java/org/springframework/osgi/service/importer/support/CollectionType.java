/*
 * Copyright 2006-2009 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.osgi.service.importer.support;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

/**
 * Enumeration-like class which indicates the supported Spring DM managed OSGi
 * service collection types. This class is used mainly for configuration
 * purposes (such as parsing the OSGi namespace).
 * 
 * @author Costin Leau
 */
public enum CollectionType {
	LIST(2, "LIST", List.class), 
	SET(3, "SET", Set.class), 
	SORTED_LIST(4, "SORTED_LIST", List.class),
	SORTED_SET(5, "SORTED_SET", SortedSet.class);
	
		/** unused */
	// public static final CollectionType COLLECTION = new CollectionType(1,
	// "collection", OsgiServiceCollection.class);
	
	/** collection type */
	private final Class<?> collectionClass;
	private final int code;
	private final String label;

	/**
	 * Returns the actual collection class used underneath.
	 * 
	 * @return collection class
	 */
	public Class<?> getCollectionClass() {
		return collectionClass;
	}

	private CollectionType(int code, String label, Class<?> collectionClass) {
		this.code = code;
		this.label = label;
		this.collectionClass = collectionClass;
	}
}