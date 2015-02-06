package no.kjelli.pong.gameobjects.particles;

import no.kjelli.generic.gamewrapper.GameWrapper;
import no.kjelli.generic.gfx.AbstractParticle;
import no.kjelli.generic.gfx.Clickable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.tweens.GameObjectAccessor;
import no.kjelli.pong.Pong;

import org.lwjgl.input.Mouse;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

public class LEDLogo extends AbstractParticle implements Clickable {
	public static final float WIDTH = 120, HEIGHT = 120;

	private static final float SPEED = .8f;
	private static final int START_TIMER_DEFAULT =300;

	private int start_timer_max;
	private float startScale;
	private boolean fading;
	private int startTimer;
	private boolean fadeDir;

	public LEDLogo(float x, float y, float scale) {
		super(x, y, 0f + scale, WIDTH*scale, HEIGHT*scale, -1);
		setXScale(scale);
		setYScale(scale);
		this.startScale = scale;
		this.velocity_x = z * SPEED*scale;
		sprite = new Sprite(Pong.objects_hires, 0, 100, 120, 120);
		startTimer = start_timer_max = (int) (START_TIMER_DEFAULT * scale);
	}

	@Override
	public void updateParticle() {
		if (startTimer > 0 && !fadeDir) {
			startTimer--;
			if (startTimer == 0) {
				fadeDir = true;
			}
		} else if (startTimer < start_timer_max && fadeDir) {
			startTimer++;
			if (startTimer == start_timer_max) {
				fadeDir = false;
				if(Math.random() > 0.4f)
					destroy();
			}
		}
		sprite.getColor().a = 1.0f - ((float) startTimer / start_timer_max);
		if (x > Screen.getWidth())
			destroy();
		else
			move();
	}

	@Override
	public void draw() {
		Draw.sprite(sprite, x, y, z, rotation, getXScale(), getYScale(), false,
				false, false);
	}

	@Override
	public void onMousePressed(int mouseButton) {
		if (!fading) {
			fading = true;
			Timeline.createParallel()
					.push(Tween.to(this, GameObjectAccessor.SCALE_WH, 400)
							.target(0,0).setCallback(new TweenCallback() {

								@Override
								public void onEvent(int arg0, BaseTween<?> arg1) {
									destroy();
								}
							}))
					.push(Tween.to(this, GameObjectAccessor.POSITION_XY, 400)
							.target(x + width / 2, y + height / 2)

					).start(GameWrapper.tweenManager);
		}
	}

	@Override
	public void onMouseReleased(int mouseButton) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEnter() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub

	}

}
