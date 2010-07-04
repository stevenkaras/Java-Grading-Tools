package grading;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;

/**
 * This class captures output sent to stdout, to easily facilitate unit testing.
 * <p>
 * Note that it is not particularly safe, and hasn't been tested. This class is
 * provided AS IS, and I am not liable for any damage done to your
 * computer/code/JVM/ego. I just don't care.
 * 
 * @author Steven Karas
 * @version 2.0
 */
public class TestPrintStream extends PrintStream {
	private PrintStream stdout;
	private StringBuffer outputBuffer;
	private static TestPrintStream monitor = new TestPrintStream(System.out);

	/**
	 * Disables output capturing. Call this method after you're done doing
	 * expect.
	 */
	static void disable() {
		System.setOut(monitor.stdout);
	}

	/**
	 * Enables output capturing. Call this method before you do an expect.
	 */
	static void enable() {
		System.setOut(monitor);
	}

	/**
	 * Returns a copy of the buffer contents. Note that this will flush the
	 * buffer, so save a copy!!!
	 * 
	 * @return a copy of the captured output
	 */
	static String getBuffer() {
		// create a finalized copy of the buffer
		String buf = monitor.outputBuffer.toString();
		// and flush the buffer
		monitor.outputBuffer.setLength(0);
		return buf;
	}
	
	/**
	 * C-tor. Not much here.
	 */
	private TestPrintStream(PrintStream stdout) {
		// tie this PrintStream to the true stdout, and enable buffer flushing
		super(stdout, true);
		// store local references to the real slim shady
		this.stdout = stdout;
		outputBuffer = new StringBuffer("");
	}

	/**
	 * D-tor. Not much here.
	 */
	public void finalize() throws Throwable {
		// make sure that we remove ourselves from the JVM and don't muck up
		// stdout.
		try {
			disable();
		} finally {
			super.finalize();
		}
	}

	/*******************************************************/
	/******* Start of System.out middleman/proxy API *******/
	/*******************************************************/

	public PrintStream append(char c) {
		outputBuffer.append(c);
		return stdout.append(c);
	}

	public PrintStream append(CharSequence csq) {
		outputBuffer.append(csq);
		return stdout.append(csq);
	}

	public PrintStream append(CharSequence csq, int start, int end) {
		outputBuffer.append(csq, start, end);
		return stdout.append(csq, start, end);
	}

	public boolean checkError() {
		return stdout.checkError();
	}

	public void close() {
		stdout.close();
	}

	public void flush() {
		stdout.flush();
	}

	public PrintStream format(Locale l, String format, Object... args) {
		String buffer = String.format(l, format, args);
		outputBuffer.append(buffer);
		stdout.print(buffer);
		return stdout;
	}

	public PrintStream format(String format, Object... args) {
		String buffer = String.format(format, args);
		outputBuffer.append(buffer);
		stdout.print(buffer);
		return stdout;
	}

	public PrintStream printf(Locale l, String format, Object... args) {
		return this.format(l, format, args); // as per spec
	}

	public PrintStream printf(String format, Object... args) {
		return this.format(format, args); // as per spec
	}

	public void write(byte[] b) throws IOException {
		outputBuffer.append(b);
		stdout.write(b);
	}

	public void print(boolean x) {
		outputBuffer.append(x);
		stdout.print(x);
	}

	public void println(boolean x) {
		outputBuffer.append(x);
		outputBuffer.append("\n");
		stdout.println(x);
	}

	public void print(char x) {
		outputBuffer.append(x);
		stdout.print(x);
	}

	public void println(char x) {
		outputBuffer.append(x);
		outputBuffer.append("\n");
		stdout.println(x);
	}

	public void print(int x) {
		outputBuffer.append(x);
		stdout.print(x);
	}

	public void println(int x) {
		outputBuffer.append(x);
		outputBuffer.append("\n");
		stdout.println(x);
	}

	public void print(long x) {
		outputBuffer.append(x);
		stdout.print(x);
	}

	public void println(long x) {
		outputBuffer.append(x);
		outputBuffer.append("\n");
		stdout.println(x);
	}

	public void print(float x) {
		outputBuffer.append(x);
		stdout.print(x);
	}

	public void println(float x) {
		outputBuffer.append(x);
		outputBuffer.append("\n");
		stdout.println(x);
	}

	public void print(double x) {
		outputBuffer.append(x);
		stdout.print(x);
	}

	public void println(double x) {
		outputBuffer.append(x);
		outputBuffer.append("\n");
		stdout.println(x);
	}

	public void print(char[] x) {
		outputBuffer.append(x);
		stdout.print(x);
	}

	public void println(char[] x) {
		outputBuffer.append(x);
		outputBuffer.append("\n");
		stdout.println(x);
	}

	public void print(String x) {
		outputBuffer.append(x);
		stdout.print(x);
	}

	public void println(String x) {
		outputBuffer.append(x);
		outputBuffer.append("\n");
		stdout.println(x);
	}

	public void print(Object x) {
		outputBuffer.append(x);
		stdout.print(x);
	}

	public void println(Object x) {
		outputBuffer.append(x);
		outputBuffer.append("\n");
		stdout.println(x);
	}

	public void println() {
		outputBuffer.append("\n");
		stdout.println();
	}

	public void write(byte[] buffer, int offset, int length) {
		outputBuffer.append(new String(buffer, offset, length));
		stdout.write(buffer, offset, length);
	}

	public void write(int b) {
		outputBuffer.append(b);
		stdout.write(b);
	}
}