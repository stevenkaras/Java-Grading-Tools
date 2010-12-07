package grading;

import java.lang.reflect.Field;
import java.security.Permission;

/**
 * Utility methods for use in JUnit testsuites.
 * 
 * @author Steven Karas
 */
public class TestUtils {

	/**
	 * Returns true if a test passed. If a test didn't pass, or wasn't
	 * accessible for some reason, the test fails.
	 * 
	 * @param suite
	 *            The test suite the test belongs to
	 * @param test
	 *            The name of the test method
	 * @return true if the test passed, otherwise false
	 */
	public static boolean testPassed(Object suite, String test) {
		try {
			suite.getClass().getMethod(test, new Class<?>[0])
					.invoke(suite, new Object[0]);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Thrown when a call to System.exit() is prevented
	 * 
	 * @author Steven Karas
	 */
	public static class NoExitException extends SecurityException {
		private static final long serialVersionUID = 1L;
		public NoExitException(String string) { super(string); }
	}

	private static class NoExitSecurityManager extends SecurityManager {
		// allow anything
		public void checkPermission(Permission perm) {}
		public void checkPermission(Permission perm, Object context) {}
		public void checkExit(int status) {
			super.checkExit(status);
			throw new NoExitException("System.exit(" + status + ") called");
		}
	}

	private static SecurityManager securityManager;

	/**
	 * Prevents exits via the {@link System#exit(int)}, and throws an exception
	 * instead 
	 */
	public static void preventSystemExit() {
		securityManager = System.getSecurityManager();
		System.setSecurityManager(new NoExitSecurityManager());
	}
	
	/**
	 * Allow {@link System#exit(int)}
	 */
	public static void allowSystemExit() {
		System.setSecurityManager(securityManager);
	}

	/**
	 * Get the first declared field of the given type. Ignores access modifiers.
	 * 
	 * @param o the object to search
	 * @param type the type to retrieve
	 * @return an Object
	 */
	public static Object getFirstField(Object o, Class<?> type) {
		for (Field f : o.getClass().getDeclaredFields()) {
			if (f.getType().equals(type)) {
				f.setAccessible(true);
				try {
					return f.get(o);
				} catch (IllegalArgumentException e) {
					return null;
				} catch (IllegalAccessException e) {
					return null;
				}
			}
		}
		return null;
	}
}
