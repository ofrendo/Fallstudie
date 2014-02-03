package de.shared.message.server;

import de.shared.message.Message;
import de.shared.message.MessageType;
import de.shared.message.MessageTypeToClient;

public class MessageChatBroadcast implements Message {

	private static final long serialVersionUID = 8876009181813960873L;

	@Override
	public MessageType getType() {
		return MessageTypeToClient.CHAT_BROADCAST;
	}

	private String message;
	
	public MessageChatBroadcast(String message) {
		this.message = message;
	}
	
	@Override
	public Object getValue() {
		return message;
	}

}
