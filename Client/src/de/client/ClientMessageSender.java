package de.client;

import de.shared.message.Message;

/**
 * Used to send a message in a different thread so that the user GUI does not freeze during sending.
 */
public class ClientMessageSender extends Thread {
	
	private Message message;
	private Client client;
	
	public ClientMessageSender(Client client, Message message) {
		this.client = client;
		this.message = message;
	}
	
	@Override
	public void run() {
		client.sendMessage(message);
	}
	
}
