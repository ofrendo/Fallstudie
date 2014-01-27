package de.tests.client;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.tests.client.optimization.OptimizeAllTests;

@RunWith(Suite.class)
@SuiteClasses({ 
	OptimizeAllTests.class,
	TestInvestmentDepreciation.class,
	TestWarehouse.class
})
public class TestSuiteClient {

}
