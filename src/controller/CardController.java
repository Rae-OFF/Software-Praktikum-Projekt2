package controller;

import model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//import java.util.Map;

import static model.ActionType.*;
import static model.PersonType.*;

/**
 * Verwaltet die Karten.
 */
public class CardController extends MainController {

   private MainController mainController;

	/**
	 * Führt den Admiral Effekt aus (liegen mehr als 5 Karten in der Hafenauslage,
	 * bekommt der Spieler dieser Karte 1 Münze).
	 * @param move
	 * 		Bekommt den Zug übergeben.
	 * @param action
	 * 		Bekommt die Aktion übergeben.
	 */

	public void execAdmiral(Move move, Action action) {

		List<Card> harbourCards = move.getHarbour().getCards();
		PlayerState player = move.getActivePlayer();
		// TODO: updaten after card stack updated

		if(harbourCards.size()>=5){

			CardStack stack = player.getCoins(); // get player's coin stack
			List<Card> coins = stack.getCards(); // get a list of these coins
			coins.add(pop(move.getCardPile())); // add one coin (taken from top card of the card pile) to player's stack
			coins.add(pop(move.getCardPile())); // add one more coin
		}
	}

	/**
	 * Führt den Jester Effekt aus (liegen 0 Karten in der Hafenauslage, bekommt der Spieler dieser Karte 1 Münze).
	 * @param move
	 * 		Bekommt den Zug übergeben.
	 * @param action
	 * 		Bekommt die Aktion übergeben.
	 */
	public void execJester(Move move, Action action) {

		List<PlayerState> players = move.getPlayers();
		PlayerState player = move.getActivePlayer();

		if(mainController.getGameSystem().getCurrentGame().isJesterEnabled()){
			if(action.getActionType().equals(SKIP) && move.isPhase1()){  // if active player skipped in phase 1

				for(PlayerState playerState : players){   // check all player's
					List<Card> playercards = playerState.getCards().getCards(); // access all player's hand
						for(Card jester: playercards ){
							if(jester instanceof Person && ((Person) jester).getPersonType().equals(JESTER)){        // if JESTER is at player's hand, all these players get 1 coin
							CardStack stack =player.getCoins();
							List<Card> coins = stack.getCards();
							coins.add(pop(move.getCardPile()));  // coin card popped from card pile and removed from pile
							player.setCoins(stack);
							}
						}
				}
			}else {   // if in phase 2, only active player get 1 coin

					CardStack stack = player.getCoins();
					List<Card> coins = stack.getCards();
					coins.add(pop(move.getCardPile()));
			}
		}

	}

	/**
	 * Führt den Händler Effekt aus (nimmt der Spieler ein Schiff der gleichen Farbe auf,
	 * bekommt er den Wert des Schiffes +1 Münze).
	 * @param move
	 * 		Bekommt den Zug übergeben.
	 * @param action
	 * 		Bekommt die Aktion übergeben.
	 */
	public void execTrader(Move move, Action action) {

		PlayerState player = move.getActivePlayer();
		List<Card> playercards = player.getCards().getCards();

		Card card = action.getAffectedCard(); // get drawn card, this card should be Ship

		if (card instanceof Ship) {

			for (Card trader : playercards) {  // search all cards in player's hand, if he has a TRADER with same colour of the ship
				if (trader instanceof Person && ((Person) trader).getPersonType().equals(TRADER) && ((Person) trader).getColour().equals(((Ship) card).getColour())) {

					CardStack stack = player.getCoins();  // add one 1 to player's coin stack
					List<Card> coins = stack.getCards();
					coins.add(pop(move.getCardPile()));

				}
			}
		}
	}

