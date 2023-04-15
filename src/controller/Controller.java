package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.GameBoard;
import model.Player;
import view.FunctionView;
import view.GameView;
import view.MainStage;
import view.MoveView;
import view.RestartView;

public class Controller
{
	private MainStage mainView;
	private FunctionView functionView;
	private MoveView moveView;
	private GameView gameView;
	private RestartView restartView;
	private GameBoard board;

	private Stage stage;
	private Scene scene;

	private Timeline waitForRobotsTimeline;
	private Button buttonSetOnSafeMove;

	private int moveType;

	public Controller()
	{
		mainView = new MainStage();
		initiate();
	}

	private void initiate()
	{
		functionView = mainView.getFunctionView();
		moveView = mainView.getMoveView();
		gameView = mainView.getGameView();
		restartView = new RestartView();
		board = mainView.getGameBoard();

		stage = mainView.getStage();
		scene = mainView.getScene();

		setUpHandler();
		moveView.updateSafeTeleportButton(board.getSafeTeleportTimes());
	}

	private void setUpHandler()
	{
		setKeyInput();
		setMouseInput();
		setUpMoveViewButton();
		displayDirection();
		setUpFunctionView();
	}

	private void movePlayerAndUpdate(int typeOfMovement, int rowStep, int colStep)
	{
		if (board.movePlayer(typeOfMovement, rowStep, colStep)) {
			updateGameState();
			board.updateRobots();
			functionView.updateStatistics(board.getScore(), board.getLevel());
			updateGameState();
			gameView.render();
		}
//		functionView.updateStatistics(board.getScore(), board.getLevel());
	}

	private void playerWaitsForRobots()
	{
		waitForRobotsTimeline = new Timeline(new KeyFrame(Duration.seconds(0.25), e -> {
			if (board.getState() == GameBoard.CONTINUING) {
				movePlayerAndUpdate(Player.MOVE, 0, 0);
				gameView.render();
			}
		}));
		waitForRobotsTimeline.setCycleCount(Timeline.INDEFINITE);
		waitForRobotsTimeline.play();
	}

	private void setUpMoveViewButton()
	{
		moveView.getTeleportButton().setOnMouseClicked(e -> {
			movePlayerAndUpdate(Player.TELEPORT, 0, 0);
		});
		moveView.getTeleportButton().setFocusTraversable(false);

		moveView.getSafeTeleportButton().setOnMouseClicked(e -> {
			if (board.getSafeTeleportTimes() > 0) {
				movePlayerAndUpdate(Player.SAFE_TELEPORT, 0, 0);
				board.decreaseSafeTeleportTimes();
				moveView.updateSafeTeleportButton(board.getSafeTeleportTimes());
			}
		});
		moveView.getSafeTeleportButton().setFocusTraversable(false);

		moveView.getWaitForRobotsButton().setOnMouseClicked(e -> {
			playerWaitsForRobots();
		});
		moveView.getWaitForRobotsButton().setFocusTraversable(false);
	}

	private void setKeyInput()
	{
		updateMoveType();

		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
				case S:
					movePlayerAndUpdate(moveType, 0, -1);
					break;
				case W:
					movePlayerAndUpdate(moveType, -1, -1);
					break;
				case E:
					movePlayerAndUpdate(moveType, -1, 0);
					break;
				case R:
					movePlayerAndUpdate(moveType, -1, 1);
					break;
				case F:
					movePlayerAndUpdate(moveType, 0, 1);
					break;
				case C:
					movePlayerAndUpdate(moveType, 1, 1);
					break;
				case X:
					movePlayerAndUpdate(moveType, 1, 0);
					break;
				case Z:
					movePlayerAndUpdate(moveType, 1, -1);
					break;
				case D:
					movePlayerAndUpdate(moveType, 0, 0);
					break;
				case SPACE:
					movePlayerAndUpdate(Player.TELEPORT, 0, 0);
					break;
				default:
			}
		});
	}

	private void displayDirection()
	{
		gameView.setOnMouseMoved(e -> {
			gameView.setCursor(Cursor.NONE);

			double x = e.getX();
			double y = e.getY();

			gameView.displayArrow(x, y);
		});
	}

	private void updateMoveType()
	{
		moveType = Player.MOVE;

		if (board.getPlayer().getIsOnSafeMode()) {
			moveType = Player.SAFE_MOVE;
		}
	}

	private void setMouseInput()
	{
		gameView.setOnMouseClicked(e -> {

			gameView.setCursor(Cursor.NONE);

			double x = e.getX();
			double y = e.getY();

			String direction = gameView.displayArrow(x, y);

			switch (direction) {
				case "W":
					movePlayerAndUpdate(moveType, 0, -1);
					break;
				case "NW":
					movePlayerAndUpdate(moveType, -1, -1);
					break;
				case "N":
					movePlayerAndUpdate(moveType, -1, 0);
					break;
				case "NE":
					movePlayerAndUpdate(moveType, -1, 1);
					break;
				case "E":
					movePlayerAndUpdate(moveType, 0, 1);
					break;
				case "SE":
					movePlayerAndUpdate(moveType, 1, 1);
					break;
				case "S":
					movePlayerAndUpdate(moveType, 1, 0);
					break;
				case "SW":
					movePlayerAndUpdate(moveType, 1, -1);
					break;
				case "STAY":
					movePlayerAndUpdate(moveType, 0, 0);
					break;
				default:
			}
		});
	}

	private void updateGameState()
	{
		if (board.getState() != GameBoard.CONTINUING) {
			if (waitForRobotsTimeline != null) {
				waitForRobotsTimeline.stop();
			}
		}

		if (board.getState() == GameBoard.LOSE) {
			scene.setOnKeyPressed(null);
			gameView.setOnMouseClicked(null);
			moveView.getTeleportButton().setOnMouseClicked(null);
			moveView.getSafeTeleportButton().setOnMouseClicked(null);
			moveView.getWaitForRobotsButton().setOnMouseClicked(null);

			new Thread(() -> {
				try {
					Thread.sleep(2000);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				Platform.runLater(() -> {
					if (!restartView.getRestartDialog().isShowing()) {
						restartView.getRestartDialog().showAndWait().ifPresent(buttonType -> {
							if (buttonType.getButtonData() == ButtonData.YES) {
								board.restart();
								initiate();
								gameView.render();
								setUpFunctionView();
								functionView.updateStatistics(board.getScore(), board.getLevel());
							}
							else {
								System.exit(-1);
							}
						});
					}
				});
			}).start();

		}
		else if (board.getState() == GameBoard.WIN) {
			gameView.render();

			new Thread(() -> {
				try {
					Thread.sleep(2000);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				Platform.runLater(() -> {
					board.nextLevel();
					gameView.render();
					functionView.updateStatistics(board.getScore(), board.getLevel());
				});
			}).start();
		}
	}

	public void setUpFunctionView()
	{
		buttonSetOnSafeMove = functionView.getSafeMoveButton();
		buttonSetOnSafeMove.setOnMouseClicked(e -> {
			board.getPlayer().setOnSafeMode(!board.getPlayer().getIsOnSafeMode());
			functionView.updateSafeMoveButton(board.getPlayer().getIsOnSafeMode());
			updateMoveType();
			board.setScoreUnit(board.getPlayer().getIsOnSafeMode());
		});
		functionView.updateStatistics(board.getScore(), board.getLevel());
	}

	public Stage getStage()
	{
		return stage;
	}
}