package model;

import java.util.ArrayList;
import java.util.Random;

public class GameBoard
{
	public static final int ROWS = 30;
	public static final int COLS = 45;
	
	private static final int ROBOT_NUM = 10;
	private static final int HEAD_ROBOT_NUM = 2;
	
	private int score;
	
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
	
	public GameBoard()
	{
		int score = 0;
		
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

	public void setScore(int score)
	{
		this.score = score;
	}
}
