package de.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.tests.client.TestSuiteClient;
import de.tests.clientserver.TestSuiteClientServer;
import de.tests.shared.map.GenerateRegions;
import de.tests.shared.map.TestEnergyExchange;

@RunWith(Suite.class)
@SuiteClasses({ 
	TestSuiteClient.class,
	GenerateRegions.class,
	TestEnergyExchange.class,
	TestSuiteClientServer.class
})
public class AllTests {
	
}
