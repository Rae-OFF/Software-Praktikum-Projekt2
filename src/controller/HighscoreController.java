package controller;

import model.Player;
import java.util.List;

/**
 * Verwaltet den Highscore.
 */
public class HighscoreController {

	private MainController mainController;

	/**
	 *
	 * @return Gibt die LIste der Spieler die in der Highscoreliste stehen zurück.
	 */
	public List<Player> getHighscoreList() {
		return mainController.getGameSystem().getHighscoreList();
	}

	/**
	 * Fügt Punktzahl und Siege eines Spielers der Highscoreliste zu.
	 * @param player
	 * 		Bekommt einen Spieler übergeben.
	 */
	public void addPlayerScore(Player player) {
		List<Player> players = mainController.getGameSystem().getHighscoreList();
		players.add(player);
		mainController.getGameSystem().setHighscoreList(players);

	}

}
