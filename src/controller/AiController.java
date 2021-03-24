package controller;

import model.Action;
import model.Move;
import model.PlayerState;

/**
 * Abstrakte Klasse für alle KIs.
 */
public abstract class AiController {

	/**
	 *
	 * @return Gibt die Aktion zurück die die KI ausführen soll.
	 */
	public abstract Action getAction(Move move);

	/**
	 * Setzt eine AI als Spieler der am Zug ist.
	 * @param aiState
	 * 		Bekommt einen Spielerzustand übergeben.
	 */
	public void setAsActor(PlayerState aiState){

	}

}
