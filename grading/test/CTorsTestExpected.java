package grading.test;

import java.io.IOException;

@SuppressWarnings("unused")
public class CTorsTestExpected {
	public CTorsTestExpected(int properPublic) {}
	protected CTorsTestExpected(int properProtected, int b) {}
	CTorsTestExpected(int properPackage, int b, int c) {}
	private CTorsTestExpected(int properPrivate, int b, int c, int d) {}
	public CTorsTestExpected(int[] properThrows) throws IOException {}
	
	public CTorsTestExpected(String wrongMods) {}
	public CTorsTestExpected(long[] extraThrow) {}
	public CTorsTestExpected(float[] someExtraThrows) throws IOException {}
	public CTorsTestExpected(double[] someThrows) {}
	
	public CTorsTestExpected(Integer reallyOff) {}
}
