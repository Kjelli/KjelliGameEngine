package no.kjelli.pong.gameobjects;

import no.kjelli.generic.Collision;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.settings.Settings;
import no.kjelli.generic.sound.SoundPlayer;
import no.kjelli.pong.Pong;
import no.kjelli.pong.gameobjects.particles.BallHitParticle;

import org.newdawn.slick.Color;

public class Bullet extends AbstractCollidable {
	public static final float WIDTH = 20;
	public static final float HEIGHT = 2;
	public static final float SPEED = 8;
	public Color color;
	public Bat parent;

	public Bullet(Bat bat) {
		super(bat.getCenterX(), bat.getCenterY(), bat.getZ(), WIDTH, HEIGHT);
		parent = bat;
		if (bat.getX() < Screen.getCenterX())
			velocity_x = SPEED;
		else
			velocity_x = -SPEED;

		color = new Color(bat.getColor());
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	@Override
	public void onCollision(Collision collision) {
		if (!isVisible)
			return;
		if (collision.getTarget() instanceof Bat) {
			Bat target = ((Bat) collision.getTarget());
			if (target != parent) {
				if (!Settings.get("sound_mute", false))
					SoundPlayer.play("hit1");
				target.stun();
				destroy();
			}
		} else if (collision.getTarget() instanceof Ball) {
			Ball ball = (Ball) (collision.getTarget());
			World.add(new BallHitParticle(ball));
			if (!Settings.get("sound_mute", false))
				SoundPlayer.play("speedup");
			ball.charge(parent);
			destroy();
		}
	}

	@Override
	public void update() {
		if (Pong.ball.pauseTimer > 40)
			destroy();
		if (x > Screen.getWidth() || x + width < 0)
			destroy();
		move();
	}

	@Override
	public void draw() {
		Draw.fillRect(x, y, width, height, color);
	}

	@Override
	public void destroy() {
		parent.increaseBullets();
		super.destroy();
	}

}
