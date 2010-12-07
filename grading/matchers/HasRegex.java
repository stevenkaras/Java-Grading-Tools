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
