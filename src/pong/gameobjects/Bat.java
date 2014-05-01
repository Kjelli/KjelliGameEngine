package pong.gameobjects;

import generic.gameobjects.GameObject;

public class Bat extends GameObject{
	public static final int WIDTH = 16;
	public static final int HEIGHT = WIDTH * 7;
	public static final float SPEED = 4f;
	
	private Ball ball;
	
	public Bat(float x, float y, Ball ball){
		this.x = x;
		this.y = y;
		
		width = WIDTH;
		height = HEIGHT;
		
		this.ball = ball;
	}

	@Override
	public void update() {
		y += velocity_y;
	}
	
	public void move(float mag){
		velocity_y = SPEED * mag;
	}

	@Override
	public void onCollision(GameObject go) {
		// TODO Auto-generated method stub
		
	}
}
