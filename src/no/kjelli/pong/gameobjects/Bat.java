package no.kjelli.pong.gameobjects;

import no.kjelli.generic.Collision;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.texts.TextFading;
import no.kjelli.generic.gfx.texts.TextScrolling;
import no.kjelli.generic.input.Input;
import no.kjelli.generic.input.InputListener;
import no.kjelli.generic.sound.SoundPlayer;
import no.kjelli.pong.Pong;
import no.kjelli.pong.config.PlayerConfig;
import no.kjelli.pong.gameobjects.particles.BatParticle;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

public class Bat extends AbstractCollidable {
	public static final float WIDTH = Pong.block_size / 2;
	public static final float HEIGHT = 8 * WIDTH;
	public static final float BORDER = 4f;
	public static final float SPEED_MAX = 5.0f;

	public static final int STUN_TIMER_MAX = 60;
	public static final int BULLETS_MAX = 1;
	public static final float ACCELERATION = 0.9f;
	public static final float SHRINK_HEIGHT = Pong.block_size / 4;

	private int bullets = BULLETS_MAX;
	private int stun_timer = 0;
	public PlayerConfig config;
	private Color color;
	private boolean up, down;
	private InputListener inputListener;

	public Bat(float x, float y, PlayerConfig config) {
		super(x, y, 1.0f, WIDTH, HEIGHT);
		color = new Color(config.playerNo == 0 ? Color.blue : Color.red);
		this.config = config;
	}

	@Override
	public void onCreate() {
		setVisible(true);
		inputListener = new InputListener() {

			@Override
			public void keyDown(int eventKey) {
				if (eventKey == config.upKey) {
					up = true;
				} else if (eventKey == config.downKey) {
					down = true;
				} else if (eventKey == config.powKey) {
					// TODO
				}
			}

			@Override
			public void keyUp(int eventKey) {
				if (eventKey == config.upKey) {
					up = false;
				} else if (eventKey == config.downKey) {
					down = false;
				} else if (eventKey == config.powKey) {
					shoot();
				}
			}

		};
		Input.register(inputListener);
	}

	protected void shoot() {
		if (stun_timer == 0 && bullets > 0) {
			SoundPlayer.play("laser1");
			World.add(new Bullet(this));
			bullets--;
		}
	}

	@Override
	public void onCollision(Collision collision) {
		if (collision.getTarget() instanceof Ball
				|| collision.getTarget() instanceof Wall) {
			stop(collision.getImpactDirection());
		}
	}

	@Override
	public void update() {
		if (stun_timer > 0) {
			stun_timer--;
			return;
		}
		movementLogic();
	}

	private void movementLogic() {
		if (up) {
			if (y + height <= Pong.UPPER_LIMIT)
				accelerateUp();
			else {
				velocity_y = 0;
			}
		} else if (down) {
			if (y > Pong.LOWER_LIMIT)
				accelerateDown();
			else
				velocity_y = 0;
		} else {
			deccelerate();
		}
		move();
	}

	private void deccelerate() {
		if (velocity_y > 0)
			deccelerateUp();
		else
			deccelerateDown();
	}

	private void deccelerateUp() {
		if (velocity_y > ACCELERATION) {
			velocity_y -= ACCELERATION;
		} else {
			velocity_y = 0;
		}
	}

	private void accelerateDown() {
		if (velocity_y > -SPEED_MAX) {
			velocity_y -= ACCELERATION;
		} else {
			velocity_y = -SPEED_MAX;
		}
	}

	private void deccelerateDown() {
		if (velocity_y < -ACCELERATION) {
			velocity_y += ACCELERATION;
		} else {
			velocity_y = 0;
		}
	}

	private void accelerateUp() {
		if (velocity_y < SPEED_MAX) {
			velocity_y += ACCELERATION;
		} else {
			velocity_y = SPEED_MAX;
		}
	}

	@Override
	public void draw() {
		Draw.fillRect(x, y, width, height, color);
		Draw.fillRect(x + BORDER, y + BORDER, 2f, width - 2 * BORDER, height
				- 2 * BORDER, Color.white);
		drawGui();
	}

	private void drawGui() {
		float baseX, baseY = Screen.getHeight() - Sprite.CHAR_HEIGHT * 3;
		if (config.playerNo == 0) {
			baseX = 20;
			for (int score = 0; score < config.score; score++) {
				Draw.fillRect(baseX + (Ball.WIDTH + 1) * score, baseY - 30,
						Ball.WIDTH, Ball.HEIGHT);
			}
		} else {
			baseX = Screen.getWidth() - 120;
			for (int score = 0; score < config.score; score++) {
				Draw.fillRect(Screen.getWidth() - 2 * Ball.WIDTH
						- (Ball.WIDTH + 1) * score, baseY - 30, Ball.WIDTH,
						Ball.HEIGHT);
			}
		}

		Draw.string(config.name, baseX, baseY, 4f, color);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void destroy() {
		Input.unregister(inputListener);
		super.destroy();
	}

	public void stun() {
		World.add(new BatParticle(Bat.this));
		shrink();
		stun_timer = STUN_TIMER_MAX;
	}

	private void shrink() {
		height -= SHRINK_HEIGHT;
		y += SHRINK_HEIGHT / 2;
	}

	public void increaseBullets() {
		bullets++;
	}

	public void point() {
		config.score++;
		String text = config.name + " scored!";
		TextScrolling tf = new TextScrolling(text, TextScrolling.VERTICAL,
				-1.0f, color);
		Wall.color(color);
		World.add(tf);
	}

	public void reset() {
		up = false;
		down = false;
		height = HEIGHT;
		setY(Screen.getCenterY() - height / 2);
		velocity_y = 0;
		stun_timer = 0;
	}
}
