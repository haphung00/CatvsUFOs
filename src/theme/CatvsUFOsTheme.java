package theme;

import javafx.scene.image.Image;

public class CatvsUFOsTheme extends Theme
{
	private static final String CAT = "file:res/cat.png";
	private static final String ANGEL_CAT = "file:res/angelcat.png";
	private static final String ROBOT = "file:res/robot.png";
	private static final String HEAD_ROBOT = "file:res/headrobot.png";
	private static final String RUBBLE = "file:res/rubble.png";
	
	@Override
	public Image getPlayerImage()
	{
		return new Image(CAT);
	}

	@Override
	public Image getLostPlayerImage()
	{
		return new Image(ANGEL_CAT);
	}

	@Override
	public Image getRobotImage()
	{
		return new Image(ROBOT);
	}

	@Override
	public Image getHeadRobotImage()
	{
		return new Image(HEAD_ROBOT);
	}

	@Override
	public Image getRubbleImage()
	{
		return new Image(RUBBLE);
	}
}
