package no.kjelli.damm.board;

import no.kjelli.damm.Damm;
import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Textures;

public class Piece extends AbstractGameObject {

	public static final int BLACK = 1, WHITE = 2, COLOR_MASK = 3, KING = 4;
	public static final int LOWER_LEFT = 0, LOWER_RIGHT = 1, UPPER_LEFT = 2,
			UPPER_RIGHT = 3;

	public Move[] available_moves = new Move[4];

	int color;
	int x_index;
	int y_index;

	Tile tile;

	@Override
	public void draw() {
		Draw.texture(this);
	}

	public Piece(Tile tile, int x_index, int y_index, int color) {

		this.x_index = x_index;
		this.y_index = y_index;
		this.x = Damm.board.getX() + x_index * Tile.SIZE;
		this.y = Damm.board.getY() + y_index * Tile.SIZE;
		this.width = Tile.SIZE;
		this.height = Tile.SIZE;
		this.tile = tile;

		this.color = color;

		if (color == BLACK)
			texture = Textures.load("res\\piece_black.png");
		else
			texture = Textures.load("res\\piece_white.png");
	}

	public Piece[] getAdjacencies() {
		Piece[] adjacencies = new Piece[4];
		adjacencies[LOWER_LEFT] = Damm.board.getPieceFromIndex(x_index - 1,
				y_index - 1);
		adjacencies[LOWER_RIGHT] = Damm.board.getPieceFromIndex(x_index + 1,
				y_index - 1);
		adjacencies[UPPER_LEFT] = Damm.board.getPieceFromIndex(x_index - 1,
				y_index + 1);
		adjacencies[UPPER_RIGHT] = Damm.board.getPieceFromIndex(x_index + 1,
				y_index + 1);
		return adjacencies;
	}

	@Override
	public void update() {
	}

	@Override
	public String toString() {
		return ((color & WHITE) == WHITE ? "White" : "Black")
				+ ((color & KING) == KING ? " king" : "") + " piece: ("
				+ x_index + ", " + y_index + ")";
	}

	public void calculateMoves() {

		for (int i = 0; i < 4; i++) {
			available_moves[i] = null;
		}

		int startTile = 0, endTile = 0;
		if ((color & KING) == KING) {
			startTile = LOWER_LEFT;
			endTile = UPPER_RIGHT;
		} else if ((color & WHITE) == WHITE) {
			startTile = UPPER_LEFT;
			endTile = UPPER_RIGHT;
		} else if ((color & BLACK) == BLACK) {
			startTile = LOWER_LEFT;
			endTile = LOWER_RIGHT;
		}
		int moves = 0;
		Piece[] pieces = getAdjacencies();
		for (int i = startTile; i <= endTile; i++) {
			Tile midTile;
			Tile destTile;
			if (pieces[i] == null) {
				destTile = Damm.board.getAdjacentTile(x_index, y_index, i);
				Move move = new Move(tile, null, destTile, this);
				if (move.type != Move.INVALID) {
					available_moves[moves++] = move;
				}
			} else if (pieces[i] != null) {
				midTile = Damm.board.getAdjacentTile(x_index, y_index, i);
				destTile = Damm.board.getAdjacentTile(pieces[i].x_index,
						pieces[i].y_index, i);
				Move move = new Move(tile, midTile, destTile, this);
				if (move.type != Move.INVALID) {
					available_moves[moves++] = move;
				}
			}

		}
	}

	public void move(Tile destTile) {
		tile.piece = null;
		destTile.piece = this;
		tile = destTile;
		x_index = destTile.x_index;
		y_index = destTile.y_index;
		this.x = Damm.board.getX() + x_index * Tile.SIZE;
		this.y = Damm.board.getY() + y_index * Tile.SIZE;

		if (color == WHITE && y_index == Damm.board.rows - 1) {
			color |= KING;
			texture = Textures.load("res\\piece_white_king.png");
		} else if (color == BLACK && y_index == 0) {
			color |= KING;
			texture = Textures.load("res\\piece_black_king.png");
		}
	}
}
