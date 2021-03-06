/**
 * ExampleExpected.java
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

package grading.examples;

import java.io.IOException;

@SuppressWarnings("unused")
public class ExampleExpected implements Cloneable, Runnable {

	// It tests Constructors!
	public ExampleExpected(int properPublic) {}
	protected ExampleExpected(int properProtected, int b) {}
	ExampleExpected(int properPackage, int b, int c) {}
	private ExampleExpected(int properPrivate, int b, int c, int d) {}
	public ExampleExpected(int[] properThrows) throws IOException {}

	public ExampleExpected(String wrongMods) {}
	public ExampleExpected(long[] extraThrow) {}
	public ExampleExpected(float[] someExtraThrows) throws IOException {}
	public ExampleExpected(double[] someThrows) {}
	public ExampleExpected(Integer reallyOff) {}

	// It tests fields!
	public int wrongModifier = 0;
	public int wrongType;
	public int wrongTypeMod = 0;

	public int correctPub;
	protected int correctProt;
	int correctPackage;
	private int correctPrivate;
	public static final int correctConstant = 1;
	private static final int correctPrivateConstant = 2;

	public void run() {}

	// Heck, it even tests methods!
	/*
	 * The important part to remember is that you don't have to implement the methods,
	 * so long as the API is the same.
	 */
	/*
	 * Also, don't copy the school solution into a different package. That will change the types
	 * used
	 * in the API, and break the tests.
	 */
	public int properPublic() {return 0;}
	protected int properProtected() {return 0;}
	int properPackage() {return 0;}
	private int properPrivate() {return 0;}

	public void wrongMods() {}
	public int wrongReturnType() {return 0;}
	public int throwsExtra() {return 0;}
	public int throwsExtras() throws IOException {return 0;}
	public int reallyOff() {return 0;}

	public void wrongParams() {}
	public void correctWithParams(int a, int b) {}
	public void correctWithVarParams(int a, int... args) {}
	public void wrongParamsType(long a, String b) {}
}
