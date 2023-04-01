package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameBoard
{
	public static final int MOVE = 1;
	public static final int TELEPORT = 2;

	public static final int ROWS = 30;
	public static final int COLS = 45;

	public static final int SCORE_UNIT = 10;

	private static final int ROBOT_NUM = 10;
	private static final int HEAD_ROBOT_NUM = 2;

	private int score;
	private boolean isLost;

	private GameObject[][] board;
	private Player player;
	private ArrayList<Robot> robots;
	private ArrayList<Rubble> rubbles;

	Random random = new Random();

	public static boolean isValidPosition(int row, int col)
	{
		if (isValidRow(row) && isValidCol(col)) {
			return true;
		}
		return false;
	}

	public static boolean isValidRow(int row)
	{
		if (row >= 0 && row < ROWS) {
			return true;
		}
		return false;
	}

	public static boolean isValidCol(int col)
	{
		if (col >= 0 && col < COLS) {
			return true;
		}
		return false;
	}

	public void play(int option, int rowStep, int colStep)
	{
		if (!isGameOver()) {
			if (option == 1) {
				player.move(rowStep, colStep);
			}
			else {
				player.teleport();
			}

			for (int i = 0 ; i < robots.size() ; i++) {
				int removedRobots = robots.get(i).updateMove(player.getRow(), player.getCol());
				i = i - removedRobots > 0 ? i - removedRobots : 0;
			}
		}
		else {
			System.out.println("over");
			
			if (isWin()) {
				System.out.println("won");
			}
		}
	}

	public GameBoard()
	{
		int score = 0;
		isLost = false;

		board = new GameObject[ROWS][COLS];

		player = new Player(ROWS / 2, COLS / 2, this);
		board[ROWS / 2][COLS / 2] = player;

		robots = new ArrayList<>();
		rubbles = new ArrayList<>();		

		for (int i = 0 ; i < ROBOT_NUM ; i++) {
			int row = random.nextInt(ROWS - 1);
			int col = random.nextInt(COLS - 1);

			while (board[row][col] != null) {
				row = random.nextInt(ROWS - 1);
				col = random.nextInt(COLS - 1);
			}

			Robot robot = new Robot(row, col, Robot.ROBOT, this);
			robots.add(robot);
			board[row][col] = robot;
		}

		for (int i = 0 ; i < HEAD_ROBOT_NUM ; i++) {
			int row = random.nextInt(ROWS - 1);
			int col = random.nextInt(COLS - 1);

			while (board[row][col] != null) {
				row = random.nextInt(ROWS - 1);
				col = random.nextInt(COLS - 1);
			}

			Robot headRobot = new Robot(row, col, Robot.HEADROBOT, this);
			robots.add(headRobot);
			board[row][col] = headRobot;
		}
	}

	public void robotvsRobotCollision(Robot thisRobot, Robot otherRobot)
	{
		addScore(thisRobot.type * SCORE_UNIT + otherRobot.type * SCORE_UNIT);
		thisRobot.setOffBoard();
		otherRobot.setOffBoard();
		robots.remove(thisRobot);
		robots.remove(otherRobot);
		Rubble rubble = new Rubble(otherRobot.row, otherRobot.col, this);
		rubbles.add(rubble);
		board[rubble.row][rubble.col] = rubble;
	}

	public void robotvsRubbleCollision(Robot robot, Rubble rubble)
	{
		addScore(robot.type * SCORE_UNIT);
		robot.setOffBoard();
		robots.remove(robot);		
	}

	public void setLost()
	{
		isLost = true;
	}
	
	public boolean isGameOver()
	{
		return isLost || isWin();
	}

	public boolean isWin()
	{
		if (robots.isEmpty()) {
			return true;
		}
		return false;
	}

	public Player getPlayer()
	{
		return player;
	}

	public GameObject[][] getBoard()
	{
		return board;
	}

	public ArrayList<Robot> getRobots()
	{
		return robots;
	}

	public ArrayList<Rubble> getRubbles()
	{
		return rubbles;
	}

	public int getScore()
	{
		return score;
	}

	public void addScore(int score)
	{
		this.score += score;
	}
}
