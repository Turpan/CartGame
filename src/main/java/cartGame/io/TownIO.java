package cartGame.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cartGame.travel.towns.Road;
import cartGame.travel.towns.Town;
import cartGame.travel.towns.WorldMap;

public class TownIO extends CartGameIO{
	
	private static final byte[] HEADERDATA = "TOWNDATA".getBytes();
	
	private static final byte[] METADATA = "META".getBytes();
	
	private static final byte[] METAENDDATA = "METAEND".getBytes();
	
	private static final byte[] FOOTERDATA = "TOWNEND".getBytes();
	
	public static void writeTownData(WorldMap townData, String fileLocation) throws IOException {
		BufferedOutputStream fileWriter = new BufferedOutputStream(new FileOutputStream(fileLocation));
		byte[] townCount = intToByte(townData.getMap().getSortedVertices().size());
		
		fileWriter.write(HEADERDATA);
		
		fileWriter.write(METADATA);
		fileWriter.write(townCount);
		fileWriter.write(METAENDDATA);
		
		for (Town town : townData.getMap().getSortedVertices()) {
			byte[] idLength = intToByte(town.getID().length());
			byte[] townID = town.getID().getBytes();
			
			byte[] nameLength = intToByte(town.getName().length());
			byte[] townName = town.getName().getBytes();
			
			byte[] mapX = intToByte(town.getMapX());
			byte[] mapY = intToByte(town.getMapY());
			
			byte[] iconLength = intToByte(town.getIconLocation().length());
			byte[] iconLocation = town.getIconLocation().getBytes();
			
			byte[] backgroundLength = intToByte(town.getBackgroundLocation().length());
			byte[] backgroundLocation = town.getBackgroundLocation().getBytes();
			
			byte[] introLength = intToByte(town.getIntroLocation().length());
			byte[] introLocation = town.getIconLocation().getBytes();
			
			LinkedList<Town> linkedTowns = townData.getMap().getVertexLinkedList(town);
			List<Town> connections = new ArrayList<Town>();
			connections.addAll(linkedTowns);
			if (connections.size() > 0) connections.remove(0);
			
			byte[] connectionCount = intToByte(connections.size());
			byte[][] connectionNameLength = new byte[connections.size()][];
			byte[][] connectionName = new byte[connections.size()][];
			byte[][] roadNameLength = new byte[connections.size()][];
			byte[][] roadName = new byte[connections.size()][];
			
			for (int i=0; i<connections.size(); i++) {
				Town connection = connections.get(i);
				Road road = townData.getMap().getEdgeValue(town, connection);
				
				connectionNameLength[i] = intToByte(connection.getID().length());
				connectionName[i] = connection.getID().getBytes();
				
				roadNameLength[i] = intToByte(road.getID().length());
				roadName[i] = road.getID().getBytes();
			}
			
			fileWriter.write(idLength);
			fileWriter.write(townID);
			
			fileWriter.write(nameLength);
			fileWriter.write(townName);
			
			fileWriter.write(mapX);
			fileWriter.write(mapY);
			
			fileWriter.write(iconLength);
			fileWriter.write(iconLocation);
			
			fileWriter.write(backgroundLength);
			fileWriter.write(backgroundLocation);
			
			fileWriter.write(introLength);
			fileWriter.write(introLocation);
			
			fileWriter.write(connectionCount);
			
			for (int i=0; i<connections.size(); i++) {
				fileWriter.write(connectionNameLength[i]);
				fileWriter.write(connectionName[i]);
				
				fileWriter.write(roadNameLength[i]);
				fileWriter.write(roadName[i]);
			}
		}
		
		fileWriter.write(FOOTERDATA);
		
		fileWriter.close();
	}
	
