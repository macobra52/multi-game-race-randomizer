package Constants;

public enum GoalType {
	FAST_ACHIEVEMENT
	("Achievement based goals that generally do not take long to complete. (15 minutes or less for someone familiar with the game)"),
	LONGER_ACHIEVEMENT
	("Longer achievement based goals (more than 15 minutes for someone familiar with the game)"),
	FAST_SPEEDRUN
	("Speedrunning goals that do not take long to complete. (15 minutes or less for someone familiar with the game)"),
	LONGER_SPEEDRUN
	("Longer speedrunning goals. (more than 15 minutes for someone familiar with the game)");
	
	private String description;
	
	private GoalType(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
}