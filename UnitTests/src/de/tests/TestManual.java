package de.tests;

import de.client.gui.Controller;
import de.server.Server;

public class TestManual {
	public static void main(String[] args) {
		Server.getInstance().start();
		Controller.getInstance();
	}
}
