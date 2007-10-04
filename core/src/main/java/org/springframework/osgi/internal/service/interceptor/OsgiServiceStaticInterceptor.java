/*
 * Copyright 2002-2006 the original author or authors.
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
package org.springframework.osgi.internal.service.interceptor;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.springframework.osgi.ServiceUnavailableException;
import org.springframework.osgi.internal.service.support.ServiceWrapper;
import org.springframework.util.Assert;

/**
 * Interceptor offering static behavior around an OSGi service. If the OSGi
 * becomes unavailable, no look up or retries will be executed, the interceptor
 * throwing an exception.
 * 
 * @author Costin Leau
 * 
 */
public class OsgiServiceStaticInterceptor extends OsgiServiceClassLoaderInvoker {

	private ServiceWrapper wrapper;

	public OsgiServiceStaticInterceptor(BundleContext context, ServiceReference reference, int contextClassLoader,
			ClassLoader classLoader) {
		super(context, reference, contextClassLoader, classLoader);
		Assert.notNull(reference, "a not null service reference is required");
		this.wrapper = new ServiceWrapper(reference, context);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.osgi.service.interceptor.OsgiServiceInvoker#getTarget()
	 */
	protected Object getTarget() {
		// service has died, clean up
		if (!wrapper.isServiceAlive()) {
			wrapper.cleanup();
			throw new ServiceUnavailableException("Service n/a", wrapper.getClass(), null);
		}

		return wrapper.getService();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.osgi.service.interceptor.OsgiServiceInvoker#getServiceReference()
	 */
	protected ServiceReference getServiceReference() {
		return wrapper.getReference();
	}

	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other instanceof OsgiServiceStaticInterceptor) {
			OsgiServiceStaticInterceptor oth = (OsgiServiceStaticInterceptor) other;
			return (this.context.equals(oth.context) && contextClassLoader == oth.contextClassLoader && wrapper.equals(oth.wrapper));
		}
		else
			return false;
	}
}