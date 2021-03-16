package model;

import java.util.List;

/**
 * Klasse des Systems, welches alle Spieler und alle Spiele kennt.
 */
public class GameSystem {

	private List<Player> players;

	private List<Game> games;

	private Game currentGame;

	/**
	 * Konstruktor.
	 * @param players
	 * 		Bekommt eine Liste aller Spieler.
	 */
	public GameSystem(List<Player> players) {
		this.players = players;
	}

	/**
	 *
	 * @return Gibt eine Liste aller Spieler zurück.
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * Setzt eine Liste aller Spieler.
	 * @param players
	 * 		Bekommt eine Liste aller Spieler.
	 */
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	/**
	 *
	 * @return Gibt die Liste der Spiele zurück.
	 */
	public List<Game> getGames() {
		return games;
	}

	/**
	 * Setzt die Liste der Spieler.
	 * @param games
	 * 		Bekommt eine Liste von Spielen übergeben.
	 */
	public void setGames(List<Game> games) {
		this.games = games;
	}

	/**
	 *
	 * @return Gibt das aktuelle Spiel zurück.
	 */
	public Game getCurrentGame() {
		return currentGame;
	}

	/**
	 * Setzt das aktuelle Spiel.
	 * @param currentGame
	 * 		Bekommt ein Spiel übergeben.
	 */
	public void setCurrentGame(Game currentGame) {
		this.currentGame = currentGame;
	}


}
