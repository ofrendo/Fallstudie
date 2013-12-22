package de.tests.clientserver;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	TestSetup.class, 
	TestInit.class,
	TestReady.class
})
public class TestSuiteClientServer {
	
	public static void main(String[] args) {
		System.out.println("Hello");
	}
	
}
