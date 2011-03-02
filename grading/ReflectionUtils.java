/**
 * ReflectionUtils.java
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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;

/**
 * This class packages useful methods for writing JUnit tests to check for API
 * compliance. They efficiently wrap Reflection into useful functions/tests.
 * <p>
 * I personally suggest using the {@link #checkClassForExtras(Class, Class, boolean)} method in
 * tests, since it performs the report compilation on its own. --steven.
 * 
 * @author Steven Karas
 * @version 1.0
 */
public class ReflectionUtils {
	/**
	 * Checks whether the provided class implements an interface directly.
	 * 
	 * @param clazz
	 *            the class to check
	 * @param interfaze
	 *            the interface to check for
	 * @return true if clazz implements interfaze, otherwise false
	 */
	public static boolean implementsInterface(Class<?> clazz, Class<?> interfaze) {
		Class<?>[] interfaces = clazz.getInterfaces();
		for (Class<?> checking : interfaces)
			if (checking.equals(interfaze))
				return true;
		return false;
	}

	/**
	 * Compare two classes, and generate a report on extra members present in actual that are not
	 * present in expected. Will also make note of various discrepancies between modifiers and more.
	 * Note that this will not check for missing members or interfaces.
	 * 
	 * @param expected
	 * @param actual
	 * @param checkPrivates
	 *            Whether to report extra private members
	 * @return a list of discrepancies found
	 */
	public static List<String> checkForExtras(Class<?> expected, Class<?> actual,
			boolean checkPrivates) {
		// check methods
		List<String> results = new ArrayList<String>();
		if (!actual.getSimpleName().equals(expected.getSimpleName()))
			results.add("Expected class name" + expected.getSimpleName() + " but was really: "
					+ actual.getSimpleName());
		if (actual.getModifiers() != expected.getModifiers())
			results.add("Expected class modifiers: " + Modifier.toString(expected.getModifiers())
					+ " but was really: " + Modifier.toString(actual.getModifiers()));
		if (!expected.getSuperclass().equals(actual.getSuperclass()))
			results.add("Expected class to extend " + expected.getSuperclass().getSimpleName()
					+ " but really extends " + actual.getSuperclass().getSimpleName());
		results.addAll(checkForExtraIFaces(expected, actual));
		results.addAll(checkForExtraCTors(expected, actual, checkPrivates));
		results.addAll(checkForExtraMethods(expected, actual, checkPrivates));
		results.addAll(checkForExtraFields(expected, actual, checkPrivates));
		return results;
	}

	/**
	 * Compare two classes' interfaces, and report any extras that appear in
	 * the actual that don't appear in the expected.
	 * Note that this will not check for missing interfaces.
	 * 
	 * @param expected
	 * @param actual
	 * @return a list of discrepancies found
	 */
	public static List<String> checkForExtraIFaces(Class<?> expected, Class<?> actual) {
		List<String> results = new ArrayList<String>();
		for (Class<?> interfaze : checkForExtrasGeneric(expected.getInterfaces(), actual
				.getInterfaces())) {
			results.add("Found extra interface: " + interfaze.getSimpleName());
		}
		return results;
	}

	/**
	 * Compare two classes' constructors, and report any extras that appear in
	 * the actual that don't appear in the expected. Also makes note of
	 * discrepancies between the expected and actual access modifiers.
	 * Note that this will not check for missing constructors.
	 * 
	 * @param expected
	 * @param actual
	 * @param checkPrivates
	 *            Whether to report extra private constructors
	 * @return a list of discrepancies found
	 */
	public static List<String> checkForExtraCTors(Class<?> expected, Class<?> actual,
			boolean checkPrivates) {
		List<String> results = new ArrayList<String>();
		for (Constructor<?> ctor : actual.getDeclaredConstructors()) {
			try {
				// find an appropriate constructor
				Constructor<?> candidate = null;
				for (Constructor<?> exCtor : expected.getDeclaredConstructors()) {
					if (compareClassLists(ctor.getParameterTypes(), exCtor.getParameterTypes())) {
						candidate = exCtor;
						break;
					}
				}
				if (candidate == null)
					throw new NoSuchMethodException();
				
				// now compare modifiers
				if (candidate.getModifiers() != ctor.getModifiers())
					results.add(ctorSignature(ctor) + " had different modifiers than expected: "
							+ Modifier.toString(candidate.getModifiers()));
				// now compare throws
				for (Class<?> extra : checkForExtrasGeneric(candidate.getExceptionTypes(), ctor
						.getExceptionTypes())) {
					results.add(ctorSignature(ctor) + " throws an extra type: "
							+ extra.getSimpleName());
				}
				// check any other differences between actual and expected ctor
			} catch (SecurityException e) {
				throw new UnknownError(e.getMessage());
			} catch (NoSuchMethodException e) {
				if (!checkPrivates && Modifier.isPrivate(ctor.getModifiers())) {
					continue;
				}
				results.add("Found extra ctor: " + ctorSignature(ctor));
			}
		}
		return results;
	}

