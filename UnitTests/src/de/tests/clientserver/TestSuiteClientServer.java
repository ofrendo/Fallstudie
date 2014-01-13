package de.tests.clientserver;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.tests.clientserver.mechanics.TestBuildingFinished;
import de.tests.clientserver.mechanics.TestContract;
import de.tests.clientserver.mechanics.TestRegionBid;

@RunWith(Suite.class)
@SuiteClasses({ 
	TestSetup.class, 
	TestInit.class,
	TestReady.class,
	TestGameStart.class,
	TestGameRound.class,
	TestContract.class,
	TestRegionBid.class,
	TestBuildingFinished.class
})
public class TestSuiteClientServer {
}
