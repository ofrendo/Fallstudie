package de.shared.message.server;

import de.shared.message.Message;
import de.shared.message.MessageType;
import de.shared.message.MessageTypeToClient;

public class MessageGameEnd implements Message {

	private static final long serialVersionUID = 6633310553600903856L;

	@Override
	public MessageType getType() {
		return MessageTypeToClient.GAME_END;
	}
	
	private boolean isWin;
	
	public MessageGameEnd(boolean isWin) {
		this.isWin = isWin;
	}

	@Override
	public Object getValue() {
		return isWin;
	}
}
