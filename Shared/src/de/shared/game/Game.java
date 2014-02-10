package de.shared.game;

import java.util.ArrayList;

import de.shared.map.*;
import de.shared.map.region.*;

public abstract class Game {
	
	protected ArrayList<Player> players;
	
	public GamePhase gamePhase = GamePhase.PLAYERS_JOINING;
	protected Map map;
	private int round;
	
	public Game() {
		players = new ArrayList<Player>();
	}
	
	public synchronized Map getMap() {
		return map;
	}
	
	public synchronized void addPlayer(Player player) {
		this.players.add(player);
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public void nextGamePhase() {
		switch (gamePhase) {
		case PLAYERS_JOINING:
			gamePhase = GamePhase.GAME_STARTED;
			break;
		case GAME_STARTED:
			gamePhase = GamePhase.GAME_FINISHED;
			break;
		default:
			break;
		}
	}
	
	public int getRound() {
		return round;
	}
	
	public void incrementRound() {
		round++;
	}
	
	public static int regionDistance(Coords coords1, Coords coords2) {
		//If vorzeichen is same: add beträge
		//if vorzeichen is different: take bigger one
		int dx = coords1.x - coords2.x;
		int dy = coords1.y - coords2.y;
		
		if ((dx < 0 && dy > 0) || (dx > 0 && dy < 0)) {
			//Different vorzeichen
			return Math.max(dx, dy);
		}
		else {
			//Same: add beträge
			return Math.abs(dx) + Math.abs(dy);
		}
	}
	
	//METHOD FOR TESTING
	public ResourceRegion getFirstNeutralResourceRegion() {
		for (Region region : map.getRegions()) {
			if (region instanceof ResourceRegion) {
				ResourceRegion r = (ResourceRegion) region;
				if (r.resourceRegionStatus == ResourceRegionStatus.NEUTRAL && r.resourceType != ResourceType.EMPTY && r.resourceType.isRenewable == false) {
					//System.out.println(r.resourceType);
					return r;
				}
			}
		}
		return null;
	}

	//METHOD FOR TESTING
	public CityRegion getFirstCityRegion() {
		for (Region region : map.getRegions()) {
			if (region instanceof CityRegion) {
				CityRegion r = (CityRegion) region;
				return r;
			}
		}
		return null;
	}
	
}
