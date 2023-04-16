package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.GameBoard;

public class InstructionView
{
	private static final int WIDTH = 300;
	private static final int HEIGHT = 200;
	private Stage stage;
	private Scene scene;
	private ScrollPane pane;
	private VBox contentPane;	

	private Font titleFont = Font.font("Consolas", FontWeight.EXTRA_BOLD, 16);
	private Font headerFont = Font.font("Consolas", FontWeight.BOLD, 14);	
	private Font textFont = Font.font("Consolas", 12);

	public InstructionView()
	{
		constructPane();
		
		scene = new Scene(pane);
		stage = new Stage();
		stage.setTitle("Instruction");
		stage.setScene(scene);
		stage.setResizable(false);
	}

	public void show()
	{
		stage.show();
		stage.setAlwaysOnTop(true);
	}
	
	private void constructPane()
	{
		constructContentPane();
		
		pane = new ScrollPane();
		pane.setPrefViewportWidth(WIDTH);
		pane.setPrefViewportHeight(HEIGHT);
		
		pane.setContent(contentPane);
		pane.setHbarPolicy(ScrollBarPolicy.NEVER);
		pane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		pane.setPrefViewportWidth(WIDTH);
		pane.setBackground(new Background(new BackgroundFill(
				Color.ALICEBLUE, CornerRadii.EMPTY, new Insets(0))));
	}
	
	private void constructContentPane()
	{
		contentPane = new VBox();;
		contentPane.setPadding(new Insets(5));
		contentPane.setSpacing(8);
		
		Text header = new Text("INSTRUCTION");
		header.setFont(titleFont);

		VBox overallPane = overallPane();
		VBox keyInstructionPane = keyInstructionPane();
		VBox scorePane = scoreInstructionPane();
		VBox specialInstructionPane = specialInstructionPane();

		contentPane.getChildren().addAll(header, 
				overallPane,
				keyInstructionPane,
				scorePane, 
				specialInstructionPane);
	}

	private VBox keyInstructionPane()
	{
		VBox keyPane = new VBox();
		keyPane.setSpacing(3);
		keyPane.setPrefWidth(WIDTH);;
		
		Text keyInstruction = new Text("Keyboard Instruction:");
		keyInstruction.setFont(headerFont);
		
		Text keys = new Text("W: North West\n"
				+ "E: North\n"
				+ "R: North East\n"
				+ "F: East\n"
				+ "C: South East\n"
				+ "X: South\n"
				+ "Z: South West\n"
				+ "S: West\n"
				+ "D: Stay\n"
				+ "Space: Teleport");
		keys.setFont(textFont);
		
		keyPane.getChildren().addAll(keyInstruction, keys);
		
		return keyPane;
	}
	
	private VBox overallPane()
	{
		VBox pane = new VBox();
		
		Text header = new Text("Rules:");
		header.setFont(headerFont);
		
		Text rules = new Text("The player moves in order"
				+ "to make the robots crashed.\n"
				+ "Some robots can move at most 1 step, "
				+ "and some can move more than 1 at a time.");
		rules.setFont(textFont);
		rules.setWrappingWidth(WIDTH);
		
		pane.getChildren().addAll(header, rules);
		
		return pane;
	}
	
	private VBox scoreInstructionPane()
	{
		VBox scorePane = new VBox();
		scorePane.setSpacing(3);;
		scorePane.setPrefWidth(WIDTH);
		
		Text scoring = new Text("Scoring:");
		scoring.setFont(headerFont);
		
		Text rules = new Text("For each robot destroyed, "
				+ "the player gains " + GameBoard.SCORE_UNIT
				+ " scores.\n"
				+ "However, if the player chooses "
				+ "to play in Safe Mode, each robot destroyed "
				+ "only results in " + GameBoard.SAFE_SCORE_UNIT
				+ " scores.");
		rules.setFont(textFont);
		rules.setWrappingWidth(WIDTH);
		
		scorePane.getChildren().addAll(scoring, rules);
		
		return scorePane;
	}
	
	private VBox specialInstructionPane()
	{
		VBox pane = new VBox();
		pane.setSpacing(3);
		
		Text header = new Text("Special features: ");
		header.setFont(headerFont);
		
		Text features = new Text("1. Safe Mode: Ensures the player "
				+ "never dies when moving, unless "
				+ "teleporting and wait for robots.\n"
				+ "2. Safe Teleport: Allows the player to move to "
				+ "a random position without the risk of "
				+ "instantly losing.\n"
				+ "3. Wait for Robots: Lets the player stand "
				+ "at the current position, and asks all robots "
				+ "to move towards the player till the game "
				+ "ends.");
		features.setFont(textFont);
		features.setWrappingWidth(WIDTH);
		
		pane.getChildren().addAll(header, features);
		
		return pane;
	}
}
