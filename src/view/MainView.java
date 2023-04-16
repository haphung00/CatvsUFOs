package view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.GameBoard;
import theme.Theme;

public class MainView
{
	private Stage stage;
	private Scene scene;
	private BorderPane pane;
	private GameView gameView;
	private FunctionView functionView;
	private MoveView moveView;

	public MainView(Theme theme)
	{		
		pane = new BorderPane();

		gameView = new GameView(theme);		
		functionView = new FunctionView();
		moveView = new MoveView();

		pane.setTop(functionView);
		pane.setCenter(gameView);
		pane.setBottom(moveView);

		scene = new Scene(pane);
		stage = new Stage();

		stage.setTitle("Cats vs UFOs");
		stage.setScene(scene);
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
		return gameView.getGameBoard();
	}
}
