package controller;

import model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static model.ActionType.*;

/**
 * Verwaltet das Spiel.
 */
public class GameController {

	private MainController mainController;

	/**
	 *
	 * @return Gibt den aktuellen Zug zurück.
	 */
	public Move currentMove() {
		return mainController.getGameSystem().getCurrentGame().getLastMove();
	}

	/**
	 * Wechselt den aktuellen Spieler zum nächsten Spieler in Phase 2.
	 * @param move
	 * 		Bekommt den Zug übergeben.
	 */
	public void changeActor(Move move) {
		List<PlayerState> playerList = move.getPlayers();
		for (int index = 0; index < playerList.size(); index++) {
			if (playerList.get(index).equals(move.getActor())) {
				if (index != playerList.size() - 1) {
					move.setActor(playerList.get(index + 1));
				} else {
					move.setActor(playerList.get(0));
				}
				break;
			}
		}
	}

	/**
	 * Führt das Beenden einer Runde aus.
	 * @param move
	 * 		Bekommt einen Zug übergeben.
	 */
	public void finishRound(Move move) {
		Action lastAction = mainController.getGameController().currentMove().getAction();
		CardStack harbour = move.getHarbour();
		List<Card> harbourCards = harbour.getCards();
		if (move.isPhase1()) {
			//ab diesem Punkt darf man nicht weiter ziehen
			if (lastAction.getActionType().equals(SKIP) ||  isZonked(move)) {
				if (harbourCards.size() == 0) {
					mainController.getCardController().execJester(move, lastAction);
					changeActivePlayer(move);
				}else {
					changeActor(move);
					move.setPhase1(false);
				}
			}
		}else {//Exception caught in drawCard (TaxIncrease/Expedition cards only)
			if( move.getActivePlayer().equals(move.getActor()) || harbourCards.size() == 0){
				changeActivePlayer(move);
				move.setPhase1(true);
				move.getDiscardPile().getCards().addAll(harbourCards);
				move.getHarbour().getCards().clear();
			}
			else if(lastAction.getActionType().equals(SKIP)){
				changeActor(move);
			}
		}
	}


	/**
	 * Führt das initialie Starten des Spiels aus (alle Spieler bekommen 3 Münzen, Nachziehstapel etc.)
	 */
	public void init() {
		//Move initMove = new Move((PlayerState actor, boolean phase1, PlayerState activePlayer, Action action));
		//Action initAction =  new Action();

		CardStack discardPile = mainController.getGameSystem().getCurrentGame().getDiscardPile();
		CardStack harbourPile = mainController.getGameSystem().getCurrentGame().getHarbour();
		CardStack cardPile = new CardStack();
		mainController.getCardController().shuffle(null, null);


		if(mainController.getGameSystem().getPlayers().size()<5){
			for(Card card : cardPile.getCards()){
				if( card instanceof Expedition ){ //Eigenschaft von SonderExpedition fehlt noch
					cardPile.getCards().remove(card);
				}
			}
		}
	}


	/**
	 * Generiert einen neuen Zug anhand der übergebenen Aktion.
	 * @param move
	 * 		Bekommt einen Zug.
	 * @param action
	 * 		Bekommt eine Aktion.
	 * @return Gibt einen neuen Zug zurück.
	 */
	//DRAW_CARD, TAKE_SHIP,BUY_PERSON, DEFEND, START_EXPEDITION, SKIP, SHUFFLE,ACCEPT_SHIP
	public Move generateMove(Move move, Action action){
		Move nextMove = new Move(move);
		switch ( action.getActionType()){
			case SKIP:
				mainController.getCardController().skip(nextMove, action);
				break;
			case TAKE_SHIP:
				mainController.getCardController().takeShip(nextMove, action);
				break;

		}
		finishRound(nextMove);
		return nextMove;
	}

	/**
	 * Gibt eine Liste der möglichen Aktionen zurück.
	 * @param move
	 * 		Bekommt einen Zug.
	 * @return Gibt eine Liste von Aktionen zurück.
	 */
	public List<Action> getPossibleActions(Move move) {
		List<Action> results = new ArrayList<>();

		List<Card> harbourCards = move.getHarbour().getCards();
		List<Card> expeditionCards = move.getExpeditionPile().getCards();
		List<Card> playerCards = move.getActivePlayer().getCards().getCards();
		PlayerState activePlayer = move.getActivePlayer();
		PlayerState actor = move.getActor();

		//check if actor owns Mademoiselle
		//count Governors and set buyLimit

		//DRAW_CARD;
		if( actor == activePlayer && !isZonked(move)) {
			results.add(new Action(DRAW_CARD,move.getCardPile().getCards().get(0)));
		}

		if(move.getShipToDefend() == null) {
			//TAKE_SHIP;
			for (Card card : harbourCards) {
				if(card instanceof Ship){
					results.add(new Action(TAKE_SHIP, card));
				}
			}
		}else{
			//DEFEND
			results.add(new Action(DEFEND,move.getCardPile().getCards().get(0)));

			//ACCEPT_SHIP
			results.add(new Action(ACCEPT_SHIP,move.getCardPile().getCards().get(0)));
		}

		//BUY_PERSON;
		for (Card card : harbourCards) {
			if(card instanceof Person){
				if(move.getBuyLimit() >0 && ((Person) card).getPrice() <= actor.getCoins().getCards().size())
				results.add(new Action(BUY_PERSON, card));
			}
		}


		//START_EXPEDITION;
		for(Card expedition : expeditionCards){
			if(expedition instanceof Expedition){
				// Test if current expedition card is claimable
				results.add(new Action(START_EXPEDITION, expedition));
			}

		}

		//SKIP;
		results.add(new Action(SKIP, null));

		return results;
	}

