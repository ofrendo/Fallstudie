package de.server.message;
import de.shared.message.*;
import de.shared.map.*;

public class MessageMapUpdate implements Message {
	private static final long serialVersionUID = 2876122061028503215L;
	private Map map;
	public MessageMapUpdate(Map map) {
		this.map = map;
	}
	
	@Override
	public MessageType getType() {
		return MessageTypeToClient.MAP_UPDATE;
	}

	@Override
	public Object getValue() {
		return map;
	}

}
