import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBox;

import Constants.Console;
import Constants.GameType;

public class Game extends JCheckBox implements Comparable<Game> {

	private static final long serialVersionUID = 1L;
	
	private String gameName;
	private Console console;
	private GameType gameType;
	
	public Game(String gameName, String console, String gameType) {
		super(gameName);
		this.gameName = gameName;
		this.console = Console.valueOf(console);
		this.gameType = GameType.valueOf(gameType);
	}
	
	//Default constructors
	public Game() {
		// TODO Auto-generated constructor stub
	}

	public Game(Icon icon) {
		super(icon);
		// TODO Auto-generated constructor stub
	}

	public Game(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public Game(Action a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	public Game(Icon icon, boolean selected) {
		super(icon, selected);
		// TODO Auto-generated constructor stub
	}

	public Game(String text, boolean selected) {
		super(text, selected);
		// TODO Auto-generated constructor stub
	}

	public Game(String text, Icon icon) {
		super(text, icon);
		// TODO Auto-generated constructor stub
	}

	public Game(String text, Icon icon, boolean selected) {
		super(text, icon, selected);
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
	
	
}
