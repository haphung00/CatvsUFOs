package model;

public class Robot extends GameObject
{
	public static final int ROBOT = 1;
	public static final int HEAD_ROBOT = 2;

	private int type;

	public Robot(int row, int col, int type, GameBoard gameBoard)
	{
		super(row, col, gameBoard);
		this.type = type;
	}

	public void updateMove(int playerRow, int playerCol)
	{
		int[] direction = getRelativePosition(playerRow, playerCol);
		int proposedRow = row + direction[0];
		int proposedCol = col + direction[1];

		if (GameBoard.isValidPosition(proposedRow, proposedCol)){
			row = proposedRow;
			col = proposedCol;
		}
	}

	public int[] getRelativePosition(int playerRow, int playerCol)
	{
		int rowDirection = 1, colDirection = 1;
		
		if (row > playerRow) {
			rowDirection = -1;
		}
		
		if (col > playerCol) {
			colDirection = -1;
		}
		
		int rowStep = Math.abs(playerRow - row) < type ? playerRow - row : rowDirection * type;
		int colStep = Math.abs(playerCol - col) < type ? playerCol - col : colDirection * type;
		
		return new int[] {rowStep, colStep};
	}
	
	public int getType()
	{
		return type;
	}
}
