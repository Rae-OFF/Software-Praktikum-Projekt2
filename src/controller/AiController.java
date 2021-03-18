package controller;

import model.Action;
import model.Move;
import model.PlayerState;

/**
 * Abstrakte Klasse für alle KIs.
 */
public abstract class AiController {

	private MainController mainController;

	private EasyAi easyAi;

	/**
	 *
	 * @return Gibt die Aktion zurück die die KI ausführen soll.
	 */
	public abstract Action getAction(Move move);


	public void setAsActor(PlayerState ai){

	}

}
