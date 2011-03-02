/**
 * Matchers.java
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

package grading;

import grading.matchers.HasRegex;
import grading.matchers.MatchesRegex;

import org.hamcrest.Matcher;

/**
 * Matchers for use with the org.hamcrest Matchers and assertThat JUnit tests
 * 
 * @author Steven Karas
 */
public class Matchers {

	/**
	 * Generate a new Matcher that matches against a regular expression.
	 * 
	 * @param regex
	 * @return
	 */
	public static Matcher<String> matchesRegex(final String regex) {
		return new MatchesRegex<String>(regex);
	}

	/**
	 * Generate a new Matcher that matches against a regular expression, that
	 * matches against substrings.
	 * 
	 * @param regex
	 * @return
	 */
	public static Matcher<String> hasRegex(final String regex) {
		return new HasRegex<String>(regex);
	}
}
