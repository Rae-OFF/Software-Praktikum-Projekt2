package controller;

import model.Action;
import model.ActionType;
import model.Move;

import java.util.List;
import java.util.Random;

/**
 * Klasse für einfache KI
 */
public class EasyAi extends AiController {

	private MainController mainController;

	public EasyAi(MainController mainController){
		this.mainController = mainController;
	}

	@Override
	public Action getAction(Move move){
		Random rand = new Random();
		List<Action> possibleActions=mainController.getGameController().getPossibleActions(move);
		if(possibleActions.size() > 1){
			Action removeAction = null;
			for(Action action : possibleActions){
				if(action.getActionType().equals(ActionType.SKIP));
				removeAction = action;
			}
			if(removeAction != null){
				possibleActions.remove(removeAction);
			}
		}
		int randIndex = rand.nextInt(possibleActions.size());
		//mainController.getPlayerController().executeAction(possibleActions.get(randIndex));
		return possibleActions.get(randIndex);
	}
}
