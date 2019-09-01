import Constants.Console;
import Constants.GameType;
import Constants.GoalType;

public class Goal {
	
	private String gameName;
	private Console console;
	private GameType gameType;
	
	private String goalName;
	private String description;
	private GoalType goalType;
	private String timeEndsWhen;
	
	public Goal(String gameName, String console, String gameType, String goalName, String description, String goalType, String timeEndsWhen) {
		this.gameName = gameName;
		this.gameType = GameType.valueOf(gameType);
		this.console = Console.valueOf(console);
		this.goalName = goalName;
		this.description = description;
		this.goalType = GoalType.valueOf(goalType);
		this.timeEndsWhen = timeEndsWhen;
	}

	public String getGoalName() {
		return goalName;
	}

	public void setGoalName(String goalName) {
		this.goalName = goalName;
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
		return gameType;
	}

	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}

	@Override
	public String toString() {
		return "Goal [gameName=" + gameName + ", goalName=" + goalName + "]";
	}
	
}
