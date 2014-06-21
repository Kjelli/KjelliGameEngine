package no.kjelli.balance.gameobjects;

import no.kjelli.balance.Balance;
import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Draw;

import org.newdawn.slick.Color;

public class BGStar extends AbstractGameObject {
	public BGStar(float x, float y) {
		this.x = x;
		this.y = y;
		int type = (int) (Math.random() * 3) + 1;
		this.width = (float) Math.pow(2, type - 1) - 1;
		this.height = (float) Math.pow(2, type - 1) - 1;
		// texture = Textures.load("res\\bgstar" + type + ".jpg");
		velocity_y = -(Math.random() * 1.5 + Balance.GRAVITY);
		velocity_x = 0;
		color = new Color(Draw.DEFAULT_COLOR);
	}

	private void determineColor() {
		this.color.r = (float) Math.abs(Math.sin((float) Balance.ticks / 7200
				* Math.PI));
		this.color.g = (float) Math.abs(Math.sin((float) (Balance.ticks + 1200)
				/ 3600 * Math.PI));
		this.color.b = (float) Math.abs(Math.sin((float) (Balance.ticks + 2400)
				/ 3600 * Math.PI));
	}

	@Override
	public void update() {
		determineColor();
		move();
		if (y + height < 0)
			destroy();
	}

	@Override
	public void draw() {
		Draw.texture(this);
	}

}
