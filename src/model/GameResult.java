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

	@Override
	public int hashCode()
	{
		int total = 0;

		for (int i = 0; i < userName.length(); i++) {
			total += userName.charAt(i);
		}

		return 17 * total + 19 * totalScore + 21 * level;
	}

	@Override
	public boolean equals(Object object)
	{
		if (!(object instanceof GameResult)) {
			return false;
		}

		GameResult other = (GameResult) object;

		if (other.level == level && other.totalScore == totalScore) {
			return true;
		}

		return false;
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
