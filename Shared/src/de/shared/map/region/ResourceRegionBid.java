package de.shared.map.region;

import java.io.Serializable;

import de.shared.game.Player;

public class ResourceRegionBid implements Serializable {
	
	private static final long serialVersionUID = -3753618455018375629L;
	
	public final double amount;
	public Player player;
	public Coords coords;
	
	public ResourceRegionBid(Coords coords, Player player, double amount) {
		this.coords = coords;
		this.player = player;
		this.amount = amount;
	}
	
}
