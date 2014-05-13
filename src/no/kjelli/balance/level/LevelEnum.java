package no.kjelli.balance.level;

public enum LevelEnum {
	easy("res\\levels\\easy.level"), medium("res\\levels\\medium.level"), hard(
			"res\\levels\\hard.level");

	String filePath;

	LevelEnum(String filepath) {
		this.filePath = filepath;
	}

	public String getFilepath() {
		return filePath;
	}
}
