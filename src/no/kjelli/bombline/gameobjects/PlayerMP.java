package no.kjelli.bombline.gameobjects;

import no.kjelli.bombline.gameobjects.Player;
import no.kjelli.bombline.levels.Level;
import no.kjelli.generic.Physics;
import no.kjelli.generic.gfx.Sprite;

public class PlayerMP extends Player {

	int id;

	public PlayerMP(int id, String name) {
		super(Level.getPlayerSpawnX(id), Level.getPlayerSpawnY(id));
		this.invincibillity_timer = INVINCIBILLITY_TIMER_MAX;
		this.id = id;
		System.out.println("Setting name to " + name);
		setName(name);
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
		} else if (animation == ANIMATION.STANDING) {
			walkingFrame = 0;
			animationChange = true;
		}
		z = 2.0f - y / Level.getHeight();
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

	public void updateMP(float x, float y, int directionID, int animationID) {
		setX(x);
		setY(y);
		setDirection(directionID);
		setAnimation(animationID);
		step();

		if (playerName != null) {
			playerName.setX(x + width / 2 - name.length() * Sprite.CHAR_WIDTH
					/ 2);
			playerName.setY(y + 2 * Sprite.CHAR_HEIGHT);
		}

		Physics.getCollisions(this);
	}

}
