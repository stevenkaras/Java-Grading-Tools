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
		if (actual.getSimpleName().equals(expected.getSimpleName()))
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
				Constructor<?> cantidate = expected
						.getDeclaredConstructor(ctor.getParameterTypes());
				if (cantidate.getModifiers() != ctor.getModifiers())
					results.add(ctorSignature(ctor) + " had different modifiers than expected: "
							+ Modifier.toString(cantidate.getModifiers()));
				for (Class<?> extra : checkForExtrasGeneric(cantidate.getExceptionTypes(), ctor
						.getExceptionTypes())) {
					results.add(ctorSignature(ctor) + " throws an extra type: "
							+ extra.getSimpleName());
				}
				// check any other differences between actual and expected ctor
			} catch (SecurityException e) {
				throw new IllegalStateException(e.getMessage(), e);
			} catch (NoSuchMethodException e) {
				if (!checkPrivates && Modifier.isPrivate(ctor.getModifiers())) {
					continue;
				}
				results.add("Found extra ctor: " + ctorSignature(ctor));
			}
		}
		return results;
	}

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
			String methodName = method.getName();
			try {
				Method cantidate = expected.getDeclaredMethod(methodName, method
						.getParameterTypes());
				if (!cantidate.getReturnType().equals(method.getReturnType()))
					results.add(methodSignature(method)
							+ " had different return type than expected: "
							+ method.getReturnType().getName());
				if (cantidate.getModifiers() != method.getModifiers())
					results.add(methodSignature(method)
							+ " had different modifiers than expected: "
							+ Modifier.toString(method.getModifiers()));
				for (Class<?> extra : checkForExtrasGeneric(cantidate.getExceptionTypes(), method
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
			try {
				Field cantidate = expected.getDeclaredField(field.getName());
				if (field.getModifiers() != cantidate.getModifiers())
					results.add(fieldName(field) + " had different modifiers than expected: "
							+ Modifier.toString(cantidate.getModifiers() ^ field.getModifiers()));
				if (!field.getType().equals(cantidate.getType()))
					results.add(fieldName(field) + " had different type than expected: "
							+ cantidate.getType().getSimpleName());
			} catch (SecurityException e) {
				throw new IllegalStateException(e.getMessage(), e);
			} catch (NoSuchFieldException e) {
				int modifiers = field.getModifiers();
				if (!checkPrivates && Modifier.isPrivate(modifiers)) {
					if (Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers)) {
						// always warn on this usage
						results.add("Private static non-final field: " + field.getName());
					}
					continue;
				}
				results.add("Found extra field: " + fieldName(field));
			}
		}
		return results;
	}

	private static Class<?>[] checkForExtrasGeneric(Class<?>[] expecteds, Class<?>[] actuals) {
		ArrayList<Class<?>> temp = new ArrayList<Class<?>>();
		for (Class<?> actual : actuals) {
			boolean found = false;
			for (Class<?> expected : expecteds) {
				if (actual.equals(expected))
					found = true;
			}
			if (!found)
				temp.add(actual);
		}
		return temp.toArray(new Class<?>[0]);
	}

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

	private static String fieldName(Field field) {
		return Modifier.toString(field.getModifiers()) + " " + field.getType().getSimpleName()
				+ " " + field.getName();
	}

	private static String generateReport(List<String> a) {
		StringBuilder buffer = new StringBuilder();
		for (String line : a) {
			buffer.append(line);
			buffer.append('\n');
		}
		return buffer.toString();
	}

	public static void checkClassForExtras(Class<?> expected, Class<?> actual, boolean checkPrivates) {
		List<String> report = checkForExtras(expected, actual, checkPrivates);
		assertTrue(generateReport(report), report.isEmpty());
	}
}
