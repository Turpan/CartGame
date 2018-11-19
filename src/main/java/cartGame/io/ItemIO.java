package cartGame.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cartGame.resources.Item;

public class ItemIO {
	
	private static final byte[] HEADERDATA = "ITEMDATA".getBytes();
	
	private static final byte[] METADATA = "META".getBytes();
	
	private static final byte[] METAENDDATA = "METAEND".getBytes();
	
	private static final byte[] FOOTERDATA = "ITEMEND".getBytes();
	
	private static final int BYTELENGTH = 4;
	
	public static void writeItemData(List<Item> itemData, String fileLocation) throws IOException {
		BufferedOutputStream fileWriter = new BufferedOutputStream(new FileOutputStream(fileLocation));
		byte[] itemCount = intToByte(itemData.size());
		
		fileWriter.write(HEADERDATA);
		
		fileWriter.write(METADATA);
		fileWriter.write(itemCount);
		fileWriter.write(METAENDDATA);
		
		for (Item item : itemData) {
			byte[] itemIDLength = intToByte(item.getID().length());
			byte[] itemID = item.getID().getBytes();
			
			fileWriter.write(itemIDLength);
			fileWriter.write(itemID);
		}
		
		fileWriter.write(FOOTERDATA);
		
		fileWriter.close();
	}
	
	public static List<Item> readItemData(String fileLocation) {
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
		
		int itemCount = getByteData(data, offset);
		if (itemCount < 0) {
			return null;
		}
		offset += BYTELENGTH;
		
		if (!checkSignature(data, offset, METAENDDATA)) {
			return null;
		}
		offset += METAENDDATA.length;
		
		List<Item> items = new ArrayList<Item>();
		
		for (int i=0; i<itemCount; i++) {
			int itemIDLength = getByteData(data, offset);
			if (itemIDLength < 0) {
				return null;
			}
			offset += BYTELENGTH;
			
			String itemID = getByteString(data, offset, itemIDLength);
			if (itemID == null) {
				return null;
			}
			offset += itemIDLength;
			
			Item item = new Item();
			item.setID(itemID);
			
			items.add(item);
		}
		
		//check the footer
		if (!checkSignature(data, offset, FOOTERDATA)) {
			return null;
		}
				
		return items;
	}
	
	private static byte[] intToByte(int num) {
		return new byte[] { 
			(byte)(num >> 24),
			(byte)(num >> 16),
			(byte)(num >> 8),
			(byte)num };
	}
	
	private static int byteToInt(byte[] data) {
		return data[0] << 24 | (data[1] & 0xFF) << 16 | (data[2] & 0xFF) << 8 | (data[3] & 0xFF);
	}
	
	/*
	 * return false if array is not long enough or if data does not match
	 */
	private static boolean checkSignature(byte[] data, int offset, byte[] sig) {
		try {
			for (int i=offset; i< offset + sig.length; i++) {
				if (data[i] != sig[i-offset]) {
					return false;
				}
			}
			return true;
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	/*
	 * will catch for nullpointer which denotes that the file is cut short too early
	 */
	private static int getByteData(byte[] data, int offset) {
		try {
			byte[] num = new byte[BYTELENGTH];
			for (int i=offset; i<offset+4; i++) {
				num[i-offset] = data[i];
			}
			return byteToInt(num);
		} catch (NullPointerException e) {
			return Integer.MIN_VALUE;
		}
	}
	
	private static String getByteString(byte[] data, int offset, int length) {
		try {
			byte[] stringData = new byte[length];
			for (int i=offset; i<offset+length; i++) {
				stringData[i-offset] = data[i];
			}
			
			return new String(stringData);
			
		} catch (NullPointerException e) {
			return null;
		}
	}
}