	/**
	 * Führt das ziehen einer Karte aus.
	 * @param move
	 * 		Bekommt den Zug übergeben.
	 * @param action
	 * 		Bekommt die Aktion übergeben.
	 */
	public void drawCard(Move move, Action action) {

		CardStack stack = move.getCardPile();

		Card card = pop(stack);   // pop 1 card from the card pile

		action.setAffectedCard(card);   // set this popped card as affected card

		if(card instanceof TaxIncrease){

			taxIncrease(move);
		}else if(card instanceof Exception){

			move.getExpeditionPile().getCards().add(card);  //if is Expedition, put it in expedition's pile

		}else if(card instanceof Ship){

			// getGameController().getPossibleActions(move);   alternatively can use this method
			move.getHarbour().getCards().add(card);

		}else{      // in case of a Person

			move.getHarbour().getCards().add(card);

		}
	}

	/**
	 * get a card from a pile, and remove this card from this pile
	 * @param stack a CardStack object
	 * @return a card
	 */
	public Card pop(CardStack stack){

		Card card = stack.getCards().get(0);

		stack.getCards().remove(card);

		return card;
	}

	/**
	 * method of execute a taxIncrease card
	 *
	 * @param move get the value of move back
	 */
	public void taxIncrease(Move move) {

		Card currentCard = move.getCardPile().peek();
		List<PlayerState> players = move.getPlayers();

		int maxSwords=0;
		int minShield =0;

		int playerSwords =0;

		if(currentCard instanceof TaxIncrease) {

			move.getDiscardPile().getCards().add(currentCard);

			for (PlayerState playerState : players) {  // check if anyone has more than 12 coins, if yes, take 1/2 coins
				int numOfCoins = playerState.getCoins().getCards().size();
				if (numOfCoins >= 12) {
					playerState.getCards().popList(numOfCoins/2);
					move.getDiscardPile().getCards().addAll(playerState.getCards().popList(numOfCoins/2));

				}
			}
		// if no one has more than 12 coins:
			List<Integer> swordlist = new ArrayList<>();
			for (PlayerState playerState : players) {
				List<Card> playerHand = playerState.getCards().getCards();


				for(Card card: playerHand ){


					if( card instanceof Person &&( ((Person) card).getPersonType().equals(SAILOR)|| ((Person) card).getPersonType().equals(PIRATE) )){
						playerSwords += ((Person) card).getSwords();
					}
				}  swordlist.add(playerSwords);  // create a list number of swords of all each player

				if(maxSwords< playerSwords){     //get the maximum number
					maxSwords = playerSwords;
				}
			}
			List<Integer> shildList = new ArrayList<>();   // create a list number of shilds of all each player
			for (PlayerState playerState : players) {

				int playerShield = getPlayerController().getVictoryPoints(playerState,move);

				if(minShield > playerShield){              //get the minimum number
					minShield = playerShield;
				}
				shildList.add(playerShield);
			}

			for( int i=0; i< players.size(); i++){         // check again in players list, get the one that has minShield or maxSwords
				if(swordlist.get(i) == maxSwords  || shildList.get(i)== minShield){

					players.get(i).getCoins().getCards().add(move.getCardPile().pop());
				}
			}


			}
	}


	/**
	 * Führt das Aufnehmen eines Schiffes aus (der Spieler bekommt den Wert des Schiffes als Münzen,
	 * das Schiff wird auf den Ablegestapel gelegt).
	 * @param move
	 * 		Bekommt den Zug übergeben.
	 * @param action
	 * 		Bekommt die Aktion übergeben.
	 */
	public void takeShip(Move move, Action action) {

		PlayerState player = move.getActivePlayer();
		PlayerState actor = move.getActor();

		if(player.equals(actor)) {  // if active place wants to take the ship

			for(int i=0; i< ((Ship) action.getAffectedCard()).getCoins(); i++ ) {
				player.getCoins().getCards().add(pop(move.getCardPile()));
			}
		}else{   // if other players want to take the ship

			player.getCoins().getCards().add(pop(move.getCardPile()));
			for(int i=0; i< ((Ship) action.getAffectedCard()).getCoins()-1; i++ ) {

				actor.getCoins().getCards().add(pop(move.getCardPile()));
			}
		}
	}

