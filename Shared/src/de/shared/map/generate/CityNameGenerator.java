package de.shared.map.generate;

import java.util.Arrays;
import java.util.Collections;

public class CityNameGenerator {
	
	private static int indexState = -1;
	private static int indexCity = 0;
	
	private static String[][] names = {
		{
			"Berlin",
			"Hamburg",
			"München",
			"Köln",
			"Frankfurt am Main",
			"Stuttgart",
			"Düsseldorf",
			"Dortmund",
			"Essen",
			"Bremen",
			"Dresden",
			"Leipzig",
			"Hannover",
			"Nürnberg",
			"Duisburg",
			"Bochum",
			"Wuppertal",
			"Bielefeld",
			"Bonn",
			"Münster",
			"Karlsruhe",
			"Mannheim",
			"Wiesbaden",
			"Augsburg",
			"Gelsenkirchen",
			"Mönchengladbach",
			"Braunschweig",
			"Chemnitz",
			"Aachen",
			"Kiel",
			"Magdeburg",
			"Krefeld",
			"Oberhausen",
			"Lübeck",
			"Erfurt",
			"Rostock",
			"Mainz"
		}
	};
	
	public static String getNextCityName() {
		if (indexState == -1) {
			indexState = (int) (Math.random() * (names.length));
			Collections.shuffle(Arrays.asList(names[indexState]));
		}
		
		return names[indexState][indexCity++];
	}
	
}

