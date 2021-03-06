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
package org.springframework.osgi.blueprint.container;

import java.awt.Point;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Costin Leau
 */
public class GenerifiedBean {

	private GenericHolder holder;

	public GenericHolder getHolder() {
		return holder;
	}

	public void setStringHolder(GenericHolder<String> stringHolder) {
		this.holder = stringHolder;
	}

	public void setBooleanHolder(GenericHolder<Boolean> booleanHolder) {
		this.holder = booleanHolder;
	}

	public void setPointMap(TreeMap<String, Point> pointMap) {
		System.out.println("created " + pointMap);
	}
	
	public void setConcurrentMap(ConcurrentMap map) {
		System.out.println("created " + map.getClass());
	}
	
}
