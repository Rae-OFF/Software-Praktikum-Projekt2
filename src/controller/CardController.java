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

	/**
	 * Konstruktor.
	 * @param mainController
	 * 		Bekommt den MainController übergeben.
	 */
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
	 */
	public void execAdmiral(Move move, PlayerState player) {
			List<Card> harbourCards = move.getHarbour().getCards();

			if (harbourCards.size() >= 5) {

				CardStack stack = player.getCoins(); // get player's coin stack

				stack.pushList(gameController.popCardPile(move, 2)); // add one coin (taken from top card of the card pile) to player's stack
			}

	}

	/**
	 * Führt den Jester Effekt aus (liegen 0 Karten in der Hafenauslage, bekommt der Spieler dieser Karte 1 Münze).
	 * @param move
	 * 		Bekommt den Zug übergeben.
	 */
	public void execJester(Move move) {

		List<PlayerState> players = move.getPlayers();
		PlayerState player = move.getActivePlayer();

		for(PlayerState playerState : players){   // check all player's
			List<Card> playerCards = playerState.getCards().getCards(); // access all player's hand
			for(Card card: playerCards ){
				if(card instanceof Person && ((Person) card).getPersonType().equals(JESTER)){
					player.getCoins().pushList(gameController.popCardPile(move, 1));
				}
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
			coinsMoreThan12(move);
			if(((TaxIncrease) card).isTypeSwords()){
				taxIncreaseOfMaxSwords(move, action);
			}else{
				taxIncreaseOfMinShields(move, action);
			}
			move.getDiscardPile().push(card);
		}else if(card instanceof Expedition){

			move.getExpeditionPile().push(card);  //if is Expedition, put it in expedition's pile

		}else if(card instanceof Ship){
			// getGameController().getPossibleActions(move);   alternatively can use this method
			move.setShipToDefend(card);

		}else{      // in case of a Person

			move.getHarbour().push(card);

		}
	}

	public int getBuyLimitFromShips(Move move){
		List<Card> harbour = move.getHarbour().getCards();

		int yellow = 0;
		int blue = 0;
		int green = 0;
		int red = 0;
		int black = 0;

		for(Card card : harbour){
			if(card instanceof Ship){
				Ship ship = (Ship) card;

				if(ship.getColour() == Colour.YELLOW && yellow == 0){
					yellow++;
				}
				else if(ship.getColour() == Colour.BLUE && blue == 0){
					blue++;
				}
				else if(ship.getColour() == Colour.GREEN && green == 0){
					green++;
				}
				else if(ship.getColour() == Colour.RED && red == 0){
					red++;
				}
				else if(ship.getColour() == Colour.BLACK && black == 0){
					black++;
				}
			}
		}

		int sum = yellow + blue + green + red + black;

		if(sum == 5){
			return 3;
		}
		else if(sum == 4){
			return 2;
		}

		else {
			return 1;
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
			int numOfCoins = playerState.getCoins().getSize();
			if (numOfCoins >= 12) {
				move.getDiscardPile().pushList(playerState.getCoins().popList(numOfCoins/2));
			}
		}
	}

	/**
	 * Steuererhöhung mit max. Schwertern.
	 * @param move
	 * 		Bekommt einen Zug übergeben.
	 * @param action
	 * 		Bekommt eine Aktion übergeben.
	 */
	public void taxIncreaseOfMaxSwords(Move move, Action action) {

		List<PlayerState> players = move.getPlayers();

		int maxSwords = -1;
		List<PlayerState> maxPlayers = new ArrayList<>();

				for (PlayerState playerState : players) {
					List<Card> playerHand = playerState.getCards().getCards();
					int playerSwords = 0;
					for (Card card : playerHand) {

						if (card instanceof Person && (((Person) card).getPersonType().equals(SAILOR) || ((Person) card).getPersonType().equals(PIRATE))) {
							playerSwords += ((Person) card).getSwords();
						}
					}

					if (playerSwords > maxSwords) {     //get the maximum number
						maxSwords = playerSwords;
						maxPlayers.clear();
						maxPlayers.add(playerState);
					}
					else if(playerSwords == maxSwords){
						maxPlayers.add(playerState);
					}
				}

				for(PlayerState tplayer : maxPlayers){
					tplayer.getCoins().pushList(gameController.popCardPile(move,1));
				}

		}

	/**
	 * Steuererhöhung mit min. Siegpunkten.
	 * @param move
	 * 		Bekommt einen Zug übergeben.
	 * @param action
	 * 		Bekommt eine Aktion übergeben.
	 */
	public void taxIncreaseOfMinShields(Move move, Action action) {

		List<PlayerState> players = move.getPlayers();
		int minPoints = 999;
		List<PlayerState> minPlayers = new ArrayList<>();

		for (PlayerState playerState : players) {

			int playerPoints = playerState.getVictoryPoints();

			if (playerPoints < minPoints) {              //get the minimum number
				minPoints = playerPoints;
				minPlayers.clear();
				minPlayers.add(playerState);
			}

			else if(playerPoints == minPoints){
				minPlayers.add(playerState);
			}

		}

		for(PlayerState tplayer : minPlayers){
			tplayer.getCoins().pushList(gameController.popCardPile(move,1));
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

		Ship ship = (Ship) action.getAffectedCard();

		int bonus = getTraderBonus(actor,ship);

		if(player.equals(actor)) {  // if active place wants to take the ship

			CardStack stack = player.getCoins();
			stack.pushList(gameController.popCardPile(move,((Ship) action.getAffectedCard()).getCoins() + bonus));

			move.getDiscardPile().push(move.getHarbour().getCard(action.getAffectedCard()));

		}else{   // if other players want to take the ship

			actor.getCoins().pushList(gameController.popCardPile(move,((Ship) action.getAffectedCard()).getCoins() + bonus));

			player.getCoins().pushList(actor.getCoins().popList(1));

			move.getDiscardPile().push(move.getHarbour().getCard(action.getAffectedCard()));

		}
	}

	/**
	 * Führt ausrechnen des Trader bonus fuer einen Spieler aus, der sich ein Schiff kauft.
	 *
	 * @param player
	 * 		Bekommt den handelnden Spieler übergeben.
	 *
	 * @param ship
	 * 		Bekommt das Schiff übergeben, welches gekauft wird.
	 */
	public int getTraderBonus(PlayerState player, Ship ship){
		int bonus = 0;
		List<Card> cards = player.getCards().getCards();

		for(Card card : cards){
			if(card instanceof Person && ((Person) card).getPersonType().equals(TRADER)){
				Colour colour = ((Person) card).getColour();
				if(colour.equals(ship.getColour())){
					bonus++;
				}
			}
		}

		return bonus;
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

		int costs = ((Person)action.getAffectedCard()).getPrice();

		int mademoiselle = mainController.getCardController().getAmountOf(MADEMOISELLE, actor);

		costs = costs - mademoiselle;

		if(costs < 0){
			costs = 0;
		}

		Card card = (Person)action.getAffectedCard();

		if(player.getPlayer().equals(actor.getPlayer())){

			CardStack stack = actor.getCoins();

			move.getDiscardPile().pushList(stack.popList(costs));

			actor.getCards().push(move.getHarbour().getCard(card));
		}

		else if(!player.getPlayer().equals(actor.getPlayer()))
		{
			player.getCoins().push((actor.getCoins().pop()));
			CardStack stack = actor.getCoins();

			move.getDiscardPile().pushList(stack.popList(costs));

			actor.getCards().push(move.getHarbour().getCard(card));
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
			int limit = getBuyLimitFromShips(move);
			move.setBuyLimit(limit);

	}

	public void acceptShip(Move move, Action action){
		move.getHarbour().push(move.getShipToDefend());
		move.setShipToDefend(null);
		int limit = getBuyLimitFromShips(move);
		move.setBuyLimit(limit);
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
			PlayerState player = move.getActor();
			Card exped = action.getAffectedCard();  // get this Expedition card from pile, which the player wants to exchange.
			Map<PersonType, Integer> require= ((Expedition) exped).getRequirements(); //get requirements of this Expedition
			int captains = require.get(CAPTAIN);
			int priests = require.get(PRIEST);
			int settlers = require.get(SETTLER);
			List<Card> materials = new ArrayList<>();
			List<Card> cards = player.getCards().getCards();
			for(Card card : cards){
				if(card instanceof Person){
					Person person = (Person) card;
					if(person.getPersonType().equals(CAPTAIN) && captains > 0){
						captains--;
					}
					else if(person.getPersonType().equals(PRIEST) && priests > 0){
						priests--;
					}
					else if(person.getPersonType().equals(SETTLER) && settlers > 0) {
						settlers--;
					}else continue;
					materials.add(person);
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
	 * @param person
	 * 		Bekommt den gesuchten Personentyp übergeben.
	 * @param player
	 * 		Bekommt den Spieler übersucht.
	 * @return Gibt die Anzahl der Karten zurück.
	 */
	public int getAmountOf(PersonType person, PlayerState player){
		return (int) player.getCards().getCards().stream()
				.filter(item ->
						item instanceof Person && ((Person) item).getPersonType().equals(person)
				)
				.count();
	}

}
