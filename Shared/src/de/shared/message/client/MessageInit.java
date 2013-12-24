package de.shared.message.client;

import de.shared.game.Player;
import de.shared.message.Message;
import de.shared.message.MessageType;
import de.shared.message.MessageTypeToServer;

public class MessageInit implements Message {
	private static final long serialVersionUID = 5660018588315707107L;
	private Player player;
	
	public MessageInit(Player player) {
		this.player = player;
	}
	
	@Override
	public MessageType getType() {
		return MessageTypeToServer.INIT;
	}
	
	@Override
	public Object getValue() {
		return player;
	}

}
