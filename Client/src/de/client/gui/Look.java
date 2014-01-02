package de.client.gui;

import java.awt.Color;

public class Look {

	public static Color COLOR_MAP_BACKGROUND = getColor("#FFEEB2");
	public static Color COLOR_MENU_BACKGROUND = getColor("#C38537");
	
	public static Color getColor(String hex) {
		hex = hex.replace("#", "");
		int intValue = Integer.parseInt(hex, 16);
		return new Color(intValue);
	}
	

}
