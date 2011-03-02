/**
 * CTorsTestActual.java
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

import java.io.IOException;

@SuppressWarnings("unused")
public class CTorsTestActual {
	public CTorsTestActual(int properPublic) {}
	protected CTorsTestActual(int properProtected, int b) {}
	CTorsTestActual(int properPackage, int b, int c) {}
	private CTorsTestActual(int properPrivate, int b, int c, int d) {}
	public CTorsTestActual(int[] properThrows) throws IOException {}
	
	public CTorsTestActual(long extraPublic) {}
	protected CTorsTestActual(long extraProtected, int b) {}
	CTorsTestActual(long extraPackage, int b, int c) {}
	private CTorsTestActual(long extraPrivate, int b, int c, int d) {}
	
	CTorsTestActual(String wrongMods) {}
	public CTorsTestActual(long[] extraThrow) throws IOException {}
	public CTorsTestActual(float[] someExtraThrows) throws IOException, SecurityException {}
	public CTorsTestActual(double[] someThrows) throws IOException, SecurityException {}
	
	protected CTorsTestActual(Integer reallyOff) throws UnknownError {}
}
