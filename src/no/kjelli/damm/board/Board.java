package no.kjelli.damm.board;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gameobjects.Clickable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.sound.SoundPlayer;

import org.lwjgl.input.Mouse;

public class Board extends AbstractGameObject implements Clickable {
	public static final int LOWER_LEFT = 0, LOWER_RIGHT = 1, UPPER_LEFT = 2,
			UPPER_RIGHT = 3;
	public static final int BLACK_TURN = Piece.BLACK, WHITE_TURN = Piece.WHITE;
	public final int columns;
	public final int rows;

	public static int turn = WHITE_TURN;
	public static int highest_type = 0;
	public static Move last_move;
	public static boolean jump_sequence = false;
	public static final ArrayList<Move> valid_moves = new ArrayList<>();

	private final Tile[] tiles;
	private Piece selection;

	public Board(int x, int y, int columns, int rows) {
		this.x = x;
		this.y = y;
		this.columns = columns;
		this.rows = rows;
		this.width = columns * Tile.SIZE;
		this.height = rows * Tile.SIZE;

		tiles = new Tile[columns * rows];
	}

	public Board(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;
		this.width = columns * Tile.SIZE;
		this.height = columns * Tile.SIZE;

		tiles = new Tile[columns * rows];
	}

	public void draw() {
		for (Tile tile : tiles) {
			if (tile == null)
				continue;
			tile.draw();
		}
		if (selection != null) {
			Draw.rect(selection.getX(), selection.getY(), selection.getWidth(),
					selection.getHeight());
		}
	}

	@Override
	public void update() {

	}

	public void generate() {
		for (int x = 0; x < columns; x++) {
			for (int y = 0; y < rows; y++) {
				tiles[y + x * columns] = new Tile(x, y, this.x, this.y);
			}
		}
	}

