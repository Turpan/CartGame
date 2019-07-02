package cartGame.combat.player.abilities;

import movement.Movable;
import movement.mathDS.Vector;

public class JumpAbility extends Ability{
	 
	
	
	public JumpAbility () {
		setUpAnimationLocation(null);
		setDownAnimationLocation(null);
		setLeftAnimationLocation(null);
		setRightAnimationLocation(null);
		
		setCooldown(100);
		setDuration(5);
	}	
	@Override
	public void collisionEffect (Movable m) {
		return;
	}
	
	@Override
	public void activationEffect(Movable self) {
		super.activationEffect(self);
		self.applyForce(new Vector(500, new double[] {0,1,0}));
		var rotationVector = new Vector(self.getRotationAxis());
		var positionVector = new Vector(new double[] {1,0,0});
		self.applyForce(Vector.scalarMultiply(Vector.addVectors(Vector.scalarMultiply(positionVector, Math.cos(self.getAngle())), Vector.scalarMultiply(Vector.crossProduct(positionVector, rotationVector), Math.sin(self.getAngle())), Vector.scalarMultiply(rotationVector, (1 - Math.cos(self.getAngle())) * Vector.dotProduct(rotationVector, positionVector))),450));
	}
	
	@Override
	public void constantEffect(Movable self) {
		self.applyForce(new Vector(100, new double[] {0,1,0}));		
}
	@Override
	public boolean prerequisitesMet(Movable self) {
		return super.prerequisitesMet(self) && self.onFloor();
	}
	
	@Override
	public void endEffect(Movable self) {
		return;
	}
}
