package model;

import javafx.scene.image.Image;

public class Robot extends GameObject
{
	public static int ROBOT = 1;
	public static int HEADROBOT = 2;

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

	public void updateMove(int playerRow, int playerCol)
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

		if (gameObject != null) {
			if (gameObject instanceof Player) {
				
			}
			else {
				gameBoard.getRobots().remove(this);
				gameBoard.getRobots().remove(gameBoard.getBoard()[row][col]);
				
				Rubble rubble = new Rubble(row, col, gameBoard);
				gameBoard.getRubbles().add(rubble);
				gameBoard.getBoard()[row][col] = rubble;
			}
		}
		else {
			gameBoard.getBoard()[row][col] = this;
		}
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
		else if (this.col < playerCol) {
			return new int[] {-1 * type, 1 * type};
		}
		else if (this.col > playerCol) {
			return new int[] {-1 * type, -1 * type};
		}
		else {
			return new int[] {-1 * type, 0};
		}
	}

	public int getType()
	{
		return type;
	}
}
