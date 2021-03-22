package model;

import static model.PersonType.PIRATE;
import static model.PersonType.SAILOR;

/**
 * Klasse der Spielerzustände.
 */
public class PlayerState {

	private CardStack cards;

	private CardStack coins;

	private Player player;

	/**
	 *
	 * @return Gibt die Siegpunkte des Spielers zurück.
	 */
	public int getVictoryPoints() {
		int vPoints = 0;

		for(Card card : this.cards.getCards()){
			if(card instanceof Person){
				vPoints += ((Person) card).getVictoryPoints();
			}
			if(card instanceof Expedition){
				vPoints += ((Expedition) card).getVictoryPoints();
			}
		}

		return vPoints;
	}

/*	public void setVitoryPoints(int vitoryPoints) {
		this.vitoryPoints = vitoryPoints;
	}*/

	/**
	 * Copy Konstruktor.
	 * @param skeleton
	 * 		Bekommt einen Spielerzustand übergeben.
	 * Erstellt einen neuen Spielerzustanden der die gleichen Inhalte hat wie der übergebene.
	 */

	public PlayerState(PlayerState skeleton) {
		this(skeleton.getPlayer());
		this.cards = new CardStack(skeleton.getCards());
		this.coins = new CardStack(skeleton.getCoins());
	}

	/**
	 * Konstruktor.
	 * @param player
	 * 		Bekommt einen Spieler übergeben.
	 *  Erstellt außerdem einen neuen Kartenstapel und einen neuen Münzenstapel.
	 */
	public PlayerState(Player player) {
		this.player = player;
		this.cards = new CardStack();
		this.coins = new CardStack();
	}

	/**
	 *
	 * @return Gibt den Kartenstapel zurück.
	 */
	public CardStack getCards() {
		return cards;
	}

	/**
	 * Setzt den Kartenstapel.
	 * @param cards
	 * 		Bekommt einen Kartenstapel übergeben.
	 */
	public void setCards(CardStack cards) {
		this.cards = cards;
	}

	/**
	 *
	 * @return Gibt den Münzenstapel zurück.
	 */
	public CardStack getCoins() {
		return coins;
	}

	/**
	 * Setzt den Münzenstapel.
	 * @param coins
	 * 		Bekommt einen Kartenstapel übergeben.
	 */
	public void setCoins(CardStack coins) {
		this.coins = coins;
	}

	/**
	 *
	 * @return Gibt einen Spieler zurück.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Setzt den Spieler.
	 * @param player
	 * 		Bekommt einen Spieler übergeben.
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
}
