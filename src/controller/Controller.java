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
import model.GameResult;
import model.LeaderBoard;
import model.Player;
import theme.CatvsUFOsTheme;
import theme.Theme;
import view.FunctionView;
import view.GameView;
import view.InstructionView;
import view.MainView;
import view.MoveView;
import view.RankingView;
import view.RestartView;
import view.UserNameView;

public class Controller
{
	private InstructionView instructionView;
	private RankingView rankingView;
	private UserNameView userNameView;

	private LeaderBoard leaderBoard;

	private MainView mainView;
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
	private String userName;

	private Theme theme;

	public Controller()
	{
		theme = new CatvsUFOsTheme();

		instructionView = new InstructionView();
		userNameView = new UserNameView(theme);
		rankingView = new RankingView();
		leaderBoard = rankingView.getLeaderBoard();
		restartView = new RestartView(theme);

		mainView = new MainView(theme);
		initiate();
	}

	private void initiate()
	{
		askForUserName();
		instructionView.show();

		functionView = mainView.getFunctionView();
		moveView = mainView.getMoveView();
		gameView = mainView.getGameView();
		board = mainView.getGameBoard();

		stage = mainView.getStage();
		scene = mainView.getScene();

		setUpHandler();

		functionView.updateStatistics(board.getScore(), board.getLevel());
		moveView.updateSafeTeleportButton(board.getSafeTeleportTimes());

		gameView.render();
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
					Thread.sleep(1000);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				Platform.runLater(() -> {
					logGameResult();
					displayLeaderBoard();
				});
			}).start();

		}
		else if (board.getState() == GameBoard.WIN) {
			gameView.render();

			new Thread(() -> {
				try {
					Thread.sleep(1000);
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

	private void displayRestartView()
	{
		if (!restartView.getDialog().isShowing()) {
			restartView.getDialog().showAndWait().ifPresent(buttonType -> {
				if (buttonType.getButtonData() == ButtonData.YES) {
					board.restart();
					initiate();
					gameView.render();
				}
				else {
					System.exit(-1);
				}
			});
		}

	}

	private void displayLeaderBoard()
	{
		rankingView.update();

		if (!rankingView.getStage().isShowing()) {
			rankingView.getStage().showAndWait();
			if (!rankingView.getStage().isShowing()) {
				displayRestartView();
			}
		}
	}

	private void logGameResult()
	{
		GameResult result = new GameResult(userName, board.getLevel(), board.getScore());
		leaderBoard.add(result);
	}

	private void askForUserName()
	{
		userNameView.getDialog().showAndWait().ifPresent(e -> {
			userName = userNameView.getDialog().getResult();
		});
	}

	private void setUpFunctionView()
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