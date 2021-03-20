package controller;

import model.Player;
import java.util.List;

/**
 * Verwaltet den Highscore.
 */
public class HighscoreController {

	private MainController mainController;

	/**
	 * Konstruktor.
	 * @param mainController
	 * 		Bekommt den MainController übergeben.
	 * @throws NullPointerException
	 * 		Wird geworfen wenn der Parameter <em>null</em> ist.
	 */
	public HighscoreController(MainController mainController) throws NullPointerException{
		if(mainController == null){
			throw new NullPointerException();
		}
		this.mainController = mainController;
	}


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
	public void addPlayerScore(Player player) throws NullPointerException{
		if(player == null) {
			throw new NullPointerException();
		}
		List<Player> players = mainController.getGameSystem().getHighscoreList();
		players.add(player);
		mainController.getGameSystem().setHighscoreList(players);

	}

}
