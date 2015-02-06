package no.kjelli.pong.menu;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import aurelienribon.tweenengine.Tween;
import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gamewrapper.GameWrapper;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.tweens.GameObjectAccessor;
import no.kjelli.pong.Pong;

public class Logo extends AbstractGameObject {
	private static final float xScale = 2.0f, yScale = 2.0f;

	public static final float SPRITE_WIDTH = 199;
	public static final float SPRITE_HEIGHT = 99;
	public static final float WIDTH = SPRITE_WIDTH * xScale;
	public static final float HEIGHT = SPRITE_HEIGHT * yScale;
	private final Color color = new Color(Color.blue);

	public Logo(float x, float y) {
		super(x, y, 5.0f, WIDTH, HEIGHT);
		sprite = new Sprite(Pong.objects, 0, 0, (int) SPRITE_WIDTH,
				(int) SPRITE_HEIGHT);
	}

	@Override
	public void onCreate() {
		setVisible(true);
		setRotation(-5);
		Tween.to(this, GameObjectAccessor.ROTATION, 1000).target(5)
				.repeatYoyo(-1, 100).start(GameWrapper.tweenManager);
	}

	@Override
	public void update() {
		color.r = (float) (Math.sin((float) Pong.ticks / 200 + 66));
		color.g = (float) (Math.sin((float) 1.3 * Pong.ticks / 200 + 132));
		color.b = (float) (Math.sin((float) 1.5 * Pong.ticks / 200 + 198));
	}

	@Override
	public void draw() {
		Draw.fillRect(x, y, z - 0.1f, width, height, rotation, color);
		Draw.sprite(sprite, x, y, z, rotation, xScale, xScale, false, false,
				false);
	}

}
