package no.kjelli.mathmania.gameobjects.blocks;

import no.kjelli.generic.gameobjects.Collidable;
import no.kjelli.mathmania.levels.Level;

public interface Block extends Collidable {
	public static final int SPRITE_SIZE = 32;
	public static final float SCALE = 1.0f;
	public static final float SIZE = SPRITE_SIZE * SCALE;

	public void question();

	public boolean isSelected();

	public void select(boolean bool);

	public boolean isObstructionBlock();
}