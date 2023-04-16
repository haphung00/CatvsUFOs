package view;

import javafx.scene.control.TextInputDialog;

public class UserNameView
{
	private TextInputDialog dialog;
	
	public UserNameView()
	{	
		dialog = new TextInputDialog();
		
		dialog.setTitle("Username");
		dialog.setContentText("Enter your username:");
	}
	
	public TextInputDialog getDialog()
	{
		return dialog;
	}
}
