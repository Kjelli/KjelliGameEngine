package no.kjelli.pong.config;


public class PlayerConfig {
	public String name;
	public int upKey, downKey, powKey;
	public int playerNo;

	public PlayerConfig() {
	}

	public PlayerConfig(String name, int upKey, int downKey, int powerKey, int playerNo) {
		this.name = name;
		this.upKey = upKey;
		this.downKey = downKey;
		this.powKey = powerKey;
		this.playerNo = playerNo;
	}

	@Override
	public String toString() {
		return "NAME: " + name +", UP: " + upKey + ", DOWN: " + downKey + ", POWER:" + powKey;
	}

	public boolean isValid() {
		if(name == null || name.isEmpty())
			return false;
		if (upKey == 0 || downKey == 0 || powKey == 0)
			return false;
		if (upKey == downKey || upKey == powKey || downKey == powKey)
			return false;
		return true;
	}
}
