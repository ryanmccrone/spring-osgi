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

package org.springframework.osgi.extensions.annotation;

import org.springframework.osgi.service.importer.support.ImportContextClassLoaderEnum;

/**
 * Spring-DM managed OSGi service <code>ClassLoader</code> property.
 * 
 * @author Andy Piper
 */
public enum ServiceReferenceClassLoader {
	CLIENT(ImportContextClassLoaderEnum.CLIENT), SERVICE_PROVIDER(ImportContextClassLoaderEnum.SERVICE_PROVIDER), UNMANAGED(
			ImportContextClassLoaderEnum.UNMANAGED);

	private ImportContextClassLoaderEnum icclValue;


	private ServiceReferenceClassLoader(ImportContextClassLoaderEnum iccl) {
		icclValue = iccl;
	}

	public String toString() {
		return icclValue.name();
	}

	public ImportContextClassLoaderEnum toImportContextClassLoader() {
		return icclValue;
	}
}
