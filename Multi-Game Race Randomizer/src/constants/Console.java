package constants;


public enum Console {
	DS("Nintendo DS"),
	GAMECUBE("Nintendo GameCube"),
	GB("Nintendo Game Boy"),
	GBA("Nintendo Game Boy Advance"),
	GBC("Nintendo Game Boy Color"),
	GENESIS("Sega Genesis"),
	N64("Nintendo 64"),
	NES("Nintendo Entertainment System"),
	PC("Windows"),
	PLAYSTATION("Sony PlayStation"),
	PLAYSTATION2("Sony PlayStation 2"),
	PSP("Sony PlayStation Portable"),
	SNES("Super Nintendo"),
	WII("Nintendo Wii");
	
	private String description;
	
	private Console(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
}
