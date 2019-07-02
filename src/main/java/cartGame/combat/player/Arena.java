package cartGame.combat.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import amyGLGraphics.IO.EventManager;
import movement.Entity;
import movement.Room;
import movement.mathDS.Vector;

public class Arena extends Room {
	
	private static final double[][][] directionTable = new double[][][]
			{{{-0.7071,0,0.7071}, {0,0,1}, {0.7071,0,0.7071}},
			{{-1,0,0},null, {1,0,0}},
			{{-0.7071,0,-0.7071}, {0,0,-1}, {0.7071,0,-0.7071}}};
	
	private Being player;
	private List<Entity> toRemove = new ArrayList<Entity>();
	
	public Arena() {
		super();
	}

	public Being getPlayer() {
		return player;
	}

	public void setPlayer(Being player) {
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
		if (player != null) { 
			player.updateMovementTexture(x, y);
			if ((EventManager.getManagerInstance().getMoveUp().isPressed() ||
				EventManager.getManagerInstance().getMoveDown().isPressed() || 
				EventManager.getManagerInstance().getMoveLeft().isPressed() ||
				EventManager.getManagerInstance().getMoveRight().isPressed()) &&
				player.onFloor() && player.getActiveAbility() == -1) {
				var direction = directionTable[y][x];
				if (direction != null) { 
					player.locomote(direction);
					player.setAngle(Math.atan2(direction[2], direction[0]));
				}
			}	
			if(EventManager.getManagerInstance().getSpace().isPressed()) {
				player.useAbility(1);
			}
			if (EventManager.getManagerInstance().getAttack1().isPressed()){
				player.useAbility(0);
				
			}
		}
		
		super.tick();
		
		
		var it = getContents().iterator();
		while(it.hasNext()) {
			var next = it.next();
			if(next instanceof Alive && ((Alive) next).dead()) {
					toRemove.add(next);
			}
		}	
		for (Entity entity : toRemove) {
			  removeEntity(entity);
		}
		toRemove.clear();
	}
}
