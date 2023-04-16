package view;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.GameResult;
import model.LeaderBoard;

public class RankingView
{
	private LeaderBoard leaderBoard;
	private Stage stage;
	private Scene scene;
	private TableView<GameResult> table;
	
	public RankingView()
	{
		leaderBoard = new LeaderBoard();
		initiate();
	}
	
	public LeaderBoard getLeaderBoard()
	{
		return leaderBoard;
	}
	
	@SuppressWarnings("unchecked")
	private void createTable()
	{
		table = new TableView<>();
		
		TableColumn<GameResult, String> nameColumn = new TableColumn<>("User Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
		
		TableColumn<GameResult, Integer> levelColumn = new TableColumn<>("Level");
		levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
		
		TableColumn<GameResult, Integer> scoreColumn = new TableColumn<>("Total Score");
		scoreColumn.setCellValueFactory(new PropertyValueFactory<>("totalScore"));
		
		table.getColumns().addAll(nameColumn, levelColumn, scoreColumn);
		table.setItems(FXCollections.observableList(leaderBoard.getResults()));
	}
	
	private void initiate()
	{
		createTable();
		
		VBox pane = new VBox(table);
		scene = new Scene(pane);
		
		stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Leaderboard");
	}
	
	public void show()
	{
		stage.show();
	}
	
	public Stage getStage()
	{
		return stage;
	}
}
