package controller;

import model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//import java.util.Map;

import static model.ActionType.SKIP;
import static model.ActionType.TAKE_SHIP;
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
		Card topCard = move.getCardPile().getCards().get(0);  //get coin from top card of card pile
		Card secCard = move.getCardPile().getCards().get(1);


		if(harbourCards.size()>=5){

			CardStack stack = player.getCoins();
			List<Card> coins = stack.getCards();
			coins.add(topCard);
			coins.add(secCard);
			player.setCoins(stack);
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

		List<Card> harbourCards = move.getHarbour().getCards();
		List<PlayerState> players = move.getPlayers();
		PlayerState player = move.getActivePlayer();
		Card topCard = move.getCardPile().getCards().get(0);

		if(mainController.getGameSystem().getCurrentGame().isJesterEnabled()){
			if(action.getActionType().equals(SKIP) && move.isPhase1()){

				for(PlayerState allPlayer: players){
					List<Card> playercards = allPlayer.getCards().getCards();

						if(playercards.contains(JESTER)){
							CardStack stack = allPlayer.getCoins();
							List<Card> coins = stack.getCards();
							coins.add(topCard);
							player.setCoins(stack);
						}
				}

			}else {
				CardStack stack = player.getCoins();
				List<Card> coins = stack.getCards();
				coins.add(topCard);
				player.setCoins(stack);
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

		Card card = move.getCardPile().getCards().get(0); // 1st card draw from card pile

		if (card instanceof Ship) {

			for (Card trader : playercards) {
				if (trader.equals(TRADER) && ((Person) trader).getColour().equals(((Ship) card).getColour())) {

					CardStack stack = player.getCoins();
					List<Card> coins = stack.getCards();
					Card topCard = move.getCardPile().getCards().get(0);
					coins.add(topCard);
					player.setCoins(stack);
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

		List<Card>cardPile = move.getCardPile().getCards();
		Card currentCard = cardPile.get(0);
		List<PlayerState> playerStateList = move.getPlayers();
		int maxSwords;
		int minShild;
		List<PlayerState> maxSworsPlayerState = new ArrayList<>();
		List<PlayerState> minSworsPlayerState = new ArrayList<>();

		/*  not finished. different cards will be discussed and make different action.
		if(currentCard instanceof TaxIncrease) {
			for (PlayerState playerState : playerStateList) {
				int numOfCoins = playerState.getCoins().getCards().size();
				if (numOfCoins >= 12) {
					for (int i = 0; i < (numOfCoins + 1) / 2; i++) {
						playerState.getCoins().getCards().remove(i);
						i--;
					}
				}
			}
			if (((TaxIncrease) currentCard).isTypeSwords()) {
				maxSwords = playerStateList.get(0).getCards();
				for (PlayerState playerState : playerStateList) {
					if (playerState.getCoins().getCards().size() => maxSwords){
						maxSwords = playerState.getCoins().getCards().size();
					}
				}

				for (PlayerState playerState : playerStateList) {
					if (playerState.getCoins().getCards().size() == maxSwords) {
						maxSworsPlayerState.add(playerState);
					}
				}

				for (PlayerState playerState : maxSworsPlayerState) {
					Card newCard = move.getCardPile().getCards().get(0);
					playerState.getCoins().getCards().add(newCard);
					move.getCardPile().getCards().remove(0);
					playerState.getCoins().getCards().add(newCard);
				}
			} else {
				minShild = playerStateList.get(0).getCoins().getCards().size();
				for (PlayerState playerState : playerStateList) {
					if (playerState.getCoins().getCards().size() <= minShild) {
						minShild = playerState.getCoins().getCards().size();
					}
				}

				for (PlayerState playerState : playerStateList) {
					if (playerState.getCoins().getCards().size() == maxSwords) {
						minSworsPlayerState.add(playerState);
					}
				}

				for (PlayerState playerState : minSworsPlayerState) {
					Card newCard = move.getCardPile().getCards().get(0);
					playerState.getCoins().getCards().add(newCard);
					move.getCardPile().getCards().remove(0);
					playerState.getCoins().getCards().add(newCard);
				}
			}
		}
		if(currentCard instanceof Ship){

		} else{

		}
 */


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

		if(move.isPhase1()) {

			move.getActor().getCoins().getCards().add(move.getCardPile().getCards().get(0));
		//	move.getDiscardPile().getCards().add(card);
			move.getCardPile().getCards().remove(move.getCardPile().getCards().get(0));
		}else{



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
		int money = getPlayerController().getCoins(player,move);
		int personPrice = ((Person)action.getAffectedCard()).getPrice();

		if(money >= personPrice ){

			CardStack stack = player.getCoins();
			List<Card> coins = stack.getCards();
			List<Card> disCard = move.getDiscardPile().getCards();

			for(int i=0; i< personPrice; i++) {
				coins.remove(0);
				disCard.add(coins.get(0));
			}
			player.setCoins(stack);
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
			List<Card> cardPile = move.getCardPile().getCards();
			if (shipCard.getForce() == 0) {
				move.getHarbour().getCards().add(shipCard);

			} else {
				for (Card card : cardsInHand) {
					if (card instanceof Person) {
						if ((((Person) card).getPersonType().equals(PIRATE) || ((Person) card).getPersonType().equals(SAILOR)) && shipCard.getForce() <= ((Person) card).getSwords()) {
							for (int i = 0; i < shipCard.getCoins(); i++) {
								move.getActivePlayer().getCoins().getCards().add(move.getCardPile().getCards().get(0));
								move.getCardPile().getCards().remove(move.getCardPile().getCards().get(0));
							}
							move.getDiscardPile().getCards().add(card);
							break;
						}
					}
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


	public void startExpedition(Move move, Action action) {  // not finished

		PlayerState player = move.getActivePlayer();

		List<Card> ExpedPoints = move.getExpeditionPile().getCards();


	}



	/**
	 * Führt das überspringen eines Zuges aus.
	 * @param move
	 * 		Bekommt den Zug übergeben.
	 * @param action
	 * 		Bekommt die Aktion übergeben.
	 */
	public void skip(Move move, Action action) {

		PlayerState player = move.getActivePlayer();

		if(action.getActionType().equals(SKIP)) {
			if (player != move.getActor()) {
				getGameController().changeActor(move);

			} else {
				getGameController().changeActivePlayer(move);
			}

		}
	}


	public List<Card>  initCards(){

		List<Card> cards = new ArrayList<>();
		//add yellow/green/blue ships with 1 force
		for (int i = 0; i < 4; i++) {
			cards.add(new Ship(Colour.YELLOW, 1, 1));
			cards.add(new Ship(Colour.BLUE, 2, 1));
			cards.add(new Ship(Colour.GREEN, 1, 1));
		}

		for (int i = 0; i < 3; i++) {
//yellow ships with force 2, 4
			cards.add(new Ship(Colour.YELLOW, 2, 2));
			cards.add(new Ship(Colour.YELLOW, 3, 4));
//green ships with force 3, 5
			cards.add(new Ship(Colour.GREEN, 2, 3));
			cards.add(new Ship(Colour.GREEN, 3, 5));
//green ships with force 2, 5
			cards.add(new Ship(Colour.BLUE, 2, 2));
			cards.add(new Ship(Colour.BLUE, 3, 5));
//red ships with force 1, 3
			cards.add(new Ship(Colour.RED, 1, 1));
			cards.add(new Ship(Colour.RED, 3, 3));
//black ships with force 2, 4
			cards.add(new Ship(Colour.BLACK, 2, 2));
			cards.add(new Ship(Colour.BLACK, 3, 4));
		}

		for (int i = 0; i < 2; i++) {
//black ships with force 6, skull
			cards.add(new Ship(Colour.RED, 3, 6));
			cards.add(new Ship(Colour.RED, 3, 100));
//black ships with force 7, skull
			cards.add(new Ship(Colour.BLACK, 3, 7));
			cards.add(new Ship(Colour.BLACK, 3, 100));

		}
//add ADMIRAL - 6 cards (not sure about detailed number of type)

		Person person = new Person();
		person.setMetaData(ADMIRAL.name(),null, ADMIRAL);
		person.setValues(5,1,0);
		cards.add(person); cards.add(person);

	/**
	 * Führt das mischen der Karten aus.
	 * @param move
	 * 		Bekommt den Zug übergeben.
	 * @param action
	 * 		Bekommt die Aktion übergeben.
	 */
		person.setValues(7,2,0);
		cards.add(person); cards.add(person);

		person.setValues(9,3,0);
		cards.add(person); cards.add(person);

// add TRADER - 10 cards (not sure about detailed number of colors)
		Person trader = new Person();

		trader.setValues(3,1,0);
		trader.setMetaData(TRADER.name(),Colour.GREEN,TRADER);
		cards.add(trader);
		cards.add(trader);

		trader.setMetaData(TRADER.name(),Colour.BLUE,TRADER);
		cards.add(trader);

		trader.setMetaData(TRADER.name(),Colour.BLACK,TRADER);
		cards.add(trader);

		trader.setMetaData(TRADER.name(),Colour.RED,TRADER);
		cards.add(trader);

		trader.setMetaData(TRADER.name(),Colour.YELLOW,TRADER);
		cards.add(trader);

		trader.setValues(5,2,0);
		trader.setMetaData(TRADER.name(),Colour.YELLOW,TRADER);
		cards.add(trader);
		cards.add(trader);

		trader.setMetaData(TRADER.name(),Colour.BLUE,TRADER);
		cards.add(trader);
		cards.add(trader);

// add SETTLERS
		Person settler = new Person();

		settler.setValues(4,1,0);
		settler.setMetaData(SETTLER.name(),null,SETTLER);

// add Captain
		Person captain = new Person();

		captain.setValues(4,1,0);
		captain.setMetaData(CAPTAIN.name(), null,CAPTAIN);

// add Priest
		Person priest = new Person();

		priest.setValues(4,1,0);
		priest.setMetaData(PRIEST.name(), null,PRIEST);

// add all with 5 cards (ALL SAME VALUE?)
		for(int i=0; i<5; i++){
			cards.add(captain);
			cards.add(settler);
			cards.add(priest);
		}

// add JackOfAllTrader  - 3 cards (ALL SAME VALUE?)
		Person jack = new Person();

		jack.setValues(6,1,0);
		jack.setMetaData(PersonType.JACK_OF_ALL_TRADES.name(), null,PersonType.JACK_OF_ALL_TRADES);
		cards.add(jack);
		cards.add(jack);
		cards.add(jack);

// add Pirates - 3 cards (COMPLETE)
		Person pirate = new Person();

		pirate.setMetaData(PIRATE.name(), null, PIRATE);
		pirate.setValues(5,1,2);
		cards.add(pirate);

		pirate.setValues(7,2,2);
		cards.add(pirate);

		pirate.setValues(9,3,2);
		cards.add(pirate);

// add Jester - 3 cards (2 LOST)
		Person jester = new Person();

		jester.setMetaData(PersonType.JESTER.name(), null,PersonType.JESTER);
		jester.setValues(5,1,0);
		cards.add(jester);

		jester.setValues(7,2,0);
		cards.add(jester);

		jester.setValues(9,3,0);
		cards.add(jester);

// add Sailor - 10 cards (1 LOST)
		Person sailor = new Person();

		sailor.setMetaData(SAILOR.name(), null, SAILOR);
		sailor.setValues(3,1,1);
		cards.add(sailor); cards.add(sailor); cards.add(sailor);

		sailor.setValues(5,2,1);
		cards.add(sailor);cards.add(sailor); cards.add(sailor);

		sailor.setValues(7,3,1);
		cards.add(sailor); cards.add(sailor); cards.add(sailor);

// add	Mademoiselles - 4 cards (COMPLETE)

		Person mademoiselles = new Person();

		mademoiselles.setMetaData(PersonType.MADEMOISELLE.name(), null,PersonType.MADEMOISELLE);
		mademoiselles.setValues(7,2,0);
		cards.add(mademoiselles);
		cards.add(mademoiselles);
		mademoiselles.setValues(9,3,0);
		cards.add(mademoiselles);
		cards.add(mademoiselles);

// add Governors 4 cards (3 LOST)

		Person governor = new Person();

		governor.setMetaData(PersonType.GOVERNOR.name(), null,PersonType.GOVERNOR);
		governor.setValues(9,0,0);
		cards.add(governor);

//add taxIncrease - 4 cards (COMPLETE)

		TaxIncrease taxIncrease = new TaxIncrease(false);
		cards.add(taxIncrease);
		cards.add(taxIncrease);

		taxIncrease.setTypeSwords(true);
		cards.add(taxIncrease);
		cards.add(taxIncrease);

// add Expedition - failed with Map

	/*	Map<PersonType, Integer> requirements = new Map<PersonType.CAPTAIN, 2>();
		Expedition expedition = new Expedition(requirements,2, 4);

		cards.add(expedition);
*/

		return cards;

	}
	public void shuffle(Move move, Action action) {



		Collections.shuffle(initCards());

	}

}
