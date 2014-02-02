package de.client.gui;

import java.awt.Color;
import java.awt.Font;

public class Look {
	
	public static Font fontSectionTitle = new Font("Dialog", Font.PLAIN, 30);
	public static Font fontSectionPart = new Font("Dialog", Font.PLAIN, 20);
	
	public static Color COLOR_MAP_BACKGROUND = getColor("#FFEEB2");
	public static Color COLOR_MENU_BACKGROUND = getColor("#C38537");
	
	public static Color COLOR_MAP_BORDER_MINE = getColor("#FF0000");
	public static Color COLOR_MAP_BORDER_POWERSTATION = getColor("#000000");
	public static Color COLOR_MAP_BORDER_OTHEROWNED = Color.red;
	
	public static Color COLOR_MAP_BACKGROUND_CURRENTSELECTED = Color.green;
	public static final int BORDER_WIDTH_CURRENT_SELECTED = 4;
	
	public static Color COLOR_MAP_BORDER_CITY_CONTRACT = getColor("#FF00FF");
	public static final int BORDER_WIDTH_CITYCONTRACT = 4;

	public static Color COLOR_MAP_POWERLINE = getColor("A65353");
	
	public static final Color COLOR_MAP_BORDER_BUYABLE = Color.white;
	
	public static final int PANEL_LEFT_PADDING = 20;
	
	public static Color getColor(String hex) {
		hex = hex.replace("#", "");
		int intValue = Integer.parseInt(hex, 16);
		return new Color(intValue);
	}
	
	public static String getReadySymbol(boolean ready) {
		return ready ? "\u2713" : "\u00D7"; //Cross and checkmark
	}

}
