package model;

import java.util.Random;

import javafx.scene.image.Image;

public class Player extends GameObject
{
	private Random random = new Random();
	
	public Player(int row, int col, GameBoard gameBoard)
	{
		super(row, col, gameBoard);
	}

	@Override
	public Image getImage()
	{
		return new Image("C:\\Users\\Ha\\projects\\eclipse-workspace\\Robots\\res\\cat.png");
	}	

	public void move(int rowStep, int colStep)
	{		
		if (GameBoard.isValidPosition(row + rowStep, col + colStep)) {
			gameBoard.getBoard()[this.row][this.col] = null;
			row += rowStep;
			col += colStep;
		}
		
		gameBoard.getBoard()[this.row][this.col] = this;
	}
	
	public void teleport()
	{
		int row = random.nextInt(GameBoard.ROWS - 1);
		int col = random.nextInt(GameBoard.COLS - 1);
		
		while (row == this.row && col == this.col) {
			row = random.nextInt(GameBoard.ROWS - 1);
			col = random.nextInt(GameBoard.COLS - 1);
		}

		gameBoard.getBoard()[this.row][this.col] = null;
		
		this.row = row;
		this.col = col;
		
		gameBoard.getBoard()[this.row][this.col] = this;
	}
}
