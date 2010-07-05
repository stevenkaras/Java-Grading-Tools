/**
 * ExampleActual.java
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

package grading.examples;

import java.io.IOException;
import java.io.Serializable;

@SuppressWarnings({ "unused", "serial" })
public class ExampleActual implements Cloneable, Serializable, Runnable {
	public ExampleActual(int properPublic) {}
	protected ExampleActual(int properProtected, int b) {}
	ExampleActual(int properPackage, int b, int c) {}
	private ExampleActual(int properPrivate, int b, int c, int d) {}
	public ExampleActual(int[] properThrows) throws IOException {}
	
	public ExampleActual(long extraPublic) {}
	protected ExampleActual(long extraProtected, int b) {}
	ExampleActual(long extraPackage, int b, int c) {}
	private ExampleActual(long extraPrivate, int b, int c, int d) {}
	
	ExampleActual(String wrongMods) {}
	public ExampleActual(long[] extraThrow) throws IOException {}
	public ExampleActual(float[] someExtraThrows) throws IOException, SecurityException {}
	public ExampleActual(double[] someThrows) throws IOException, SecurityException {}
	
	protected ExampleActual(Integer reallyOff) throws UnknownError {}

	public final int wrongModifier = 0;
	public long wrongType;
	public final long wrongTypeMod = 0;
	private static int ImNotFinal;
	private static final int ImFinal = 0;
	public int extraPub;
	protected int extraProt;
	int extraPackage;
	private int extraPrivate;
	
	public int correctPub;
	protected int correctProt;
	int correctPackage;
	private int correctPrivate;
	public static final int correctConstant = 1;
	private static final int correctPrivateConstant = 2;
	
	public void run() {}
	
	public int properPublic() {return 0;}
	protected int properProtected() {return 0;}
	int properPackage() {return 0;}
	private int properPrivate() {return 0;}
	
	void wrongMods() {}
	public long wrongReturnType() {return 0;}
	public int throwsExtra() throws Exception {return 0;}
	public int throwsExtras() throws IOException, SecurityException, Exception {return 0;}
	
	protected void reallyOff() throws UnknownError {}
	
	public void extraPublic() {}
	protected void extraProtected() {}
	void extraPackage() {}
	private void extraPrivate() {}
	
	public void wrongParams(int a) {}
	public void correctWithParams(int a, int b) {}
	public void correctWithVarParams(int a, int... args) {}
	public void wrongParamsType(int a, int b) {}
}
