package view;

import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import theme.Theme;

public class UserNameView
{
	private TextInputDialog dialog;
	
	public UserNameView(Theme theme)
	{	
		dialog = new TextInputDialog();
		
		dialog.setTitle("Username");
		dialog.setContentText("Enter your username:");
		dialog.setHeaderText("Cat vs UFOs");
		dialog.setGraphic(new ImageView(theme.getPlayerImage()));
	}
	
	public TextInputDialog getDialog()
	{
		return dialog;
	}
}
