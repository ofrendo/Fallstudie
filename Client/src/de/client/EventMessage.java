package de.client;

import de.shared.map.region.Coords;

public class EventMessage {
	
	public String message;
	public Coords coords;
	
	public EventMessage(String message, Coords coords) {
		this.message = message;
		this.coords = coords;
	}
}
