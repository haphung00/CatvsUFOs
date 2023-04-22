package theme;

import java.io.InputStream;

import javafx.scene.image.Image;

public abstract class Theme
{
	private static final String ARROW = "/res/arrow.png";
	
	private static final String LEFT_WIN = "/res/leftwin.png";
	private static final String RIGHT_WIN = "/res/rightwin.png";
	private static final String LEFT_LOSE = "/res/leftlose.png";
	private static final String RIGHT_LOSE = "/res/rightlose.png";

	public abstract Image getPlayerImage();
	
	public abstract Image getLostPlayerImage();
	
	public abstract Image getRobotImage();
	
	public abstract Image getHeadRobotImage();
	
	public abstract Image getRubbleImage();
	
	protected Image getImage(String fileName)
	{
		try {
			InputStream stream = getClass().getResourceAsStream(fileName);
			return new Image(stream);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Image getArrowImage()
	{
		return getImage(ARROW);
	}
	
	public Image getLeftWin()
	{
		return getImage(LEFT_WIN);
	}
	
	public Image getRightWin()
	{
		return getImage(RIGHT_WIN);
	}
	
	public Image getLeftLose()
	{
		return new Image(LEFT_LOSE);
	}
	
	public Image getRightLose()
	{
		return getImage(RIGHT_LOSE);
	}
}
