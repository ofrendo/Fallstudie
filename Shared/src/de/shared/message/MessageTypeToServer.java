package de.shared.message;


public enum MessageTypeToServer implements MessageType {
	INIT,
	READY,
	START_RESOURCE_REGION_BIDDING,
	RESOURCE_REGION_BID,
	REQUEST_CONTRACT, 
	CONFIRM_CONTRACT,
	CANCEL_CONTRACT,
	
	BUILDING_FINISHED, //Sent when a building has finished
	
	TRADE_ENERGY; //Sent when energy is bought or sold from the energyexchange
}
