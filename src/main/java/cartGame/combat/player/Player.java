package cartGame.combat.player;

import amyGraphics.Animation;
import amyGraphics.Texture;
import movement.Movable;
import movement.Obstacle;
import movement.SelfPropelled;

public class Player extends SelfPropelled {
	
	private static final double MASS =5;
	private static final double BASEMOVEFORCE =2000;
	private static final double COEFFICIENT_OF_RESTITUTION = 0.05;	//'bounciness' Used for collisions. See Controllable. Not the whole story, as I've attempted to disallow objects enterring other objects, regardless of CoR
	private static final double COEFFICIENT_OF_DRAG = 0.1;			//coefficient of proportionality between quadratic drag and speed
	private static final double  COEFFICIENT_OF_FRICTION = 8; 	//the coefficient of proportionality between the constant drag and mass
	
	private static final int UP = 0;
	private static final int DOWN = 2;
	private static final int LEFT = 0;
	private static final int RIGHT = 2;
	
	private Animation upAnimation;
	private Animation downAnimation;
	private Animation leftAnimation;
	private Animation rightAnimation;
	
	private Texture upIdle;
	private Texture downIdle;
	private Texture leftIdle;
	private Texture rightIdle;
	
	private int lastX;
	private int lastY;

	public Player() {
		super();
		setBaseMoveForce(BASEMOVEFORCE);
		setMass(MASS);
		setCoF(COEFFICIENT_OF_FRICTION);
		setCoD(COEFFICIENT_OF_DRAG);
		setCoR(COEFFICIENT_OF_RESTITUTION);
	}
	protected Player(Player player) {
		super(player);
		setBaseMoveForce(player.getBaseMoveForce());
		setMass(player.getMass());
		setCoF(player.getCoF());
		setCoD(player.getCoD());
		setCoR(player.getCoR());
	}
	
	@Override
	public Player clone(){
	return new Player(this);	
	}	
	public void updateMovementTexture(int x, int y) {
		if (x == RIGHT) {
			moveRight();
		} else if (x == LEFT) {
			moveLeft();
		} else if (y == UP) {
			moveUp();
		} else if (y == DOWN) {
			moveDown();
		}
		
		if (x == RIGHT || x == LEFT
				|| y == UP || y == DOWN) {
			lastX = x;
			lastY = y;
			return;
		}
		
		if (lastX == RIGHT) {
			idleRight();
			return;
		} else if (lastX == LEFT) {
			idleLeft();
			return;
		} else if (lastY == UP) {
			idleUp();
			return;
		} else if (lastY == DOWN) {
			idleDown();
			return;
		}
		
		idleRight();
	}
	
	public void moveUp() {
		setActiveTexture(upAnimation);
	}

	public void moveDown() {
		setActiveTexture(downAnimation);
	}
	
	public void moveLeft() {
		setActiveTexture(leftAnimation);
	}
	
	public void moveRight() {
		setActiveTexture(rightAnimation);
	}
	
	public void idleUp() {
		setActiveTexture(upIdle);
	}
	
	public void idleDown() {
		setActiveTexture(downIdle);
	}
	
	public void idleLeft() {
		setActiveTexture(leftIdle);
	}
	
	public void idleRight() {
		setActiveTexture(rightIdle);
	}
	
	public void setUpAnimation(Animation animation) {
		if (getTextures().contains(upAnimation)) {
			removeTexture(upAnimation);
		}
		
		addTexture(animation);
		upAnimation = animation;
	}
	
	public void setDownAnimation(Animation animation) {
		if (getTextures().contains(downAnimation)) {
			removeTexture(downAnimation);
		}
		
		addTexture(animation);
		downAnimation = animation;
	}

	public void setLeftAnimation(Animation animation) {
		if (getTextures().contains(leftAnimation)) {
			removeTexture(leftAnimation);
		}
		
		addTexture(animation);
		leftAnimation = animation;
	}

	public void setRightAnimation(Animation animation) {
		if (getTextures().contains(rightAnimation)) {
			removeTexture(rightAnimation);
		}
		
		addTexture(animation);
		rightAnimation = animation;
	}
	
	public void setUpIdle(Texture texture) {
		if (getTextures().contains(upIdle)) {
			removeTexture(upIdle);
		}
		
		addTexture(texture);
		upIdle = texture;
	}
	
	public void setDownIdle(Texture texture) {
		if (getTextures().contains(downIdle)) {
			removeTexture(downIdle);
		}
		
		addTexture(texture);
		downIdle = texture;
	}
	
	public void setLeftIdle(Texture texture) {
		if (getTextures().contains(leftIdle)) {
			removeTexture(leftIdle);
		}
		
		addTexture(texture);
		leftIdle = texture;
	}
	
	public void setRightIdle(Texture texture) {
		if (getTextures().contains(rightIdle)) {
			removeTexture(rightIdle);
		}
		
		addTexture(texture);
		rightIdle = texture;
	}

	public Animation getUpAnimation() {
		return upAnimation;
	}

	public Animation getDownAnimation() {
		return downAnimation;
	}

	public Animation getLeftAnimation() {
		return leftAnimation;
	}

	public Animation getRightAnimation() {
		return rightAnimation;
	}

	public Texture getUpIdle() {
		return upIdle;
	}

	public Texture getDownIdle() {
		return downIdle;
	}

	public Texture getLeftIdle() {
		return leftIdle;
	}

	public Texture getRightIdle() {
		return rightIdle;
	}
	@Override
	public void collision(Obstacle o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collision(Movable m) {
		// TODO Auto-generated method stub
		
	}

}
