package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.GameBoard;
import model.Player;
import view.FunctionView;
import view.GameView;
import view.MainView;
import view.MoveView;


public class Controller
{
	private MainView mainView;
	private FunctionView functionView;
	private MoveView moveView;
	private GameView gameView;
	private GameBoard board;

	private Stage stage;
	private Scene scene;

	private Timeline timeline;
	
	public Controller()
	{
		initiate();
		setKeyInput();
		setMouseInput();
		setUpMoveViewButton();
		displayDirection();
	}

	public void initiate()
	{
		mainView = new MainView();
		functionView = mainView.getFunctionView();
		moveView = mainView.getMoveView();
		gameView = mainView.getGameView();
		board = mainView.getGameBoard();

		stage = mainView.getStage();
		scene = mainView.getScene();
	}
	
	public void setUpHandler()
	{
		setKeyInput();
		setMouseInput();
		setUpMoveViewButton();
	}

	public void movePlayerAndUpdate(int typeOfMovement, int rowStep, int colStep)
	{
		updateGameState();
		board.movePlayer(typeOfMovement, rowStep, colStep);
		updateGameState();
		board.updateRobots();
		functionView.updateStatistics(board.getScore(), board.getLevel());
		updateGameState();
		gameView.render();
		functionView.updateStatistics(board.getScore(), board.getLevel());
	}
	
	public void playerWaitsForRobots()
	{
		  timeline = new Timeline(new KeyFrame(Duration.seconds(0.25), e -> {
		        if (board.getState() == GameBoard.CONTINUING) {
		            movePlayerAndUpdate(Player.STAY, 0, 0);
		            gameView.render();
		        }
		    }));
		    timeline.setCycleCount(Timeline.INDEFINITE);
		    timeline.play();
	}
	
	public void setUpMoveViewButton()
	{
		moveView.setOnKeyPressed(e -> {
			e.consume();
		});
		moveView.setOnKeyReleased(e -> {
			e.consume();
		});
		moveView.setFocusTraversable(false);
		
		moveView.getTeleportButton().setOnMouseClicked(e -> {
			movePlayerAndUpdate(Player.TELEPORT, 0, 0);
		});
		moveView.getTeleportButton().setFocusTraversable(false);
		
		moveView.getSafeTeleportButton().setOnMouseClicked(e -> {
			movePlayerAndUpdate(Player.SAFE_TELEPORT, 0, 0);
		});
		moveView.getSafeTeleportButton().setFocusTraversable(false);
		
		moveView.getWaitForRobotsButton().setOnMouseClicked(e -> {
			playerWaitsForRobots();
		});
		moveView.getWaitForRobotsButton().setFocusTraversable(false);
	}

	public void setKeyInput()
	{
		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
				case S:
					movePlayerAndUpdate(Player.MOVE, 0, -1);
					break;
				case W:
					movePlayerAndUpdate(Player.MOVE, -1, -1);
					break;
				case E:
					movePlayerAndUpdate(Player.MOVE, -1, 0);
					break;
				case R:
					movePlayerAndUpdate(Player.MOVE, -1, 1);
					break;
				case F:
					movePlayerAndUpdate(Player.MOVE, 0, 1);
					break;
				case C:
					movePlayerAndUpdate(Player.MOVE, 1, 1);
					break;
				case X:
					movePlayerAndUpdate(Player.MOVE, 1, 0);
					break;
				case Z:
					movePlayerAndUpdate(Player.MOVE, 1, -1);
					break;
				case D:
					movePlayerAndUpdate(Player.STAY, 0, 0);
					break;
				case SPACE:
					movePlayerAndUpdate(Player.TELEPORT, 0, 0);
					break;
				default:
			}
		});
	}

	public void displayDirection()
	{
		gameView.setOnMouseMoved(e -> {
			gameView.setCursor(Cursor.NONE);

			double x = e.getX();
			double y = e.getY();

			gameView.displayArrow(x, y);

		});
	}

	public void setMouseInput()
	{
		gameView.setOnMouseClicked(e -> {
			gameView.setCursor(Cursor.NONE);

			double x = e.getX();
			double y = e.getY();

			String direction = gameView.displayArrow(x, y);

			switch (direction) {
				case "W":
					movePlayerAndUpdate(Player.MOVE, 0, -1);
					break;
				case "NW":
					movePlayerAndUpdate(Player.MOVE, -1, -1);
					break;
				case "N":
					movePlayerAndUpdate(Player.MOVE, -1, 0);
					break;
				case "NE":
					movePlayerAndUpdate(Player.MOVE, -1, 1);
					break;
				case "E":
					movePlayerAndUpdate(Player.MOVE, 0, 1);
					break;
				case "SE":
					movePlayerAndUpdate(Player.MOVE, 1, 1);
					break;
				case "S":
					movePlayerAndUpdate(Player.MOVE, 1, 0);
					break;
				case "SW":
					movePlayerAndUpdate(Player.MOVE, 1, -1);
					break;
				case "STAY":
					movePlayerAndUpdate(Player.STAY, 0, 0);
					break;
				default:
			}
		});
	}

	public void updateGameState()
	{
		if (board.getState() == GameBoard.LOSE) {
			if (timeline != null) {
				timeline.stop();
			}
			
			scene.setOnKeyPressed(null);
			gameView.setOnMouseClicked(null);
			moveView.getTeleportButton().setOnMouseClicked(null);
			moveView.getSafeTeleportButton().setOnMouseClicked(null);
			moveView.getWaitForRobotsButton().setOnMouseClicked(null);
		}

		if (board.getState() == GameBoard.WIN) {
			if (timeline != null) {
				timeline.stop();
			}
			board.nextLevel();
		}
	}

	public Stage getStage()
	{
		return stage;
	}
}
