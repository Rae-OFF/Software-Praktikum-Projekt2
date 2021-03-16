package controller;

import model.Action;

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
	public Action getAction() {
		return null;
	}

}
