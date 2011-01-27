package grading;

import org.junit.After;
import org.junit.Before;

public abstract class GeneralTest {

	@Before
	public void setUp() {
		TestUtils.preventSystemExit();
		IOUtils.enableOutputBuffering();
		IOUtils.getOutputBuffer();
	}
	
	@After
	public void tearDown() {
		IOUtils.disableOutputBuffering();
		TestUtils.allowSystemExit();
	}
}
