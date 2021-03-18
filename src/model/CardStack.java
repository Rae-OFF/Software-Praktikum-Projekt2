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

	/**
	 *
	 * @return Gibt die oberste Karte vom Stapel zurück (und entfernt diese vom Stapel).
	 */
	public Card pop(){
		return this.getCards().remove(0);
	}

	/**
	 * @param numberOf
	 * 		Bekommt übergeben wie viele Karten vom Kartenstapel abgenommen werden sollen.
	 * @return Gibt eine Liste von Karten zurück.
	 */
	public List<Card> popList(int numberOf){
		List<Card> cards = new ArrayList<>();
		for(int i = 0; i <= numberOf; i++){
			cards.add(this.getCards().remove(0));
		}
		return cards;
	}

	/**
	 * @param newCard
	 * 		Bekommt die Karte übergeben die dem Stapel hinzugefügt werden soll.
	 */
	public void push(Card newCard){
		this.getCards().add(newCard);
	}

	/**
	 * @param newCards
	 * 		Bekommt die Liste der Karten übergeben, die dem Stapel hinzugefügt werden sollen.
	 */
	public void pushList(List<Card> newCards){
		this.getCards().addAll(newCards);
	}

	/**
	 * @return Gibt die oberste Karte zurück ohne diese zu entfernen.
	 */
	public Card peek(){
		if(this.getCards().get(0) != null){
			return this.getCards().get(0);
		}

		else{
			return null;
		}

	}

	/**
	 *
	 * @return
	 */

	public int getSize(){
		return this.cards.size();
	}
}
