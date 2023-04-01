package model;

import javafx.scene.image.Image;

public class Robot extends GameObject
{
	public static final int ROBOT = 1;
	public static final int HEADROBOT = 2;

	int type;

	public Robot(int row, int col, int type, GameBoard gameBoard)
	{
		super(row, col, gameBoard);
		this.type = type;
	}

	@Override
	public Image getImage()
	{
		if (type == ROBOT) {
			return new Image("C:\\Users\\Ha\\projects\\eclipse-workspace\\Robots\\res\\robot.png");
		}
		else {
			return new Image("C:\\Users\\Ha\\projects\\eclipse-workspace\\Robots\\res\\headrobot.png");
		}
	}

	public int updateMove(int playerRow, int playerCol)
	{
		int[] direction = getRelativePosition(playerRow, playerCol);
		int proposedRow = row + direction[0];
		int proposedCol = col + direction[1];

		if (GameBoard.isValidRow(proposedRow)) {
			gameBoard.getBoard()[row][col] = null;
			row += direction[0];
		}

		if (GameBoard.isValidCol(proposedCol)) {
			gameBoard.getBoard()[row][col] = null;
			col += direction[1];
		}

		GameObject gameObject = gameBoard.getBoard()[row][col];

		if (gameObject != null && gameObject != this) {
			if (gameObject instanceof Player) {
				gameBoard.setLost();
			}
			else {				
				gameBoard.getRobots().remove(this);

				if (gameObject instanceof Robot) {
					Robot otherRobot = (Robot) gameObject;
					gameBoard.robotvsRobotCollision(this, otherRobot);
					return 2;
				}
				else {
					Rubble rubble = (Rubble) gameObject;
					gameBoard.robotvsRubbleCollision(this, rubble);
					return 1;
				}
			}
		}
		else {
			gameBoard.getBoard()[row][col] = this;
		}

		return 0;
	}

	public int[] getRelativePosition(int playerRow, int playerCol)
	{
		if (this.row == playerRow) {
			if (this.col < playerCol) {
				return new int[] {0, 1 * type};
			}
			else if (this.col > playerCol) {
				return new int[] {0, -1 * type};
			}
			else {
				return new int[] {0, 0};
			}
		}
		else if (this.row < playerRow) {
			if (this.col < playerCol) {
				return new int[] {1 * type, 1 * type};
			}
			else if (this.col > playerCol) {
				return new int[] {1 * type, -1 * type};
			}
			else {
				return new int[] {1 * type, 0};
			}
		}
		else {
			if (this.col < playerCol) {
				return new int[] {-1 * type, 1 * type};
			}
			else if (this.col > playerCol) {
				return new int[] {-1 * type, -1 * type};
			}
			else {
				return new int[] {-1 * type, 0};
			}
		}
	}

	public int getType()
	{
		return type;
	}
}
