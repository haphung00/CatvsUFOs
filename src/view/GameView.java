package view;

import java.util.HashSet;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.GameBoard;
import model.Player;
import model.Robot;
import model.Rubble;
import theme.CatvsUFOsTheme;
import theme.Theme;

public class GameView extends Pane
{
	public static final int WIDTH = 800;
	public static final int HEIGHT = 500;

	private SimpleDoubleProperty blockWidthProperty;
	private SimpleDoubleProperty blockHeightProperty;

	private static final int CIRCLE_RADIUS = 5;
	private static final double ARROW_IMAGE_RATIO = 0.8;
	private static final Color CIRCLE_COLOR = Color.HOTPINK;

	private static final Color DARK_SHADE = Color.SKYBLUE;
	private static final Color LIGHT_SHADE = Color.LIGHTBLUE;

	private GameBoard gameBoard;
	private Player player;
	private HashSet<Robot> robots;
	private HashSet<Rubble> rubbles;

	private Theme theme;
	private Image playerImage;
	private Image lostPlayerImage;
	private Image robotImage;
	private Image headRobotImage;
	private Image rubbleImage;

	private Circle circleMouse;
	private ImageView arrowImageViewMouse;

	public GameView(Theme theme)
	{
		setPrefWidth(WIDTH);
		setPrefHeight(HEIGHT);

		blockWidthProperty = new SimpleDoubleProperty();
		blockWidthProperty.bind(widthProperty().divide(GameBoard.COLS));
		blockHeightProperty = new SimpleDoubleProperty();
		blockHeightProperty.bind(heightProperty().divide(GameBoard.ROWS));

		gameBoard = new GameBoard();

		this.theme = theme;
		playerImage = theme.getPlayerImage();
		lostPlayerImage = theme.getLostPlayerImage();
		robotImage = theme.getRobotImage();
		headRobotImage = theme.getHeadRobotImage();
		rubbleImage = theme.getRubbleImage();

		render();
	}

	public void render()
	{
		getChildren().clear();
		renderBoard();
		renderRobot();
		renderRubble();
		renderPlayer();

		circleMouse = new Circle(CIRCLE_RADIUS);
		circleMouse.setFill(CIRCLE_COLOR);
		circleMouse.setOpacity(0);

		arrowImageViewMouse = new ImageView(theme.getArrowImage());
		arrowImageViewMouse.fitWidthProperty().bind(blockWidthProperty.multiply(ARROW_IMAGE_RATIO));
		arrowImageViewMouse.fitHeightProperty().bind(blockHeightProperty.multiply(ARROW_IMAGE_RATIO));
		arrowImageViewMouse.setOpacity(0);

		getChildren().addAll(circleMouse, arrowImageViewMouse);

		renderEndNotice();
	}

	public void renderEndNotice()
	{
		int playerRow = gameBoard.getPlayer().getRow();
		int playerCol = gameBoard.getPlayer().getCol();
		int gameState = gameBoard.getState();

		if (gameState == GameBoard.WIN) {
			if (playerCol < GameBoard.COLS / 2) {
				ImageView winLeftView = new ImageView(theme.getLeftWin());
				winLeftView.xProperty().bind(blockWidthProperty.multiply(playerCol + 0.5));
				winLeftView.yProperty().bind(blockHeightProperty.multiply(playerRow - 1));
				getChildren().add(winLeftView);
			}
			else {
				ImageView winRightView = new ImageView(theme.getRightWin());
				winRightView.xProperty().bind(blockWidthProperty.multiply(playerCol - 2));
				winRightView.yProperty().bind(blockHeightProperty.multiply(playerRow - 1.5));
				getChildren().add(winRightView);
			}
		}
		else if (gameState == GameBoard.LOSE) {
			if (playerCol < GameBoard.COLS / 2) {
				ImageView loseLeftView = new ImageView(theme.getLeftLose());
				loseLeftView.xProperty().bind(blockWidthProperty.multiply(playerCol + 0.5));
				loseLeftView.yProperty().bind(blockHeightProperty.multiply(playerRow - 1));
				getChildren().add(loseLeftView);
			}
			else {
				ImageView loseRightView = new ImageView(theme.getRightLose());
				loseRightView.xProperty().bind(blockWidthProperty.multiply(playerCol - 2));
				loseRightView.yProperty().bind(blockHeightProperty.multiply(playerRow - 1.5));
				getChildren().add(loseRightView);
			}
		}
	}

