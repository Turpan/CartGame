package cartGame.combat.player.abilities;

import cartGame.combat.player.Alive;
import movement.Movable;
import movement.mathDS.Vector;

public class AttackAbility extends Ability{

	public AttackAbility() {

		setUpAnimationLocation(null);
		setDownAnimationLocation(null);
		setLeftAnimationLocation(null);
		setRightAnimationLocation(null);
		
		setDuration(50);
		setCooldown(150);
	}
	
	@Override
	public void collisionEffect(Movable m) {
		if (m instanceof Alive) {
			((Alive) m).damage(10);
		}
	}
	@Override
	public void activationEffect(Movable self) {
		super.activationEffect(self);
		var rotationVector = new Vector(self.getRotationAxis());
		var positionVector = new Vector(new double[] {1,0,0});
		self.applyForce(Vector.scalarMultiply(Vector.addVectors(Vector.scalarMultiply(positionVector, Math.cos(self.getAngle())), Vector.scalarMultiply(Vector.crossProduct(positionVector, rotationVector), Math.sin(self.getAngle())), Vector.scalarMultiply(rotationVector, (1 - Math.cos(self.getAngle())) * Vector.dotProduct(rotationVector, positionVector))),1000));
	}
	@Override
	public void constantEffect(Movable self) {
		return;		
	}
	@Override
	public void endEffect(Movable self) {
		return;
	}

	@Override
	public boolean prerequisitesMet(Movable self) {
		return super.prerequisitesMet(self) && self.onFloor();
	}
	

}
