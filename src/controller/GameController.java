package controller;

import model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static model.ActionType.*;
import static model.PersonType.*;

/**
 * Verwaltet das Spiel.
 */
public class GameController {

	private MainController mainController;

	/**
	 * Konstruktor.
	 * @param mainController
	 * 		Bekommt den MainController übergeben.
	 */
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

	/**
	 * Nimmt eine bestimmte Anzahl an Karten vom Stapel ab.
	 * @param move
	 * 		Bekommt einen Zug übergeben.
	 * @param num
	 * 		Bekommt die Anzahl übergeben.
	 * @return Gibt eine Liste von Karten zurück.
	 */
	public List<Card> popCardPile (Move move, int num) {
		CardStack cardStack = move.getCardPile();
		List<Card> popCardPile = new ArrayList<>();
		for( int i = 0; i< num ; i++ ){
			if(cardStack.peek()!=null){
				popCardPile.add(cardStack.pop());
			}else{
				shuffleDiscardPile(move);
				popCardPile.add(cardStack.pop());
			}
		}
		return popCardPile;
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

		int govenors = mainController.getCardController().getAmountOf(GOVERNOR, move.getActor());
		move.setBuyLimit(1 + govenors);


		int admiral = mainController.getCardController().getAmountOf(ADMIRAL, move.getActor());

		if(admiral > 0 && move.isPhase1() == false){
			for(int i = 1; i <= admiral; i++){
				mainController.getCardController().execAdmiral(move,move.getActor());
			}
		}
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

					mainController.getCardController().execJester(move);

					changeActivePlayer(move);
/*					if(move.equals(currentMove())){
						mainController.getIoController().log("-----------------ZONK FOLLOWS---------------------");
					}*/

				}
			}

			if(lastAction.getActionType().equals(DEFEND)){
				if(isZonked(move)){

					mainController.getCardController().execJester(move);
					changeActivePlayer(move);
/*					if(move.equals(currentMove())){
						mainController.getIoController().log("-----------------ZONK FOLLOWS---------------------");
					}*/
				}
			}

			if(lastAction.getActionType().equals(ACCEPT_SHIP)){
				if(isZonked(move)){

					mainController.getCardController().execJester(move);
					changeActivePlayer(move);
/*					if(move.equals(currentMove())){
						mainController.getIoController().log("-----------------ZONK FOLLOWS---------------------");
					}*/
				}
			}

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
				changeActor(move);
				if(move.getActor().equals(move.getActivePlayer())){
					changeActivePlayer(move);
				}
			}

			if(lastAction.getActionType().equals(SHUFFLE)){

			}

	}


	/**
	 * Beendet das Spiel wenn ein Spieler die Gewinnbedingung erreicht hat.
	 * @param move
	 * 		Bekommt einen Zug.
	 */
	public void finishGame (Move move){

		List<PlayerState> playerList = move.getPlayers();
		//Siegpunkte in HighscoreList hinzufuegen
		for (PlayerState playerState : playerList) {
			playerState.getPlayer().setScore(playerState.getVictoryPoints());
			if(playerState.getVictoryPoints() >= 12){
				playerState.getPlayer().setWins(playerState.getPlayer().getWins() + 1);
			}
			mainController.getHighscoreController().addPlayerScore(playerState.getPlayer());
		}
	}


	/**
	 * Führt das initialie Starten des Spiels aus (alle Spieler bekommen 3 Münzen, Nachziehstapel etc.)
	 */

	// nicht fertig. Nach Ergaenzung von CardFactory Controller wird die Methode fertig gemacht.
	public void init(String cardPilePath, List<Player> players, boolean variant, boolean shuffleCards, boolean randomPlayerOrder) {

		PlayerController playerController = mainController.getPlayerController();

		CardStack discardPile = new CardStack();
		CardStack harbourPile = new CardStack();
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
			Collections.shuffle(cardPile.getCards());
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
		move.setBuyLimit(1);

		Action action = new Action(SHUFFLE, null);
		move.setAction(action);

		game.setLastMove(move);


	}

	/**
	 *
	 * @return Gibt den aktiven Spieler zurück.
	 */
	public PlayerState getActivePlayer(){
		return currentMove().getActivePlayer();
	}

	/**
	 *
	 * @return Gibt den Spieler zurück der gerade am Zug ist (Phase 2).
	 */
	public PlayerState getActor(){
		return currentMove().getActor();
	}

	/**
	 *
	 * @return Gibt zurück ob das Spiel noch läuft oder schon beendet wurde.
	 */
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
				//mainController.getCardController().skip(nextMove, action);
				break;
			//Es gibt keine Methode acceptShip() in CardController;
			case ACCEPT_SHIP:
				mainController.getCardController().acceptShip(nextMove, action);
				break;
