package de.tests.server;

import org.junit.Test;

import de.server.Server;

public class ServerStart {

	@Test
	public void test() {
		Server.getInstance().init();
	}

}
