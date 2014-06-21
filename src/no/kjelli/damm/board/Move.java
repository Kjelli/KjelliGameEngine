package no.kjelli.damm.board;

import javax.swing.JOptionPane;

import no.kjelli.damm.Damm;
import no.kjelli.generic.sound.SoundPlayer;

public class Move {
	public static final int INVALID = -1, STEP = 1, JUMP = 2;

	public final Tile srcTile;
	public final Tile midTile;
	public final Tile destTile;
	public final Piece piece;
	public final int type;
	public boolean valid = false;

	public Move(Tile srcTile, Tile midTile, Tile destTile, Piece piece) {
		this.srcTile = srcTile;
		this.midTile = midTile;
		this.destTile = destTile;
		this.piece = piece;
		type = checkType();
		if (type != INVALID)
			valid = true;
	}

	public Move(int sx, int sy, int dx, int dy) throws IllegalArgumentException {
		System.out.println("Attempting to fetch piece from " + (sx) + ", "
				+ (sy) + "\nAnd moving it to " + (dx) + ", " + (dy));
		srcTile = Damm.board.getTileFromIndex(sx, sy);
		if (srcTile == null) {
			throw new IllegalArgumentException();
		}

		destTile = Damm.board.getTileFromIndex(dx, dy);
		if (destTile == null) {
			throw new IllegalArgumentException();
		}

		if (Math.abs(Tile.distance(srcTile, destTile)) == 2) {
			int xMidIndexOffset = (dx - sx) / 2;
			int yMidIndexOffset = (dy - sy) / 2;
			int mx, my;

			if (xMidIndexOffset < 0)
				mx = sx + 1;
			else
				mx = sx - 1;
			if (yMidIndexOffset < 0)
				my = sy - 1;
			else
				my = sy + 1;

			midTile = Damm.board.getTileFromIndex(mx, my);

			if (midTile == null)
				throw new IllegalArgumentException();
		} else {
			midTile = null;
		}

		piece = srcTile.piece;
		if (piece == null) {
			throw new IllegalArgumentException();
		}

		type = checkType();
		if (type != INVALID)
			valid = true;
	}

	public static Move parseMove(String src, String dest) {
		char[] srcChars = src.toLowerCase().toCharArray();
		if (srcChars.length != 2)
			return null;
		int sx = srcChars[0] - 97;
		int sy = srcChars[1] - 49;

		char[] destChars = dest.toLowerCase().toCharArray();
		if (destChars.length != 2)
			return null;
		int dx = destChars[0] - 97;
		int dy = destChars[1] - 49;

		try {
			Move move = new Move(sx, sy, dx, dy);
			return move;
		} catch (IllegalArgumentException e) {
			announceInvalidMove();
			return null;
		}
	}

	private int checkType() {
		boolean nullCheck, refCheck, validCheck;
		nullCheck = (destTile != null && srcTile != null && piece != null);
		if (!nullCheck)
			return INVALID;
		refCheck = (srcTile.piece == piece && srcTile != destTile);
		validCheck = (destTile.piece == null);

		if (!(refCheck && validCheck)) {
			return INVALID;
		}
		double distance = Math.abs(Tile.distance(srcTile, destTile));
		// Jump-move:
		if (distance >= 2) {
			if (midTile == null || midTile.piece == null)
				return INVALID;
			else if ((piece.color & Piece.COLOR_MASK) == (midTile.piece.color & Piece.COLOR_MASK))
				return INVALID;
			else
				return JUMP;
		}
		// Step:
		else if (distance < 2) {
			return STEP;
		}

		return INVALID;
	}

	public void make() {
		if (!valid) {
			announceInvalidMove();
			return;
		}

		switch (type) {
		case STEP:
			piece.move(destTile);
			SoundPlayer.play("bounce");
			break;
		case JUMP:
			piece.move(destTile);
			midTile.piece.destroy();
			midTile.piece = null;
			SoundPlayer.play("win");
			break;
		default:
		case INVALID:
			System.err.println("Could not make the move. (INVALID MOVE)");
			return;
		}
	}

	private static void announceInvalidMove() {
		JOptionPane.showConfirmDialog(null, "Invalid move!", "Error",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
	}

	@Override
	public String toString() {
		return "Move from " + srcTile + " to " + destTile
				+ (midTile != null ? " jumping over " + midTile : "");
	}
}
