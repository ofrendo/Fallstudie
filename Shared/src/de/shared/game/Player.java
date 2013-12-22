package de.shared.game;

import java.io.Serializable;

public class Player implements Serializable {
	private static final long serialVersionUID = -2244058085130643635L;
	public String playerName;
	public String companyName;
	public boolean ready = false;	
	
	public Player(String playerName) {
		this.playerName = playerName;
		this.companyName = "0815 company AG";
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Player) {
			Player otherPlayer = (Player) o;
			return otherPlayer.playerName.equals(playerName);
		}
		return false;
	}
	
}
