package grading.test;

import java.io.Serializable;

@SuppressWarnings("unused")
public class FieldsTestExpected implements Cloneable, Runnable {
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
}
