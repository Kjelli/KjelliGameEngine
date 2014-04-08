package pong.gameobjects;

import generic.gameobjects.GameObject;

public class Ball extends GameObject{
	
	public static final int SIZE = 16;
	public static final float MAX_SPEED_X = 3f;
	public static final float MAX_SPEED_Y = 8f;
	public static final float DAMPING = 0.05f;
	
	public Ball(float x, float y){
		this.x = x;
		this.y = y;
		this.width = SIZE;
		this.height = SIZE;
		
		velocity_x = -MAX_SPEED_X;
		velocity_y = 0;
	}

	@Override
	public void update() {
		if(velocity_y > MAX_SPEED_Y)
			velocity_y = MAX_SPEED_Y;
		if(velocity_y < -MAX_SPEED_Y)
			velocity_y = -MAX_SPEED_Y;
		
		x += velocity_x;
		y += velocity_y;
	}

	public void reverseX(float center) {
		velocity_x *= -1;
		velocity_y += (getCenterY() - center) * DAMPING;
	}
	
	public void reverseY() {
		velocity_y *= -1;
	}

}
