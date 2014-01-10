package de.shared.message;


public enum MessageTypeToServer implements MessageType {
	INIT,
	READY,
	START_RESOURCE_REGION_BIDDING,
	RESOURCE_REGION_BID,
	REQUEST_CONTRACT, 
	CONFIRM_CONTRACT,
	CANCEL_CONTRACT,
	BUILDING_FINISHED;
}
