package controller;

import model.Action;
import model.Move;

/**
 * Klasse für die mittelschwere KI.
 */
public class MediumAi extends AiController {

	private MainController mainController;

	public MediumAi(MainController mainController) {
		this.mainController = mainController;
	}

	@Override
	public Action getAction(Move move){
		return null;
	}

}
