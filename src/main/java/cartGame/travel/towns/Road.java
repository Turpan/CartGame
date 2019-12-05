package cartGame.travel.towns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Road {
	
	String ID;
	
	int distance;
	
	Map<String, Double> biomes = new LinkedHashMap<String, Double>();
	
	public Road() {
		
	}
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public Map<String, Double> getBiomes() {
		return biomes;
	}
	
	public void addBiome(String biomeID, double distance) {
		biomes.put(biomeID, distance);
	}
	
	public void removeBiome(String biomeID) {
		biomes.remove(biomeID);
	}
	
	public static Road createReturnRoad(Road road) {
		Road returnRoad = new Road();
		returnRoad.setID(road.getID());
		returnRoad.setDistance(road.getDistance());
		List<String> biomes = new ArrayList<String>(road.getBiomes().keySet());
		Collections.reverse(biomes);
		double lastStop = 0;
		for (String biome : biomes) {
			double distance = road.getBiomes().get(biome);
			
			returnRoad.addBiome(biome, 0 + lastStop);
			lastStop = 1000 - distance;
		}
		
		return returnRoad;
	}

}
