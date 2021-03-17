package controller;

import model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static model.ActionType.*;

/**
 * Verwaltet das Spiel.
 */
public class GameController {

	private MainController mainController;

	public Card pop(CardStack stack){

		Card card = stack.getCards().get(0);

		stack.getCards().remove(card);

		return card;
	}

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
		PlayerState actor = move.getActor();
		int index = (playerList.indexOf(actor)+1) % playerList.size();
		move.setActor(playerList.get(index));
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

	// nicht fertig. Nach Ergaenzung von CardFactory Controller wird die Methode fertig gemacht.
	public void init() {

		CardStack discardPile = mainController.getGameSystem().getCurrentGame().getDiscardPile();
		CardStack harbourPile = mainController.getGameSystem().getCurrentGame().getHarbour();
		CardStack cardPile = new CardStack(); //  defaultCards in CardFactoryController
		mainController.getCardController().shuffle(null, null);
		if(mainController.getGameSystem().getPlayers().size()<5){
			for(Card card : cardPile.getCards()){
				if( card instanceof Expedition /*&&((Expedition) card).getRequirements().equals()*/ ){ //Eigenschaft von SonderExpedition fehlt noch
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
			case TAKE_SHIP:
				mainController.getCardController().takeShip(nextMove, action);
				break;
			case BUY_PERSON:
				mainController.getCardController().buyPerson(nextMove, action);
				break;
			case DEFEND:
				mainController.getCardController().defend(nextMove, action);
				break;
			case START_EXPEDITION:
				mainController.getCardController().startExpedition(nextMove, action);
				break;
			case SKIP:
				mainController.getCardController().skip(nextMove, action);
				break;
			//Es gibt keine Methode acceptShip() in CardController;
			/*case ACCEPT_SHIP:
				//mainController.getCardController().(nextMove, action);
				break;
			 */
			case SHUFFLE:
				mainController.getCardController().shuffle(nextMove, action);
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

		return null;
	}

	/**
	 * Setzt einen Zug zurück.
	 * @param move
	 * 		Bekommt einen Zug.
	 */
	public void undo(Move move) {
		List<Move> moveList = mainController.getGameSystem().getCurrentGame().getMoves();
		if(moveList.lastIndexOf(currentMove()) > 0 ) {
			// zu letzte move zuruekgehen und das undoMove in moveList hinzufuegen
			Move undoMove = moveList.get(moveList.indexOf(currentMove()) - 1);
			moveList.add(undoMove);
		}
	}

	/**
	 * Stellt einen Zug wieder her.
	 * @param move
	 * 		Bekommt einen Zug.
	 */
	public void redo(Move move) {
		List<Move> moveList = mainController.getGameSystem().getCurrentGame().getMoves();
		if(moveList.lastIndexOf(currentMove()) < moveList.size()-1) {
			// zu naechste move zuruekgehen und redoMove in moveList hinzufuegen.
			Move redoMove = moveList.get(moveList.indexOf(currentMove()) + 1);
			moveList.add(redoMove);
		}
	}

	/**
	 * Mischt den Ablegestapel.
	 * @param move
	 * 		Bekommt einen Zug.
	 */
	public void shuffleDiscardPile(Move move) {

		if (mainController.getGameSystem().getCurrentGame().isShuffleCards()) {
			Collections.shuffle(move.getDiscardPile().getCards());
			move.setCardPile(move.getDiscardPile());
		} else {
			List<Card> discardPile = move.getDiscardPile().getCards();
			List<Card> initCards = mainController.getGameSystem().getCurrentGame().getInitCardPile().getCards();
			List<Card> newCards = new ArrayList<>();
			int indexOfNewCards = 0;
			// discardPile nach initCards sortieren und in newCards hinzufuegen
			for (Card initCard : initCards) {
				for( Card discard : discardPile ){
					if(initCard.equals(discard)){
						newCards.set(indexOfNewCards,discard);
						indexOfNewCards++;
					}
				}
			}
			move.getDiscardPile().getCards().clear();
			move.getCardPile().getCards().addAll(newCards);
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
			int index = playerList.indexOf(startPlayer)+1 % playerList.size();
			mainController.getGameSystem().getCurrentGame().setStartPlayer(playerList.get(index));
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
			// neue Karte aufdecken
			Card currentCard = pop(move.getCardPile());
			List<Card> harbour = move.getHarbour().getCards();
			if (currentCard instanceof Ship) {
				for (Card card : harbour) {
					if (card instanceof Ship && ((Ship) card).getColour().equals(((Ship) currentCard).getColour())) {
						move.setDiscardPile(move.getHarbour());
						harbour.clear();
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


