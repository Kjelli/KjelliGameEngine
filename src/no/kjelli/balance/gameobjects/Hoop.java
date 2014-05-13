package no.kjelli.balance.gameobjects;

import no.kjelli.generic.Collision;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractCollidable;
import no.kjelli.generic.sound.SoundPlayer;

public class Hoop extends AbstractCollidable {
	public static final int VERTICAL = 0, HORIZONTAL = 1;
	public static final int SIZE = 8;

	private HoopEdge edge1;
	private HoopEdge edge2;

	public Hoop(float x, float y, float length, int alignment) {

		if (alignment == VERTICAL) {
			height = length;
			width = SIZE;
			this.x = x - width / 2;
			this.y = y - height / 2;
			edge1 = new HoopEdge(x + HoopEdge.SIZE / 2, y + length / 2
					- HoopEdge.SIZE / 2);
			edge1.setVisible(true);
			edge2 = new HoopEdge(x + HoopEdge.SIZE / 2, y - length / 2
					- HoopEdge.SIZE / 2);
			edge2.setVisible(true);

			World.add(edge1, World.FOREGROUND);
			World.add(edge2, World.FOREGROUND);
		} else {
			width = length;
			height = SIZE;
			this.x = x - width / 2;
			this.y = y - height / 2;
			edge1 = new HoopEdge(x - length / 2 - HoopEdge.SIZE / 2, y- HoopEdge.SIZE / 2);
			edge1.setVisible(true);
			edge2 = new HoopEdge(x + length / 2 - HoopEdge.SIZE / 2, y - HoopEdge.SIZE / 2);
			edge2.setVisible(true);

			World.add(edge1, World.FOREGROUND);
			World.add(edge2, World.FOREGROUND);
		}
	}

	public void destroy() {
		setVisible(false);
		World.remove(this);
		edge1.destroy();
		edge2.destroy();
	}

	@Override
	public void onCollision(Collision collision) {
		if (collision.getTarget() instanceof Ball) {
			SoundPlayer.play("win");
			destroy();
		}
	}

	@Override
	public void update() {

	}

}
