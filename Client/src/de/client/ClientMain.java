package de.client;

import java.net.UnknownHostException;


public class ClientMain {

	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub
		Client client = new Client("127.0.0.1", "RandomClient"); //InetAddress.getLocalHost().getHostAddress()
		client.connectToServer();
		/*client.sendMessage(new InitMessage("Jörn ist ein noob"));
		client.handleMessage();
		System.out.println("Sending ready message...");
		client.sendMessage(new ReadyMessage());
		client.handleMessage(); */
	}

}
