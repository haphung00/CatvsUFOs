package model;

import java.util.HashSet;
import java.util.Random;

public class Player extends GameObject
{
	public static final int STAY = 0;
	public static final int MOVE = 1;
	public static final int TELEPORT = 2;

	public static final int SAFE_MOVE = 3;
	public static final int SAFE_TELEPORT = 4;

	private Random random = new Random();

	public Player(int row, int col, GameBoard gameBoard)
	{
		super(row, col, gameBoard);
	}

	@SuppressWarnings("unlikely-arg-type")
	public boolean isCollided()
	{
		HashSet<Robot> robots = gameBoard.getRobots();
		HashSet<Rubble> rubbles = gameBoard.getRubbles();

		if (robots.contains(this) || rubbles.contains(this)) {
			return true;
		}
		return false;
	}

	public void updatePosition(int typeOfMovement, int row, int col)
	{
		if (typeOfMovement == MOVE) {
			int nextRow = this.row + row;
			int nextCol = this.col + col;

			if (GameBoard.isValidPosition(nextRow, nextCol)) {
				this.row += row;
				this.col += col;
			}
		}
		else if (typeOfMovement == TELEPORT) {
			teleport();
		}
		else if (typeOfMovement == SAFE_TELEPORT) {
			safeTeleport();
		}

		if (isCollided()) {
			gameBoard.setState(GameBoard.LOSE);
		}
	}

	public void safeTeleport()
	{
		int row = random.nextInt(GameBoard.ROWS - 1);
		int col = random.nextInt(GameBoard.COLS - 1);

		while (row == this.row && col == this.col && isSafe(row, col)) {
			row = random.nextInt(GameBoard.ROWS - 1);
			col = random.nextInt(GameBoard.COLS - 1);
		}
		
		this.row = row;
		this.col = col;
	}

	public boolean isSafe(int row, int col)
	{
		int maxDistance = gameBoard.getRobotMaxMove();
		for (int r = row - maxDistance ; r <= row + maxDistance ; r++) {
			for (int c = col - maxDistance ; c <= col + maxDistance ; c++) {
				if (GameBoard.isValidPosition(r, c)) {
					GameObject obstacle = new Rubble(r, c, gameBoard);
					if (gameBoard.getRobots().contains(obstacle) && gameBoard.getRubbles().contains(obstacle)) {
						return false;
					}
				}
			}
		}
		
		return true;
	}

	public void teleport()
	{
		int row = random.nextInt(GameBoard.ROWS - 1);
		int col = random.nextInt(GameBoard.COLS - 1);

		while (row == this.row && col == this.col) {
			row = random.nextInt(GameBoard.ROWS - 1);
			col = random.nextInt(GameBoard.COLS - 1);
		}

		this.row = row;
		this.col = col;

		if (isCollided()) {
			gameBoard.setState(GameBoard.LOSE);
		}
	}
}
