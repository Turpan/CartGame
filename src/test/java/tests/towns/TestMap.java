package tests.towns;

import cartGame.travel.towns.Road;
import cartGame.travel.towns.Town;
import cartGame.travel.towns.WorldMap;

public class TestMap extends WorldMap {
	public TestMap() {
		super();
		
		Town EM = new EastMighter();
		Town SC = new ShepardCrossing();
		
		Road EMSC = new EMSCRoad();
		
		addTown(EM);
		addTown(SC);
		
		addConnection(SC, EM, EMSC);
		
		setStart(SC);
	}
}
