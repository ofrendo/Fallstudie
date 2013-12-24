package de.shared.message.server;

import java.util.ArrayList;

import de.shared.game.Player;
import de.shared.message.Message;
import de.shared.message.MessageType;
import de.shared.message.MessageTypeToClient;

public class MessageInitConfirm implements Message {

	private static final long serialVersionUID = -1287203793014130267L;

	@Override
	public MessageType getType() {
		return MessageTypeToClient.INIT_CONFIRM;
	}
	
	private ArrayList<Player> players;
	
	public MessageInitConfirm(ArrayList<Player> players) {
		this.players = players;
	}

	@Override
	public Object getValue() {
		return players;
	}

}
