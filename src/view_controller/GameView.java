package view_controller;

import java.util.ArrayList;

import javafx.beans.InvalidationListener;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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

	GameObjectImageView playerImageView;

	public GameView(GameBoard gb)
	{
		setPrefWidth(PREF_BLOCK_SIZE * GameBoard.COLS);
		setPrefHeight(PREF_BLOCK_SIZE * GameBoard.ROWS);

		gameBoard = new GameBoard();
		player = gameBoard.getPlayer();
		robots = gameBoard.getRobots();
		rubbles = gameBoard.getRubbles();

		render();
		
		setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.SPACE) {
				gameBoard.play(GameBoard.TELEPORT, 0, 0);
			}
			else if (e.getText().equals("e")) {
				gameBoard.play(GameBoard.MOVE, -1, 0);
			}
			else if (e.getText().equals("f")) {
				gameBoard.play(GameBoard.MOVE, 0, 1);
			}
			else if (e.getText().equals("c")) {
				gameBoard.play(GameBoard.MOVE, 1, 0);
			}
			else if (e.getText().equals("s")) {
				gameBoard.play(GameBoard.MOVE, 0, -1);
			}
			
			playerImageView.setX(player.getCol() * PREF_BLOCK_SIZE);
			playerImageView.setY(player.getRow() * PREF_BLOCK_SIZE);

		});
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
		playerImageView = new GameObjectImageView(playerImage);
		playerImageView.setGameObject(player);

		InvalidationListener listener = e -> {
			for (Node node : getChildren()) {
				if (node instanceof GameObjectImageView) {
					((GameObjectImageView) node).update();
				}
			}
		};
		
		playerImageView.xProperty().addListener(listener);
		playerImageView.yProperty().addListener(listener);

		getChildren().add(playerImageView);

		// render the robots
		for (Robot robot : robots) {
			Image robotImage = robot.getImage();
			GameObjectImageView robotImageView = new GameObjectImageView(robotImage);
			robotImageView.setGameObject(robot);

			getChildren().add(robotImageView);
		}

		// render the rubbles
		for (Rubble rubble : rubbles) {
			Image rubbleImage = rubble.getImage();
			GameObjectImageView rubbleImageView = new GameObjectImageView(rubbleImage);
			rubbleImageView.setGameObject(rubble);

			getChildren().add(rubbleImageView);
		}
	}

	class GameObjectImageView extends ImageView
	{
		GameObject gameObject;

		GameObjectImageView(Image image)
		{
			super(image);
		}

		void setGameObject(GameObject gameObject)
		{
			this.gameObject = gameObject;
			setX(gameObject.getCol() * PREF_BLOCK_SIZE);
			setY(gameObject.getRow() * PREF_BLOCK_SIZE);
			setFitWidth(PREF_BLOCK_SIZE);
			setFitHeight(PREF_BLOCK_SIZE);
		}
		
		void update()
		{
			if (gameObject.getOnBoard()) {
				setX(gameObject.getCol() * PREF_BLOCK_SIZE);
				setY(gameObject.getRow() * PREF_BLOCK_SIZE);
			}
			else {
				setOpacity(0);
			}
		}
	}
}