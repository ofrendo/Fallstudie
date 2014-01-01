package de.tests.clientserver;

import de.server.Server;

public class ClientServerMonitor {
	
	private static ClientServerMonitor instance;
	
	private ClientServerMonitor() {
		
	}
	
	public static ClientServerMonitor getInstance() {
		if (instance == null)
			instance = new ClientServerMonitor();
		
		return instance;
	}
	
	private boolean isTesting = false;
	
	public synchronized void startTest() {
		try {
			while (isTesting == true)
				wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void endTest() {
		Server.getInstance().reset();
		isTesting = false;
		notifyAll();
	}
	
}