	/**
	 * Construct a canonical signature for the given constructor
	 * 
	 * @param ctor
	 * @return
	 */
	private static String ctorSignature(Constructor<?> ctor) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(ctor.getDeclaringClass().getSimpleName());
		buffer.append('(');
		for (Class<?> parameter : ctor.getParameterTypes()) {
			buffer.append(parameter.getSimpleName());
			buffer.append(',');
		}
		if (ctor.getParameterTypes().length > 0)
			buffer.deleteCharAt(buffer.length() - 1);
		buffer.append(')');
		return buffer.toString();
	}

	/**
	 * Compare two classes' methods, and report any extras that appear in the
	 * actual that don't appear in the expected. Also makes note of
	 * discrepancies between the expected and actual access modifiers.
	 * Note that this will not check for missing methods.
	 * 
	 * @param expected
	 * @param actual
	 * @param checkPrivates
	 *            Whether to report extra private methods
	 * @return a list of discrepancies found
	 */
	public static List<String> checkForExtraMethods(Class<?> expected, Class<?> actual,
			boolean checkPrivates) {
		List<String> results = new ArrayList<String>();
		for (Method method : actual.getDeclaredMethods()) {
			try {
				// find an appropriate method
				Method candidate = null;
				for (Method exMeth : expected.getDeclaredMethods()) {
					if (method.getName().equals(exMeth.getName()) &&
							compareClassLists(method.getParameterTypes(), exMeth.getParameterTypes())) {
						candidate = exMeth;
						break;
					}
				}
				if (candidate == null)
					throw new NoSuchMethodException();
				
				// compare return types
				if (!compareClasses(candidate.getReturnType(), method.getReturnType()))
					results.add(methodSignature(method)
							+ " had different return type than expected: "
							+ method.getReturnType().getName());
				// compare modifiers
				if (candidate.getModifiers() != method.getModifiers())
					results.add(methodSignature(method)
							+ " had different modifiers than expected: "
							+ Modifier.toString(method.getModifiers()));
				// compare throws
				for (Class<?> extra : checkForExtrasGeneric(candidate.getExceptionTypes(), method
						.getExceptionTypes())) {
					results.add(methodSignature(method) + " throws an extra type: "
							+ extra.getSimpleName());
				}
			} catch (SecurityException e) {
				throw new IllegalStateException(e.getMessage(), e);
			} catch (NoSuchMethodException e) {
				if (!checkPrivates && Modifier.isPrivate(method.getModifiers())) {
					continue;
				}
				results.add("Found extra method: " + Modifier.toString(method.getModifiers()) + " "
						+ method.getReturnType().getSimpleName() + " " + methodSignature(method));
			}
		}
		return results;
	}

	/**
	 * Compare two classes' fields, and report any extras that appear in the
	 * actual that don't appear in the expected. Also makes note of
	 * discrepancies between the expected and actual access modifiers.
	 * Note that this will not check for missing fields.
	 * 
	 * @param expected
	 * @param actual
	 * @param checkPrivates
	 *            Whether to report extra private fields
	 * @return a list of discrepancies found
	 */
	public static List<String> checkForExtraFields(Class<?> expected, Class<?> actual,
			boolean checkPrivates) {
		List<String> results = new ArrayList<String>();
		for (Field field : actual.getDeclaredFields()) {
			if (field.isSynthetic())
				continue; // we don't care about compiler-generated fields
			int modifiers = field.getModifiers();
			if (!checkPrivates && Modifier.isPrivate(modifiers)) {
				continue;
			}
			if (Modifier.isPrivate(modifiers) && Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers)) {
				// always warn on this usage
				results.add("Private static non-final field: " + field.getName());
				continue;
			}
			try {
				Field candidate = expected.getDeclaredField(field.getName());
				if (field.getModifiers() != candidate.getModifiers())
					results.add(fieldName(field) + " had different modifiers than expected: "
							+ Modifier.toString(candidate.getModifiers() ^ field.getModifiers()));
				if (!compareClasses(field.getType(), candidate.getType()))
					results.add(fieldName(field) + " had different type than expected: "
							+ candidate.getType().getSimpleName());
			} catch (SecurityException e) {
				throw new IllegalStateException(e.getMessage(), e);
			} catch (NoSuchFieldException e) {
				if (!checkPrivates && Modifier.isPrivate(modifiers)) {
					continue;
				}
				results.add("Found extra field: " + fieldName(field));
			}
		}
		return results;
	}

	/**
	 * Compares two class arrays and returns an array of all the classes present in actuals that are
	 * not in expecteds.
	 * 
	 * @param expecteds
	 * @param actuals
	 * @return
	 */
	private static Class<?>[] checkForExtrasGeneric(Class<?>[] expecteds, Class<?>[] actuals) {
		ArrayList<Class<?>> temp = new ArrayList<Class<?>>();
		for (Class<?> actual : actuals) {
			boolean found = false;
			for (Class<?> expected : expecteds) {
				if (compareClasses(expected, actual))
					found = true;
			}
			if (!found)
				temp.add(actual);
		}
		return temp.toArray(new Class<?>[0]);
	}

	/**
	 * Build a canonical signature for the given method
	 * 
	 * @param method
	 * @return
	 */
	private static String methodSignature(Method method) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(method.getName());
		buffer.append('(');
		for (Class<?> parameter : method.getParameterTypes()) {
			buffer.append(parameter.getSimpleName());
			buffer.append(',');
		}
		if (method.getParameterTypes().length > 0)
			buffer.deleteCharAt(buffer.length() - 1);
		buffer.append(')');
		return buffer.toString();
	}

	/**
	 * Build a canonical signature for the given field
	 * 
	 * @param field
	 * @return
	 */
	private static String fieldName(Field field) {
		return Modifier.toString(field.getModifiers()) + " " + field.getType().getSimpleName()
				+ " " + field.getName();
	}

	/**
	 * Convert the given list of individual reports into a single string, with each report on a
	 * separate line.
	 * 
	 * @param a
	 * @return
	 */
	private static String generateReport(List<String> a) {
		StringBuilder buffer = new StringBuilder();
		for (String line : a) {
			buffer.append(line);
			buffer.append('\n');
		}
		return buffer.toString();
	}

	/**
	 * Check a class for extras, and assert that the report is empty
	 * @param expected
	 * @param actual
	 * @param checkPrivates
	 */
	public static void checkClassForExtras(Class<?> expected, Class<?> actual, boolean checkPrivates) {
		List<String> report = checkForExtras(expected, actual, checkPrivates);
		assertTrue(generateReport(report), report.isEmpty());
	}
	
	/**
	 * Compares two arrays of classes by binary name.
	 * 
	 * @param expected
	 * @param actual
	 * @return
	 */
	private static boolean compareClassLists(Class<?>[] expected, Class<?>[] actual) {
		if (actual.length != expected.length)
			return false;
		for (int i = 0; i < expected.length; i++) {
			if (!compareClasses(expected[i], actual[i]))
				return false;
		}
		return true;
	}
	
	/**
	 * Compare two classes by binary name
	 * 
	 * @param expected
	 * @param actual
	 * @return
	 */
	private static boolean compareClasses(Class<?> expected, Class<?> actual) {
		return expected.getName().equals(actual.getName());
	}
}