	public void renderBoard()
	{
		for (int r = 0; r < GameBoard.ROWS; r++) {
			for (int c = 0; c < GameBoard.COLS; c++) {
				Rectangle rectangle = new Rectangle();

				rectangle.xProperty().bind(blockWidthProperty.multiply(c));
				rectangle.yProperty().bind(blockHeightProperty.multiply(r));

				rectangle.widthProperty().bind(blockWidthProperty);
				rectangle.heightProperty().bind(blockHeightProperty);

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

	public void renderRobot()
	{
		robots = gameBoard.getRobots();

		for (Robot robot : robots) {
			ImageView robotImageView = new ImageView(robotImage);

			if (robot.getType() == Robot.HEAD_ROBOT) {
				robotImageView = new ImageView(headRobotImage);
			}

			robotImageView.xProperty().bind(blockWidthProperty.multiply(robot.getCol()));
			robotImageView.yProperty().bind(blockHeightProperty.multiply(robot.getRow()));

			robotImageView.fitWidthProperty().bind(blockWidthProperty);
			robotImageView.fitHeightProperty().bind(blockHeightProperty);

			getChildren().add(robotImageView);
		}
	}

	public void renderRubble()
	{
		rubbles = gameBoard.getRubbles();

		for (Rubble rubble : rubbles) {
			ImageView rubbleImageView = new ImageView(rubbleImage);

			rubbleImageView.xProperty().bind(blockWidthProperty.multiply(rubble.getCol()));
			rubbleImageView.yProperty().bind(blockHeightProperty.multiply(rubble.getRow()));

			rubbleImageView.fitWidthProperty().bind(blockWidthProperty);
			rubbleImageView.fitHeightProperty().bind(blockHeightProperty);

			getChildren().add(rubbleImageView);
		}
	}

	public void renderPlayer()
	{
		player = gameBoard.getPlayer();

		ImageView playerImageView = new ImageView(playerImage);

		if (gameBoard.getState() == GameBoard.LOSE) {
			playerImageView = new ImageView(lostPlayerImage);
		}

		playerImageView.xProperty().bind(blockWidthProperty.multiply(player.getCol()));
		playerImageView.yProperty().bind(blockHeightProperty.multiply(player.getRow()));

		playerImageView.fitWidthProperty().bind(blockWidthProperty);
		playerImageView.fitHeightProperty().bind(blockHeightProperty);

		getChildren().add(playerImageView);
	}

	public String displayArrow(double x, double y)
	{
		double xStartValue = getBlockWidth() * (gameBoard.getPlayer().getCol());
		double xEndValue = getBlockWidth() * (gameBoard.getPlayer().getCol() + 1);

		double yStartValue = getBlockHeight() * (gameBoard.getPlayer().getRow());
		double yEndValue = getBlockHeight() * (gameBoard.getPlayer().getRow() + 1);

		int betweenX = between(x, xStartValue, xEndValue);
		int betweenY = between(y, yStartValue, yEndValue);

		if (betweenX == 0 && betweenY == 0) {
			circleMouse.setOpacity(1);
			arrowImageViewMouse.setOpacity(0);

			circleMouse.setCenterX(x);
			circleMouse.setCenterY(y);

			return "STAY";
		}
		else {
			if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
				arrowImageViewMouse.setOpacity(1);
				circleMouse.setOpacity(0);

				arrowImageViewMouse.setTranslateX(x);
				arrowImageViewMouse.setTranslateY(y);

				if (betweenX == 1 && betweenY == 0) {
					// E
					arrowImageViewMouse.setRotate(0);
					return "E";
				}
				else if (betweenX == -1 && betweenY == 0) {
					// W
					arrowImageViewMouse.setRotate(-180);
					return "W";
				}
				else if (betweenX == 0 && betweenY == -1) {
					// N
					arrowImageViewMouse.setRotate(-90);
					return "N";
				}
				else if (betweenX == 0 && betweenY == 1) {
					// S
					arrowImageViewMouse.setRotate(90);
					return "S";
				}
				else if (betweenX == -1 && betweenY == -1) {
					// NW
					arrowImageViewMouse.setRotate(-135);
					return "NW";
				}
				else if (betweenX == 1 && betweenY == -1) {
					// NE
					arrowImageViewMouse.setRotate(-45);
					return "NE";
				}
				else if (betweenX == -1 && betweenY == 1) {
					// SW
					arrowImageViewMouse.setRotate(-225);
					return "SW";
				}
				else {
					// SE
					arrowImageViewMouse.setRotate(45);
					return "SE";
				}
			}
			
			return "NO";
		}
	}

	/**
	 * @param  value      given value to check if it is between the two given values
	 * @param  startValue the starting point of the range
	 * @param  endValue   the ending point of the range
	 * 
	 * @return            -1 if {@value} is smaller than the range, 1 if {@value} is
	 *                    larger than the range, and 0 if {@value} is in the range
	 */
	private int between(double value, double startValue, double endValue)
	{
		if (value < startValue) {
			return -1;
		}
		else if (value >= startValue && value <= endValue) {
			return 0;
		}
		else {
			return 1;
		}
	}

	public void setThemeImage(Theme newTheme)
	{
		theme = newTheme;
	}

	public GameBoard getGameBoard()
	{
		return gameBoard;
	}

	public double getBlockWidth()
	{
		return blockWidthProperty.doubleValue();
	}

	public double getBlockHeight()
	{
		return blockHeightProperty.doubleValue();
	}
}