package grading.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import grading.IOUtils;

import java.io.IOException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IOTest {

	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}
	
	@Test
	public void testRedirectionRead() {
		IOUtils.enableInputRedirection();
		IOUtils.addLineToInput("hiya");
		
		byte[] buf = new byte[6];
		try {
			System.in.read(buf);
		} catch (IOException e) {
			fail("Problem reading from System.in");
		}
		
		String result = new String(buf);
		assertTrue(result, result.equals("hiya\r\n"));
	}
	
	@Test
	public void testRedirectionScannerNext() {
		IOUtils.enableInputRedirection();
		IOUtils.addLineToInput("hello, world");
		
		Scanner scan = new Scanner(System.in);
		String result = scan.next();
		assertTrue(result, result.equals("hello,"));
	}
	
}
