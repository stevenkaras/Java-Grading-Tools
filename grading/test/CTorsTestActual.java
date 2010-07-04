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
