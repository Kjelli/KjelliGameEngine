package pong.gameobjects;

import generic.gameobjects.GameObject;

public class Wall extends GameObject{
	
	public static final int DEFAULT_SIZE = 16;
	
	private Ball ball;

	public Wall(float x, float y, float width, float height, Ball ball){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.ball = ball;
	}
	
	@Override
	public void update() {
	}

	@Override
	public void onCollision(GameObject go) {
		// TODO Auto-generated method stub
		
	}
}
