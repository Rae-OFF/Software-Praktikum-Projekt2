package controller;

import model.Action;
import model.Move;
import model.Player;
import model.PlayerState;

import java.util.List;

/**
 * Verwaltet die Spieler.
 */
public class PlayerController {

	private MainController mainController;

	/**
	 * Führt eine Aktion aus.
	 * @param action
	 * 		Bekommt eine Aktion.
	 */
	public void executeAction(Action action) {

	}

	/**
	 * Setzt die Spieler Reihenfolge.
	 * @param playerList
	 * 		Bekommt eine Liste der Spieler.
	 * @param random
	 * 		Bekommt übergeben ob die Reihenfolge zufällig sein soll(true) oder nicht(false).
	 */
	public void setPlayerOrder(List<Player> playerList, boolean random) {

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
		return 0;
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
		return 0;
	}

}
