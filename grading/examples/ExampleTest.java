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
