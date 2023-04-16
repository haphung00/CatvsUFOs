package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderBoard
{
	private static final String FILE_NAME = "ranking.dat";
	private List<GameResult> leaderBoard;

	@SuppressWarnings("unchecked")
	public LeaderBoard()
	{
		leaderBoard = new ArrayList<>();
		try (ObjectInputStream objectInStream = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
			leaderBoard = (List<GameResult>) objectInStream.readObject();
		}
		catch (ClassNotFoundException | IOException e) {
			// Handle exception
		}
	}

	public void add(GameResult gameResult)
	{
		leaderBoard.add(gameResult);
		Collections.sort(leaderBoard);
		save();
	}

	public void remove(GameResult gameResult)
	{
		leaderBoard.remove(gameResult);
		save();
	}

	public void save()
	{
		try (ObjectOutputStream objectOutStream = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
			objectOutStream.writeObject(leaderBoard);
		}
		catch (IOException e) {
			// Handle exception
		}
	}

	public List<GameResult> getResults()
	{
		return leaderBoard;
	}
}