	/**
	 * Setzt einen Zug zurück.
	 * @param move
	 * 		Bekommt einen Zug.
	 */
	public void undo(Move move) {

	}

	/**
	 * Stellt einen Zug wieder her.
	 * @param move
	 * 		Bekommt einen Zug.
	 */
	public void redo(Move move) {

	}

	/**
	 * Mischt den Ablegestapel.
	 * @param move
	 * 		Bekommt einen Zug.
	 */
	public void shuffleDiscardPile(Move move) {

		CardStack discardPile = move.getDiscardPile();
		if (mainController.getGameSystem().getCurrentGame().isShuffleCards()) {
			Collections.shuffle(move.getDiscardPile().getCards());
			move.setCardPile(move.getDiscardPile());
		} else {
			CardStack initCard = mainController.getGameSystem().getCurrentGame().getInitCardPile();
			for (Card card : initCard.getCards()) {
				if (card instanceof Person) {

				}
			}
		}
	}


		/**
		 * Beendet das Spiel wenn ein Spieler die Gewinnbedingung erreicht hat.
		 * @param move
		 * 		Bekommt einen Zug.
		 */
		public void finishGame (Move move){
			PlayerState startPlayer = mainController.getGameSystem().getCurrentGame().getStartPlayer();
			List<PlayerState> playerList = move.getPlayers();
			//Der Spieler rechts neben dem Startspieler ist der letzte aktive Spieler dieser Partie
			for (int index = 0; index < playerList.size(); index++) {
				if (playerList.get(index).equals(startPlayer)) {
					if (index != playerList.size() - 1) {
						mainController.getGameSystem().getCurrentGame().setStartPlayer(playerList.get(index + 1));
						break;
					} else {
						mainController.getGameSystem().getCurrentGame().setStartPlayer(playerList.get(0));
					}
				}
			}
			//Siegpunkte in HighscoreList hinzufuegen
			for (PlayerState playerState : playerList) {
				playerState.getPlayer().setScore(playerState.getPlayer().getWins());
				mainController.getHighscoreController().addPlayerScore(playerState.getPlayer());
			}
		}

		/**
		 * Gibt zurück ob ein Spieler Phase 1 durch zwei gleichfarbige Schiffe in der Hafenauslage beendet hat.
		 * @param move
		 * 		Bekommt einen Zug.
		 * @return Gibt zurück ob die Hafenauslage zurückgelegt werden muss (true) oder ob der Spieler
		 * 		weiterspielen kann.
		 */
		public boolean isZonked (Move move){
			Card currentCard = move.getCardPile().getCards().get(0);
			List<Card> harbour = move.getHarbour().getCards();
			move.getCardPile().getCards().remove(0);
			if (currentCard instanceof Ship) {
				for (Card card : harbour) {
					if (card instanceof Ship && ((Ship) card).getColour().equals(((Ship) currentCard).getColour())) {
						move.setDiscardPile(move.getHarbour());
						harbour.removeAll(harbour);
						return true;
					}
				}
			}
			return false;
		}


		/*/**
		 * Entscheided ob ein Schiff abgewehrt werden soll oder nicht,
		 * @param defend
		 * 		Bekommt übergeben ob es abgewehrt werden soll (true) oder nicht (false).
		 * @param move
		 * 		Bekommt einen Zug übergeben.
		 * @return Gibt eine Aktion zurück.
		 */
		/*
		public Action decideShipAction ( boolean defend, Move move){
			Action action = move.getAction();
			if (defend) {
				List<Card> discardPile = move.getDiscardPile().getCards();
				discardPile.add(move.getCardPile().getCards().get(0));
				action.setActionType(DEFEND);
				return action;
			} else {
				List<Card> harbour = move.getDiscardPile().getCards();
				harbour.add(move.getCardPile().getCards().get(0));
				action.setActionType(ACCEPT_SHIP);
				return action;
			}
		}*/

		/**
		 * Wechselt den aktiven Spieler.
		 * @param move
		 * 		Bekommt einen Zug übergeben.
		 */
		public void changeActivePlayer (Move move){
			List<PlayerState> playerList = move.getPlayers();
			PlayerState activePlayer = move.getActivePlayer();
			int index = playerList.indexOf(activePlayer)+1 % playerList.size();
			move.setActivePlayer(playerList.get(index));
		}
	}


