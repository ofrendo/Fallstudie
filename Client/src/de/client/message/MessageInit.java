package de.client.message;

import de.shared.message.*;

public class MessageInit implements Message {
	private static final long serialVersionUID = 5660018588315707107L;
	private String playerName;
	
	public MessageInit(String playerName) {
		this.playerName = playerName;
	}
	
	@Override
	public MessageType getType() {
		return MessageTypeToServer.INIT;
	}
	
	@Override
	public Object getValue() {
		return playerName;
	}

}
