package controller;

import model.AINode;
import model.AITree;
import model.Action;
import model.Move;

/**
 * Klasse für die mittelschwere KI.
 */
public class MediumAi extends AiController {

	private MainController mainController;

	/**
	 * Konstruktor.
	 * @param mainController
	 * 		Bekommt den MainController übergeben.
	 */
	public MediumAi(MainController mainController) {
		this.mainController = mainController;
	}

	/**
	 * Überschreibt die Methode getAction.
	 * @param move
	 * 		Bekommt einen Zug übergeben.
	 * @return Gibt eine Aktion zurück.
	 */
	@Override
	public Action getAction(Move move){

		AINode root = AITree.generateTree(move,mainController,5);

		Action action = AITree.getBestActionMedium(root, move.getActor());

		return action;

	}

}
