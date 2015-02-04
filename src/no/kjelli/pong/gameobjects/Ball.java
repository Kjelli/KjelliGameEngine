package no.kjelli.pong.gameobjects;

import no.kjelli.generic.Collision;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.sound.SoundPlayer;
import no.kjelli.pong.Pong;
import no.kjelli.pong.gameobjects.particles.BallParticle;
import no.kjelli.pong.gameobjects.particles.BallParticleReverse;

import org.newdawn.slick.Color;

public class Ball extends AbstractCollidable {
	public static final float WIDTH = Pong.block_size / 2;
	public static final float HEIGHT = Pong.block_size / 2;
	public static final float MAXBOUNCEANGLE = (float) (5 * Math.PI / 12);
	public static final float SPEEDINIT = 5.0f;
	public static final float BASE_BORDER = 3f;
	public static float border = BASE_BORDER;
	private static float UPPER_LIMIT = Screen.getHeight() - Sprite.CHAR_HEIGHT
			* 3;

	public float speed = SPEEDINIT;
	public Color color = new Color(Color.white);
	public boolean wasHit = false;
	public float angle;
	private long pauseTimer = -1;

	private Bat batCharger;

	public Ball(float x, float y) {
		super(x, y, 1.0f, WIDTH, HEIGHT);
	}

	@Override
	public void onCreate() {
		setVisible(true);
	}

	private void randomizeAngle() {
		angle = (float) (Math.random() * Math.PI / 2 - Math.PI / 4);
		if (Math.random() > 0.5) {
			angle = (float) ((angle + Math.PI) % (2 * Math.PI));
		}
	}

	@Override
	public void onCollision(Collision collision) {
		if (collision.getTarget() instanceof Bat) {
			Bat bat = (Bat) collision.getTarget();
			stop(collision.getImpactDirection());
			wasHit = true;
			SoundPlayer.play("bounce");
			calculateAngle(bat);
			speedUp();

			color = new Color(bat.getColor());
			if (batCharger != null && bat != batCharger) {
				bat.stun();
				batCharger = null;
			}
			World.add(new BallParticle(this));

		} else if (collision.getTarget() instanceof Wall) {
			stop(collision.getImpactDirection());
			collidingWall();
			speedUp();
		}
	}

	public void speedUp() {
		speed += 0.12f;
	}

	private void calculateAngle(Bat target) {
		float relativeIntersectY = (this.getCenterY() - target.getCenterY());
		float normalizedRelativeIntersectionY = (relativeIntersectY / (Bat.HEIGHT / 2));
		float bounceAngle = normalizedRelativeIntersectionY * MAXBOUNCEANGLE;
		if (x < target.getX())
			angle = (float) (Math.PI - bounceAngle % (2 * Math.PI));
		else
			angle = bounceAngle;
	}

	private void collidingWall() {
		angle = (float) ((2 * Math.PI - angle) % (2 * Math.PI));
	}

	@Override
	public void update() {
		if (pauseTimer > 0) {
			pauseTimer--;
			return;
		} else if (pauseTimer == -1) {
			reset();
			return;
		}

		if (x > Screen.getWidth() || x + width < 0) {
			if (wasHit) {
				if (x > Screen.getWidth()) {
					Pong.bat1.point();
				} else {
					Pong.bat2.point();
				}
			}
			Pong.reset();
			return;
		}
		move(Math.cos(angle) * speed, Math.sin(angle) * speed);
	}

	@Override
	public void draw() {
		Draw.fillRect(x, y, 2f, width, height, color);
		Draw.fillRect(x + border, y + border, 3f, width - 2 * border, height
				- 2 * border, Color.white);
		if(batCharger !=null){
			Draw.rect(x - 2, y - 2, width + 4, height + 4, batCharger.getColor());
		}
	}

	public Color getColor() {
		return color;
	}

	public void reset() {
		pauseTimer = BallParticleReverse.TIME_TO_LIVE_MAX;
		speed = SPEEDINIT;
		color = new Color(Color.white);
		wasHit = false;
		setX(Screen.getCenterX() - Ball.WIDTH / 2);
		setY(Screen.getCenterY() - Ball.HEIGHT / 2);
		randomizeAngle();
		World.add(new BallParticleReverse(this));
	}

	public void charge(Bat parent) {
		batCharger = parent;
	}
}
