package quadtrees.gameobjects;

import generic.gameobjects.GameObject;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

public class Ball extends GameObject {

	public static final int SIZE = 32;
	public static final float MAX_SPEED_X = 0.5f;
	public static final float MAX_SPEED_Y = 0.5f;
	public static final float DAMPING = 0.05f;

	public Ball(float x, float y) {
		this.x = x;
		this.y = y;
		this.width = SIZE;
		this.height = SIZE;

		velocity_x = (float) (Math.random() * 2 * MAX_SPEED_X) - MAX_SPEED_X / 2;
		velocity_y = (float) (Math.random() * 2 * MAX_SPEED_Y) - MAX_SPEED_Y / 2;
	}

	@Override
	public void update() {

		if (velocity_y > MAX_SPEED_Y)
			velocity_y = MAX_SPEED_Y;
		if (velocity_y < -MAX_SPEED_Y)
			velocity_y = -MAX_SPEED_Y;

		if (x <= 0 || x + width>= Display.getWidth())
			velocity_x *= -1;

		if (y <= 0 || y + height>= Display.getHeight())
			velocity_y *= -1;
		
		x += velocity_x;
		y += velocity_y;
	}

	public String toString() {
		return "X: " + x + ", Y: " + y;
	}

	@Override
	public void onCollision(GameObject go) {
		color = new Color(1f, 1f, 0f);
	}

}
