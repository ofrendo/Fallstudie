package de.tests;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class TestUtils {
	
	public static int shortWaitTime = 500;
	public static int longWaitTime = 4500;
	
	public static String getIP() {
		//return "127.0.0.1";
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "127.0.0.1";
		}
	}
	
	/**
	 * Blocks the thread for a given time. Please try changing the value of 
	 * TestUtils.shortWaitTime and TestUtils.longWaitTime if the unit tests are red!
	 * @param waitTime Blocking time in milliseconds.
	 */
	public static void block(int waitTime) {
		long startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - startTime < waitTime) {
			
		}
	}

	/**
	 * Blocks the thread for a long time. Please try changing the value of 
	 * TestUtils.shortWaitTime if the unit tests are red!
	 */
	public static void blockShort() {
		block(shortWaitTime);
	}
	
	/**
	 * Blocks the thread for a long time. Please try changing the value of 
	 * TestUtils.shortWaitTime if the unit tests are red!
	 */
	public static void blockLong() {
		block(longWaitTime);
	}
	
}
