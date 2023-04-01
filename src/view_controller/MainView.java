package view_controller;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.GameBoard;

public class MainView
{
	Stage stage;
	Scene scene;
	BorderPane pane;
	
	// FunctionView functionView;
	
	GameView gameView;
	MoveView moveView;
	
	GameBoard gameBoard;
	
	public MainView()
	{		
		pane = new BorderPane();
		
		gameBoard = new GameBoard();
		
		gameView = new GameView(gameBoard);		
		moveView = new MoveView();

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
}
