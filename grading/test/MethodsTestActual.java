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
