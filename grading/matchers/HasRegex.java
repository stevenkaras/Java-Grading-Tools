/**
 * HasRegex.java
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

package grading.matchers;

import java.util.regex.Pattern;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class HasRegex<T extends CharSequence> extends BaseMatcher<T> {
	
	private Pattern regex;
	
	public HasRegex(String regex) {
		this.regex = Pattern.compile(regex);
	}

	public boolean matches(Object item) {
		if (! (item instanceof CharSequence))
			return false;
		return regex.matcher((CharSequence) item).matches();
	}
	
	public void describeTo(Description description) {
		description.appendText("matches ").appendText(regex.pattern());
	}
}
