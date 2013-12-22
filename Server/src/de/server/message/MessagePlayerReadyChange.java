package de.server.message;

import java.util.ArrayList;

import de.shared.game.Player;
import de.shared.message.Message;
import de.shared.message.MessageType;
import de.shared.message.MessageTypeToClient;

public class MessagePlayerReadyChange implements Message {

	private static final long serialVersionUID = -4592083247312841969L;

	@Override
	public MessageType getType() {
		return MessageTypeToClient.PLAYER_READY_CHANGE;
	}
	
	private ArrayList<Player> players;
	
	public MessagePlayerReadyChange(ArrayList<Player> players) {
		this.players = players;
	}
	
	
	@Override
	public Object getValue() {
		return players;
	}
	
	
	
}
