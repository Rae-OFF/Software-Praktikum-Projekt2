package model;

public class Player {

	private String name;

	private int score;

	private int wins;

	private CardStack cardField;

	private PlayerType playerType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public CardStack getCardField() {
		return cardField;
	}

	public void setCardField(CardStack cardField) {
		this.cardField = cardField;
	}

	public PlayerType getPlayerType() {
		return playerType;
	}

	public void setPlayerType(PlayerType playerType) {
		this.playerType = playerType;
	}
}
