package model;

import java.util.ArrayList;
import java.util.List;


/**
 * Klasse für alle Kartenstapel.
 */
public class CardStack {

	private List<Card> cards;

	/**
	 * Copy Konstruktor
	 * @param skeleton
	 * 		Bekommt einen Kartenstapel übergeben und kopiert den Inhalt in einen neuen Kartenstapel.
	 */
	public CardStack(CardStack skeleton) {

		this.cards = new ArrayList<>();

		this.cards.addAll(skeleton.getCards());
	}

	/**
	 * Konstruktor, setzt die Liste der Karten auf eine neue Arrayliste.
	 */
	public CardStack() {
		this.cards = new ArrayList<>();

	}

	/**
	 *
	 * @return Gibt die Liste der Karten zurück.
	 */
	public List<Card> getCards() {
		return cards;
	}

	/**
	 * Setzt die Liste der Karten
	 * @param cards
	 * 		Erhält die Liste der Karten die gesetzt werden sollen als Parameter.
	 */
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
}
