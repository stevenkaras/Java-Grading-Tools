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
