/**
 * TestPrintStream.java
 * 
 * Copyright (C) 2010
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

package grading.io;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
public class TestPrintStream {
	private PrintStream stdout;
	private ByteArrayOutputStream outputBuffer;
	private MultiplexingPrintStream splitter;
	private static TestPrintStream monitor = new TestPrintStream();

	/**
	 * Disables output capturing. Call this method after you're done doing
	 * expect.
	 */
	public static void disable() {
		System.setOut(monitor.stdout);
	}

	/**
	 * Enables output capturing. Call this method before you do an expect.
	 */
	public static void enable() {
		System.setOut(monitor.splitter);
	}

	/**
	 * Returns a copy of the buffer contents. Note that this will flush the
	 * buffer, so save a copy!!!
	 * 
	 * @return a copy of the captured output
	 */
	public static String getBuffer() {
		// create a finalized copy of the buffer
		String buf = monitor.outputBuffer.toString();
		// and flush the buffer
		monitor.outputBuffer.reset();
		return buf;
	}

	/**
	 * C-tor. Not much here.
	 */
	private TestPrintStream() {
		// store local references to the real slim shady
		this.stdout = System.out;
		outputBuffer = new ByteArrayOutputStream();
		splitter = new MultiplexingPrintStream(stdout, new PrintStream(outputBuffer));
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

}