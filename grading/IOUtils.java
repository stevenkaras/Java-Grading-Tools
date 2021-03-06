/**
 * IOUtils.java
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

import grading.io.TestInputStream;
import grading.io.TestPrintStream;

import java.io.File;
import java.io.IOException;

/**
 * Class for handling I/O redirection.
 * 
 * @author Steven Karas
 */
public class IOUtils {
	/**
	 * Disables output capturing. Call this method after you're done doing
	 * expect.
	 */
	public static void disableOutputBuffering() {
		TestPrintStream.disable();
	}

	/**
	 * Enables output capturing. Call this method before you do an expect.
	 */
	public static void enableOutputBuffering() {
		TestPrintStream.enable();
	}

	/**
	 * Returns a copy of the buffer contents.<br>
	 * Note that this will flush the buffer, so save a copy!!!
	 * 
	 * @return a copy of the captured output
	 */
	public static String getOutputBuffer() {
		return TestPrintStream.getBuffer();
	}

	/**
	 * Adds a line to the end of the input buffer.
	 * <p>
	 * Think of this as calling println() to place new data in the input buffer
	 * 
	 * @param line
	 *            the line to add to the input
	 */
	public static void addLineToInput(String line) {
		TestInputStream.addLine(line);
	}

	/**
	 * Adds some text to the end of the input buffer
	 * 
	 * Think of this as calling print() to place new data in the input buffer
	 * 
	 * @param text
	 *            the text to add to the input
	 */
	public static void addTextToInput(String text) {
		TestInputStream.addText(text);
	}

	/**
	 * Adds the contents of a file to the end of the input buffer
	 * <p>
	 * Note that this performs EOL conversion, for platform independence, so testers don't have to
	 * share the same architecture! 
	 * 
	 * @param filepath
	 *            the file path and name to add to the input
	 * @return true if the file was added successfully, otherwise false
	 */
	public static boolean addFileToInput(String filepath) {
		try {
			TestInputStream.addFile(filepath);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Adds the contents of a file to the end of the input buffer
	 * <p>
	 * Note that this performs EOL conversion, for platform independence, so testers don't have to
	 * share the same architecture!
	 * 
	 * @param file
	 *            the file to add to the input buffer
	 * @return true if the file was added successfully, otherwise false
	 */
	public static boolean addFileToInput(File file) {
		try {
			TestInputStream.addFile(file);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * When called, this method will stop input redirection.
	 */
	public static void disableInputRedirection() {
		TestInputStream.unload();
	}

	/**
	 * When called, this method starts input redirection.
	 */
	public static void enableInputRedirection() {
		TestInputStream.load();
	}
}
