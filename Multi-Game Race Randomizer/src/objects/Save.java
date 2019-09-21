package objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Save implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<Goal> goals;
	private Set<Game> gameList;
	
	public Save() {
		goals = new ArrayList<Goal>();
		gameList = new TreeSet<Game>();
	}
	
	public Save(List<Goal> goals, Set<Game> gameList) {
		this.goals = goals;
		this.gameList = gameList;
	}

	public List<Goal> getGoals() {
		return goals;
	}

	public void setGoals(List<Goal> goals) {
		this.goals = goals;
	}

	public Set<Game> getGameList() {
		return gameList;
	}

	public void setGameList(Set<Game> gameList) {
		this.gameList = gameList;
	}
}
