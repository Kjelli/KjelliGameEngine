package no.kjelli.mathmania.gameobjects.blocks;

import no.kjelli.generic.gameobjects.Collidable;
import no.kjelli.mathmania.levels.Level;

public interface Block extends Collidable {
	public static final int SPRITE_SIZE = 32;
	public static final int SIZE = 32;

	public void question();

	public boolean isSelected();

	public void select(boolean bool);

	public boolean isObstructionBlock();
	
	public boolean isEncrypted();

	public int getEncryptKey();

	public void decrypt();
}