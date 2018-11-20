package cartGame.combat.player;

import amyGLGraphics.IO.EventManager;
import movement.Room;
import movement.mathDS.Vector.MalformedVectorException;

public class Arena extends Room {
	
	private static final double[][][] directionTable = new double[][][]
			{{{-0.7071,0,0.7071}, {0,0,1}, {0.7071,0,0.7071}},
			{{-1,0,0},null, {1,0,0}},
			{{-0.7071,0,-0.7071}, {0,0,-1}, {0.7071,0,-0.7071}}};
	
	private Player player;
	
	public Arena() {
		super();
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		if (getContents().contains(player)) {
			removeEntity(player);
		}
		
		addEntity(player);
		this.player = player;
	}
	
	private int getMovementY() {
		int y = 1;
		if (EventManager.getManagerInstance().getMoveUp().isPressed()) y -= 1;
		if (EventManager.getManagerInstance().getMoveDown().isPressed()) y += 1;
		return y;
	}
	
	private int getMovementX() {
		int x = 1;
		if (EventManager.getManagerInstance().getMoveLeft().isPressed()) x -= 1;
		if (EventManager.getManagerInstance().getMoveRight().isPressed()) x += 1;
		return x;
	}
	
	@Override
	public void tick() {
		int x = getMovementX();
		int y = getMovementY();
		if (player != null) player.updateMovementTexture(x, y);
		if (EventManager.getManagerInstance().getMoveUp().isPressed() ||
				EventManager.getManagerInstance().getMoveDown().isPressed() || 
				EventManager.getManagerInstance().getMoveLeft().isPressed() ||
				EventManager.getManagerInstance().getMoveRight().isPressed() &&
				player != null) {
			try {
				var direction = directionTable[y][x];
				if (direction != null) { 
					player.locomote(direction);
				}
			} catch (MalformedVectorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		super.tick();
	}
}
