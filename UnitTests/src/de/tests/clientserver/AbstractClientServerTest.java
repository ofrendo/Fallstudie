package de.tests.clientserver;

import org.junit.After;
import org.junit.Before;

import de.server.Server;

public class AbstractClientServerTest {
	
	@Before
	public void setup() {
		ClientServerMonitor.getInstance().startTest();
	}
	
	@After
	public void destroy() {
		Server.getInstance().reset();
		ClientServerMonitor.getInstance().endTest();
	}
	
}
