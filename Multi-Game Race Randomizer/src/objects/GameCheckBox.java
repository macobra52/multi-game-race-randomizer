package objects;
import java.io.Serializable;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBox;

import constants.Console;
import constants.GameType;

public class GameCheckBox extends JCheckBox implements Comparable<GameCheckBox>, Serializable {

	private static final long serialVersionUID = 1L;
	
	private Game game;
	
	public GameCheckBox(Game game) {
		super(game.getGameName());
		this.game = game;
	}
	
	//Default constructors
	public GameCheckBox() {
		// TODO Auto-generated constructor stub
	}

	public GameCheckBox(Icon icon) {
		super(icon);
		// TODO Auto-generated constructor stub
	}

	public GameCheckBox(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public GameCheckBox(Action a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	public GameCheckBox(Icon icon, boolean selected) {
		super(icon, selected);
		// TODO Auto-generated constructor stub
	}

	public GameCheckBox(String text, boolean selected) {
		super(text, selected);
		// TODO Auto-generated constructor stub
	}

	public GameCheckBox(String text, Icon icon) {
		super(text, icon);
		// TODO Auto-generated constructor stub
	}

	public GameCheckBox(String text, Icon icon, boolean selected) {
		super(text, icon, selected);
		// TODO Auto-generated constructor stub
	}
	
	public Game getGame() {
		return game;
	}
	
	public String getGameName() {
		return game.getGameName();
	}

	public Console getConsole() {
		return game.getConsole();
	}

	public GameType getGameType() {
		return game.getGameType();
	}

	@Override
	public int compareTo(GameCheckBox game) {
		return this.getGameName().compareTo(game.getGameName());
	}

	@Override
	public String toString() {
		return game.getGameName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((game.getGameName() == null) ? 0 : game.getGameName().hashCode());
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
		GameCheckBox other = (GameCheckBox) obj;
		if (game.getGameName() == null) {
			if (other.game.getGameName() != null)
				return false;
		} else if (!game.getGameName().equals(other.game.getGameName()))
			return false;
		return true;
	}

	
	
	
}
