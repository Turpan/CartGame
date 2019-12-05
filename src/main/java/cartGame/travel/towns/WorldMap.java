package cartGame.travel.towns;

import java.util.List;

import movement.mathDS.Graph;

public class WorldMap {
	
	Graph<Town, Road> towns;
	
	Town start;
	
	public WorldMap() {
		towns = new Graph<Town, Road>();
	}
	
	public void addTown(Town town) {
		towns.addVertex(town);
	}
	
	public void removeTown(Town town) {
		towns.removeVertex(town);
	}
	
	public void setStart(Town start) {
		this.start = start;
	}
	
	public Town getStart() {
		return start;
	}
	
	public Graph<Town, Road> getMap() {
		return towns;
	}
	
	public List<Town> getTowns() {
		return towns.getUnsortedVerticesList();
	}
	
	public void addConnection(Town from, Town to, Road road) {
		Road returnRoad = Road.createReturnRoad(road);
		
		towns.addEdge(from, to, road);
		towns.addEdge(to, from, returnRoad);
	}
	
	public void removeConnection(Town from, Town to) {
		towns.removeEdge(from, to);
	}
	
	
}