	public void placePieces() {
		int color = Piece.WHITE;

		for (int x = 0; x < columns; x++) {
			for (int y = 0; y < 3; y++) {
				if ((x + y) % 2 == 1 && y < 3) {
					Piece p = new Piece(tiles[y + x * columns], x, y, color);
					tiles[y + x * columns].setPiece(p);
					World.add(p);
				}
			}
		}

		color = Piece.BLACK;

		for (int x = 0; x < columns; x++) {
			for (int y = rows - 1; y > rows - 4; y--) {
				if ((x + y) % 2 == 1 && y > rows - 4) {
					Piece p = new Piece(tiles[y + x * columns], x, y, color);
					tiles[y + x * columns].setPiece(p);
					World.add(p);
				}
			}
		}
		calculateAllMoves();
	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);
		for (Tile tile : tiles) {
			if (tile == null)
				continue;
			tile.setVisible(visible);
		}
	}

	public Tile getAdjacentTile(int x_index, int y_index, int direction) {
		switch (direction) {
		case LOWER_LEFT:
			return getTileFromIndex(x_index - 1, y_index - 1);
		case LOWER_RIGHT:
			return getTileFromIndex(x_index + 1, y_index - 1);
		case UPPER_LEFT:
			return getTileFromIndex(x_index - 1, y_index + 1);
		case UPPER_RIGHT:
			return getTileFromIndex(x_index + 1, y_index + 1);
		default:
			return null;
		}

	}

	public int getTileIndex(float x, float y) {
		int x_tile = (int) ((x - this.x) / Tile.SIZE);
		if (x_tile >= columns || x_tile < 0) {
			return -1;
		}
		int y_tile = (int) ((y - this.y) / Tile.SIZE);
		if (y_tile >= rows || y_tile < 0) {
			System.out.println(y_tile + " was greater than " + columns
					+ " or less than " + 0);
			return -1;
		}

		int index = y_tile + x_tile * columns;
		return index;
	}

	public Tile getTileFromIndex(int x, int y) {
		if (x >= columns || x < 0) {
			return null;
		}

		if (y >= rows || y < 0) {
			return null;
		}

		return tiles[y + x * columns];
	}

	public Tile getTile(float x, float y) {
		int index = getTileIndex(x, y);
		if (index != -1)
			return tiles[getTileIndex(x, y)];
		else
			return null;
	}

	public Piece getPiece(float x, float y) {
		int index = getTileIndex(x, y);
		if (index == -1)
			return null;
		else
			return tiles[index].piece;
	}

	public Piece getPieceFromIndex(int x, int y) {
		if (x >= columns || x < 0) {
			return null;
		}

		if (y >= rows || y < 0) {
			return null;
		}

		return tiles[y + x * columns].piece;
	};

	@Override
	public void onMousePressed(int mouseButton) {
		Piece piece = getPiece(Mouse.getX(), Mouse.getY());
		// Click on an empty tile
		if (piece == null) {
			// Selection premade, check if player wants to move to new tile
			if (selection != null) {
				Tile clickedTile = getTile(Mouse.getX(), Mouse.getY());
				boolean moveMade = false;
				for (int i = 0; i < 4; i++) {
					Move move = selection.available_moves[i];
					if (move != null && move.destTile == clickedTile) {
						// Check if the move is valid
						if (valid_moves.contains(move)) {
							move.make();
							last_move = move;
							moveMade = true;
						}
					}
				}
				if (moveMade) {
					// Recalculate valid moves
					calculateAllMoves();
					// If player can jump over multiple, it has to
					if (!checkForJumpSequence()) {
						// If not, check win-condition and next turn.
						turn = 1 + (turn % 2);
						checkWinCondition();
					}
					unselect();
				} else {
					// Player clicked on empty tile with no valid moves there.
					unselect();
				}
			}
		} else if (piece != null) {
			select(piece);
		}

	}

	public void unselect() {
		for (Tile tile : tiles)
			tile.highlight(false);
		for (int i = 0; i < 4; i++) {
			if (selection.available_moves[i] == null)
				continue;
			if (valid_moves.contains(selection.available_moves[i])) {
				selection.available_moves[i].destTile.highlight(false);
			}
		}
		selection = null;
	}

	public void select(Piece piece) {
		if (turn != (piece.color & Piece.COLOR_MASK)) {
			SoundPlayer.play("lose");
			return;
		}
		if (selection != null)
			unselect();
		selection = piece;

		for (int i = 0; i < 4; i++) {
			if (selection.available_moves[i] == null)
				continue;
			if (valid_moves.contains(selection.available_moves[i])) {
				selection.available_moves[i].destTile.highlight(true);
			}
		}
	}

	private void checkWinCondition() {
		calculateAllMoves();
		if (valid_moves.isEmpty()) {
			if (turn == BLACK_TURN)
				JOptionPane.showConfirmDialog(null, "Player WHITE has won!");
			else if (turn == WHITE_TURN)
				JOptionPane.showConfirmDialog(null, "Player BLACK has won!");
		}

		return;
	}

	private void calculateAllMoves() {
		valid_moves.clear();
		highest_type = 0;
		for (Tile tile : tiles) {
			if (tile == null)
				continue;
			Piece piece = tile.piece;
			if (piece != null && (piece.color & Piece.COLOR_MASK) == turn) {
				piece.calculateMoves();
				for (Move move : piece.available_moves) {
					if (move == null)
						continue;
					if (move.type == highest_type) {
						valid_moves.add(move);
					} else if (move.type > highest_type) {
						highest_type = move.type;
						System.out.println(highest_type);
						valid_moves.clear();
						valid_moves.add(move);
					}
				}
			}
		}

	}

	private boolean checkForJumpSequence() {
		calculateAllMoves();
		if (last_move.type != Move.JUMP)
			return false;

		Move temp = null;
		for (Move move : valid_moves) {
			if (move.type == Move.JUMP && move.piece == last_move.piece) {
				temp = move;
			}
		}
		if (temp != null) {
			valid_moves.clear();
			valid_moves.add(temp);
			return true;
		}

		return false;
	}

	@Override
	public void onMouseReleased(int mouseButton) {
	}

	@Override
	public void onEnter() {
		//
	}

	@Override
	public void onExit() {
		//
	}

}
