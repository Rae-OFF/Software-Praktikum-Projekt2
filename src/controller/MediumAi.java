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

	public MediumAi(MainController mainController) {
		this.mainController = mainController;
	}

	@Override
	public Action getAction(Move move){

		AINode root = AITree.generateTree(move,mainController,5);

		Action action = AITree.getBestAction(root, move.getActor());

		return action;

	}

}
