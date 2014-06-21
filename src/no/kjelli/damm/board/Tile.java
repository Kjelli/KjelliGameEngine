package no.kjelli.damm.board;

import no.kjelli.damm.Damm;
import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Textures;

public class Tile extends AbstractGameObject {
	public static final int SIZE = 80;

	int x_index;
	int y_index;

	public Piece piece;

	private boolean isHighlighted;

	public Tile(int x_index, int y_index, float x_offset, float y_offset) {
		this.x_index = x_index;
		this.y_index = y_index;

		width = SIZE;
		height = SIZE;
		x = x_index * width + x_offset;
		y = y_index * height + y_offset;

		if ((x_index + y_index) % 2 == 0)
			texture = Textures.load("res\\tile_brown.png");
		else
			texture = Textures.load("res\\tile_lightbrown.png");

	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void draw() {
		Draw.texture(this);
		if (piece != null)
			piece.draw();
		if (isHighlighted) {
			Draw.rect(x + 2, y + 1, width - 3, height - 3);
		}
	}

	@Override
	public void update() {
		//
	}

	@Override
	public void setVisible(boolean isVisible) {
		super.setVisible(isVisible);
		if (piece != null)
			piece.setVisible(isVisible);
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public void highlight(boolean highlight) {
		this.isHighlighted = highlight;
	}

	public static double distance(Tile srcTile, Tile destTile) {
		int xDist = destTile.x_index - srcTile.x_index;
		int yDist = destTile.y_index - srcTile.y_index;
		double hyp = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
		return hyp;
	}

	@Override
	public String toString() {
		return "x: " + x_index + ", y: " + y_index;
	}

	public static Tile parseTile(String src) {
		char[] chars = src.toLowerCase().toCharArray();
		int x = chars[0] - 97;
		int y = chars[1] - 49;
		return Damm.board.getTileFromIndex(x, y);
	}
}
