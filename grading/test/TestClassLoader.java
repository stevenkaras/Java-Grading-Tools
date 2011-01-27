package grading.test;
import grading.GradingClassLoader;
import grading.TestUtils;

import org.junit.*;

public class TestClassLoader {
	
	private GradingClassLoader sampleClassLoader;
	
	public TestClassLoader() {
		sampleClassLoader = GradingClassLoader.newInstance("solution.jar", "grading.test.TestClassLoader2");
		sampleClassLoader.classes.add("searchengine.SearchResult");
		sampleClassLoader.classes.add("searchengine.SearchResults");
	}
	
	@Before
	public void setUp() {
		TestUtils.preventSystemExit();
	}
	
	@After
	public void tearDown() {
		TestUtils.allowSystemExit();
	}
	
	@Test
	public void testSearchEngine_Ctor_readsFile() throws Throwable {
		sampleClassLoader.runTest();
	}
	
}
