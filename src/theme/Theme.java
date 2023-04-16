package theme;

import javafx.scene.image.Image;

public abstract class Theme
{
	private static final String ARROW = "file:res/arrow.png";
	
	private static final String LEFT_WIN = "file:res/leftwin.png";
	private static final String RIGHT_WIN = "file:res/rightwin.png";
	private static final String LEFT_LOSE = "file:res/leftlose.png";
	private static final String RIGHT_LOSE = "file:res/rightlose.png";
	
	public abstract Image getPlayerImage();
	
	public abstract Image getLostPlayerImage();
	
	public abstract Image getRobotImage();
	
	public abstract Image getHeadRobotImage();
	
	public abstract Image getRubbleImage();
	
	public Image getArrowImage()
	{
		return new Image(ARROW);
	}
	
	public Image getLeftWin()
	{
		return new Image(LEFT_WIN);
	}
	
	public Image getRightWin()
	{
		return new Image(RIGHT_WIN);
	}
	
	public Image getLeftLose()
	{
		return new Image(LEFT_LOSE);
	}
	
	public Image getRightLose()
	{
		return new Image(RIGHT_LOSE);
	}
}
