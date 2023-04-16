package view;

import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import theme.Theme;

public class RestartView
{
	private Dialog<ButtonType> restartDialog;
	private Theme theme;

	public RestartView(Theme theme)
	{
		this.theme = theme;
		
		restartDialog = new Dialog<>();
		restartDialog.setTitle("Restart?");
		restartDialog.setHeaderText("Restart?");
		restartDialog.setGraphic(new ImageView(this.theme.getPlayerImage()));

		ButtonType restartButton = new ButtonType("Yes", ButtonData.YES);
		ButtonType quitButton = new ButtonType("Quit", ButtonData.NO);
		restartDialog.getDialogPane().getButtonTypes().addAll(restartButton, quitButton);
		restartDialog.setResultConverter(buttonType -> {
			return buttonType;
		});
	}
	
	public Dialog<ButtonType> getDialog()
	{
		return restartDialog;
	}
}
