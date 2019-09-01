package Constants;

public enum GameType {
	VANILLA("The base/original game without any modifications"),
	ROMHACK("An unofficial game with one or more modifications to the original"),
	RANDOMIZER("A game generated through an external randomizer");
	
	private String description;
	
	private GameType(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
}
