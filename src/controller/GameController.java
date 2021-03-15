package controller;

import model.Card;
import model.CardStack;
import model.Move;
import model.Action;

import java.util.List;

public class GameController {

	private MainController mainController;



	public Move currentMove() {


		return null;
	}

	public void changeActor(Move move) {

	}

	public void finishRound(Move move) {

	}

	public void init() {


	}

	public Move generateMove(Move move, Action action) {
		return null;
	}

	public List<Action> getPossibleActions(Move move) {
		return null;
	}

	public void undo(Move move) {

	}

	public void redo(Move move) {

	}

	public void shuffleDiscardPile(Move move) {

	}

	public void finishGame(Move move) {

	}

	public boolean isZonked(Move move) {
		return false;
	}

	public Action decideShipAction(boolean defend, Move move) {
		return null;
	}

	public void changeActivePlayer(Move move) {

	}

}
