package cartGame.travel.towns;

import java.util.List;

import movement.mathDS.Graph;

public class WorldMap {
	
	Graph<Town, Road> towns;
	
	public WorldMap() {
		towns = new Graph<Town, Road>();
	}
	
	public void addTown(Town town) {
		towns.addVertex(town);
	}
	
	public void removeTown(Town town) {
		towns.removeVertex(town);
	}
	
	public Graph<Town, Road> getMap() {
		return towns;
	}
	
	public List<Town> getTowns() {
		return towns.getUnsortedVerticesList();
	}
	
	public void addConnection(Town from, Town to, Road road) {
		towns.addEdge(from, to, road);
	}
	
	public void removeConnection(Town from, Town to) {
		towns.removeEdge(from, to);
	}
}
