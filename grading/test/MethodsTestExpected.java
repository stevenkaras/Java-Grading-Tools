package grading.test;

import java.io.IOException;

@SuppressWarnings("unused")
public class MethodsTestExpected {
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
