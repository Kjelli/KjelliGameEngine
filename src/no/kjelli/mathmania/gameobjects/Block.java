package no.kjelli.mathmania.gameobjects;

import no.kjelli.generic.gameobjects.Collidable;


public interface Block extends Collidable{
	public static final int SPRITE_SIZE = 32;
	public static final float SCALE = 1.0f;
	public static final float SIZE = SPRITE_SIZE * SCALE;
	
	public void question();
	public boolean isSelected();
	public void select(boolean bool);
	public boolean isObstructionBlock();
}