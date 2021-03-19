package controller;

import model.*;

import javax.xml.transform.Result;
import java.io.*;
import java.util.*;

/**
 * Verwaltet die IO.
 */
public class IoController {

	private MainController mainController;

	private PrintWriter writer;


	public IoController(MainController mainController) {

		this.mainController = mainController;

		String currentPath = System.getProperty("user.dir");
		String logFile = currentPath+"\\game.log";
		PrintWriter writer;
		try {
			writer = new PrintWriter(new FileOutputStream(logFile, false));
		}catch(IOException e){
			System.out.println(e.getMessage());
			return;
		}


		writer.close();


	}

	/**
	 * Lädt einen Spielstand.
	 */
	public void load() {

	}

	/**
	 * Speichert einen Spielstand.
	 */
	public void save() {

	}

	/**
	 *
 	 */
	public void log(Move move, int count){
		String currentPath = System.getProperty("user.dir");
		String logFile = currentPath+"\\game.log";

		PrintWriter writer;
		try {
			writer = new PrintWriter(new FileOutputStream(logFile, true));
		}catch(IOException e){
			System.out.println(e.getMessage());
			return;
		}

		String currentActor = move.getActor().getPlayer().getName();
		String actorHarbour = move.getHarbour().getCards().toString();
		int harbourSize = move.getHarbour().getSize();
		int discardPileSize = move.getDiscardPile().getSize();
		int cardPileSize = move.getCardPile().getSize();

		PlayerState activePlayer = move.getActivePlayer();
		PlayerState actor = move.getActor();

		//List<PlayerState>

		Action action = move.getAction();

		String zonked;

		String player1 = "Name: " ;

		String actionMessage = "[Action] " + "ActivePlayer:" + "Type: " + action.getActionType().toString() + action.getAffectedCard().toString();
		String stateMessage = "[State] " +  "Count: " + count + " CardPileSize: " +cardPileSize + " HarbourSize: " + harbourSize + " DiscardPileSize: " + discardPileSize;

		writer.println(stateMessage);
		//writer.write(message);

		writer.close();
	}


	/**
	 * Lädt ein Kartendeck.
	 * @param path
	 * 		Bekommt den Ort an dem sich das Kartendeck befindet übergeben.
	 */

	public CardStack loadCardDeck(String path) {
		CardStack cards = new CardStack();
		List<Card> deck = cards.getCards();
		BufferedReader reader;
		try{
			reader = new BufferedReader(new FileReader(path));
		}catch(IOException e){
			System.out.println(e.getMessage());
			return null;
		}
		String line;
		try {
			reader.readLine();//erste Zeile skippen
			line = reader.readLine();//erste Zeile skippen
		}catch(IOException e){
			System.out.println(e.getMessage());
			return null;
		}
		while(line!=null){
		    String[] cardSpecs = line.split(",");
		    String type = cardSpecs[0];
			String variant = cardSpecs[1];
			int coins = Integer.parseInt(cardSpecs[2]);

			Card newCard = genCard(type,variant, coins);
			if(newCard!=null) {
				deck.add(newCard);
			}
			try {
				line = reader.readLine();//erste Zeile skippen
			}catch(IOException e){
				System.out.println(e.getMessage());
			}
		}

		return cards;
	}


	private Expedition genExpedition(String variant, int coins){
		Expedition result;
		Map<PersonType, Integer> requirements= new HashMap<PersonType, Integer>();
		int victoryPoints=0;

		switch (variant){
			case "3 Different (5P only)":
				if(mainController.getGameSystem().getCurrentGame().getPlayers().size()>4) {
				    requirements.put(PersonType.SETTLER, 0);
					requirements.put(PersonType.CAPTAIN, 1);
					requirements.put(PersonType.PRIEST, 2);
					victoryPoints=5;
				}else{
					return null;
				}
				break;
			case "House Pair":
				requirements.put(PersonType.SETTLER, 0);
				requirements.put(PersonType.SETTLER, 1);
				victoryPoints=4;
				break;
			case "Cross Pair":
				requirements.put(PersonType.PRIEST, 0);
				requirements.put(PersonType.PRIEST, 1);
				victoryPoints=4;
				break;
			case "Anchor Pair":
				requirements.put(PersonType.CAPTAIN, 0);
				requirements.put(PersonType.CAPTAIN, 1);
				victoryPoints=4;
				break;
			case "Cross Pair + House":
				requirements.put(PersonType.PRIEST, 0);
				requirements.put(PersonType.PRIEST, 1);
				requirements.put(PersonType.SETTLER, 1);
				victoryPoints=6;
				break;
			case "Anchor Pair + House":
				requirements.put(PersonType.CAPTAIN, 0);
				requirements.put(PersonType.CAPTAIN, 1);
				requirements.put(PersonType.SETTLER, 1);
				victoryPoints=6;
				break;
		}
		result = new Expedition(requirements, coins,victoryPoints);
		return result;
	}

