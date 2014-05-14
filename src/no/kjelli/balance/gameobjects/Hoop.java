package no.kjelli.balance.gameobjects;

import no.kjelli.balance.Balance;
import no.kjelli.generic.Collision;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.sound.SoundPlayer;

public class Hoop extends AbstractCollidable {
	public static final int VERTICAL = 0, HORIZONTAL = 1;
	public static final int SIZE = 8;

	private final int alignment;

	private HoopEdge edge1;
	private HoopEdge edge2;

	private float alpha;
	private long fadestart;

	public boolean open = true;

	public Hoop(float x, float y, float length, int alignment) {

		alpha = 0;
		fadestart = Balance.ticks;
		this.alignment = alignment;

		if (alignment == VERTICAL) {
			height = length;
			width = SIZE;
			this.x = x - width / 2;
			this.y = y - height / 2;
			edge1 = new HoopEdge(x - HoopEdge.SIZE / 2, y + length / 2);
			edge2 = new HoopEdge(x - HoopEdge.SIZE / 2, y - length / 2
					- HoopEdge.SIZE);
		} else {
			width = length;
			height = SIZE;
			this.x = x - width / 2;
			this.y = y - height / 2;
			edge1 = new HoopEdge(x - length / 2 - HoopEdge.SIZE, y
					- HoopEdge.SIZE / 2);
			edge2 = new HoopEdge(x + length / 2, y - HoopEdge.SIZE / 2);
		}
	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);
		edge1.setVisible(visible);
		edge2.setVisible(visible);
		if (visible) {
			World.add(edge1, World.FOREGROUND);
			World.add(edge2, World.FOREGROUND);
		}
	}

	public void destroy() {
		setVisible(false);
		World.remove(this);
		edge1.destroy();
		edge2.destroy();
		Balance.hoopsLeft--;
	}

	@Override
	public void onCollision(Collision collision) {
		if (collision.getTarget() instanceof Ball) {
			if (open) {
				open = false;
				SoundPlayer.play("win");
				fadestart = Balance.ticks;
			}
		}
	}

	@Override
	public void update() {
		if (open)
			fadeIn();
		else
			fadeOut();
	}

	private void fadeOut() {
		if (alpha > 0) {
			alpha = (float) Math.cos((float) (Balance.ticks - fadestart) / 90
					* Math.PI);
			color.a = alpha;
			edge1.setTransparency(alpha);
			edge2.setTransparency(alpha);
			if (alignment == VERTICAL) {
				width -= 0.1;
				x += 0.05;
			} else if (alignment == HORIZONTAL) {
				height -= 0.1;
				y += 0.05;
			}
		} else
			destroy();
	}

	private void fadeIn() {
		if (alpha < 1) {
			alpha = (float) Math.sin((float) (Balance.ticks - fadestart) / 30
					* Math.PI);
			color.a = alpha;
			edge1.setTransparency(alpha);
			edge2.setTransparency(alpha);
		}
	}

}
