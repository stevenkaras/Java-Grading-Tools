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
