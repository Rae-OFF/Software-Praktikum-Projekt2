package model;

import java.util.List;

public class Move {

	private CardStack discardPile;

	private CardStack cardPile;

	private PlayerState actor;

	private boolean phase1;

	private PlayerState activePlayer;

	private Card shipToDefend;

	private Card card;

	private List<PlayerState> playerState;

	private CardStack cardStack;

	private Action action;

	public CardStack getDiscardPile() {
		return discardPile;
	}

	public void setDiscardPile(CardStack discardPile) {
		this.discardPile = discardPile;
	}

	public CardStack getCardPile() {
		return cardPile;
	}

	public void setCardPile(CardStack cardPile) {
		this.cardPile = cardPile;
	}

	public PlayerState getActor() {
		return actor;
	}

	public void setActor(PlayerState actor) {
		this.actor = actor;
	}

	public boolean isPhase1() {
		return phase1;
	}

	public void setPhase1(boolean phase1) {
		this.phase1 = phase1;
	}

	public PlayerState getActivePlayer() {
		return activePlayer;
	}

	public void setActivePlayer(PlayerState activePlayer) {
		this.activePlayer = activePlayer;
	}

	public Card getShipToDefend() {
		return shipToDefend;
	}

	public void setShipToDefend(Card shipToDefend) {
		this.shipToDefend = shipToDefend;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public List<PlayerState> getPlayerState() {
		return playerState;
	}

	public void setPlayerState(List<PlayerState> playerState) {
		this.playerState = playerState;
	}

	public CardStack getCardStack() {
		return cardStack;
	}

	public void setCardStack(CardStack cardStack) {
		this.cardStack = cardStack;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
}
