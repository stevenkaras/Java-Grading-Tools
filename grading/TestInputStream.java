package grading;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.io.PrintStream;

/**
 * This class acts as a middle-man buffer for stdin, to easily facilitate unit
 * testing.
 * <p>
 * Note that it is not particularly safe, and hasn't been tested. This class is
 * provided AS IS, and I am not liable for any damage done to your
 * computer/code/JVM/ego. I just don't care.
 * <p>
 * Behavior in general follows:<br>
 * If we have input buffered, offer it first.
 * Afterwards, offer the read stdin if not.
 * 
 * @author Steven Karas
 * @version 2.0
 */
public class TestInputStream extends java.io.PipedInputStream {
	// these are the pipes, and the printer for the outPipe (convenience)
	private java.io.PipedOutputStream outPipe;
	private java.io.PrintStream outPipePrinter;

	// this saves the real stdin, for posterity's sake.
	private java.io.InputStream stdin;

	/* Start of Singleton Pattern code */
	private static TestInputStream myBuffer;

	static {
		// initialize our singleton
		try {
			myBuffer = new TestInputStream();
		} catch (IOException e) {
			throw new IllegalStateException("Couldn't construct TestInputStream Singleton", e);
		}
	}

	/* End of Singleton Pattern code */

	/*******************************************************/
	/****************** Start of user API ******************/
	/*******************************************************/
	/**
	 * Adds a line to the end of the input buffer.
	 * 
	 * Think of this as calling println() to place new data in the input buffer
	 * 
	 * @param line
	 *            the line to add to the input
	 */
	static void addLine(String line) {
		myBuffer.outPipePrinter.println(line);
	}

	/**
	 * Adds some text to the end of the input buffer
	 * 
	 * Think of this as calling print() to place new data in the input buffer
	 * 
	 * @param text
	 *            the text to add to the input
	 */
	static void addText(String text) {
		myBuffer.outPipePrinter.print(text);
	}

	/**
	 * Adds the contents of a file to the end of the input buffer
	 * 
	 * @param filepath
	 *            the file path and name to add to the input
	 * @throws IOException 
	 */
	static void addFile(String filepath) throws IOException {
		addFile(new File(filepath));
	}
	
	static void addFile(File file) throws IOException {
		// start reading in the file
		BufferedReader read = new BufferedReader(new FileReader(file));
		while (read.ready()) {
			myBuffer.outPipePrinter.println(read.readLine());
		}
	}

	/**
	 * When called, this method will unload the TestInputStream from the
	 * program, so all calls go directly to System.in, rather than being
	 * processed.
	 * 
	 * May be reloaded at any time by calling {@link #load()}
	 * 
	 */
	static void unload() {
		System.setIn(myBuffer.stdin);
	}

	static void load() {
		if (System.in == myBuffer)
			return; // we're already loaded, so do nothing
		else {
			// just in case the stdin changed since we were unloaded
			myBuffer.stdin = System.in;
			System.setIn(myBuffer);
		}
	}

	/*******************************************************/
	/******************* End of user API *******************/
	/*******************************************************/

	/**
	 * c-tor. Not much here
	 * 
	 * @throws IOException
	 *             if the outPipe didn't connect properly
	 */
	private TestInputStream() throws IOException {
		super();
		// set up the pipes
		outPipe = new PipedOutputStream(this);
		// created with auto-flush
		outPipePrinter = new PrintStream(outPipe, true);
	}

	/**
	 * d-tor. Not much here
	 * 
	 * @return
	 */
	public void finalize() throws Throwable {
		// ensure that we don't create problems if we pass away!
		try {
			unload();
		} finally {
			super.finalize();
		}
	}

	public int read() throws IOException {
		// test if we have any buffered input, give it if we do
		if (this.available() >= 1) {
			return super.read();
		} else {
			return stdin.read();
		}
	}

	public int read(byte[] b) throws IOException {
		if (this.available() >= b.length) {
			return super.read(b);
		} else {
			return stdin.read(b);
		}
	}

	public int read(byte[] b, int off, int len) throws IOException {
		if (this.available() >= len) {
			return super.read(b, off, len);
		} else {
			return stdin.read(b, off, len);
		}
	}

	public boolean markSupported() {
		return false;
	}
}
