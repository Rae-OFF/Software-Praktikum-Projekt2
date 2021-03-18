package controller;

import model.Action;
import model.Move;

/**
 * Klasse der schweren KI.
 */
public class HardAi extends AiController {

	private MainController mainController;

	public HardAi(MainController mainController) {
		this.mainController = mainController;
	}


	@Override
	public Action getAction(Move move) {
		return null;
	}
}
