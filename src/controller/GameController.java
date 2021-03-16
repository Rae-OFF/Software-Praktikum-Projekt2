package controller;

import model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Verwaltet das Spiel.
 */
public class GameController {

	private MainController mainController;

	/**
	 *
	 * @return Gibt den aktuellen Zug zurück.
	 */
	public Move currentMove() {
		return null;
	}

	/**
	 * Wechselt den aktuellen Spieler zum nächsten Spieler in Phase 2.
	 * @param move
	 * 		Bekommt den Zug übergeben.
	 */
	public void changeActor(Move move) {

		List<PlayerState> players = move.getPlayerState();

		for (int index =0; index< players.size(); index++){

			if(move.getActor().equals(players.get(index))) {
				move.setActor(players.get((index++)%players.size()));
				break;
			}
		}

	}

	/**
	 * Führt das Beenden einer Runde aus.
	 * @param move
	 * 		Bekommt einen Zug übergeben.
	 */
	public void finishRound(Move move) {

	}

	/**
	 * Führt das initialie Starten des Spiels aus (alle Spieler bekommen 3 Münzen, Nachziehstapel etc.)
	 */
	public void init() {

	}

	/**
	 * Generiert einen neuen Zug anhand der übergebenen Aktion.
	 * @param move
	 * 		Bekommt einen Zug.
	 * @param action
	 * 		Bekommt eine Aktion.
	 * @return Gibt einen neuen Zug zurück.
	 */
	public Move generateMove(Move move, Action action) {
		return null;
	}

	/**
	 * Gibt eine Liste der möglichen Aktionen zurück.
	 * @param move
	 * 		Bekommt einen Zug.
	 * @return Gibt eine Liste von Aktionen zurück.
	 */
	public List<Action> getPossibleActions(Move move) {
		return null;
	}

	/**
	 * Setzt einen Zug zurück.
	 * @param move
	 * 		Bekommt einen Zug.
	 */
	public void undo(Move move) {

	}

	/**
	 * Stellt einen Zug wieder her.
	 * @param move
	 * 		Bekommt einen Zug.
	 */
	public void redo(Move move) {

	}

	/**
	 * Mischt den Ablegestapel.
	 * @param move
	 * 		Bekommt einen Zug.
	 */
	public void shuffleDiscardPile(Move move) {

	}

	/**
	 * Beendet das Spiel wenn ein Spieler die Gewinnbedingung erreicht hat.
	 * @param move
	 * 		Bekommt einen Zug.
	 */
	public void finishGame(Move move) {

	}

	/**
	 * Gibt zurück ob ein Spieler Phase 1 durch zwei gleichfarbige Schiffe in der Hafenauslage beendet hat.
	 * @param move
	 * 		Bekommt einen Zug.
	 * @return Gibt zurück ob die Hafenauslage zurückgelegt werden muss (true) oder ob der Spieler
	 * 		weiterspielen kann.
	 */
	public boolean isZonked(Move move) {
		return false;
	}

	/**
	 * Entscheided ob ein Schiff abgewehrt werden soll oder nicht,
	 * @param defend
	 * 		Bekommt übergeben ob es abgewehrt werden soll (true) oder nicht (false).
	 * @param move
	 * 		Bekommt einen Zug übergeben.
	 * @return Gibt eine Aktion zurück.
	 */
	public Action decideShipAction(boolean defend, Move move) {
		return null;
	}

	/**
	 * Wechselt den aktiven Spieler.
	 * @param move
	 * 		Bekommt einen Zug übergeben.
	 */
	public void changeActivePlayer(Move move) {

	}

}
