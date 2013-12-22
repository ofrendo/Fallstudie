package de.shared.message;

import java.io.Serializable;

public interface Message extends Serializable {

	public MessageType getType();
	public Object getValue();	
	
}
