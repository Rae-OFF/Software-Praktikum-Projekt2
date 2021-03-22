package controller;

import model.*;

import java.util.*;

import static model.ActionType.*;
import static model.PersonType.*;

/**
 * Verwaltet die Karten.
 */
public class CardController {

   private MainController mainController;

   private GameController gameController;

   public static int UNDEFENDABLE_SHIP = 100;

	public CardController(MainController mainController) {
		this.mainController = mainController;
		GameController gameController = mainController.getGameController();
		this.gameController = gameController;
	}

	/**
	 * Führt den Admiral Effekt aus (liegen mehr als 5 Karten in der Hafenauslage,
	 * bekommt der Spieler dieser Karte 1 Münze).
	 * @param move
	 * 		Bekommt den Zug übergeben.
	 * @param action
	 * 		Bekommt die Aktion übergeben.
	 */

	public void execAdmiral(Move move, Action action) {
		if(action.getAffectedCard() instanceof Person && ((Person) action.getAffectedCard()).getPersonType().equals(ADMIRAL)) {
			List<Card> harbourCards = move.getHarbour().getCards();
			PlayerState player = move.getActivePlayer();

			if (harbourCards.size() >= 5) {

				CardStack stack = player.getCoins(); // get player's coin stack

				stack.pushList(gameController.popCardPile(move, 2)); // add one coin (taken from top card of the card pile) to player's stack
			}
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
								CardStack stack =playerState.getCoins();
								stack.pushList(gameController.popCardPile(move, 1));
							}
						}
				}
			}else {   // if in phase 2, only active player get 1 coin

					CardStack stack = player.getCoins();
					stack.pushList(gameController.popCardPile(move, 1));
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
					stack.pushList(gameController.popCardPile(move, 1));

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

		Card card = gameController.popCardPile(move, 1).get(0);// pop 1 card from the card pile

		action.setAffectedCard(card);   // set this popped card as affected card

		if(card instanceof TaxIncrease ){
			/*coinsMoreThan12(move);
			if(((TaxIncrease) card).isTypeSwords()){
				taxIncreaseOfMaxSwords(move, action);
			}else{
				taxIncreaseOfMinShields(move, action);
			}*/
			move.getHarbour().push(card);
		}else if(card instanceof Expedition){

			move.getExpeditionPile().push(card);  //if is Expedition, put it in expedition's pile

		}else if(card instanceof Ship){
			// getGameController().getPossibleActions(move);   alternatively can use this method
			move.setShipToDefend(card);

		}else{      // in case of a Person

			move.getHarbour().push(card);

		}
	}


	/**
	 * method of execute a taxIncrease card
	 *
	 * @param move get the value of move back
	 */
	public void coinsMoreThan12 ( Move move ){
		List<PlayerState> players = move.getPlayers();

		for (PlayerState playerState : players) {  // check if anyone has more than 12 coins, if yes, take 1/2 coins
			int numOfCoins = playerState.getCoins().getCards().size();
			if (numOfCoins >= 12) {
				move.getDiscardPile().getCards().addAll(playerState.getCoins().popList(numOfCoins/2));
			}
		}
	}


	public void taxIncreaseOfMaxSwords(Move move, Action action) {

		Card currentCard = action.getAffectedCard();
		List<PlayerState> players = move.getPlayers();

		int maxSwords = -1;
		int playerSwords = 0;

			move.getDiscardPile().getCards().add(currentCard);
			// if no one has more than 12 coins:
			List<Integer> swordlist = new ArrayList<>();
			if (action.getAffectedCard() instanceof TaxIncrease && ((TaxIncrease) action.getAffectedCard()).isTypeSwords()) {
				for (PlayerState playerState : players) {
					List<Card> playerHand = playerState.getCards().getCards();
					for (Card card : playerHand) {
						if (card instanceof Person && (((Person) card).getPersonType().equals(SAILOR) || ((Person) card).getPersonType().equals(PIRATE))) {
							playerSwords += ((Person) card).getSwords();
						}
					}
					swordlist.add(playerSwords);  // create a list number of swords of all each player

					if (maxSwords < playerSwords) {     //get the maximum number
						maxSwords = playerSwords;
					}
					playerSwords=0;
				}
				for (int i = 0; i < players.size(); i++) {         // check again in players list, get the one that has minShield or maxSwords
					if (swordlist.get(i) == maxSwords) {

						players.get(i).getCoins().getCards().add(move.getCardPile().pop());
					}
				}
			}
		}


	public void taxIncreaseOfMinShields(Move move, Action action) {
		Card currentCard = action.getAffectedCard();
		List<PlayerState> players = move.getPlayers();
		int minShield = 99;

		List<Integer> shildList = new ArrayList<>();   // create a list number of shilds of all each player
		for (PlayerState playerState : players) {

			int playerShield = mainController.getPlayerController().getVictoryPoints(playerState, move);

			if (minShield >= playerShield) {              //get the minimum number
				minShield = playerShield;
			}
			shildList.add(playerShield);
		}

		for (int i = 0; i < players.size(); i++) {         // check again in players list, get the one that has minShield or maxSwords
			if (shildList.get(i) == minShield) {

				players.get(i).getCoins().getCards().add(move.getCardPile().pop());
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

			CardStack stack = player.getCoins();
			stack.pushList(gameController.popCardPile(move,((Ship) action.getAffectedCard()).getCoins()));

			move.getDiscardPile().push(move.getHarbour().getCard(action.getAffectedCard()));

		}else{   // if other players want to take the ship

			actor.getCoins().pushList(gameController.popCardPile(move,((Ship) action.getAffectedCard()).getCoins()));

			player.getCoins().pushList(actor.getCoins().popList(1));

			move.getDiscardPile().push(move.getHarbour().getCard(action.getAffectedCard()));

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

		//int money = mainController.getPlayerController().getCoins(actor,move);  // get total amount of coins from actor
		int personPrice = ((Person)action.getAffectedCard()).getPrice();  // get pric

		Card card = (Person)action.getAffectedCard();

		if(player.equals(actor)){

			CardStack stack = actor.getCoins();

			move.getDiscardPile().pushList(stack.popList(personPrice));

			actor.getCards().push(move.getHarbour().getCard(card));
			int vPoints = ((Person) card).getVictoryPoints();
			//actor.setVitoryPoints(actor.getVitoryPoints() + vPoints);
		}

		else if(!player.equals(actor))
		{
			player.getCoins().push((actor.getCoins().pop()));
			CardStack stack = actor.getCoins();

			move.getDiscardPile().pushList(stack.popList(personPrice));

			actor.getCards().push(move.getHarbour().getCard(card));
			int vPoints = ((Person) card).getVictoryPoints();
			//actor.setVitoryPoints(actor.getVitoryPoints() + vPoints);
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
			Ship shipCard = (Ship) action.getAffectedCard();
			List<Card> cardsInHand = move.getActivePlayer().getCards().getCards();
			int numSwords = 0;

			if (shipCard.getForce() >= 100) {
				move.getHarbour().push(shipCard);
				move.setShipToDefend(null);

			} else {
				for (Card card : cardsInHand) {
					if (card instanceof Person) {
						if ((((Person) card).getPersonType().equals(PIRATE) || ((Person) card).getPersonType().equals(SAILOR))) {
							numSwords += ((Person) card).getSwords();
						}
					}
				}
				if (numSwords >= shipCard.getForce()) {

					move.getDiscardPile().push(shipCard);
					move.setShipToDefend(null);

				}
				else{
					move.getHarbour().push(shipCard);
					move.setShipToDefend(null);
				}

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


		public void startExpedition (Move move, Action action){

			if(action.getActionType().equals(START_EXPEDITION)){

				PlayerState player = move.getActivePlayer();

				Card exped = action.getAffectedCard();  // get this Expedition card from pile, which the player wants to exchange.

				Map<PersonType, Integer> require= ((Expedition) exped).getRequirements(); //get requirements of this Expedition

				int coins = ((Expedition) exped).getCoins();

				int victoryPoints = ((Expedition) exped).getVictoryPoints();

				int captains = require.get(CAPTAIN);
				int priests = require.get(PRIEST);
				int settlers = require.get(SETTLER);

				List<Card> materials = new ArrayList<>();

				List<Card> cards = player.getCards().getCards();

				for(Card card : cards){
					if(card instanceof Person){
						Person person = (Person) card;
						if(person.getPersonType().equals(CAPTAIN) && captains > 0){
							materials.add(person);
							captains--;
						}
						if(person.getPersonType().equals(PRIEST) && priests > 0){
							materials.add(person);
							priests--;
						}
						if(person.getPersonType().equals(SETTLER) && settlers > 0){
							materials.add(person);
							settlers--;
						}
					}
				}



				List<Card> jacks = new ArrayList<>();

				int jacksNeeded = captains + priests + settlers;

				if(jacksNeeded> 0){
					for(Card card : cards){
						if(card instanceof Person){
							Person person = (Person) card;
							if(person.getPersonType().equals(JACK_OF_ALL_TRADES) && jacksNeeded > 0){
								jacksNeeded--;
								jacks.add(card);
							}
						}
					}
				}
					cards.removeAll(materials);
					cards.removeAll(jacks);

					move.getDiscardPile().pushList(materials);
					move.getDiscardPile().pushList(jacks);

					move.getExpeditionPile().getCards().remove(exped);


					player.getCards().push(exped);

					player.getCoins().pushList(gameController.popCardPile(move, ((Expedition) exped).getCoins()));
					//player.setVitoryPoints(player.getVitoryPoints() + ((Expedition) exped).getVictoryPoints());

			}
		}

	/*	/**
		 * Führt das mischen der Karten aus.
		 * @param move
		 * 		Bekommt den Zug übergeben.
		 * @param action
		 * 		Bekommt die Aktion übergeben.


		public void shuffle (Move move, Action action){

			if (action.getActionType() == SHUFFLE) {
				Collections.shuffle(CardFactory.newCardsWithSpecial().getCards());
			}

		}
 	*/

	/**
	 * Zählmethode für die Anzahl der Karten eines Spielers.
	 * @param persons
	 * 		Bekommt den gesuchten Personentyp übergeben.
	 * @param player
	 * 		Bekommt den Spieler übersucht.
	 * @return Gibt die Anzahl der Karten zurück.
	 */
	public int getAmountOf(PersonType persons, PlayerState player){
		return (int) player.getCards().getCards().stream()
				.filter(item ->
						item instanceof Person && ((Person) item).getPersonType().equals(persons)
				)
				.count();
	}

}
