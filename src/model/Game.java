package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasse des jeweiligen Spiels.
 */
public class Game {

	private boolean defaultVariant;

	private PlayerState startPlayer;

	private boolean highscoreEnabled;

	private boolean shuffleCards;

	private boolean ongoing;

	private boolean jesterEnabled;

	private List<Player> players;

	private CardStack initCardPile;

	private List<Move> moves;

	private List<PlayerState> playerStates;

	private Move lastMove;

	/**
	 * Konstruktor für ein Spiel.
	 * @param defaultVariant
	 * 		Bekommt übergeben ob man mit 12 Siegpunkten (true) oder 12 Siegpunkten und 1 Expedition (false) gewinnt.
	 * @param startPlayer
	 * 		Bekommt den Startspieler übergeben, welcher den ersten Zug macht.
	 * @param shuffleCards
	 * 		Bekommt übergeben ob man die Karten mischen darf (true) oder nicht (false).
	 * @param players
	 * 		Bekommt eine Liste der Spieler übergeben welche in diesem Spiel mitspielen.
	 * @param cardStack
	 * 		Bekommt einen initialen Kartenstapel übergeben.
	 * 	Der Konstruktor setzt außerdem:
	 * 		ob das Spiel in die Highscoreliste mit aufgenommen werden darf (inital auf true gesetzt).
	 * 		ob das Spiel schon beendet wurde oder nicht (inital auf true gesetzt).
	 * 		ob die Jesterkarte bereits ausgelöst wurde (initial auf false gesetzt).
	 * 		erzeugt neue Listen für die Züge (moves) und die Spielerzustände (playerState
	 */
	public Game(boolean defaultVariant, PlayerState startPlayer, boolean shuffleCards, List<Player> players, CardStack cardStack) {
		this.defaultVariant = defaultVariant;
		this.startPlayer = startPlayer;
		this.shuffleCards = shuffleCards;
		this.players = players;
		this.initCardPile = cardStack;
		this.highscoreEnabled = true;
		this.ongoing = true;
		this.jesterEnabled = false;
		this.moves = new ArrayList<>();
		this.playerStates = new ArrayList<>();


	}

	/**
	 *
	 * @return	Gibt den Hafen zurück.
	 */
	public CardStack getHarbour() {
		return null;
	}

	/**
	 *
	 * @return	Gibt den Ablegestapel zurück.
	 */
	public CardStack getDiscardPile() {
		return null;
	}

	/**
	 *
	 * @return	Gibt den Nachziehstapel zurück.
	 */
	public CardStack getCardPile() {
		return null;
	}

	public boolean isDefaultVariant() {
		return defaultVariant;
	}

	public void setDefaultVariant(boolean defaultVariant) {
		this.defaultVariant = defaultVariant;
	}

	/**
	 *
	 * @return	Gibt den Startspieler zurück.
	 */
	public PlayerState getStartPlayer() {
		return startPlayer;
	}

	/**
	 * Setzt den Startspieler.
	 * @param startPlayer
	 * 		Bekommt einen Spielerzustand übergeben.
	 */
	public void setStartPlayer(PlayerState startPlayer) {
		this.startPlayer = startPlayer;
	}

	/**
	 *
	 * @return Gibt an ob das Spiel in die Highscoreliste mitaufgenommen werden darf.
	 */
	public boolean isHighscoreEnabled() {
		return highscoreEnabled;
	}

	/**
	 * Setzt ob das Spiel in die Highscoreliste mitaufgenommen werden darf.
	 * @param highscoreEnabled
	 * 		Bekommt übergeben ob es aufgenommen werden darf (true) oder nicht (false).
	 */
	public void setHighscoreEnabled(boolean highscoreEnabled) {
		this.highscoreEnabled = highscoreEnabled;
	}

	/**
	 *
	 * @return Gibt zurück ob der Nachziehstapel gemischt werden darf.
	 */
	public boolean isShuffleCards() {
		return shuffleCards;
	}

	/**
	 * Setzt ob man den Nachziehstapel mischen darf.
	 * @param shuffleCards
	 * 		Bekommt übergeben ob man in mischen darf (true) oder nicht (false).
	 */
	public void setShuffleCards(boolean shuffleCards) {
		this.shuffleCards = shuffleCards;
	}

	/**
	 *
	 * @return Gibt zurück ob das Spiel schon beendet wurde oder noch gespielt wird.
	 */
	public boolean isOngoing() {
		return ongoing;
	}

	/**
	 * Setzt ob das Spiel schon beendet wurde oder noch gespielt wird.
	 * @param ongoing
	 * 		Bekommt übergeben ob es schon beendet wurde (false) oder noch gespielt wird (true).
	 */
	public void setOngoing(boolean ongoing) {
		this.ongoing = ongoing;
	}

	/**
	 *
	 * @return Gibt zurück ob die Jesterkarte in Effekt tritt.
	 */
	public boolean isJesterEnabled() {
		return jesterEnabled;
	}

	/**
	 * Setzt ob die Jesterkarte in Effekt tritt.
	 * @param jesterEnabled
	 * 		Bekommt übergeben ob die Jesterkarte in Effekt tritt (true) oder nicht (false).
	 */
	public void setJesterEnabled(boolean jesterEnabled) {
		this.jesterEnabled = jesterEnabled;
	}

	/**
	 *
	 * @return	Gibt die Liste der Spieler zurück.
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * Setzt die Liste der Spieler.
	 * @param players
	 * 		Bekommt die Liste der Spieler übergeben.
	 */
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	/**
	 *
	 * @return Gibt den initialen Kartenstapel zurück.
	 */
	public CardStack getInitCardPile() {
		return initCardPile;
	}

	/**
	 * Setzt den initialen Kartenstapel.
	 * @param cardStack
	 * 		Bekommt einen Kartenstapel übergeben.
	 */
	public void setInitCardPile(CardStack cardStack) {
		this.initCardPile = cardStack;
	}

	/**
	 *
	 * @return	Gibt die Liste der Spielerzustände zurück.
	 */
	public List<PlayerState> getPlayerStates() {
		return playerStates;
	}

	/**
	 * Setzt die Liste der Spielerzustände.
	 * @param playerStates
	 * 		Bekommt eine Liste der Spielerzustände übergeben.
	 */
	public void setPlayerStates(List<PlayerState> playerStates) {
		this.playerStates = playerStates;
	}

	/**
	 *
	 * @return	Gibt die Liste der Züge zurück.
	 */
	public List<Move> getMoves() {
		return moves;
	}

	/**
	 * Setzt den letzten Zug.
	 * @param move
	 * 		Bekommt den Zug übergeben.
	 */
	public void setLastMove(Move move) {
		this.lastMove = move;
	}

	/**
	 * Setzt die Liste aller Züge.
	 * @param move
	 * 		Bekommt eine Liste von Zügen.
	 */
	public void setMoves(List<Move> move) {
		this.moves = move;
	}
}
