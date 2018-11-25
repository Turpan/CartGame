package cartGame.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cartGame.combat.player.Ability;
import cartGame.combat.player.Player;
import cartGame.travel.towns.Town;

public class CharacterIO extends CartGameIO {
	
	private static final byte[] HEADERDATA = "TOWNDATA".getBytes();
	
	private static final byte[] METADATA = "META".getBytes();
	
	private static final byte[] METAENDDATA = "METAEND".getBytes();
	
	private static final byte[] FOOTERDATA = "TOWNEND".getBytes();
	
	public static void writeCharacterData(List<Player> characters, String fileLocation) throws IOException {
		BufferedOutputStream fileWriter = new BufferedOutputStream(new FileOutputStream(fileLocation));
		byte[] characterCount = intToByte(characters.size());
		
		fileWriter.write(HEADERDATA);
		fileWriter.write(METADATA);
		fileWriter.write(characterCount);
		fileWriter.write(METAENDDATA);
		
		for (Player character : characters) {
			byte[] charIDLength = intToByte(character.getID().length());
			byte[] charID = character.getID().getBytes();
			
			fileWriter.write(charIDLength);
			fileWriter.write(charID);
			
			for (String location : character.getTextureLocations()) {
				if (location == null) {
					location = "";
				}
				
				byte[] textureLocationLength = intToByte(location.length());
				byte[] textureLocation = location.getBytes();
				
				fileWriter.write(textureLocationLength);
				fileWriter.write(textureLocation);
			}
			
			byte[] ability1Length = intToByte(character.getAbility1().getID().length());
			byte[] ability1 = character.getAbility1().getID().getBytes();
			
			byte[] ability2Length = intToByte(character.getAbility2().getID().length());
			byte[] ability2 = character.getAbility2().getID().getBytes();
			
			byte[] ability3Length = intToByte(character.getAbility3().getID().length());
			byte[] ability3 = character.getAbility3().getID().getBytes();
			
			fileWriter.write(ability1Length);
			fileWriter.write(ability1);
			
			fileWriter.write(ability2Length);
			fileWriter.write(ability2);
			
			fileWriter.write(ability3Length);
			fileWriter.write(ability3);
		}
		
		fileWriter.write(FOOTERDATA);
		
		fileWriter.close();
	}
	
	public static List<Player> readCharacterData(String fileLocation, List<Ability> abilities) {
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
		
		int characterCount = getByteData(data, offset);
		if (characterCount < 0) {
			return null;
		}
		offset += INTLENGTH;
		
		if (!checkSignature(data, offset, METAENDDATA)) {
			return null;
		}
		offset += METAENDDATA.length;
		
		List<Player> characters = new ArrayList<Player>();
		
		for (int i=0; i<characterCount; i++) {
			Player character = new Player();
			
			int characterIDLength = getByteData(data, offset);
			if (characterIDLength < 0) {
				return null;
			}
			offset += INTLENGTH;
			
			String characterID = getByteString(data, offset, characterIDLength);
			if (characterID == null) {
				return null;
			}
			offset += characterIDLength;
			
			character.setID(characterID);
			
			for (int j=0; j<8; j++) {
				int animLocationLength = getByteData(data, offset);
				if (animLocationLength < 0) {
					return null;
				}
				offset += INTLENGTH;
				
				String animationLocation = getByteString(data, offset, animLocationLength);
				if (animationLocation == null) {
					return null;
				}
				offset += animLocationLength;
				
				character.getTextureLocations()[i] = animationLocation;
			}
			
			int ability1IDLength = getByteData(data, offset);
			if (ability1IDLength < 0) {
				return null;
			}
			offset += INTLENGTH;
			
			String ability1ID = getByteString(data, offset, ability1IDLength);
			if (ability1ID == null) {
				return null;
			}
			offset += ability1IDLength;
			
			int ability2IDLength = getByteData(data, offset);
			if (ability2IDLength < 0) {
				return null;
			}
			offset += INTLENGTH;
			
			String ability2ID = getByteString(data, offset, ability2IDLength);
			if (ability2ID == null) {
				return null;
			}
			offset += ability2IDLength;
			
			int ability3IDLength = getByteData(data, offset);
			if (ability3IDLength < 0) {
				return null;
			}
			offset += INTLENGTH;
			
			String ability3ID = getByteString(data, offset, ability3IDLength);
			if (ability3ID == null) {
				return null;
			}
			offset += ability3IDLength;
			
			character.setAbility1(getAbilityFromName(ability1ID, abilities));
			character.setAbility2(getAbilityFromName(ability2ID, abilities));
			character.setAbility3(getAbilityFromName(ability3ID, abilities));
			
			characters.add(character);
		}
		
		//check the footer
		if (!checkSignature(data, offset, FOOTERDATA)) {
			return null;
		}
		
		return characters;
	}
	
	private static Ability getAbilityFromName(String abilityID, List<Ability> abilities) {
		for (Ability ability : abilities) {
			if (ability.getID().equals(abilityID)) {
				return ability;
			}
		}
		
		return null;
	}
}
