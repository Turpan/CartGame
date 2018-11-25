package cartGame.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cartGame.resources.Item;
import cartGame.travel.towns.Shop;

public class ShopIO extends CartGameIO {

	private static final byte[] HEADERDATA = "SHOPDATA".getBytes();
	
	private static final byte[] METADATA = "META".getBytes();
	
	private static final byte[] METAENDDATA = "METAEND".getBytes();
	
	private static final byte[] FOOTERDATA = "SHOPEND".getBytes();
	
	public static void writeShopData(List<Shop> shops, String fileLocation) throws IOException {
		BufferedOutputStream fileWriter = new BufferedOutputStream(new FileOutputStream(fileLocation));
		byte[] shopCount = intToByte(shops.size());
		
		fileWriter.write(HEADERDATA);
		
		fileWriter.write(METADATA);
		fileWriter.write(shopCount);
		fileWriter.write(METAENDDATA);
		
		for (Shop shop : shops) {
			byte[] shopIDLength = intToByte(shop.getID().length());
			byte[] shopID = shop.getID().getBytes();
			
			byte[] shopItemCount = intToByte(shop.getItems().size());
			
			fileWriter.write(shopIDLength);
			fileWriter.write(shopID);
			
			fileWriter.write(shopItemCount);
			
			for (Item item : shop.getItems()) {
				byte[] itemIDLength = intToByte(item.getID().length());
				byte[] itemID = item.getID().getBytes();
				
				byte[] itemPrice = intToByte(shop.getItemPrice(item));
				
				fileWriter.write(itemIDLength);
				fileWriter.write(itemID);
				
				fileWriter.write(itemPrice);
			}
		}
		
		fileWriter.write(FOOTERDATA);
		
		fileWriter.close();
	}
	
	public static List<Shop> readShopData(String fileLocation, List<Item> itemData) {
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
		
		int shopCount = getByteData(data, offset);
		if (shopCount < 0) {
			return null;
		}
		offset += INTLENGTH;
		
		if (!checkSignature(data, offset, METAENDDATA)) {
			return null;
		}
		offset += METAENDDATA.length;
		
		List<Shop> shops = new ArrayList<Shop>();
		
		for (int i=0; i<shopCount; i++) {
			int shopIDLength = getByteData(data, offset);
			if (shopIDLength < 0) {
				return null;
			}
			offset += INTLENGTH;
			
			String shopID = getByteString(data, offset, shopIDLength);
			if (shopID == null) {
				return null;
			}
			offset += shopIDLength;
			
			int itemCount = getByteData(data, offset);
			if (itemCount < 0) {
				return null;
			}
			offset += INTLENGTH;
			
			Shop shop = new Shop();
			shop.setID(shopID);
			
			shops.add(shop);
			
			for (int j=0; j<itemCount; j++) {
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
				
				int itemPrice = getByteData(data, offset);
				if (itemPrice < 0) {
					return null;
				}
				offset += INTLENGTH;
				
				Item item = getItemFromName(itemID, itemData);
				if (item == null) {
					continue;
				}
				
				shop.addItem(item, itemPrice);
			}
		}
		
		//check the footer
		if (!checkSignature(data, offset, FOOTERDATA)) {
			return null;
		}
		
		return shops;
	}
	
	private static Item getItemFromName(String itemID, List<Item> itemData) {
		for (Item item : itemData) {
			if (item.getID().equals(itemID)) {
				return item;
			}
		}
		
		return null;
	}
}
