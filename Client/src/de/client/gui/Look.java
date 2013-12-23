package de.client.gui;

import java.awt.Color;

import de.shared.map.region.ResourceType;

public class Look {

	public static Color getButtonColor(ResourceType resourceType) {
		String hexString;
		switch (resourceType) {
		case COAL: 
			hexString = "#656660"; break;
		case GAS:
			hexString = "#656660"; break;
		case SOLAR:
			hexString = "#656660"; break;
		case URANIUM:
			hexString = "#656660"; break;
		case WATER:
			hexString = "#656660"; break;
		case WIND:
			hexString = "#656660"; break;
		case EMPTY:
			hexString = "#656660"; break;
		default:
			hexString = "#ffffff"; break;
		}
		
		hexString.replace("#", "");
		int intValue = Integer.parseInt(hexString, 16);
		return new Color( intValue );
	}
	
}
