package de.tests;

public class TestUtils {

	public static int shortWaitTime = 500;
	public static int longWaitTime = 3500;
	
	public static String getIP() {
		return "127.0.0.1";
	}
	
	public static void block(int waitTime) {
		long startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - startTime < waitTime) {
			
		}
	}

	public static void blockShort() {
		block(shortWaitTime);
	}
	
	public static void blockLong() {
		block(longWaitTime);
	}
	
}
