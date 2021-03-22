package controller;

import model.AINode;
import model.AITree;
import model.Action;
import model.Move;

/**
 * Klasse der schweren KI.
 */
public class HardAi extends AiController {

	private MainController mainController;

	/**
	 * Konstruktor.
	 * @param mainController
	 * 		Bekommt den MainController übergeben.
	 */
	public HardAi(MainController mainController) {
		this.mainController = mainController;
	}


	@Override
	public Action getAction(Move move){

		AINode root = AITree.generateTree(move,mainController,7);

		Action action = AITree.getBestAction(root, move.getActor());

		return action;

	}
}
