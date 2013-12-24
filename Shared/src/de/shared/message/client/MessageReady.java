package de.shared.message.client;

import de.shared.message.*;

public class MessageReady implements Message {
	private static final long serialVersionUID = 2215906044572484762L;

	@Override
	public MessageType getType() {
		return MessageTypeToServer.READY;
	}
	
	private boolean ready;
	
	public MessageReady(boolean ready) {
		this.ready = ready;
	}

	@Override
	public Object getValue() {
		return ready;
	}

}
