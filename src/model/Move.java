package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasse der Züge.
 */
public class Move {

	private CardStack discardPile;

	private CardStack cardPile;

	private PlayerState actor;

	private boolean phase1;

	private PlayerState activePlayer;

	private Card shipToDefend;

	private List<PlayerState> players;

	private CardStack harbour;

	private Action action;

	private int buyLimit;

	/**
	 * Konstruktor.
	 * @param actor
	 * 		Bekommt den Spielerzustand der gerade am Zug ist.
	 * @param phase1
	 * 		Bekommt übergeben in welcher Phase wir uns befinden.
	 * @param activePlayer
	 * 		Bekommt den Spielerzustand des aktuellen Spielers.
	 * @param action
	 * 		Bekommt eine Aktion.
	 * Der Konstruktor erstellt außerdem:
	 * 		eine neue Liste für die Spielerzustände.
	 * 		neue Kartenstapel für den Hafen, den Nachziehstapel und den Ablegestapel.
	 */
	public Move(PlayerState actor, boolean phase1, PlayerState activePlayer, Action action) {
		this.actor = actor;
		this.phase1 = phase1;
		this.activePlayer = activePlayer;
		this.action = action;
		this.players = new ArrayList<>();
		this.harbour = new CardStack();
		this.cardPile = new CardStack();
		this.discardPile = new CardStack();



	}

	/**
	 * Copy Konstruktor.
	 * @param skeleton
	 * 		Bekommt einen Zug übergeben.
	 * 	Übernimmt alle Informationen des übergebenen Zugs.
	 */
	public Move(Move skeleton) {

		this.actor = skeleton.getActor();
		this.phase1 = skeleton.isPhase1();
		this.activePlayer = skeleton.getActivePlayer();
		this.harbour = new CardStack(skeleton.getHarbour());
		this.cardPile = new CardStack(skeleton.getCardPile());
		this.discardPile = new CardStack(skeleton.getDiscardPile());
		this.shipToDefend = skeleton.getShipToDefend();
		this.players = new ArrayList<>();
		this.action = skeleton.getAction();
		this.buyLimit = skeleton.getBuyLimit();

		for(PlayerState playerState: skeleton.getPlayers()){

			this.players.add(new PlayerState(playerState));
		}
	}

	/**
	 *
	 * @return Gibt den Ablegestapel zurück.
	 */
	public CardStack getDiscardPile() {
		return discardPile;
	}

	/**
	 * Setzt den Ablegestapel.
	 * @param discardPile
	 * 		Bekommt einen Kartenstapel übergeben.
	 */
	public void setDiscardPile(CardStack discardPile) {
		this.discardPile = discardPile;
	}

	/**
	 *
	 * @return Gibt den Nachziehstapel zurück.
	 */
	public CardStack getCardPile() {
		return cardPile;
	}

	/**
	 * Setzt den Nachziehstapel.
	 * @param cardPile
	 * 		Bekommt einen Kartenstapel übergeben.
	 */
	public void setCardPile(CardStack cardPile) {
		this.cardPile = cardPile;
	}

	/**
	 *
	 * @return Gibt den Spieler zurück der aktuell am Zug ist.
	 */
	public PlayerState getActor() {
		return actor;
	}

	/**
	 * Setzt den Spieler der aktuell am Zug ist.
	 * @param actor
	 * 		Bekommt einen Spielerzustand übergeben.
	 */
	public void setActor(PlayerState actor) {
		this.actor = actor;
	}

	/**
	 *
	 * @return Gibt zurück in welcher Phase sich der Zug befindet.
	 */
	public boolean isPhase1() {
		return phase1;
	}

	/**
	 * Setzt in welcher Phase sich der Zug befindet.
	 * @param phase1
	 * 		Bekommt übergeben ob es Phase 1 ist (true) oder Phase 2 (false)
	 */
	public void setPhase1(boolean phase1) {
		this.phase1 = phase1;
	}

	/**
	 *
	 * @return Gibt den aktiven Spieler zurück.
	 */
	public PlayerState getActivePlayer() {
		return activePlayer;
	}

	/**
	 * Setzt den aktiven Spieler.
	 * @param activePlayer
	 * 		Bekommt einen Spielerzustand übergeben.
	 */
	public void setActivePlayer(PlayerState activePlayer) {
		this.activePlayer = activePlayer;
	}

	/**
	 *
	 * @return Gibt die Schiffkarte zurück die abgewehrt werden muss.
	 */
	public Card getShipToDefend() {
		return shipToDefend;
	}

	/**
	 * Setzt die Schiffkarte die abgewehr werden müssen.
	 * @param shipToDefend
	 * 		Bekommt eine (Schiff-)Karte übergeben.
	 */
	public void setShipToDefend(Card shipToDefend) {
		this.shipToDefend = shipToDefend;
	}

	/**
	 *
	 * @return Gibt eine Liste aller Spielerzustände des Zuges zurück.
	 */
	public List<PlayerState> getPlayers() {
		return players;
	}

	/**
	 * Setzt die Liste aller Spielerzustände des Zuges.
	 * @param players
	 * 		Bekommt eine Liste von Spielerzuständen.
	 */
	public void setPlayers(List<PlayerState> players) {
		this.players = players;
	}

	/**
	 *
	 * @return Gibt die Hafenauslage zurück.
	 */
	public CardStack getHarbour() {
		return harbour;
	}

	/**
	 * Setzt die Hafenauslage.
	 * @param harbour
	 * 		Bekommt einen Kartenstapel übergeben.
	 */
	public void setHarbour(CardStack harbour) {
		this.harbour = harbour;
	}

	/**
	 *
	 * @return Gibt die Aktion zurück.
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * Setzt die Aktion des Zuges.
	 * @param action
	 * 		Bekommt eine Aktion übergeben. (Enthält Aktionstyp und betroffene Karte.)
	 */
	public void setAction(Action action) {
		this.action = action;
	}

	/**
	 *
	 * @return Gibt das Limit zurück, wie viele Karten gekauft werden können.
	 */
	public int getBuyLimit() {
		return buyLimit;
	}

	/**
	 * Setzt das Limit wie viele Karten gekauft werden können.
	 * @param buyLimit
	 * 		Bekommt das Limit als Zahl übergeben.
	 */
	public void setBuyLimit(int buyLimit) {
		this.buyLimit = buyLimit;
	}
}
