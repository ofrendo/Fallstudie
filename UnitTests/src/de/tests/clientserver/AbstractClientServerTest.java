package de.tests.clientserver;

import org.junit.After;
import org.junit.Before;

import de.client.gui.Controller;
import de.server.Server;
import de.shared.map.generate.CityNameGenerator;

public class AbstractClientServerTest {
	
	@Before
	public void setup() {
		Controller.getInstance().setUnitTestMode();
		ClientServerMonitor.getInstance().startTest();
	}
	
	@After
	public void destroy() {
		Server.getInstance().reset();
		CityNameGenerator.resetIndex();
		ClientServerMonitor.getInstance().endTest();
	}
	
}
