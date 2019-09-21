package objects;
import java.io.Serializable;

import constants.Console;
import constants.GameType;

public class Game implements Comparable<Game>, Serializable {

	private static final long serialVersionUID = 1L;
	
	private String gameName;
	private Console console;
	private GameType gameType;
	
	public Game(String gameName, String console, String gameType) {
		this.gameName = gameName;
		this.console = Console.valueOf(console);
		this.gameType = GameType.valueOf(gameType);
	}
	
	public Game(String gameName, Console console, GameType gameType) {
		this.gameName = gameName;
		this.console = console;
		this.gameType = gameType;
	}
	
	//Default constructors
	public Game() {
		// TODO Auto-generated constructor stub
	}
	
	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public Console getConsole() {
		return console;
	}

	public void setConsole(Console console) {
		this.console = console;
	}

	public GameType getGameType() {
		return gameType;
	}

	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}


	@Override
	public int compareTo(Game game) {
		return this.getGameName().compareTo(game.getGameName());
	}

	@Override
	public String toString() {
		return gameName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gameName == null) ? 0 : gameName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (gameName == null) {
			if (other.gameName != null)
				return false;
		} else if (!gameName.equals(other.gameName))
			return false;
		return true;
	}

	
	
	
}
