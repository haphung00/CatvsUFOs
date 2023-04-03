package view;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class FunctionView extends BorderPane
{
	private static final int HEIGHT = 50;
	private static final Color SCORE_COLOR = Color.HOTPINK;
	
	private Label scoreLabel;
	
	public FunctionView()
	{
		setHeight(HEIGHT);
		
		scoreLabel = new Label("Score");
		scoreLabel.setTextFill(SCORE_COLOR);
	}
}
