package theme;

import javafx.scene.image.Image;

public abstract class ThemeImage
{
	private static final String ARROW = "file:res/arrow.png";
	
	public abstract Image getPlayerImage();
	
	public abstract Image getLostPlayerImage();
	
	public abstract Image getRobotImage();
	
	public abstract Image getHeadRobotImage();
	
	public abstract Image getRubbleImage();
	
	public Image getArrowImage()
	{
		return new Image(ARROW);
	}
}
