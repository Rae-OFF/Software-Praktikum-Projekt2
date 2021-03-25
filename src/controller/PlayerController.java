package controller;

import model.*;

import java.util.Collections;
import java.util.List;

/**
 * Verwaltet die Spieler.
 */
public class PlayerController {

	private MainController mainController;

	/**
	 * Konstruktor.
	 * @param mainController
	 * 		Bekommt den MainController übergeben.
	 */
	public PlayerController(MainController mainController) {
		this.mainController = mainController;
	}

	/**
	 * Führt eine Aktion aus.
	 * @param action
	 * 		Bekommt eine Aktion.
	 */
	public void executeAction(Action action) {
		GameController gameController = mainController.getGameController();
		Move currentMove = gameController.currentMove();
		Move newMove= gameController.generateMove(currentMove, action);
		mainController.getGameSystem().getCurrentGame().setLastMove(newMove);

		if (mainController.getGameViewAUI() != null) {
			mainController.getGameViewAUI().refresh(newMove);
		}
	}

	/**
	 * Setzt die Spieler Reihenfolge.
	 * @param playerList
	 * 		Bekommt eine Liste der Spieler.
	 * @param random
	 * 		Bekommt übergeben ob die Reihenfolge zufällig sein soll(true) oder nicht(false).
	 */
	public List<Player> setPlayerOrder(List<Player> playerList, boolean random) {
		if(!random){
			return playerList;
		}else{
			Collections.shuffle(playerList);
			return playerList;
		}
	}

	/**
	 *
	 * @param move
	 * 		Bekommt einen Zug.
	 * @return Gibt dem Spieler einen Hinweis zurück.
	 */
	public Action getHint(Move move) {
		return null;
	}

	/**
	 * Holt die Siegpunkte eines Spielers in einem Zug zurück.
	 * @param player
	 * 		Bekommt einen Spielerzustand übergeben.
	 * @param move
	 * 		Bekommt einen Zug.
	 * @return Gibt die Anzahl der Siegpunkte zurück.
	 */
	public int getVictoryPoints(PlayerState player, Move move) {
		int points=0;
		for(PlayerState playerState : move.getPlayers()){
			if(playerState==player){
			   	for(Card card : playerState.getCards().getCards()){
					if(card.getClass()==Expedition.class) {
						points+=((Expedition) card).getVictoryPoints();
					}if(card.getClass()==Person.class){
						points += ((Person) card).getVictoryPoints();
					}
				}
			}
		}
		return points;
	}

	/**
	 * Gibt die Anzahl der Münzen des Spielers zurück.
	 * @param player
	 * 		Bekommt einen Spielerzustand übergeben.
	 * @param move
	 * 		Bekommt einen Zug zurück.
	 * @return Gibt die Anzahl der Münzen zurück.
	 */
	public int getCoins(PlayerState player, Move move) {
		for(PlayerState playerState : move.getPlayers()){
			if(playerState==player){
				return playerState.getCoins().getSize();
			}
        }
		return 0;
	}

	/**
	 * Gibt die Anzahl der Expeditionen des Spielers zurück.
	 * @param player
	 * 		Bekommt einen Spielerzustand zurück.
	 * @param move
	 * 		Bekommt einen Zug zurück.
	 * @return Gibt die Anzahl der Expeditionen zurück.
	 */
	public int countExpeditions(PlayerState player, Move move) {
		int expeditions=0;
		for(PlayerState playerState : move.getPlayers()){
			if(playerState==player){
				for(Card card : playerState.getCards().getCards()){
					if(card.getClass()==Expedition.class) {
						expeditions+=1;
					}
				}
			}
		}
		return expeditions;
	}

	/*
	 *
	 * @return
	public PlayerState getActor(){
		return mainController.getGameSystem().getCurrentGame().
	}
	 */

}
