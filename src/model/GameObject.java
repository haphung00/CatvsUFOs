package model;

public class GameObject
{
	protected int row;
	protected int col;
	protected GameBoard gameBoard;
	
	public GameObject(int row, int col, GameBoard gameBoard)
	{
		this.row = row;
		this.col = col;
		this.gameBoard = gameBoard;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
	
	@Override
	public int hashCode()
	{
		return 17 * row + 19 * col;
	}

	@Override
	public boolean equals(Object otherGameObject)
	{
		if (!(otherGameObject instanceof GameObject)) {
			return false;
		}
		
		if (otherGameObject == this) {
			return true;
		}
		
		GameObject gameObject = (GameObject) otherGameObject;
		return gameObject.row == this.row && gameObject.col == this.col;		
	}	
	
	@Override
	public String toString()
	{
		return String.format("%d %d", row, col);
	}
}
