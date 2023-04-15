package model;

import java.io.Serializable;

public class GameResult implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String userName;
	private int level;
	private int totalScore;
	
	public GameResult(String userName, int level, int totalScore)
	{
		this.userName = userName;
		this.level = level;
		this.totalScore = totalScore;
	}

	public String getUserName()
	{
		return userName;
	}

	public int getLevel()
	{
		return level;
	}

	public int getTotalScore()
	{
		return totalScore;
	}
}
