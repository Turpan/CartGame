package cartGame.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cartGame.travel.towns.Road;

public class RoadIO extends CartGameIO {
	
	private static final byte[] HEADERDATA = "ROADDATA".getBytes();
	
	private static final byte[] METADATA = "META".getBytes();
	
	private static final byte[] METAENDDATA = "METAEND".getBytes();
	
	private static final byte[] FOOTERDATA = "ROADEND".getBytes();
	
	public static void writeRoadData(List<Road> roadData, String fileLocation) throws IOException {
		BufferedOutputStream fileWriter = new BufferedOutputStream(new FileOutputStream(fileLocation));
		byte[] roadCount = intToByte(roadData.size());
		
		fileWriter.write(HEADERDATA);
		
		fileWriter.write(METADATA);
		fileWriter.write(roadCount);
		fileWriter.write(METAENDDATA);
		
		for (Road road : roadData) {
			byte[] roadIDLength = intToByte(road.getID().length());
			byte[] roadID = road.getID().getBytes();
			
			byte[] roadDistance = intToByte(road.getDistance());
			
			byte[] biomeCount = intToByte(road.getBiomes().size());
			
			fileWriter.write(roadIDLength);
			fileWriter.write(roadID);
			fileWriter.write(roadDistance);
			
			fileWriter.write(biomeCount);
			
			for (String biome : road.getBiomes().keySet()) {
				byte[] roadBiomeLength = intToByte(biome.length());
				byte[] roadBiome = biome.getBytes();
				byte[] biomeLength = doubleToByte(road.getBiomes().get(biome));
				
				fileWriter.write(roadBiomeLength);
				fileWriter.write(roadBiome);
				fileWriter.write(biomeLength);
			}
		}
		
		fileWriter.write(FOOTERDATA);
		
		fileWriter.close();
	}
	
	public static List<Road> readRoadData(String fileLocation) {
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
		
		int roadCount = getByteData(data, offset);
		if (roadCount < 0) {
			return null;
		}
		offset += INTLENGTH;
		
		if (!checkSignature(data, offset, METAENDDATA)) {
			return null;
		}
		offset += METAENDDATA.length;
		
		List<Road> roads = new ArrayList<Road>();
		
		for (int i=0; i<roadCount; i++) {
			int roadIDLength = getByteData(data, offset);
			if (roadIDLength < 0) {
				return null;
			}
			offset += INTLENGTH;
			
			String roadID = getByteString(data, offset, roadIDLength);
			if (roadID == null) {
				return null;
			}
			offset += roadIDLength;
			
			int roadDistance = getByteData(data, offset);
			if (roadDistance < 0) {
				return null;
			}
			offset += INTLENGTH;
			
			int biomeCount = getByteData(data, offset);
			if (biomeCount < 0) {
				return null;
			}
			offset += INTLENGTH;
			
			Road road = new Road();
			road.setID(roadID);
			road.setDistance(roadDistance);
			
			for (int j=0; j<biomeCount; j++) {
				int biomeIDLength = getByteData(data, offset);
				if (biomeIDLength < 0) {
					return null;
				}
				offset += INTLENGTH;
				
				String biomeID = getByteString(data, offset, biomeIDLength);
				if (biomeID == null) {
					return null;
				}
				offset += biomeIDLength;
				
				double biomeDistance = getByteDouble(data, offset);
				if (biomeDistance < 0) {
					return null;
				}
				offset += DOUBLELENGTH;
				
				road.addBiome(biomeID, biomeDistance);
			}
			
			roads.add(road);
		}
		
		//check the footer
		if (!checkSignature(data, offset, FOOTERDATA)) {
			return null;
		}
		
		return roads;
	}
}
