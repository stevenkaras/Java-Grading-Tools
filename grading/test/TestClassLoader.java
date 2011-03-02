/**
 * TestClassLoader.java
 * 
 * Copyright (C) 2010-2011
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * See README for contact information. See LICENSE for GPL license
 */

package grading.test;
import grading.GradingClassLoader;
import grading.TestUtils;

import org.junit.*;

public class TestClassLoader {
	
	private GradingClassLoader sampleClassLoader;
	
	public TestClassLoader() {
		sampleClassLoader = GradingClassLoader.newInstance("solution.jar", "grading.test.TestClassLoader2");
		sampleClassLoader.classes.add("searchengine.SearchResult");
		sampleClassLoader.classes.add("searchengine.SearchResults");
	}
	
	@Before
	public void setUp() {
		TestUtils.preventSystemExit();
	}
	
	@After
	public void tearDown() {
		TestUtils.allowSystemExit();
	}
	
	@Test
	public void testSearchEngine_Ctor_readsFile() throws Throwable {
		sampleClassLoader.runTest();
	}
	
}
