package no.kjelli.julekalender.luke3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.newdawn.slick.Color;

import no.kjelli.generic.gfx.Draw;

public class Board {
	public static final boolean white = true, black = false;

	private static Cell[][] board;
	private static Horsey horsey;
	private static int width, height;

	public static void init(int width, int height) {
		Board.width = width;
		Board.height = height;
		board = new Cell[width][height];
		populateBoard();
		horsey = new Horsey(0, 0);
		offsetX = (float) (JuleKalender.getGameWidth() / 2 - width
				* JuleKalender.block_size / 2);
		offsetY = (float) (JuleKalender.getGameHeight() / 2 - height
				* JuleKalender.block_size / 2);
	}

	private static void populateBoard() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				board[x][y] = new Cell(x, y);
			}
		}
	}

	public static Cell get(int x, int y) {
		return board[x][y];
	}

	public static Horsey getHorsey() {
		return horsey;
	}

	public static int getNumberOfBlackCells() {
		int blacks = 0;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (board[x][y].color == black) {
					blacks++;
				}
			}
		}
		return blacks;
	}

	public static class Cell {

		private int x;
		private int y;
		private boolean color;

		public Cell(int x, int y) {
			this.x = x;
			this.y = y;
			color = white;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getNumber() {
			return x * 10 + y;
		}

		public void render() {
			Draw.rect(offsetX + x * JuleKalender.block_size, offsetY + y
					* JuleKalender.block_size, 0, JuleKalender.block_size,
					JuleKalender.block_size, Color.gray);
			Draw.fillRect(offsetX + x * JuleKalender.block_size, offsetY + y
					* JuleKalender.block_size, 0, JuleKalender.block_size,
					JuleKalender.block_size, color == black ? Color.black
							: Color.white);
		}
	}

	static class Horsey {
		private static Cell cell;

		public Horsey(int x, int y) {
			cell = board[x][y];
		}

		public static Cell getCell() {
			return cell;
		}

		public static void setCell(Cell cell) {
			// System.out.println("Moved horsey from "
			// + (Horsey.cell.color == black ? "black" : "white")
			// + " cell " + Horsey.cell.getNumber() + " to "
			// + (cell.color == black ? "black" : "white") + " cell "
			// + cell.getNumber());
			Horsey.cell.color = !Horsey.cell.color;
			Horsey.cell = cell;
		}

		public static void traverse(int n) {
			for (int i = 0; i < n; i++) {
				ArrayList<Cell> cells = getEligibleCells(Horsey.cell.color);
				if (cells.size() == 0) {
					cells = getEligibleCells(!Horsey.cell.color);
					System.out.println("Moving to opposite");
				}

				setCell(cells.get(0));
			}

		}

		private static void render() {
			Draw.fillRect(offsetX + JuleKalender.block_size / 4 + cell.x
					* JuleKalender.block_size, offsetY
					+ JuleKalender.block_size / 4 + cell.y
					* JuleKalender.block_size, 1, JuleKalender.block_size / 2,
					JuleKalender.block_size / 2, Color.red);
		}

		public static ArrayList<Cell> getEligibleCells(final boolean color) {
			ArrayList<Cell> cells = new ArrayList<Cell>();
			for (int x = -2; x <= 2; x++) {
				if (x == 0)
					continue;
				for (int y = -2; y <= 2; y++) {
					if (y == 0 || x == y)
						continue;
					if (Math.abs(x) + Math.abs(y) != 3)
						continue;
					if (Horsey.cell.x + x >= width || Horsey.cell.x + x < 0
							|| Horsey.cell.y + y >= height
							|| Horsey.cell.y + y < 0)
						continue;
					Cell eligible = board[Horsey.cell.x + x][Horsey.cell.y + y];
					if (eligible.color == color)
						cells.add(eligible);
					else
						continue;
				}
			}
			Collections.sort(cells, new Comparator<Cell>() {
				@Override
				public int compare(Cell o1, Cell o2) {
					if (Horsey.cell.color == color)
						return Integer.compare(o1.getNumber(), o2.getNumber());
					else
						return Integer.compare(o2.getNumber(), o1.getNumber());
				}
			});
			return cells;
		}
	}

	public static float offsetX;
	public static float offsetY;

	public static void render() {
		Draw.rect(offsetX, offsetY, (float) width * JuleKalender.block_size,
				(float) height * JuleKalender.block_size, Color.yellow);
		for (Cell[] row : board) {
			for (Cell c : row) {
				c.render();
			}
		}
		Horsey.render();
	}

}
