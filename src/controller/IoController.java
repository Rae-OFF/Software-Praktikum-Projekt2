package controller;

import model.*;

import javax.xml.transform.Result;
import java.io.*;
import java.util.List;

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
		return null;
    }
	/*
	public CardStack loadCardDeck(String path) {
		 final String COMMA_DELIMITER = ",";
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
		while((line = reader.readLine())!=null){
		    String[] cardSpecs = line.split(",");
		    String type = cardSpecs[0];

		    for(String spec : cardSpecs){
		    	System.out.println(cardSpecs);

			}
		}

		return cards;
	}

	private Card genCard(String name, String variant, int coins){
		switch (name){
			case "Expedition":
				break;
			case "Tax Increase":
				break;
			case "Admiral":
				break;
			case "Governor":
				break;
			case "Jack of all Trades":
				break;
			case "Jester":
			case "Mademoiselle":
			case "Pirate":
			case "Priest":
			case "Sailor":
			case "Settler":
			case "Trader":
			case "Flute":
			case "Frigate":
			case "Galleon":
			case "Pinnace":
			case "Skiff":
		}
	}

	private Expedition genExpedition(String variant, int coins){
		switch (variant){
			case "3 Different (5P only)":

		}
	}

	 */
}
