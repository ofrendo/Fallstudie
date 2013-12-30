package de.shared.message;


public enum MessageTypeToClient implements MessageType {
	RESET, //reset what??
	INIT_REJECT, 
	INIT_CONFIRM,
	PLAYER_READY_CHANGE,
	MAP_UPDATE,
	CONTRACT_REQUEST_ANSWER,
	
	BUILDING_FINISHED;
}
