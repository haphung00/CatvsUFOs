package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
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

	public Controller()
	{
		mainView = new MainView();
		functionView = mainView.getFunctionView();
		moveView = mainView.getMoveView();
		gameView = mainView.getGameView();
		board = mainView.getGameBoard();

		stage = mainView.getStage();
		scene = mainView.getScene();
		
		setKey();
	}

	public void setKey()
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
					movePlayerAndUpdate(Player.MOVE, 0, 0);
					break;
				case SPACE:
					movePlayerAndUpdate(Player.TELEPORT, 0, 0);
					break;
				default:
			}
		});
	}

	public void movePlayerAndUpdate(int typeOfMovement, int rowStep, int colStep)
	{
		board.movePlayer(typeOfMovement, rowStep, colStep);
		board.updateRobots();
		System.out.println("controller " + board.getRobots());
		gameView.render();
	}

	public MainView getMainView()
	{
		return mainView;
	}

	public FunctionView getFunctionView()
	{
		return functionView;
	}

	public MoveView getMoveView()
	{
		return moveView;
	}

	public GameView getGameView()
	{
		return gameView;
	}

	public GameBoard getBoard()
	{
		return board;
	}

	public Stage getStage()
	{
		return stage;
	}
}
