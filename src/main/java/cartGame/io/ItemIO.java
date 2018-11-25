package cartGame.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cartGame.resources.Item;

public class ItemIO extends CartGameIO {
	
	private static final byte[] HEADERDATA = "ITEMDATA".getBytes();
	
	private static final byte[] METADATA = "META".getBytes();
	
	private static final byte[] METAENDDATA = "METAEND".getBytes();
	
	private static final byte[] FOOTERDATA = "ITEMEND".getBytes();
	
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
		offset += INTLENGTH;
		
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
			offset += INTLENGTH;
			
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
}