/*			case SHUFFLE:
				mainController.getCardController().shuffle(nextMove, action);
				break;*/
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
		List<Card> playerCards = move.getActivePlayer().getCards().getCards();
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

		if( actor.getPlayer().equals(activePlayer.getPlayer()) && !isZonked(move) && move.isPhase1() && move.getCardPile().getSize() + move.getDiscardPile().getSize() > 0) {
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

				int costs = ((Person) card).getPrice();

				if(!actor.getPlayer().equals(activePlayer.getPlayer())){
					costs++;
				}

				int mademoiselle = mainController.getCardController().getAmountOf(MADEMOISELLE, actor);

				costs = costs - mademoiselle;

				if(costs < 0){
					costs = 0;
				}

				if(move.getBuyLimit() >0 && costs <= actor.getCoins().getSize()){
					results.add(new Action(BUY_PERSON, card));
				}

			}
		}


		//START_EXPEDITION;
		for(Card expedition : expeditionCards){
			if(expedition instanceof Expedition){
				// Test if current expedition card is claimable
				int captains = 0;
				int priests = 0;
				int settlers = 0;
				int jacks = 0;

				Map<PersonType, Integer> require= ((Expedition) expedition).getRequirements();

				List<Card> cards = actor.getCards().getCards();

				for(Card card : cards){
					if(card instanceof Person){
						if(require.containsKey(((Person) card).getPersonType())){
							if(((Person) card).getPersonType().equals(CAPTAIN)){
								captains++;
							}

							if(((Person) card).getPersonType().equals(PRIEST)){
								priests++;
							}

							if(((Person) card).getPersonType().equals(SETTLER)){
								settlers++;
							}

							if(((Person) card).getPersonType().equals(JACK_OF_ALL_TRADES)){
								jacks++;
							}
						}
					}
				}

				int captainDistance = (require.get(CAPTAIN) - captains);
				int priestDistance = (require.get(PRIEST) - priests);
				int settlerDistance = (require.get(SETTLER) - settlers);

				if(captainDistance < 0){
					captainDistance = 0;
				}

				if(priestDistance < 0){
					priestDistance = 0;
				}

				if(settlerDistance < 0){
					settlerDistance = 0;
				}

				boolean fulfilled = false;

				if((captainDistance + priestDistance + settlerDistance) <= jacks){
					fulfilled = true;
				}
				if(fulfilled == true){
					results.add(new Action(START_EXPEDITION, expedition));
					//mainController.getIoController().log("Expedition possible");
				}

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
		List<Move> moveList = mainController.getGameSystem().getCurrentGame().getMoves();
		if(moveList.lastIndexOf(currentMove()) > 0 ) {
			// zu letzte move zuruekgehen und das undoMove in moveList hinzufuegen
			Move undoMove = moveList.get(moveList.indexOf(currentMove()) - 1);
			mainController.getGameSystem().getCurrentGame().setLastMove(undoMove);
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
			mainController.getGameSystem().getCurrentGame().setLastMoveAndCut(redoMove);
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

			move.getCardPile().pushList(move.getDiscardPile().popList(move.getDiscardPile().getSize()));
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
		 * Gibt zurück ob ein Spieler Phase 1 durch zwei gleichfarbige Schiffe in der Hafenauslage beendet hat.
		 * @param move
		 * 		Bekommt einen Zug.
		 * @return Gibt zurück ob die Hafenauslage zurückgelegt werden muss (true) oder ob der Spieler
		 * 		weiterspielen kann.
		 */
		public boolean isZonked (Move move){
			if(move.getHarbour().getSize() > 0) {
				Card currentCard = move.getHarbour().showLastCard();
				List<Card> harbour = move.getHarbour().getCards();
				if (currentCard instanceof Ship) {
					for (Card card : harbour) {
						if (card instanceof Ship && (!card.equals(currentCard)) && ((Ship) card).getColour().equals(((Ship) currentCard).getColour())) {
							move.getDiscardPile().pushList(move.getHarbour().getCards());
							move.getHarbour().getCards().clear();
							return true;
						}
					}
				}
			}
			return false;
		}


	}


