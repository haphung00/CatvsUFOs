package view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.GameBoard;

public class MainView
{
	Stage stage;
	Scene scene;
	BorderPane pane;
	FunctionView functionView;
	GameView gameView;
	MoveView moveView;

	GameBoard gameBoard;

	public MainView()
	{		
		pane = new BorderPane();

		functionView = new FunctionView();
		gameView = new GameView();		
		moveView = new MoveView();
		
		gameBoard = gameView.getGameBoard();

		pane.setTop(functionView);
		pane.setCenter(gameView);
		pane.setBottom(moveView);

		scene = new Scene(pane);
		stage = new Stage();

		stage.setTitle("Cats vs UFOs");
		stage.setScene(scene);

		gameView.requestFocus();
	}

	public Stage getStage()
	{
		return stage;
	}

	public Scene getScene()
	{
		return scene;
	}

	public BorderPane getPane()
	{
		return pane;
	}

	public FunctionView getFunctionView()
	{
		return functionView;
	}

	public GameView getGameView()
	{
		return gameView;
	}

	public MoveView getMoveView()
	{
		return moveView;
	}

	public GameBoard getGameBoard()
	{
		return gameBoard;
	}
}
