package model;

import java.util.ArrayList;
import java.util.List;



public class CardStack {

	private List<Card> cards;

	public CardStack(CardStack skeleton) {

		this.cards = new ArrayList<>();

		this.cards.addAll(skeleton.getCards());
	}


	public CardStack() {
		this.cards = new ArrayList<>();

	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
}
