package cartGame.io;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import cartGame.combat.effects.Attack;
import cartGame.combat.effects.ExpandingCircle;

public class AttackIO extends CartGameIO {
	private static final byte[] HEADERDATA = "TOWNDATA".getBytes();
	
	private static final byte[] METADATA = "META".getBytes();
	
	private static final byte[] METAENDDATA = "METAEND".getBytes();
	
	private static final byte[] FOOTERDATA = "TOWNEND".getBytes();
	
	private static final String EXPANDINGCIRCLE = "ExpCir";
	
	public static void writeAttackData(List<Attack> attacks, String fileLocation) throws IOException {
		BufferedOutputStream fileWriter = new BufferedOutputStream(new FileOutputStream(fileLocation));
		byte[] attackCount = intToByte(attacks.size());
		
		fileWriter.write(HEADERDATA);
		
		fileWriter.write(METADATA);
		fileWriter.write(attackCount);
		fileWriter.write(METAENDDATA);
		
		for (Attack attack : attacks) {
			byte[] attackIDLength = intToByte(attack.getID().length());
			byte[] attackID = attack.getID().getBytes();
			
			byte[] attackDamage = intToByte(attack.getDamage());
			
			byte[] attackHitLimit = intToByte(attack.getHitLimit());
			
			byte[] iconLength = intToByte(attack.getTextureLocation().length());
			byte[] iconLocation = attack.getTextureLocation().getBytes();
			
			fileWriter.write(attackIDLength);
			fileWriter.write(attackID);
			
			fileWriter.write(attackDamage);
			
			fileWriter.write(attackHitLimit);
			
			fileWriter.write(iconLength);
			fileWriter.write(iconLocation);
			
			writeAttackClassData(attack, fileWriter);
		}
		
		fileWriter.write(FOOTERDATA);
		
		fileWriter.close();
	}
	
	private static void writeAttackClassData(Attack attack, BufferedOutputStream fileWriter) throws IOException {
		if (attack instanceof ExpandingCircle) {
			ExpandingCircle expandingCircle = (ExpandingCircle) attack;
			writeExpandingCircleData(expandingCircle, fileWriter);
			return;
		}
	}
	
	private static void writeExpandingCircleData(ExpandingCircle expandingCircle, BufferedOutputStream fileWriter) throws IOException {
		byte[] classIDLength = intToByte(EXPANDINGCIRCLE.length());
		byte[] classID = EXPANDINGCIRCLE.getBytes();
		
		byte[] initialSize = doubleToByte(expandingCircle.getInitialSize());
		
		byte[] growthRate = doubleToByte(expandingCircle.getGrowthRate());
		
		byte[] maxSize = doubleToByte(expandingCircle.getMaxSize());
		
		fileWriter.write(classIDLength);
		fileWriter.write(classID);
		
		fileWriter.write(initialSize);
		
		fileWriter.write(growthRate);
		
		fileWriter.write(maxSize);
	}
}
