package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class FunctionView extends BorderPane
{
	private static final int HEIGHT = 50;
	
	private GridPane infoPane;
	
	private Label scoreLabel;
	private Label levelLabel;
	
	private static Font font = Font.font("Ariel", FontWeight.BOLD, FontPosture.REGULAR, 12);
	
	public FunctionView()
	{
		setHeight(HEIGHT);
		
		infoPane = new GridPane();
		infoPane.setHgap(2);
		infoPane.setVgap(-1);
		
		Label scoreTextLabel = new Label("SCORE:");
		Label levelTextLabel = new Label("LEVEL:");
		scoreTextLabel.setFont(font);
		levelTextLabel.setFont(font);
		
		scoreLabel = new Label("0");
		levelLabel = new Label("1");
		scoreLabel.setFont(font);
		levelLabel.setFont(font);
		
		infoPane.addColumn(0, scoreTextLabel, levelTextLabel);
		infoPane.addColumn(1, scoreLabel, levelLabel);
		
		setCenter(infoPane);
		infoPane.setAlignment(Pos.CENTER);
	}
	
	public void updateStatistics(int score, int level)
	{
		scoreLabel.setText(String.valueOf(score));
		levelLabel.setText(String.valueOf(level));
	}
}
