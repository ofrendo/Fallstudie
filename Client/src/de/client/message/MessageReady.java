package de.client.message;

import de.shared.message.*;

public class MessageReady implements Message {
	private static final long serialVersionUID = 2215906044572484762L;

	@Override
	public MessageType getType() {
		return MessageTypeToServer.READY;
	}

	@Override
	public Object getValue() {
		return null;
	}

}
