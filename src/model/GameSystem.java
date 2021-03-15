package model;

import java.util.List;

public class GameSystem {

	private List<Player> player;

	private List<Game> games;

	private Game currentGame;

	private GameSystem gameSystem;

	public List<Player> getPlayer() {
		return player;
	}

	public void setPlayer(List<Player> player) {
		this.player = player;
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public Game getCurrentGame() {
		return currentGame;
	}

	public void setCurrentGame(Game currentGame) {
		this.currentGame = currentGame;
	}

	public GameSystem getGameSystem() {
		return gameSystem;
	}

	public void setGameSystem(GameSystem gameSystem) {
		this.gameSystem = gameSystem;
	}
}
