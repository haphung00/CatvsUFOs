package view;

import java.util.HashSet;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.GameBoard;
import model.GameObject;
import model.Player;
import model.Robot;
import model.Rubble;

public class GameView extends Pane
{
	public static final int WIDTH = 600;
	public static final int HEIGHT = 400;

	private static final Color DARK_SHADE = Color.SKYBLUE;
	private static final Color LIGHT_SHADE = Color.LIGHTBLUE;
	private static final int PREF_BLOCK_SIZE = 20;

	private GameBoard gameBoard;
	
	int i = 0;

	public GameView(GameBoard gb)
	{
		setPrefWidth(PREF_BLOCK_SIZE * GameBoard.COLS);
		setPrefHeight(PREF_BLOCK_SIZE * GameBoard.ROWS);

		gameBoard = gb;

		render();
	}
	
	public void render()
	{
		getChildren().clear();
		renderBoard();
		renderPlayer();
		renderRobot();
		renderRubble();
	}

	public void renderBoard()
	{
		for (int r = 0; r < GameBoard.ROWS; r++) {
			for (int c = 0; c < GameBoard.COLS; c++) {
				Rectangle rectangle = new Rectangle(PREF_BLOCK_SIZE, PREF_BLOCK_SIZE);

				rectangle.setX(c * PREF_BLOCK_SIZE);
				rectangle.setY(r * PREF_BLOCK_SIZE);

				if ((r + c) % 2 == 0) {
					rectangle.setFill(DARK_SHADE);
				}
				else {
					rectangle.setFill(LIGHT_SHADE);
				}

				getChildren().add(rectangle);
			}
		}
	}
	
	public void renderPlayer()
	{
		Player player = gameBoard.getPlayer();
		
		Image playerImage = player.getImage();
		ImageView playerImageView = new ImageView(playerImage);
		
		playerImageView.setFitWidth(PREF_BLOCK_SIZE);
		playerImageView.setFitHeight(PREF_BLOCK_SIZE);
		playerImageView.setX(player.getCol() * PREF_BLOCK_SIZE);
		playerImageView.setY(player.getRow() * PREF_BLOCK_SIZE);

		getChildren().add(playerImageView);
	}

	public void renderRobot()
	{
		HashSet<Robot> robots = gameBoard.getRobots();
		System.out.println("render " + robots);
		
		for (GameObject robot : robots) {
			Image robotImage = ((Robot) robot).getImage();
			ImageView robotImageView = new ImageView(robotImage);
			
			robotImageView.setFitWidth(PREF_BLOCK_SIZE);
			robotImageView.setFitHeight(PREF_BLOCK_SIZE);
			robotImageView.setX(robot.getCol() * PREF_BLOCK_SIZE);
			robotImageView.setY(robot.getRow() * PREF_BLOCK_SIZE);

			getChildren().add(robotImageView);
		}
	}
	
	public void renderRubble()
	{
		HashSet<Rubble> rubbles = gameBoard.getRubbles();
		
		for (GameObject rubble : rubbles) {
			Image rubbleImage = rubble.getImage();
			ImageView rubbleImageView = new ImageView(rubbleImage);
			
			rubbleImageView.setFitWidth(PREF_BLOCK_SIZE);
			rubbleImageView.setFitHeight(PREF_BLOCK_SIZE);
			rubbleImageView.setX(rubble.getCol() * PREF_BLOCK_SIZE);
			rubbleImageView.setY(rubble.getRow() * PREF_BLOCK_SIZE);

			getChildren().add(rubbleImageView);
		}
	}
}