package model;

import java.util.List;

public class Game {

	private boolean defaultVariant;

	private PlayerState startPlayer;

	private boolean highscoreEnabled;

	private boolean shuffleCards;

	private boolean ongoing;

	private boolean jesterEnabled;

	private List<Player> player;

	private CardStack cardStack;

	private List<Move> move;

	private List<PlayerState> playerState;

	private Move lastMove;

	public CardStack getHarbour() {
		return null;
	}

	public CardStack getDiscardPile() {
		return null;
	}

	public CardStack getCardPile() {
		return null;
	}

	public boolean isDefaultVariant() {
		return defaultVariant;
	}

	public void setDefaultVariant(boolean defaultVariant) {
		this.defaultVariant = defaultVariant;
	}

	public PlayerState getStartPlayer() {
		return startPlayer;
	}

	public void setStartPlayer(PlayerState startPlayer) {
		this.startPlayer = startPlayer;
	}

	public boolean isHighscoreEnabled() {
		return highscoreEnabled;
	}

	public void setHighscoreEnabled(boolean highscoreEnabled) {
		this.highscoreEnabled = highscoreEnabled;
	}

	public boolean isShuffleCards() {
		return shuffleCards;
	}

	public void setShuffleCards(boolean shuffleCards) {
		this.shuffleCards = shuffleCards;
	}

	public boolean isOngoing() {
		return ongoing;
	}

	public void setOngoing(boolean ongoing) {
		this.ongoing = ongoing;
	}

	public boolean isJesterEnabled() {
		return jesterEnabled;
	}

	public void setJesterEnabled(boolean jesterEnabled) {
		this.jesterEnabled = jesterEnabled;
	}

	public List<Player> getPlayer() {
		return player;
	}

	public void setPlayer(List<Player> player) {
		this.player = player;
	}

	public CardStack getCardStack() {
		return cardStack;
	}

	public void setCardStack(CardStack cardStack) {
		this.cardStack = cardStack;
	}

	public List<PlayerState> getPlayerState() {
		return playerState;
	}

	public void setPlayerState(List<PlayerState> playerState) {
		this.playerState = playerState;
	}

	public List<Move> getMove() {
		return move;
	}

	public void setLastMove(Move move) {
		this.lastMove = move;
	}

	public void setMove(List<Move> move) {
		this.move = move;
	}
}
