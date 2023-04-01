package model;

import javafx.scene.image.Image;

public abstract class GameObject
{
	protected int row;
	protected int col;
	protected GameBoard gameBoard;
	protected boolean isOnBoard;
	
	public GameObject(int row, int col, GameBoard gameBoard)
	{
		this.row = row;
		this.col = col;
		this.gameBoard = gameBoard;
		isOnBoard = true;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public boolean getOnBoard()
	{
		return isOnBoard;
	}
	
	public void setOffBoard()
	{
		this.isOnBoard = false;
	}
	
	public abstract Image getImage();
}
