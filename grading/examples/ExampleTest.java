/**
 * ExampleTest.java
 * 
 * Copyright (C) 2010
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

package grading.examples;

import grading.ReflectionUtils;

import org.junit.Test;

/**
 * This class, along with {@link ExampleActual} and {@link ExampleExpected}, are examples of how to
 * use the ReflectionUtils class. A simple call to
 * {@link ReflectionUtils#checkClassForExtras(Class, Class, boolean)} will compare two classes, and
 * fail automatically if discrepancies were discovered.
 * <p>
 * A couple of important notes about using this library:
 * <ul>
 * <li>Don't copy your solution into a separate package and expect it to work. The expected class
 * needs to reference the same classes that the actual does.</li>
 * <li>You don't need to write an actual solution for the expected, it just needs to have the same
 * API. It might be worthwhile to write your own solution anyways, so you can grade better, though!</li>
 * </ul>
 * 
 * @author Steven Karas
 */
public class ExampleTest {
	@Test
	public void exampleOfReflectionUtils() {
		ReflectionUtils.checkClassForExtras(ExampleExpected.class, ExampleActual.class, false);
	}

	@Test
	public void exampleOfReflectionUtils_PrivatesToo() {
		ReflectionUtils.checkClassForExtras(ExampleExpected.class, ExampleActual.class, true);
	}
}
