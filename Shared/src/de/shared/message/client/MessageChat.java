package de.shared.message.client;

import de.shared.message.Message;
import de.shared.message.MessageType;
import de.shared.message.MessageTypeToServer;

public class MessageChat implements Message {
	
	private static final long serialVersionUID = -4276118199596591436L;

	@Override
	public MessageType getType() {
		return MessageTypeToServer.CHAT_MESSAGE;
	}

	private String message;
	
	public MessageChat(String message) {
		this.message = message;
	}
	
	@Override
	public Object getValue() {
		return message;
	}

}
