package cartGame.tools.editor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import cartGame.resources.Item;
import cartGame.travel.towns.Shop;

public class ModIO {
	private static final byte[] HEADERDATA = "MODDATA".getBytes();
	
	private static final byte[] METADATA = "META".getBytes();
	
	private static final byte[] METAENDDATA = "METAEND".getBytes();
	
	private static final byte[] FOOTERDATA = "MODEND".getBytes();
	
	private static final int DOUBLELENGTH = 8;
	private static final int INTLENGTH = 4;
	
	public static void writeModData(String mod, ModData data) throws IOException {
		String fileLocation = "mods/" + mod + "/INFO.DAT";
		BufferedOutputStream fileWriter = new BufferedOutputStream(new FileOutputStream(fileLocation));
		byte[] version = doubleToByte(data.getVersion());
		byte[] modNameLength = intToByte(data.getName().length());
		byte[] modName = data.getName().getBytes();
		
		fileWriter.write(HEADERDATA);
		
		fileWriter.write(METADATA);
		fileWriter.write(version);
		fileWriter.write(METAENDDATA);
		
		fileWriter.write(modNameLength);
		fileWriter.write(modName);
		
		fileWriter.write(FOOTERDATA);
		
		fileWriter.close();
	}
	
	public static ModData readModData(String mod) {
		int offset = 0;
		// load file data into array
		byte[] data = new byte[] {0};
		try {
			BufferedInputStream fileReader = new BufferedInputStream(new FileInputStream("mods/" + mod + "/INFO.DAT"));
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
				
		double version = getByteDouble(data, offset);
		if (version < 0) {
			return null;
		}
		offset += DOUBLELENGTH;
		
		if (!checkSignature(data, offset, METAENDDATA)) {
			return null;
		}
		offset += METAENDDATA.length;
		
		int modNameLength = getByteData(data, offset);
		if (modNameLength < 0) {
			return null;
		}
		offset += INTLENGTH;
		
		String modName = getByteString(data, offset, modNameLength);
		if (modName == null) {
			return null;
		}
		offset += modNameLength;
		
		//check the footer
		if (!checkSignature(data, offset, FOOTERDATA)) {
			return null;
		}
		
		ModData modData = new ModData();
		modData.setVersion(version);
		modData.setName(modName);
		
		return modData;
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
			byte[] num = new byte[INTLENGTH];
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
	
	private static double getByteDouble(byte[] data, int offset) {
		try {
			byte[] num = new byte[DOUBLELENGTH];
			for (int i=offset; i<offset+num.length; i++) {
				num[i-offset] = data[i];
			}
			return byteToDouble(num);
		} catch (NullPointerException e) {
			return Double.MIN_VALUE;
		}
	}
	
	private static byte[] doubleToByte(double value) {
		byte[] data = new byte[DOUBLELENGTH];
		ByteBuffer.wrap(data).putDouble(value);
		return data;
	}
	
	private static double byteToDouble(byte[] data) {
		return ByteBuffer.wrap(data).getDouble();
	}
}
