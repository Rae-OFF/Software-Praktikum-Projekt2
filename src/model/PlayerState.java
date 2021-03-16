package model;

public class PlayerState {


	private CardStack cards;

	private CardStack coins;

	private Player player;

	public PlayerState(PlayerState skeleton) {

		this(skeleton.getPlayer());
		this.cards = new CardStack(skeleton.getCards());
		this.coins = new CardStack(skeleton.getCoins());

	}

	public PlayerState(Player player) {
		this.player = player;
		this.cards = new CardStack();
	}

	public CardStack getCards() {
		return cards;
	}

	public CardStack getCoins() {
		return coins;
	}

	public void setCoins(CardStack coins) {
		this.coins = coins;
	}

	public void setCards(CardStack cards) {
		this.cards = cards;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
