package no.kjelli.pong.gameobjects.ai;

import no.kjelli.generic.gfx.Screen;
import no.kjelli.pong.Pong;
import no.kjelli.pong.gameobjects.Ball;
import no.kjelli.pong.gameobjects.Bat;

public class HardAI extends AbstractAI {

	public HardAI() {
		super(1, 0);
	}

	@Override
	public void updateAI() {
		if(Pong.ball.pauseTimer < 25 && Pong.ball.isStarting()){
			bat.shoot();
		}
		
		if(Math.abs(Pong.ball.getCenterY() - bat.getCenterY()) < Ball.HEIGHT && bat.getCenterX() - Pong.ball.getCenterX() < 30 && Pong.ball.batCharger != bat){
			bat.shoot();
		}
		
		if(Math.abs(Pong.ball.getCenterY() - Pong.bat1.getCenterY()) < Ball.HEIGHT &&Pong.ball.getCenterX() -  Pong.bat1.getCenterX() < 100){
			bat.shoot();
		}
		
		
		if (Pong.ball.getX() > Screen.getWidth()*0.2) {
			followBall();
		} else {
			stop();
		}
		bat.move();
	}

	@Override
	public String getName() {
		return "HardBot";
	}
	@Override
	public int getDifficulty() {
		return 3;
	}
}
