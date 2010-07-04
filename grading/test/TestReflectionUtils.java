package grading.test;

import static org.junit.Assert.*;

import java.util.List;

import grading.ReflectionUtils;

import org.junit.Before;
import org.junit.Test;

public class TestReflectionUtils {

	@Before
	public void initialize() {
		
	}
	
	@Test
	@SuppressWarnings("unused")
	public void test_checkForExtraFields_ExtraPublic_noPublicAllowed() {
		class Expect {
		}
		class Actual {
			public int a;
		}
		List<String> temp = ReflectionUtils.checkForExtraFields(Expect.class, Actual.class, false);
		assertEquals(1, temp.size());
		assertEquals(temp.get(0), "Found extra field: public int a", temp.get(0));
	}
	
	@Test
	@SuppressWarnings("unused")
	public void test_checkForExtraFields_ExtraPublic_somePublicAllowed() {
		class Expect {
			public int a;
		}
		class Actual {
			public int a;
			public int b;
		}
		List<String> temp = ReflectionUtils.checkForExtraFields(Expect.class, Actual.class, false);
		assertEquals(1, temp.size());
		assertEquals(temp.get(0), "Found extra field: public int b", temp.get(0));
	}
	
	@Test
	@SuppressWarnings("unused")
	public void test_checkForExtraFields_ExtraPublic_somePublicAllowedDisjoint() {
		class Expect {
			public int a;
		}
		class Actual {
			public int b;
		}
		List<String> temp = ReflectionUtils.checkForExtraFields(Expect.class, Actual.class, false);
		assertEquals(1, temp.size());
		assertEquals(temp.get(0), "Found extra field: public int b", temp.get(0));
	}
	
	@Test
	@SuppressWarnings("unused")
	public void test_checkForExtraFields_ExtraPublic_someProtectedAllowedDisjoint() {
		class Expect {
			protected int a;
		}
		class Actual {
			public int b;
		}
		List<String> temp = ReflectionUtils.checkForExtraFields(Expect.class, Actual.class, false);
		assertEquals(1, temp.size());
		assertEquals(temp.get(0), "Found extra field: public int b", temp.get(0));
	}
	
	@SuppressWarnings("unused")
	private static class TestClass1 {
		private static int a;
	}
	@Test
	public void test_checkForExtraFields_ExtraPrivateStatic() {
		class Expect {
		}
		List<String> temp = ReflectionUtils.checkForExtraFields(Expect.class, TestClass1.class, false);
		assertEquals(1, temp.size());
		assertEquals(temp.get(0), "Private static non-final field: a", temp.get(0));
	}

	@Test
	@SuppressWarnings("unused")
	public void test_checkForExtraFields_WrongType_sameFields() {
		class Expect {
			public long a;
		}
		class Actual {
			public int a;
		}
		List<String> temp = ReflectionUtils.checkForExtraFields(Expect.class, Actual.class, false);
		assertEquals(1, temp.size());
		assertEquals(temp.get(0), "public int a had different type than expected: long", temp.get(0));
	}
	
	@Test
	@SuppressWarnings("unused")
	public void test_checkForExtraFields_Modifiers_sameFields() {
		class Expect {
			public final int a = 0;
		}
		class Actual {
			public int a;
		}
		List<String> temp = ReflectionUtils.checkForExtraFields(Expect.class, Actual.class, false);
		assertEquals(1, temp.size());
		assertEquals(temp.get(0), "public int a had different modifiers than expected: final", temp.get(0));
	}
	
	@Test (expected=AssertionError.class)
	public void test_FieldsTest_noPrivate() {
		ReflectionUtils.checkClassForExtras(FieldsTestExpected.class, FieldsTestActual.class, false);
	}
	@Test (expected=AssertionError.class)
	public void test_FieldsTest_private() {
		ReflectionUtils.checkClassForExtras(FieldsTestExpected.class, FieldsTestActual.class, true);
	}
	@Test (expected=AssertionError.class)
	public void test_MethodsTest_noPrivate() {
		ReflectionUtils.checkClassForExtras(MethodsTestExpected.class, MethodsTestActual.class, false);
	}
	@Test (expected=AssertionError.class)
	public void test_MethodsTest_private() {
		ReflectionUtils.checkClassForExtras(MethodsTestExpected.class, MethodsTestActual.class, true);
	}
	@Test (expected=AssertionError.class)
	public void test_CtorsTest_noPrivate() {
		ReflectionUtils.checkClassForExtras(CTorsTestExpected.class, CTorsTestActual.class, false);
	}
	@Test (expected=AssertionError.class)
	public void test_CtorsTest_private() {
		ReflectionUtils.checkClassForExtras(CTorsTestExpected.class, CTorsTestActual.class, true);
	}
}
