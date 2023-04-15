package model;

import java.util.HashSet;
import java.util.Random;

public class Player extends GameObject
{
	public static final int MOVE = 1;
	public static final int TELEPORT = 2;

	public static final int SAFE_MOVE = 3;
	public static final int SAFE_TELEPORT = 4;

	private boolean isOnSafeMode;

	private Random random = new Random();

	public Player(int row, int col, GameBoard gameBoard)
	{
		super(row, col, gameBoard);
		isOnSafeMode = false;
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

	public boolean updatePosition(int typeOfMovement, int row, int col)
	{
		if (typeOfMovement == SAFE_MOVE) {
			return safeMove(row, col);
		}

		if (typeOfMovement == MOVE) {
			move(row, col);
		}
		else if (typeOfMovement == TELEPORT) {
			teleport();
		}
		else if (typeOfMovement == SAFE_TELEPORT) {
			safeTeleport();
		}

		return true;
	}

	private void move(int row, int col)
	{
		int nextRow = this.row + row;
		int nextCol = this.col + col;

		if (GameBoard.isValidPosition(nextRow, nextCol)) {
			this.row = nextRow;
			this.col = nextCol;
		}

		if (isCollided()) {
			gameBoard.setState(GameBoard.LOSE);
		}
	}

	private boolean safeMove(int row, int col)
	{
		int nextRow = this.row + row;
		int nextCol = this.col + col;

		if (isSafe(nextRow, nextCol)) {
			this.row = nextRow;
			this.col = nextCol;

			return true;
		}

		return false;
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

	private void safeTeleport()
	{
		int row = random.nextInt(GameBoard.ROWS - 1);
		int col = random.nextInt(GameBoard.COLS - 1);

		while ((row == this.row && col == this.col) || !isSafe(row, col)) {
			row = random.nextInt(GameBoard.ROWS - 1);
			col = random.nextInt(GameBoard.COLS - 1);
		}

		this.row = row;
		this.col = col;
	}

	private boolean isSafe(int row, int col)
	{
		GameObject obstacle = new GameObject(row, col, gameBoard);
		if (gameBoard.getRobots().contains(obstacle) || gameBoard.getRubbles().contains(obstacle)) {
			return false;
		}
		
		for (Robot robot : gameBoard.getRobots()) {
			if (Math.abs(robot.getRow() - row) <= robot.getType() && Math.abs(robot.getCol() - col) <= robot.getType()) {
				return false;
			}
		}
		
		return true;
	}

	public void setOnSafeMode(boolean isOnSafeMode)
	{
		this.isOnSafeMode = isOnSafeMode;
	}

	public boolean getIsOnSafeMode()
	{
		return isOnSafeMode;
	}
}
