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
package org.springframework.osgi.internal.util;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.osgi.util.internal.BeanFactoryUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ObjectUtils;

/**
 * @author Costin Leau
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/org/springframework/osgi/dependingBeans.xml")
public class BeanFactoryUtilsTest{
	@Autowired
	private GenericApplicationContext applicationContext;
	private ConfigurableListableBeanFactory bf;
	
	@Before
	public void setup(){
		bf = applicationContext.getBeanFactory();
	}
	
	@Test
	public void testADependencies() {
		String[] deps = BeanFactoryUtils.getTransitiveDependenciesForBean(bf, "a", false, null);
		assertTrue(ObjectUtils.isEmpty(deps));
	}


	@Test
	public void testBDependencies() {
		String[] deps = BeanFactoryUtils.getTransitiveDependenciesForBean(bf, "b", false, null);
		assertTrue(ObjectUtils.isEmpty(deps));
	}


	@Test
	public void testCDependencies() {
		String[] deps = BeanFactoryUtils.getTransitiveDependenciesForBean(bf, "c", false, null);
		assertTrue(Arrays.equals(new String[] { "b" }, deps));

	}


	@Test
	public void testIntDependencies() {
		String[] deps = BeanFactoryUtils.getTransitiveDependenciesForBean(bf, "int", false, null);
		assertTrue(Arrays.equals(new String[] { "c", "b" }, deps));
	}


	@Test
	public void testTransitiveDependenciesForDependsOn() {
		String[] deps = BeanFactoryUtils.getTransitiveDependenciesForBean(bf, "thread", false, null);
		assertTrue(Arrays.equals(new String[] { "buffer", "int", "c", "b" }, deps));
	}


	@Test
	public void testTransitiveFBDependencies() {
		String[] deps = BeanFactoryUtils.getTransitiveDependenciesForBean(bf, "secondBuffer", true, null);
		assertTrue(Arrays.equals(new String[] { "&field", "thread", "buffer", "int", "c", "b" }, deps));
	}


	@Test
	public void testFiltering() {
		String[] deps = BeanFactoryUtils.getTransitiveDependenciesForBean(bf, "secondBuffer", true, Number.class);
		assertTrue(Arrays.equals(new String[] { "int", "b" }, deps));
	}


	@Test
	public void testFilteringOnFB() {
		String[] deps = BeanFactoryUtils.getTransitiveDependenciesForBean(bf, "secondBuffer", true, FactoryBean.class);
		assertTrue(Arrays.equals(new String[] { "&field" }, deps));
	}


	@Test
	public void testNestedDependencies() throws Exception {
		String[] deps = BeanFactoryUtils.getTransitiveDependenciesForBean(bf, "nested", true, null);
		assertTrue(Arrays.equals(new String[] { "int", "c", "b" }, deps));
	}


	@Test
	public void testNestedFactoryDependencies() throws Exception {
		String[] deps = BeanFactoryUtils.getTransitiveDependenciesForBean(bf, "nestedFB", true, null);
		assertTrue(Arrays.equals(new String[] { "thread", "buffer", "int", "c", "b" }, deps));
	}


	@Test
	public void testNestedCycle() throws Exception {
		String[] deps = BeanFactoryUtils.getTransitiveDependenciesForBean(bf, "nestedCycle", true, null);
		assertTrue(Arrays.equals(new String[] { "nestedCycle", "thread", "buffer", "int", "c", "b" }, deps));
	}
}
