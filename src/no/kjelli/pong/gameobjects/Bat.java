package no.kjelli.pong.gameobjects;

import no.kjelli.generic.Collision;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.texts.TextScrolling;
import no.kjelli.generic.input.Input;
import no.kjelli.generic.input.InputListener;
import no.kjelli.generic.settings.Settings;
import no.kjelli.generic.sound.SoundPlayer;
import no.kjelli.pong.config.PlayerConfig;
import no.kjelli.pong.gameobjects.ai.AI;
import no.kjelli.pong.gameobjects.particles.BatParticle;

import org.newdawn.slick.Color;

public class Bat extends AbstractCollidable {
	public static final float WIDTH = 16;
	public static final float HEIGHT = 8 * WIDTH;
	public static final float BORDER = 4f;
	public static final float SPEED_MAX = 5.0f;

	public static final int STUN_TIMER_MAX = 60;
	public static final int BULLETS_MAX = 1;
	public static final float ACCELERATION = 0.9f;
	public static final float SHRINK_HEIGHT = 32;
	public static final float MINIMUM_HEIGHT = HEIGHT / 4;

	private int bullets = BULLETS_MAX;
	private int stun_timer = 0;
	public PlayerConfig config;
	private Color color;
	private boolean up, down;
	private InputListener inputListener;
	private AI ai;
	private int score;
	private String name;
	private int playerNo;
	private Color whiteColor = new Color(Color.white);

	public Bat(float x, float y, PlayerConfig config) {
		super(x, y, 1.0f, WIDTH, HEIGHT);
		color = new Color(config.playerNo == 0 ? Color.blue : Color.red);
		this.config = config;
		this.name = config.name;
		this.playerNo = config.playerNo;
	}

	public Bat(float x, float y, AI ai) {
		super(x, y, 1.0f, WIDTH, HEIGHT);
		color = new Color(Color.red);
		this.ai = ai;
		this.name = ai.getName();
		this.playerNo = ai.getPlayerNo();
		if (ai.getDifficulty() == 3) {
			color = new Color(Color.green);
			whiteColor = new Color(Color.black);
		}
		ai.setBat(this);
	}

	@Override
	public void onCreate() {
		setVisible(true);
		if (config != null) {
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
	}

	public void shoot() {
		if (stun_timer == 0 && bullets > 0) {
			if (!Settings.get("sound_mute", false))
				SoundPlayer.play("laser1", 1.0f, 0.5f);
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
		if (ai == null)
			movementLogic();
		else
			ai.updateAI();
	}

	private void movementLogic() {
		if (up) {
			accelerateUp();
		} else if (down) {
			accelerateDown();
		} else {
			deccelerate();
		}
		move();
	}

	public void deccelerate() {
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

	public void accelerateDown() {
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

	public void accelerateUp() {
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
				- 2 * BORDER, whiteColor);
		drawGui();
	}

	private void drawGui() {
		float baseX, baseY = Screen.getHeight() - Sprite.CHAR_HEIGHT * 3;
		if (playerNo == 0) {
			baseX = 20;
			for (int score = 0; score < this.score; score++) {
				Draw.fillRect(baseX + (Ball.WIDTH + 1) * score, baseY - 30, 4f,
						Ball.WIDTH, Ball.HEIGHT);
			}
		} else {
			baseX = Screen.getWidth() - 120;
			for (int score = 0; score < this.score; score++) {
				Draw.fillRect(Screen.getWidth() - 2 * Ball.WIDTH
						- (Ball.WIDTH + 1) * score, baseY - 30, 4f, Ball.WIDTH,
						Ball.HEIGHT);
			}
		}

		Draw.string(name, baseX, baseY, 4f, color);
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
		if (height > MINIMUM_HEIGHT) {
			height -= SHRINK_HEIGHT;
			y += SHRINK_HEIGHT / 2;
		}
	}

	public void increaseBullets() {
		bullets++;
	}

	public void point() {
		score++;
		String text = name + " scorte!";
		TextScrolling tf = new TextScrolling(text, TextScrolling.VERTICAL,
				-1.0f, color);
		Wall.color(color);
		World.add(tf);
	}

	public void setAI(AI ai) {
		if (config != null) {
			System.err.println("Already controlled by player!");
		} else {
			this.ai = ai;
			ai.setBat(this);
		}
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
