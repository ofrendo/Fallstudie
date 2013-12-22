package de.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.tests.client.optimization.OptimizeAllTests;
import de.tests.clientserver.mechanics.TestRegionBid;
import de.tests.shared.map.GenerateRegions;

@RunWith(Suite.class)
@SuiteClasses({ 
	OptimizeAllTests.class, 
	GenerateRegions.class,
	TestRegionBid.class
})
public class AllTests {
}
