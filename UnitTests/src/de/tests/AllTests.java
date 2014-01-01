package de.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.tests.client.optimization.OptimizeAllTests;
import de.tests.client1.TestInvestmentDepreciation;
import de.tests.clientserver.TestSuiteClientServer;
import de.tests.shared.map.GenerateRegions;

@RunWith(Suite.class)
@SuiteClasses({ 
	OptimizeAllTests.class,
	TestInvestmentDepreciation.class,
	GenerateRegions.class,
	TestSuiteClientServer.class
})
public class AllTests {
}
