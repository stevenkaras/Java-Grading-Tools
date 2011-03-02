/**
 * FieldsTestActual.java
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

package grading.test;

import java.io.Serializable;

@SuppressWarnings({ "unused", "serial" })
class FieldsTestActual implements Cloneable, Serializable, Runnable {
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
}
