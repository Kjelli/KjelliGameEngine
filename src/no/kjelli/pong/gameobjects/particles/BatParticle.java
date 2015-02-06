package no.kjelli.pong.gameobjects.particles;

import no.kjelli.generic.gfx.AbstractParticle;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.pong.gameobjects.Bat;

import org.newdawn.slick.Color;

public class BatParticle extends AbstractParticle {
	public static final int TIME_TO_LIVE = 100;
	public static final int SPEED = 2;
	public Color color;
	
	public BatParticle(Bat bat){
		super(bat.getX(), bat.getY(), bat.getZ(), bat.getWidth(), bat.getHeight(), TIME_TO_LIVE);
		color = new Color(bat.getColor());
	}

	@Override
	public void updateParticle() {
		width+= 0.2*SPEED;
		velocity_x=-0.1*SPEED;
		height+= 0.2*SPEED;
		velocity_y=-0.1*SPEED;
		color.a = (float)timeToLive/TIME_TO_LIVE;
		move();
	}

	@Override
	public void draw() {
		Draw.rect(this, color);
	}

}
