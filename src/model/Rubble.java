package model;

import javafx.scene.image.Image;

public class Rubble extends GameObject
{
	public Rubble(int row, int col, GameBoard gameBoard)
	{
		super(row, col, gameBoard);
	}

	@Override
	public Image getImage()
	{
		return new Image("C:\\Users\\Ha\\projects\\eclipse-workspace\\Robots\\res\\rubble.png");
	}
}
