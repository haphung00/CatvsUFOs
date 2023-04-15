package view;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class MoveView extends HBox
{
	Button teleportButton;
	Button safeTeleportButton;
	Button waitForRobotsButton;

	public MoveView()
	{
		teleportButton = new Button("Teleport");
		safeTeleportButton = new Button("Safe Teleport");
		waitForRobotsButton = new Button("Wait For Robots");
		getChildren().addAll(teleportButton, safeTeleportButton, waitForRobotsButton);
		
		HBox.setHgrow(teleportButton, Priority.ALWAYS);
		HBox.setHgrow(safeTeleportButton, Priority.ALWAYS);
		HBox.setHgrow(waitForRobotsButton, Priority.ALWAYS);
		
		teleportButton.setMaxWidth(Double.POSITIVE_INFINITY);
		safeTeleportButton.setMaxWidth(Double.POSITIVE_INFINITY);
		waitForRobotsButton.setMaxWidth(Double.POSITIVE_INFINITY);
	}

	public Button getTeleportButton()
	{
		return teleportButton;
	}

	public Button getSafeTeleportButton()
	{
		return safeTeleportButton;
	}

	public Button getWaitForRobotsButton()
	{
		return waitForRobotsButton;
	}
	
	public void updateSafeTeleportButton(int timesLeft)
	{
		safeTeleportButton.setText("Safe Teleport: " + timesLeft);
	}
}
