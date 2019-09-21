package objects;
import java.io.Serializable;

import constants.Console;
import constants.GameType;
import constants.GoalType;

public class Goal implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Game game;
	
	private String goalName;
	private String description;
	private GoalType goalType;
	private String timeEndsWhen;
	
	public Goal(Game game, String goalName, String description, String goalType, String timeEndsWhen) {
		this.game = game;
		this.goalName = goalName;
		this.description = description;
		this.goalType = GoalType.valueOf(goalType);
		this.timeEndsWhen = timeEndsWhen;
	}
	
	public Goal(Game game, String goalName, String description, GoalType goalType, String timeEndsWhen) {
		this.game = game;
		this.goalName = goalName;
		this.description = description;
		this.goalType = goalType;
		this.timeEndsWhen = timeEndsWhen;
	}

	public String getGoalName() {
		return goalName;
	}

	public void setGoalName(String goalName) {
		this.goalName = goalName;
	}

	public String getGameName() {
		return game.getGameName();
	}


	public Console getConsole() {
		return game.getConsole();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public GoalType getGoalType() {
		return goalType;
	}

	public void setGoalType(GoalType goalType) {
		this.goalType = goalType;
	}

	public String getTimeEndsWhen() {
		return timeEndsWhen;
	}

	public void setTimeEndsWhen(String timeEndsWhen) {
		this.timeEndsWhen = timeEndsWhen;
	}

	public GameType getGameType() {
		return game.getGameType();
	}
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public String toString() {
		return "Goal [gameName=" + game.getGameName() + ", goalName=" + goalName + "]";
	}
	
	public String[] toArray() {
		return new String[]{game.getGameName(), game.getConsole().name(), game.getGameType().name(), goalName, goalType.name(), description, timeEndsWhen};
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((game == null) ? 0 : game.hashCode());
		result = prime * result + ((goalName == null) ? 0 : goalName.hashCode());
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
		Goal other = (Goal) obj;
		if (game == null) {
			if (other.game != null)
				return false;
		} else if (!game.equals(other.game))
			return false;
		if (goalName == null) {
			if (other.goalName != null)
				return false;
		} else if (!goalName.equals(other.goalName))
			return false;
		return true;
	}
	
}
