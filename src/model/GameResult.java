package model;

import java.io.Serializable;

public class GameResult implements Serializable, Comparable<GameResult>
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

	// this method is poorly written. fix!
	@Override
	public int compareTo(GameResult otherResult)
	{
		if (otherResult.level > this.level) {
			return 1;
		}
		else if (otherResult.level < this.level) {
			return -1;
		}
		else {
			if (otherResult.totalScore > this.totalScore) {
				return 1;
			}
			else if (otherResult.totalScore < this.totalScore) {
				return -1;
			}
			else {
				return 0;
			}
		}
	}
}
