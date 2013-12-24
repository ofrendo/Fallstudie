package de.shared.message.server;

import de.shared.message.Message;
import de.shared.message.MessageType;
import de.shared.message.MessageTypeToClient;

public class MessageInitReject implements Message {

	private static final long serialVersionUID = -5755173335098925260L;

	@Override
	public MessageType getType() {
		return MessageTypeToClient.INIT_REJECT;
	}

	@Override
	public Object getValue() {
		return null;
	}

}
