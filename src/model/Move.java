package model;

import java.util.ArrayList;
import java.util.List;

public class Move {

	private CardStack discardPile;

	private CardStack cardPile;

	private PlayerState actor;

	private boolean phase1;

	private PlayerState activePlayer;

	private Card shipToDefend;

	private List<PlayerState> playerState;

	private CardStack harbour;

	private Action action;

	public Move(PlayerState actor, boolean phase1, PlayerState activePlayer, Action action) {
		this.actor = actor;
		this.phase1 = phase1;
		this.activePlayer = activePlayer;
		this.action = action;
		this.playerState = new ArrayList<>();
		this.harbour = new CardStack();
		this.cardPile = new CardStack();
		this.discardPile = new CardStack();


	}

	public Move(Move skeleton) {

		this.actor = skeleton.getActor();
		this.phase1 = skeleton.isPhase1();
		this.activePlayer = skeleton.getActivePlayer();
		this.harbour = new CardStack(skeleton.getHarbour());
		this.cardPile = new CardStack(skeleton.getCardPile());
		this.discardPile = new CardStack(skeleton.getDiscardPile());
		this.shipToDefend = skeleton.getShipToDefend();
		this.playerState = new ArrayList<>();
		this.action = skeleton.getAction();

		for(PlayerState playerState: skeleton.getPlayerState()){

			this.playerState.add(new PlayerState(playerState));
		}


	}

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


	public List<PlayerState> getPlayerState() {
		return playerState;
	}

	public void setPlayerState(List<PlayerState> playerState) {
		this.playerState = playerState;
	}

	public CardStack getHarbour() {
		return harbour;
	}

	public void setHarbour(CardStack harbour) {
		this.harbour = harbour;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
}
