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

	/**
	 * Konstruktor.
	 * @param mainController
	 * 		Bekommt den MainController übergeben.
	 */
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
	public void load() throws IOException, ClassNotFoundException{
		String path =  System.getProperty("user.dir")+"\\PausedGame";
		FileInputStream fileIn = new FileInputStream(path);
		ObjectInputStream objIn = new ObjectInputStream(fileIn);
		Game loadedGame = (Game) objIn.readObject();
		fileIn.close();
		objIn.close();
		mainController.getGameSystem().setCurrentGame(loadedGame);
	}

	/**
	 * Speichert einen Spielstand.
	 */
	public void save() throws IOException{
	    Game currentGame = mainController.getGameSystem().getCurrentGame();
	    if(!currentGame.isOngoing()){
	    	return;
		}
	    String path =  System.getProperty("user.dir")+"\\PausedGame";;
		FileOutputStream fileOut = new FileOutputStream(path);
		ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
		objOut.writeObject(mainController.getGameSystem().getCurrentGame());
		objOut.close();
		fileOut.close();
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

		List<PlayerState> players = move.getPlayers();

		PlayerState playerState1 = players.get(0);

		PlayerState playerState2 = players.get(1);

		Player player1 = playerState1.getPlayer();
		Player player2 = playerState2.getPlayer();

		Action action = move.getAction();

		String zonked;

		if(action != null){
			String actionMessage = "";

			actionMessage = "[Action] " + "Type: " + action.getActionType().toString();

			if(action.getAffectedCard() != null)
			{
				actionMessage = actionMessage + " " + action.getAffectedCard().toString();
			}

			writer.println(actionMessage);
		}

		String player1Message = "|" + player1.getName() + "|" + " VPoints: " + playerState1.getVictoryPoints() + ", Coins: " + playerState1.getCoins().getSize() + ", Cards: " + playerState1.getCards().getSize();
		String player2Message = "|" + player2.getName() + "|" + " VPoints: " + playerState2.getVictoryPoints() + ", Coins: " + playerState2.getCoins().getSize()+ ", Cards: " + playerState2.getCards().getSize();

		if(activePlayer.equals(playerState1)){
			player1Message = player1Message + " #ActivePlayer#";
		}
		else{
			player2Message = player2Message + " #ActivePlayer#";
		}

		if(actor.equals(playerState1)){
			player1Message = player1Message + " #Actor#";
		}

		else{
			player2Message = player2Message + " #Actor#";
		}



		writer.println(player1Message);
		writer.println(player2Message);


		String stateMessage = "[State "  + count  + "]" + " CardPileSize: " +cardPileSize + " HarbourSize: " + harbourSize + " DiscardPileSize: " + discardPileSize;

		writer.println(stateMessage);
		//writer.write(message);
		writer.println();

		writer.close();
	}

	/**
	 * Protokoll des Spiels.
	 * @param message Bekommt einen String übergeben.
	 */
	public void log(String message){

		String currentPath = System.getProperty("user.dir");
		String logFile = currentPath+"\\game.log";
		PrintWriter writer;
		try {
			writer = new PrintWriter(new FileOutputStream(logFile, true));
		}catch(IOException e){
			System.out.println(e.getMessage());
			return;
		}

		writer.println(message);

		writer.println();

		writer.close();

	}


	/**
	 * Lädt ein Kartendeck.
	 * @param path
	 * 		Bekommt den Ort an dem sich das Kartendeck befindet übergeben.
	 */

	public CardStack loadCardDeck(String path, int playerNumber) {
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

			Card newCard = genCard(type,variant, coins, playerNumber);
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


	private Expedition genExpedition(String variant, int coins, int playerNumber){
		Expedition result;
		Map<PersonType, Integer> requirements= new HashMap<PersonType, Integer>();
		int victoryPoints=0;

		switch (variant){
			case "3 Different (5P only)":
				if(playerNumber>4) {
				    requirements.put(PersonType.SETTLER, 0);
					requirements.put(PersonType.CAPTAIN, 1);
					requirements.put(PersonType.PRIEST, 2);
					victoryPoints=5;
				}else{
					return null;
				}
				break;
			case "House Pair":
				requirements.put(PersonType.SETTLER, 2);
				requirements.put(PersonType.PRIEST, 0);
				requirements.put(PersonType.CAPTAIN, 0);
				victoryPoints=4;
				break;
			case "Cross Pair":
				requirements.put(PersonType.SETTLER, 0);
				requirements.put(PersonType.PRIEST, 2);
				requirements.put(PersonType.CAPTAIN, 0);
				victoryPoints=4;
				break;
			case "Anchor Pair":
				requirements.put(PersonType.SETTLER, 0);
				requirements.put(PersonType.PRIEST, 0);
				requirements.put(PersonType.CAPTAIN, 2);
				victoryPoints=4;
				break;
			case "Cross Pair + House":
				requirements.put(PersonType.SETTLER, 1);
				requirements.put(PersonType.PRIEST, 2);
				requirements.put(PersonType.CAPTAIN, 0);
				victoryPoints=6;
				break;
			case "Anchor Pair + House":
				requirements.put(PersonType.SETTLER, 1);
				requirements.put(PersonType.PRIEST, 0);
				requirements.put(PersonType.CAPTAIN, 2);
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
		newShip =new Ship(colour, coins, swords);
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

	private Card genCard(String name, String variant, int coins, int playerNumber){
	    Card result=null;
		switch (name){
			case "Expedition":
				result = genExpedition(variant, coins, playerNumber);
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
