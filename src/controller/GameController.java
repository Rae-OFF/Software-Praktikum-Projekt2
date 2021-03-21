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

	public GameController(MainController mainController) {
		this.mainController = mainController;
	}


	/**
	 *
	 * @return Gibt den aktuellen Zug zurück.
	 */
	public Move currentMove() {
		return mainController.getGameSystem().getCurrentGame().getLastMove();
	}

	public List<Card> popCardPile (Move move, int num) {
		CardStack cardStack = move.getCardPile();
		List<Card> popCardPile = new ArrayList<>();
		for( int i = 0; i< num ; i++ ){
			if(cardStack.peek()!=null){
				popCardPile.add(cardStack.pop());
			}else{
				shuffleDiscardPile(move);
			}
		}
		return popCardPile;
	}

/*	public void finishRound(Move move) {
		Action lastAction = move.getAction();
		if(lastAction != null) {
			CardStack harbour = move.getHarbour();
			List<Card> harbourCards = harbour.getCards();
			if (move.isPhase1()) {
				//ab diesem Punkt darf man nicht weiter ziehen
				if (lastAction.getActionType().equals(SKIP) || isZonked(move)) {
					if (harbourCards.size() == 0) {
						mainController.getCardController().execJester(move, lastAction);
						changeActivePlayer(move);
					} else if(!lastAction.getActionType().equals(DRAW_CARD)){
						changeActor(move);
						move.setPhase1(false);
					}
				}
			} else {//Exception caught in drawCard (TaxIncrease/Expedition cards only)
				if (!lastAction.getActionType().equals(DRAW_CARD) && move.getActivePlayer().equals(move.getActor()) || harbourCards.size() == 0) {
					changeActivePlayer(move);
					move.setPhase1(true);
					move.getDiscardPile().getCards().addAll(harbourCards);
					move.getHarbour().getCards().clear();
				} else if (lastAction.getActionType().equals(SKIP)) {
					changeActor(move);
				}
			}
		}
	}*/

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
		move.setBuyLimit(1);
	}

	/**
	 * Wechselt den aktiven Spieler.
	 * @param move
	 * 		Bekommt einen Zug übergeben.
	 */
	public void changeActivePlayer (Move move){
		List<PlayerState> playerList = move.getPlayers();
		PlayerState activePlayer = move.getActivePlayer();
		int index = (playerList.indexOf(activePlayer)+1) % playerList.size();
		move.setActivePlayer(playerList.get(index));
		move.setActor(playerList.get(index));
		move.setPhase1(true);
	}

	/**
	 * Führt das Beenden einer Runde aus.
	 * @param move
	 * 		Bekommt einen Zug übergeben.
	 */
	public void finishRound(Move move) {
		Action lastAction = move.getAction();

			if(lastAction.getActionType().equals(DRAW_CARD)){
				if(isZonked(move)){
					changeActivePlayer(move);
					mainController.getIoController().log("-----------------ZONK FOLLOWS---------------------");
				}
			}
		/*
			if(lastAction.getActionType().equals(DEFEND)){

			}

			if(lastAction.getActionType().equals(ACCEPT_SHIP)){

			}
			if(lastAction.getActionType().equals(SHUFFLE)){

			} */
			if(lastAction.getActionType().equals(TAKE_SHIP)){
				move.setPhase1(false);
			}

			if(lastAction.getActionType().equals(BUY_PERSON)){
				move.setPhase1(false);

			}

			if(lastAction.getActionType().equals(START_EXPEDITION)){
				move.setPhase1(false);
			}

			if(lastAction.getActionType().equals(SKIP)){

				if(move.getActor().equals(move.getActivePlayer())){
					changeActivePlayer(move);
				}
				changeActor(move);
			}



	}


	/**
	 * Führt das initialie Starten des Spiels aus (alle Spieler bekommen 3 Münzen, Nachziehstapel etc.)
	 */

	// nicht fertig. Nach Ergaenzung von CardFactory Controller wird die Methode fertig gemacht.
	public void init(String cardPilePath, List<Player> players, boolean variant, boolean shuffleCards, boolean randomPlayerOrder) {

		PlayerController playerController = mainController.getPlayerController();

		CardStack cardPile;
		if(cardPilePath == null){
			if(players.size() < 5){
				cardPile = CardFactory.newCardsWithoutSpecial();
			}
			else{
				cardPile = CardFactory.newCardsWithSpecial(); //  defaultCards in CardFactoryController
			}

		}else{
			cardPile = mainController.getIoController().loadCardDeck(cardPilePath, players.size());
		}
		List<Player> playersOrdered = playerController.setPlayerOrder(players, randomPlayerOrder);

		List<PlayerState> states = new ArrayList<>();
		for(Player player : playersOrdered){
			PlayerState tplayerState = new PlayerState(player);
			CardStack stack = new CardStack();
			stack.setCards(cardPile.popList(3));
			tplayerState.setCoins(stack);
			states.add(tplayerState);
		}

		Game game = new Game(variant, states.get(0), shuffleCards, playersOrdered, cardPile);
		game.setPlayerStates(states);


		mainController.getGameSystem().setCurrentGame(game);

		Move move = new Move(null, true, null, null);
		Card firstCard = cardPile.pop();
		move.setCardPile(cardPile);
		move.getHarbour().push(firstCard);
		move.setPlayers(game.getPlayerStates());
		move.setActor(states.get(0));
		move.setActivePlayer(states.get(0));

		Action action = new Action(SHUFFLE, null);
		move.setAction(action);

		game.setLastMove(move);


	}

	public PlayerState getActivePlayer(){
		return currentMove().getActivePlayer();
	}

	public PlayerState getActor(){
		return currentMove().getActor();
	}

	public boolean currentGameIsRunning(){
		return mainController.getGameSystem().getCurrentGame().isOngoing();
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
			case DRAW_CARD:
				mainController.getCardController().drawCard(nextMove, action);
				break;
			case TAKE_SHIP:
				mainController.getCardController().takeShip(nextMove, action);
				nextMove.setBuyLimit(nextMove.getBuyLimit() - 1);
				break;
			case BUY_PERSON:
				mainController.getCardController().buyPerson(nextMove, action);
				nextMove.setBuyLimit(nextMove.getBuyLimit() - 1);
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
			//TODO: ACCEPT_SHIP entfernen?
			case ACCEPT_SHIP:
				//mainController.getCardController().(nextMove, action);
				move.getHarbour().push(move.getShipToDefend());
				move.setShipToDefend(null);
				break;

		}
		nextMove.setAction(action);
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
		//List<Card> playerCards = move.getActivePlayer().getCards().getCards();
		PlayerState activePlayer = move.getActivePlayer();
		PlayerState actor = move.getActor();

		//check if actor owns Mademoiselle
		//count Governors and set buyLimit

		//DRAW_CARD;
		if(move.getShipToDefend() != null)  {
			//DEFEND
			results.add(new Action(DEFEND, move.getShipToDefend()));

			//ACCEPT_SHIP
			results.add(new Action(ACCEPT_SHIP, move.getShipToDefend()));

			return results;
		}

		if( actor == activePlayer && !isZonked(move) && move.isPhase1()) {
			results.add(new Action(DRAW_CARD,move.getCardPile().peek()));
		}

			if (move.getShipToDefend() == null && move.getBuyLimit() > 0) {
				//TAKE_SHIP;
				for (Card card : harbourCards) {
					if (card instanceof Ship) {
						results.add(new Action(TAKE_SHIP, card));
					}
				}
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
	// * @param move
	 * 		Bekommt einen Zug.
	 */
	public void undo() {    // parameter deleted while not in use
		List<Move> moveList = mainController.getGameSystem().getCurrentGame().getMoves();

		if(moveList.indexOf(currentMove()) > 0 ) {   //when this last move isn't the first one on list
			// get the new move from index of parameter's move minus 1
			Move undoMove = moveList.get(moveList.indexOf(currentMove()) - 1);
			mainController.getGameSystem().getCurrentGame().setLastMove(undoMove); // set this index-1 move als last move
		}
	}

	/**
	 * Stellt einen Zug wieder her.
	// * @param move
	 * 		Bekommt einen Zug.
	 */
	public void redo() {    // parameter deleted while not in use
		List<Move> moveList = mainController.getGameSystem().getCurrentGame().getMoves();
		if(moveList.lastIndexOf(currentMove()) < moveList.size()-1) {
			// zu naechste move zuruekgehen und redoMove in moveList hinzufuegen.
			Move redoMove = moveList.get(moveList.indexOf(currentMove()) + 1);
			mainController.getGameSystem().getCurrentGame().setLastMove(redoMove);
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
			// discardPile nach initCards sortieren und in newCards hinzufuegen
			for (Card initCard : initCards) {
				for( Card discard : discardPile ){
					if(initCard.equals(discard)){
						newCards.add(discard);
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
			int index = (playerList.indexOf(startPlayer)+1) % playerList.size();
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
			Card currentCard = move.getHarbour().peek();
			List<Card> harbour = move.getHarbour().getCards();
			harbour.remove(currentCard);
			if (currentCard instanceof Ship) {
				for (Card card : harbour) {
					if (!card.equals(currentCard) && card instanceof Ship && ((Ship) card).getColour().equals(((Ship) currentCard).getColour())) {
						move.getDiscardPile().pushList(move.getHarbour().getCards());
						move.getHarbour().getCards().clear();
						return true;
					}
				}
			}
			return false;
		}


	}


