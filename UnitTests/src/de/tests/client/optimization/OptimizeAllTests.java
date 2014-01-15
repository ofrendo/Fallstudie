package de.tests.client.optimization;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	OptimizeMatrix.class, 
	OptimizeBuildMatrix.class,
	OptimizePowerStations.class
})
public class OptimizeAllTests {

}