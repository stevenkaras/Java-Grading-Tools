/**
 * MultiplexingPrintStream.java
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

package grading.io;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

/**
 * This class allows the joining of multiple printstreams, so they may be used
 * together as one sink. Any output sent to this Stream is automatically copied
 * to all the attached streams.
 * <p>
 * Note: if one of the attached streams blocks, then it will block output for
 * all the attached streams.
 * 
 * @author Steven Karas
 */
public class MultiplexingPrintStream extends PrintStream {
	protected Collection<PrintStream> streams;
	
	/**
	 * Attaches a new output stream to this log. All messages passed to this
	 * log are printed to this stream
	 * 
	 * @param stream
	 *            the stream to attach
	 * @return this
	 */
	public synchronized MultiplexingPrintStream attach(PrintStream stream) {
		streams.add(stream);
		return this;
	}

	/**
	 * Detach an output stream from this log.
	 * 
	 * @param stream
	 *            the stream to remove
	 * @return this
	 */
	public synchronized MultiplexingPrintStream detach(PrintStream stream) {
		streams.remove(stream);
		return this;
	}
	
	/**
	 * Construct a new Multiplexer for the given streams
	 * 
	 * @param streams
	 */
	public MultiplexingPrintStream(PrintStream... streams) {
		// always tie to the first one.
		super(streams[0], true);
		this.streams = new ArrayList<PrintStream>(Arrays.asList(streams));
	}

	@Override
	public PrintStream append(char c) {
		for (PrintStream s : streams) {
			s.append(c);
		}
		return this;
	}

	@Override
	public PrintStream append(CharSequence csq) {
		for (PrintStream s : streams) {
			s.append(csq);
		}
		return this;
	}

	@Override
	public PrintStream append(CharSequence csq, int start, int end) {
		for (PrintStream s : streams) {
			s.append(csq, start, end);
		}
		return this;
	}

	@Override
	public boolean checkError() {
		for (PrintStream s : streams) {
			if (!s.checkError())
				return false;
		}
		return true;
	}

	@Override
	public void close() {
		for (PrintStream s : streams) {
			s.close();
		}
	}

	@Override
	public void flush() {
		for (PrintStream s : streams) {
			s.flush();
		}
	}

	@Override
	public PrintStream format(Locale l, String format, Object... args) {
		for (PrintStream s : streams) {
			s.format(l, format, args);
		}
		return this;
	}

	@Override
	public PrintStream format(String format, Object... args) {
		for (PrintStream s : streams) {
			s.format(format, args);
		}
		return this;
	}

	@Override
	public PrintStream printf(Locale l, String format, Object... args) {
		for (PrintStream s : streams) {
			s.printf(l, format, args);
		}
		return this;
	}

	@Override
	public PrintStream printf(String format, Object... args) {
		for (PrintStream s : streams) {
			s.printf(format, args);
		}
		return this;
	}

	@Override
	public void write(byte[] b) throws IOException {
		for (PrintStream s : streams) {
			s.write(b);
		}
	}

	@Override
	public void print(boolean x) {
		for (PrintStream s : streams) {
			s.print(x);
		}
	}

	@Override
	public void println(boolean x) {
		for (PrintStream s : streams) {
			s.println(x);
		}
	}

	@Override
	public void print(char x) {
		for (PrintStream s : streams) {
			s.print(x);
		}
	}

	@Override
	public void println(char x) {
		for (PrintStream s : streams) {
			s.println(x);
		}
	}

	@Override
	public void print(int x) {
		for (PrintStream s : streams) {
			s.print(x);
		}
	}

	@Override
	public void println(int x) {
		for (PrintStream s : streams) {
			s.println(x);
		}
	}

	@Override
	public void print(long x) {
		for (PrintStream s : streams) {
			s.print(x);
		}
	}

	@Override
	public void println(long x) {
		for (PrintStream s : streams) {
			s.println(x);
		}
	}

	@Override
	public void print(float x) {
		for (PrintStream s : streams) {
			s.print(x);
		}
	}

	@Override
	public void println(float x) {
		for (PrintStream s : streams) {
			s.println(x);
		}
	}

	@Override
	public void print(double x) {
		for (PrintStream s : streams) {
			s.print(x);
		}
	}

	@Override
	public void println(double x) {
		for (PrintStream s : streams) {
			s.println(x);
		}
	}

	@Override
	public void print(char[] x) {
		for (PrintStream s : streams) {
			s.print(x);
		}
	}

	@Override
	public void println(char[] x) {
		for (PrintStream s : streams) {
			s.println(x);
		}
	}

	@Override
	public void print(String x) {
		for (PrintStream s : streams) {
			s.print(x);
		}
	}

	@Override
	public void println(String x) {
		for (PrintStream s : streams) {
			s.println(x);
		}
	}

	@Override
	public void print(Object x) {
		for (PrintStream s : streams) {
			s.print(x);
		}
	}

	@Override
	public void println(Object x) {
		for (PrintStream s : streams) {
			s.println(x);
		}
	}

	@Override
	public void println() {
		for (PrintStream s : streams) {
			s.println();
		}
	}

	@Override
	public void write(byte[] buf, int offset, int length) {
		for (PrintStream s : streams) {
			s.write(buf, offset, length);
		}
	}

	@Override
	public void write(int b) {
		for (PrintStream s : streams) {
			s.write(b);
		}
	}

	@Override
	public void finalize() throws Throwable {
		try {
			for (PrintStream s : streams) {
				try {
					s.close();
				} catch (Throwable t) {
					// don't handle anything, just move on
				}
			}
		} finally {
			super.finalize();
		}
	}
}