package de.tests.client;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.client.company.Investment;

public class TestInvestmentDepreciation {
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Investment investment = new Investment(32000, 5);
		investment.performDepreciation();
		Assert.assertEquals(25600, investment.getBookValue(), 0.000000001);
		investment.performDepreciation();
		Assert.assertEquals(19200, investment.getBookValue(), 0.000000001);
		investment.performDepreciation();
		Assert.assertEquals(12800, investment.getBookValue(), 0.000000001);
		investment.performDepreciation();
		Assert.assertEquals(6400, investment.getBookValue(), 0.000000001);
		investment.performDepreciation();
		Assert.assertEquals(1, investment.getBookValue(), 0.000000001);
		investment.performDepreciation();
		Assert.assertEquals(1, investment.getBookValue(), 0.000000001);
	}
}