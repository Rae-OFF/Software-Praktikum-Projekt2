package model;

import java.util.Map;

public class Expedition extends Card {

	private Map<PersonType, Integer> requirements;

	private int coins;

	private int victoryPoints;

	public Map<PersonType, Integer> getRequirements() {
		return requirements;
	}

	public void setRequirements(Map<PersonType, Integer> requirements) {
		this.requirements = requirements;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public int getVictoryPoints() {
		return victoryPoints;
	}

	public void setVictoryPoints(int victoryPoints) {
		this.victoryPoints = victoryPoints;
	}
}
