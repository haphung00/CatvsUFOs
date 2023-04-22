package theme;

import javafx.scene.image.Image;

public class CatvsUFOsTheme extends Theme
{
	private static final String CAT = "/res/cat.png";
	private static final String ANGEL_CAT = "/res/angelcat.png";
	private static final String ROBOT = "/res/robot.png";
	private static final String HEAD_ROBOT = "/res/headrobot.png";
	private static final String RUBBLE = "/res/rubble.png";
	
	@Override
	public Image getPlayerImage()
	{
		return getImage(CAT);
	}

	@Override
	public Image getLostPlayerImage()
	{
		return getImage(ANGEL_CAT);
	}

	@Override
	public Image getRobotImage()
	{
		return getImage(ROBOT);
	}

	@Override
	public Image getHeadRobotImage()
	{
		return getImage(HEAD_ROBOT);
	}

	@Override
	public Image getRubbleImage()
	{
		return getImage(RUBBLE);
	}
}