	public static WorldMap readTownData(String fileLocation, List<Road> roadData) {
		int offset = 0;
		// load file data into array
		byte[] data = new byte[] {0};
		try {
			BufferedInputStream fileReader = new BufferedInputStream(new FileInputStream(fileLocation));
			data = fileReader.readAllBytes();
			fileReader.close();
		} catch (IOException e) {
			return null;
		}
		
		//check if file is not long enough
		if (data.length < HEADERDATA.length) {
			return null;
		}
		
		//check header
		if (!checkSignature(data, offset, HEADERDATA)) {
			return null;
		}
		offset += HEADERDATA.length;
				
		//check metadata
		if (!checkSignature(data, offset, METADATA)) {
			return null;
		}
		offset += METADATA.length;
		
		int townCount = getByteData(data, offset);
		if (townCount < 0) {
			return null;
		}
		offset += INTLENGTH;
		
		if (!checkSignature(data, offset, METAENDDATA)) {
			return null;
		}
		offset += METAENDDATA.length;
		
		List<Town> towns = new ArrayList<Town>();
		Map<Town, List<String>> connections = new LinkedHashMap<Town, List<String>>();
		Map<Town, Map<String, String>> roads = new LinkedHashMap<Town, Map<String, String>>();
		
		for (int i=0; i<townCount; i++) {
			int idLength = getByteData(data, offset);
			if (idLength < 0) {
				return null;
			}
			offset += INTLENGTH;
			
			String townID = getByteString(data, offset, idLength);
			if (townID == null) {
				return null;
			}
			offset += idLength;
			
			int nameLength = getByteData(data, offset);
			if (nameLength < 0) {
				return null;
			}
			offset += INTLENGTH;
			
			String townName = getByteString(data, offset, nameLength);
			if (townName == null) {
				return null;
			}
			offset += nameLength;
			
			int mapX = getByteData(data, offset);
			if (mapX < 0) {
				return null;
			}
			offset += INTLENGTH;
			
			int mapY = getByteData(data, offset);
			if (mapY < 0) {
				return null;
			}
			offset += INTLENGTH;
			
			int iconLength = getByteData(data, offset);
			if (iconLength < 0) {
				return null;
			}
			offset += INTLENGTH;
			
			String iconName = getByteString(data, offset, iconLength);
			if (iconName == null) {
				return null;
			}
			offset += iconLength;
			
			int backgroundLength = getByteData(data, offset);
			if (backgroundLength < 0) {
				return null;
			}
			offset += INTLENGTH;
			
			String backgroundLocation = getByteString(data, offset, backgroundLength);
			if (backgroundLocation == null) {
				return null;
			}
			offset += backgroundLength;
			
			int introLength = getByteData(data, offset);
			if (introLength < 0) {
				return null;
			}
			offset += INTLENGTH;
			
			String introLocation = getByteString(data, offset, introLength);
			if (introLocation == null) {
				return null;
			}
			offset += introLength;
			
			Town town = new Town();
			town.setID(townID);
			town.setName(townName);
			town.setMapX(mapX);
			town.setMapY(mapY);
			town.setIconLocation(iconName);
			town.setBackgroundLocation(backgroundLocation);
			town.setIntroLocation(introLocation);
			
			List<String> list = new ArrayList<String>();
			Map<String, String> roadIDs = new LinkedHashMap<String, String>();
			
			towns.add(town);
			connections.put(town, list);
			roads.put(town, roadIDs);
			
			int connectionsCount = getByteData(data, offset);
			if (connectionsCount < 0) {
				return null;
			}
			offset += INTLENGTH;
			
			for (int j=0; j<connectionsCount; j++) {
				int connectionNameLength = getByteData(data, offset);
				if (connectionNameLength < 0) {
					return null;
				}
				offset += INTLENGTH;
				
				String connectionID = getByteString(data, offset, connectionNameLength);
				if (connectionID == null) {
					return null;
				}
				offset += connectionNameLength;
				
				int roadNameLength = getByteData(data, offset);
				if (roadNameLength < 0) {
					return null;
				}
				offset += INTLENGTH;
				
				String roadID = getByteString(data, offset, roadNameLength);
				if (roadID == null) {
					return null;
				}
				offset += roadNameLength;
				
				connections.get(town).add(connectionID);
				roadIDs.put(connectionID, roadID);
			}
		}
		
		//check the footer
		if (!checkSignature(data, offset, FOOTERDATA)) {
			return null;
		}
		
		//put it together
		WorldMap worldMap = new WorldMap();
		for (Town town : towns) {
			worldMap.addTown(town);
		}
		
		for (Town town : towns) {
			List<String> connectionsList = connections.get(town);
			
			for (String connectionID : connectionsList) {
				Town connection = getConnectionFromName(connectionID, towns);
				Road road = getRoadFromName(roads.get(town).get(connectionID), roadData);
				
				if (connection == null || road == null) {
					continue;
				}
				
				worldMap.addConnection(town, connection, road);
			}
		}
		
		return worldMap;
	}
	
	private static Road getRoadFromName(String roadID, List<Road> roadData) {
		for (Road road : roadData) {
			if (road.getID().equals(roadID)) {
				return road;
			}
		}
		
		return null;
	}

	private static Town getConnectionFromName(String connectionID, List<Town> towns) {
		for (Town town : towns) {
			if (town.getID().equals(connectionID)) {
				return town;
			}
		}
		
		return null;
	}
}