	private Ship genShip(String type, String force, int coins){
		Colour colour = null;
		int swords=0;
		Ship newShip =null;
		switch(type) {
			case "Pinnace":
				colour=Colour.YELLOW;
				break;
			case "Flute":
				colour=Colour.BLUE;
				break;
			case"Skiff":
				colour=Colour.GREEN;
				break;
			case"Frigate":
				colour=Colour.RED;
				break;
			case"Galleon":
				colour=Colour.BLACK;
				break;
		}
		if(force.equals("Pirate")){
			swords=212;
		}else{
			swords=Character.getNumericValue(force.charAt(0));
		}
		newShip =new Ship();
		newShip.setColour(colour);
		newShip.setForce(swords);
		newShip.setCoins(coins);
		return newShip;
	}

	private Person genPerson(String type, String variant, int coins){
		int victoryPoints=0;
		int swords=0;
		Person result;
		PersonType personType = null;
		Colour colour = null;
		String name;

		switch(type){
			case "Admiral":
				personType=PersonType.ADMIRAL;
				break;
			case "Captain":
				personType=PersonType.CAPTAIN;
				break;
			case "Governor":
			    personType=PersonType.GOVERNOR;
			    break;
			case "Jack of All Trades":
				personType=PersonType.JACK_OF_ALL_TRADES;
				break;
			case "Jester":
				personType=PersonType.JESTER;
				break;
			case "Mademoiselle":
				personType=PersonType.MADEMOISELLE;
				break;
			case "Pirate":
				personType=PersonType.PIRATE;
				break;
			case "Priest":
				personType=PersonType.PRIEST;
				break;
			case "Sailor":
				personType=PersonType.SAILOR;
				break;
			case "Settler":
				personType=PersonType.SETTLER;
				break;
			case "Trader":
				personType=PersonType.TRADER;
				break;
		}
		if(personType!=PersonType.TRADER){
		    victoryPoints=Character.getNumericValue(variant.charAt(0));

		}else{
			String[] traderAttributes=variant.split(" \\+ ");
			String traderColor=traderAttributes[0];
			String traderVp=traderAttributes[1];
			victoryPoints=Character.getNumericValue(traderVp.charAt(0));
			switch(traderColor){
				case"Green":
					colour = Colour.GREEN;
					break;
				case"Yellow":
					colour=Colour.YELLOW;
					break;
				case"Blue":
					colour=Colour.BLUE;
					break;
				case"Black":
					colour=Colour.BLACK;
					break;
				case"Red":
					colour=Colour.RED;
					break;
			}
		}
		result=new Person();
		result.setValues(coins, victoryPoints, swords);
		result.setMetaData("", colour, personType);
		return result;
	}

	private TaxIncrease genTaxIncrease(String condition){
		TaxIncrease res = null;
		switch(condition){
			case "Max Sword":
				res = new TaxIncrease(false);
				break;
			case "Min VP":
				res = new TaxIncrease(true);
				break;
		}
		return res;
	}

	private Card genCard(String name, String variant, int coins){
	    Card result=null;
		switch (name){
			case "Expedition":
				result = genExpedition(variant, coins);
				break;
			case "Tax Increase":
			    result = genTaxIncrease(variant);
				break;
			case "Admiral":
			case "Governor":
			case "Captain":
			case "Jack of All Trades":
			case "Jester":
			case "Mademoiselle":
			case "Pirate":
			case "Priest":
			case "Sailor":
			case "Settler":
			case "Trader":
				result=genPerson(name, variant, coins);
				break;
			case "Flute":
			case "Frigate":
			case "Galleon":
			case "Pinnace":
			case "Skiff":
				result = genShip(name, variant,coins);
				break;
		}
		return result;
	}
}
