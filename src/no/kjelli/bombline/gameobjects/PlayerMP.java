package no.kjelli.bombline.gameobjects;

import no.kjelli.bombline.gameobjects.Player;
import no.kjelli.bombline.levels.Level;

public class PlayerMP extends Player {

	int id;

	public PlayerMP(int id) {
		super(Level.getPlayerSpawnX(id), Level.getPlayerSpawnY(id));
		this.id = id;
		z = 2.0f;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();	
	}

	public void update() {
		lifeLogic();
		updateAnimation();
	}

	public void step() {
		steps++;
		if (animation == ANIMATION.WALKING && steps % FRAME_DURATION == 0) {
			walkingFrame = (walkingFrame + 1) % FRAME_COUNT;
			animationChange = true;
		}else if(animation == ANIMATION.STANDING){
			walkingFrame = 0;
			animationChange = true;
		}
		z = 2.0f - y/Level.getHeight();
	}

	public int getID() {
		return id;
	}

	public void setDirection(int directionID) {
		if (this.direction.getID() != directionID) {
			this.direction = DIRECTION.resolve(directionID);
			directionChange = true;
		}
	}

	public void setAnimation(int animationID) {
		if (this.animation.getID() != animationID) {
			this.animation = ANIMATION.resolve(animationID);
			animationChange = true;
		}
	}
}
