package view_controller;

import java.beans.EventHandler;
import java.util.ArrayList;

import javafx.scene.Scene;
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
	private Player player;
	private ArrayList<Robot> robots;
	private ArrayList<Rubble> rubbles;

	public GameView(GameBoard gb)
	{		
		setPrefWidth(PREF_BLOCK_SIZE * GameBoard.COLS);
		setPrefHeight(PREF_BLOCK_SIZE * GameBoard.ROWS);

		gameBoard = new GameBoard();
		player = gameBoard.getPlayer();
		robots = gameBoard.getRobots();
		rubbles = gameBoard.getRubbles();
		
		render();
	}

	public void render()
	{
		// render the board
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
		
		// render the player
		Image playerImage = player.getImage();
		ImageView playerImageView = new ImageView(playerImage);
		playerImageView.setX(player.getCol() * PREF_BLOCK_SIZE);
		playerImageView.setY(player.getRow() * PREF_BLOCK_SIZE);
		playerImageView.setFitWidth(PREF_BLOCK_SIZE);
		playerImageView.setFitHeight(PREF_BLOCK_SIZE);
		
		getChildren().add(playerImageView);
		
		// render the robots
		for (Robot robot : robots) {
			int row = robot.getRow();
			int col = robot.getCol();
			
			Image robotImage = robot.getImage();
			ImageView robotImageView = new ImageView(robotImage);
			robotImageView.setX(col * PREF_BLOCK_SIZE);
			robotImageView.setY(row * PREF_BLOCK_SIZE);
			robotImageView.setFitWidth(PREF_BLOCK_SIZE);
			robotImageView.setFitHeight(PREF_BLOCK_SIZE);
			
			getChildren().add(robotImageView);
		}
		
		// render the rubbles
		for (Rubble rubble : rubbles) {
			int row = rubble.getRow();
			int col = rubble.getCol();
			
			Image rubbleImage = rubble.getImage();
			ImageView rubbleImageView = new ImageView(rubbleImage);
			rubbleImageView.setX(col * PREF_BLOCK_SIZE);
			rubbleImageView.setY(row * PREF_BLOCK_SIZE);
			rubbleImageView.setFitWidth(PREF_BLOCK_SIZE);
			rubbleImageView.setFitHeight(PREF_BLOCK_SIZE);
			
			getChildren().add(rubbleImageView);
		}
	}
}
