package model;

import javafx.scene.image.Image;

public abstract class GameObject
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

	public void setRow(int row)
	{
		this.row = row;
	}

	public void setCol(int col)
	{
		this.col = col;
	}
	
	public abstract Image getImage();
}
