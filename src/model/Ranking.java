package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Ranking
{
	private static int numLog = 0;
	private static final String FILE_NAME = "file:res/ranking.dat";

	private File file;
	private FileOutputStream fileStream;
	private ObjectOutputStream objectStream;

	public Ranking()
	{
		file = new File(FILE_NAME);
		try {
			fileStream = new FileOutputStream(file);
			objectStream = new ObjectOutputStream(fileStream);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void log(GameResult gameResult)
	{
		try {
			objectStream.writeObject(gameResult);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		numLog++;
	}

	public int getLogNumber()
	{
		return numLog;
	}
}