	/**
	 * Führt das anheuern einer Person aus (der Spieler erhält die Personenkarte,
	 * muss dafür den Wert der Karte in Münzen auf den Ablegestapel legen).
	 * @param move
	 * 		Bekommt den Zug übergeben.
	 * @param action
	 * 		Bekommt die Aktion übergeben.
	 */
	public void buyPerson(Move move, Action action) {

		PlayerState player = move.getActivePlayer();
		PlayerState actor = move.getActor();

		int money = getPlayerController().getCoins(actor,move);  // get total amount of coins from actor
		int personPrice = ((Person)action.getAffectedCard()).getPrice();  // get price of this Person card

		if(money >= personPrice ){

				if(!player.equals(actor)) {   // if other players want to buy

					player.getCoins().getCards().add(pop (actor.getCoins()));  // pay active player one coin

				}
 // else, this actor(active player) will pay for this card
			CardStack stack = actor.getCoins();
			List<Card> disCard = move.getDiscardPile().getCards();

			for(int i=0; i< personPrice; i++) {

				disCard.add(pop(stack));
			}
		}
	}

	/**
	 * Führt die Aktion aus ein Schiff abzuwehren (nur möglich wenn der Spieler genug Schwerter hat).
	 * @param move
	 * 		Bekommt den Zug übergeben.
	 * @param action
	 * 		Bekommt die Aktion übergeben.
	 */
	public void defend(Move move, Action action) {

		if (action.getActionType().equals(ActionType.DEFEND)) {
			Ship shipCard = (Ship) move.getShipToDefend();
			List<Card> cardsInHand = move.getActivePlayer().getCards().getCards();
			int numSwords = 0;

			if (shipCard.getForce() == 100) {
				move.getHarbour().getCards().add(shipCard);

			} else {
				for (Card card : cardsInHand) {
					if (card instanceof Person) {
						if ((((Person) card).getPersonType().equals(PIRATE) || ((Person) card).getPersonType().equals(SAILOR))) {
							numSwords += ((Person) card).getSwords();
						}
					}
				}
				if (numSwords >= shipCard.getForce()) {
					for(int i= 0; i < shipCard.getForce() ; i++) {
						move.getActivePlayer().getCoins().getCards().add(pop(move.getCardPile()));
					}
				}
				move.getDiscardPile().getCards().add(shipCard);
			}
		}
	}

		/**
		 * Führt das Ausrufen einer Expedition aus (nur möglich wenn der Spieler genug Personenkarten für die
		 * Bedingung hat; bekommt den Wert der Karten in Münzen und die Siegpunkte).
		 * @param move
		 * 		Bekommt den Zug übergeben.
		 * @param action
		 * 		Bekommt die Aktion übergeben.
		 */


		public void startExpedition (Move move, Action action){  // not finished

			//PlayerState player = move.getActivePlayer();

			List<Card> ExpedPoints = move.getExpeditionPile().getCards();

			ExpedPoints.remove(action.getAffectedCard());
			//	move.getDiscardPile().getCards().add(player.getCards().equals((Exception) ));


		}


		/**
		 * Führt das überspringen eines Zuges aus.
		 * @param move
		 * 		Bekommt den Zug übergeben.
		 * @param action
		 * 		Bekommt die Aktion übergeben.
		 */
		public void skip (Move move, Action action){

			PlayerState player = move.getActivePlayer();

			if (action.getActionType().equals(SKIP)) {
				if (player != move.getActor()) {
					getGameController().changeActor(move);

				} else {
					getGameController().changeActivePlayer(move);
				}

			}
		}

		/**
		 * Führt das mischen der Karten aus.
		 * @param move
		 * 		Bekommt den Zug übergeben.
		 * @param action
		 * 		Bekommt die Aktion übergeben.
		 */

		//TODO: beachten, was hier genau gemischt werden soll.
		public void shuffle (Move move, Action action){

			if (action.getActionType() == SHUFFLE) {
				CardFactory newList = new CardFactory();

				Collections.shuffle(newList.newCardsWithSpecial().getCards());
			}

		}

	}
