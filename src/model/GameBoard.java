package model;

import java.util.HashSet;
import java.util.Random;

public class GameBoard
{
	public static final int ROWS = 20;
	public static final int COLS = 30;

	private static final int SCORE_UNIT = 20;
	private static final int SAFE_SCORE_UNIT = 10;
	private static final int SAFE_TELEPORT_TIMES = 4;

	public static final int CONTINUING = 0;
	public static final int WIN = 1;
	public static final int LOSE = 2;

	private static int level = 1;
	private static int robotNum;
	private static int headRobotNum;

	private int scoreUnit = SCORE_UNIT;

	private int score;
	private int state;
	private int safeTeleportTimes;

	private Player player;
	private HashSet<Robot> robots;
	private HashSet<Rubble> rubbles;

	Random random = new Random();

	public static boolean isValidPosition(int row, int col)
	{
		if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
			return true;
		}
		return false;
	}

	public boolean movePlayer(int typeOfMovement, int row, int col)
	{
		return player.updatePosition(typeOfMovement, row, col);
	}

	@SuppressWarnings("unlikely-arg-type")
	public void updateRobots()
	{
		HashSet<Robot> newRobots = new HashSet<>();

		for (Robot robot : robots) {
			robot.updateMove(player.row, player.col);

			// handle collision with player
			if (robot.row == player.row && robot.col == player.col) {
				setState(GameBoard.LOSE);
			}

			// handle collision with rubbles and other robots

			if (!newRobots.contains(robot) && !rubbles.contains(robot)) {
				newRobots.add(robot);
			}
			else {
				if (newRobots.contains(robot)) {
					rubbles.add(new Rubble(robot.getRow(), robot.getCol(), this));
					newRobots.remove(robot);
					score += 2 * scoreUnit;
				}
				else {
					score += scoreUnit;
				}
			}
		}

		robots = newRobots;

		checkWin();
	}

	public GameBoard()
	{
		initializeBoard();
	}

	public void initializeBoard()
	{
		state = GameBoard.CONTINUING;
		safeTeleportTimes = SAFE_TELEPORT_TIMES;

		setRobotNumAccordingToLevel();

		player = new Player(ROWS / 2, COLS / 2, this);

		robots = new HashSet<>();
		rubbles = new HashSet<>();

		while (robots.size() < robotNum) {
			int row = random.nextInt(ROWS - 1);
			int col = random.nextInt(COLS - 1);

			if (row != player.row && col != player.col) {
				Robot robot = new Robot(row, col, Robot.ROBOT, this);
				robots.add(robot);
			}
		}

		while (robots.size() < robotNum + headRobotNum) {
			int row = random.nextInt(ROWS - 1);
			int col = random.nextInt(COLS - 1);

			if (row != player.row && col != player.col) {
				Robot headRobot = new Robot(row, col, Robot.HEAD_ROBOT, this);
				robots.add(headRobot);
			}
		}
	}

	public void checkWin()
	{
		if (robots.isEmpty()) {
			setState(GameBoard.WIN);
		}
	}

	public Player getPlayer()
	{
		return player;
	}

	public HashSet<Robot> getRobots()
	{
		return robots;
	}

	public HashSet<Rubble> getRubbles()
	{
		return rubbles;
	}

	public int getScore()
	{
		return score;
	}

	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state = state;
	}

	public int getLevel()
	{
		return level;
	}

	public void nextLevel()
	{
		level++;
		initializeBoard();
	}

	public void restart()
	{
		level = 1;
		score = 0;
		initializeBoard();
	}

	public void setScoreUnit(boolean isOnSafeMode)
	{
		if (isOnSafeMode) {
			scoreUnit = SAFE_SCORE_UNIT;
		}
		else {
			scoreUnit = SCORE_UNIT;
		}
	}

	public void setRobotNumAccordingToLevel()
	{
		if (level == 1) {
			robotNum = 7;
			headRobotNum = 0;
		}
		else {
			robotNum += 2;
			headRobotNum += 1;

		}
	}

	public int getSafeTeleportTimes()
	{
		return safeTeleportTimes;
	}

	public void decreaseSafeTeleportTimes()
	{
		safeTeleportTimes--;
	}
}
