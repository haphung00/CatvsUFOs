package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view_controller.MainView;

public class Main extends Application
{
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		MainView mainView = new MainView();
		primaryStage = mainView.getStage();
		primaryStage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
