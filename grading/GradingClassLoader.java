package grading;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.junit.Before;
import org.junit.After;

/**
 * This is a custom URLClassLoader that will make on the fly replacement of
 * certain "flagged" classes.
 * <p>
 * This is a step in the right direction, but nothing will be great until we
 * switch over to AspectJ, and can do hotswapping of methods for our own...
 * 
 * @author Steven Karas
 */
public class GradingClassLoader extends URLClassLoader {

	/**
	 * Any class in this list will be loaded by this classloader first, before
	 * delegation.
	 */
	public Collection<String> classes = Collections
			.synchronizedList(new ArrayList<String>());

	/**
	 * This holds the name of the test class
	 */
	private Class<?> testClass;
	
	/**
	 * This is the instance of the test class we'll use for testing
	 */
	private Object instance;
	
	/**
	 * Create a new GradingClassLoader using the target jar as the solution
	 * source, and with the target testing class
	 * 
	 * @param jar the path to the solution jar file
	 * @param testClass the name of the testing class
	 * @throws Exception
	 */
	public GradingClassLoader(String jar, String testClass) throws Exception {
		super(new URL[] { new File(jar).toURI().toURL() });
		this.testClass = loadClass(testClass);
		instance = this.testClass.newInstance();
	}

	@Override
	protected synchronized Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		// First, check if the class has already been loaded
		Class<?> c = findLoadedClass(name);
		if (c != null) {
			// the class is already loaded, so don't load it again
			return c;
		}
		// check in the list of overrides
		if (!classes.contains(name)) {
			return super.loadClass(name, resolve);
		}
		// look for the class on our own.
		try {
			c = findClass(name);
		} catch (ClassNotFoundException e) {
			// couldn't find it, so fall back on normal behavior
			return super.loadClass(name, resolve);
		}
		if (resolve) {
			resolveClass(c);
		}
		return c;
	}

	/**
	 * This method makes a best effort to create an instance of the given class,
	 * as referenced by it's binary FQCN.
	 * 
	 * @param className
	 * @return a new instance of the given class, or null if the effort was
	 *         unsuccessful
	 */
	public Object getInstance(String className) {
		try {
			return loadClass(className).newInstance();
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (ClassNotFoundException e) {
		}
		return null;
	}

	/**
	 * This method makes a best effort to create a new GradingClassLoader with
	 * the given jar file. This is wrapped in a method so as to catch the
	 * MalformedURLException that may be thrown by the constructor.
	 * 
	 * @param path
	 * @param testClass
	 * @return
	 */
	public static GradingClassLoader newInstance(String jar, String testClass) {
		try {
			return new GradingClassLoader(jar, testClass);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This method invokes a method with the same name as the caller, but from
	 * within the loaded testClass.
	 * 
	 * @throws Throwable
	 */
	public void runTest() throws Throwable {
		// Use the calling method's name
		StackTraceElement[] cst = Thread.currentThread().getStackTrace();
		String testName = cst[2].getMethodName();
		String callersName = cst[2].getClassName();
		try {
			runAll(Before.class);
			testClass.getMethod(testName).invoke(instance);
		} catch (InvocationTargetException e) {
			Throwable t = e.getCause();
			StackTraceElement[] st = t.getStackTrace();
			ArrayList<StackTraceElement> result = new ArrayList<StackTraceElement>();
			for (StackTraceElement ste : st) {
				// hide the classloader and the actual test from the stack trace
				if (ste.getClassName().equals(this.getClass().getName()))
					continue;
				if (ste.getClassName().equals(callersName))
					continue;
				result.add(ste);
			}
			t.setStackTrace(result.toArray(new StackTraceElement[0]));
			throw t;
		} finally {
			runAll(After.class);
		}
	}
	
	/**
	 * Invokes all methods with the given annotation in the given class, using
	 * the local instance of that test class.
	 * 
	 * @param annotation
	 * @throws Throwable
	 */
	private void runAll(Class<? extends Annotation> annotation) throws Throwable {
		for (Method m : testClass.getMethods()) {
			if (m.getAnnotation(annotation) == null) {
				continue;
			}
			m.invoke(instance);
		}
	}
}
