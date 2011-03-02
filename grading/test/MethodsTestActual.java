/**
 * MethodsTestActual.java
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
public class MethodsTestActual {
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
