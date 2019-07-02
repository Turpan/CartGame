package cartGame.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cartGame.combat.player.abilities.Ability;

public class AbilityIO extends CartGameIO {
	
	private static final byte[] HEADERDATA = "TOWNDATA".getBytes();
	
	private static final byte[] METADATA = "META".getBytes();
	
	private static final byte[] METAENDDATA = "METAEND".getBytes();
	
	private static final byte[] FOOTERDATA = "TOWNEND".getBytes();
	
	public static void writeAbilityData(List<Ability> abilities, String fileLocation) throws IOException {
		BufferedOutputStream fileWriter = new BufferedOutputStream(new FileOutputStream(fileLocation));
		byte[] itemCount = intToByte(abilities.size());
		
		fileWriter.write(HEADERDATA);
		
		fileWriter.write(METADATA);
		fileWriter.write(itemCount);
		fileWriter.write(METAENDDATA);
		
		for (Ability ability : abilities) {
			byte[] abilityIDLength = intToByte(ability.getID().length());
			byte[] abilityID = ability.getID().getBytes();
			
			byte[] abilityDuration = intToByte(ability.getDuration());
			byte[] abilityCooldown = intToByte(ability.getCooldown());
			
			byte[] abilityUpLength = intToByte(ability.getUpAnimationLocation().length());
			byte[] abilityUpLocation = ability.getUpAnimationLocation().getBytes();
			
			byte[] abilityDownLength = intToByte(ability.getDownAnimationLocation().length());
			byte[] abilityDownLocation = ability.getDownAnimationLocation().getBytes();
			
			byte[] abilityLeftLength = intToByte(ability.getLeftAnimationLocation().length());
			byte[] abilityLeftLocation = ability.getLeftAnimationLocation().getBytes();
			
			byte[] abilityRightLength = intToByte(ability.getRightAnimationLocation().length());
			byte[] abilityRightLocation = ability.getRightAnimationLocation().getBytes();
			
			fileWriter.write(abilityIDLength);
			fileWriter.write(abilityID);
			
			fileWriter.write(abilityDuration);
			fileWriter.write(abilityCooldown);
			
			fileWriter.write(abilityUpLength);
			fileWriter.write(abilityUpLocation);
			
			fileWriter.write(abilityDownLength);
			fileWriter.write(abilityDownLocation);
			
			fileWriter.write(abilityLeftLength);
			fileWriter.write(abilityLeftLocation);
			
			fileWriter.write(abilityRightLength);
			fileWriter.write(abilityRightLocation);
		}
		
		fileWriter.write(FOOTERDATA);
		
		fileWriter.close();
	}
	
	public static List<Ability> readAbilityData(String fileLocation) {
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
		
		int abilityCount = getByteData(data, offset);
		if (abilityCount < 0) {
			return null;
		}
		offset += INTLENGTH;
		
		if (!checkSignature(data, offset, METAENDDATA)) {
			return null;
		}
		offset += METAENDDATA.length;
		
		List<Ability> abilities = new ArrayList<Ability>();
		
		for (int i=0; i<abilityCount; i++) {
			int abilityIDLength = getByteData(data, offset);
			if (abilityIDLength < 0) {
				return null;
			}
			offset += INTLENGTH;
			
			String abilityID = getByteString(data, offset, abilityIDLength);
			if (abilityID == null) {
				return null;
			}
			offset += abilityIDLength;
			
			int abilityDuration = getByteData(data, offset);
			if (abilityDuration < 0) {
				return null;
			}
			offset += INTLENGTH;
			
			int abilityCooldown = getByteData(data, offset);
			if (abilityCooldown < 0) {
				return null;
			}
			offset += INTLENGTH;
			
			int abilityUpLength = getByteData(data, offset);
			if (abilityUpLength < 0) {
				return null;
			}
			offset += INTLENGTH;
			
			String abilityUpLocation = getByteString(data, offset, abilityUpLength);
			if (abilityUpLocation == null) {
				return null;
			}
			offset += abilityUpLength;
			
			int abilityDownLength = getByteData(data, offset);
			if (abilityDownLength < 0) {
				return null;
			}
			offset += INTLENGTH;
			
			String abilityDownLocation = getByteString(data, offset, abilityDownLength);
			if (abilityDownLocation == null) {
				return null;
			}
			offset += abilityDownLength;
			
			int abilityLeftLength = getByteData(data, offset);
			if (abilityLeftLength < 0) {
				return null;
			}
			offset += INTLENGTH;
			
			String abilityLeftLocation = getByteString(data, offset, abilityLeftLength);
			if (abilityLeftLocation == null) {
				return null;
			}
			offset += abilityLeftLength;
			
			int abilityRightLength = getByteData(data, offset);
			if (abilityRightLength < 0) {
				return null;
			}
			offset += INTLENGTH;
			
			String abilityRightLocation = getByteString(data, offset, abilityRightLength);
			if (abilityRightLocation == null) {
				return null;
			}
			offset += abilityRightLength;
			
//			Ability ability = new Ability();
//			ability.setID(abilityID);
//			ability.setDuration(abilityDuration);
//			ability.setCooldown(abilityCooldown);
//			ability.setUpAnimationLocation(abilityUpLocation);
//			ability.setDownAnimationLocation(abilityDownLocation);
//			ability.setLeftAnimationLocation(abilityLeftLocation);
//			ability.setRightAnimationLocation(abilityRightLocation);
//			abilities.add(ability);
		}
		
		//check the footer
		if (!checkSignature(data, offset, FOOTERDATA)) {
			return null;
		}
		
		return abilities;
	}
	
}
