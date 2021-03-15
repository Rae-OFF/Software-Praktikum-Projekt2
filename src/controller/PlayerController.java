package controller;

import model.Action;
import model.Move;
import model.Player;
import model.PlayerState;

import java.util.List;

public class PlayerController {



	private MainController mainController;

	public void executeAction(Action action) {

	}

	public void setPlayerOrder(List<Player> playerList, boolean random) {

	}

	public Action getHint(Move move) {
		return null;
	}

	public int getVictoryPoints(PlayerState player, Move move) {
		return 0;
	}

	public int getCoins(PlayerState player, Move move) {
		return 0;
	}

	public int countExpeditions(PlayerState player, Move move) {
		return 0;
	}

}
