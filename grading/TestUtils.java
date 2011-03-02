/**
 * TestUtils.java
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

import java.lang.reflect.Field;
import java.security.Permission;
import java.util.Arrays;

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
		public int status;
		public NoExitException(int status) {
			super("System.exit(" + status + ") called");
			this.status = status;
		}
	}

	private static class NoExitSecurityManager extends SecurityManager {
		// allow anything
		public void checkPermission(Permission perm) {}
		public void checkPermission(Permission perm, Object context) {}
		public void checkExit(int status) {
			super.checkExit(status);
			NoExitException t = new NoExitException(status);
			// remove this code from the stack trace
			StackTraceElement[] st = t.getStackTrace();
			for (int i = 0; i < st.length; i++) {
				if ((!st[i].getClassName().equals(System.class.getName()))
						|| (!st[i].getMethodName().equals("exit")))
					continue;
				st = Arrays.copyOfRange(st, i+1, st.length);
				break;
			}
			t.setStackTrace(st);
			throw t;
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
	
	/**
	 * Get a field of a class, ignoring visibility modifiers. Wraps exceptions
	 * in illegal argument exception, since this is meant for use with unit
	 * tests. Example usage:
	 * <p>
	 * <pre>getField(o, "privateField", String.class)</pre> 
	 * 
	 * @param <T> The type of the field
	 * @param obj The object to pull from (null if static)
	 * @param name the name of the field
	 * @param type the class object of the type
	 * @return the field
	 */
	public static <T> T getField(Object obj, String name, Class<T> type) {
		try {
			Field f = obj.getClass().getDeclaredField(name);
			f.setAccessible(true);
			
			if (!f.getType().equals(type))
				throw new IllegalArgumentException("name is not of type"+type);
			@SuppressWarnings("unchecked")
			T t = (T) f.get(obj);
			return t;
		} catch (SecurityException e) {
			throw new IllegalArgumentException(e.getMessage());
		} catch (NoSuchFieldException e) {
			throw new IllegalArgumentException(e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		
	}
}
