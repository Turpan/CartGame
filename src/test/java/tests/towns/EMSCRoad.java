package tests.towns;

import cartGame.travel.towns.Road;

public class EMSCRoad extends Road {
	public EMSCRoad() {
		super();
		setID("emscroad");
		setDistance(1000);
		
		addBiome("deep_forest", 0);
		addBiome("light_forest", 100);
	}
}
