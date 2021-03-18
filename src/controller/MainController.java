package controller;

import model.Action;
import model.GameSystem;
import model.Move;

/**
 * MainController, verwaltet die anderen Controller.
 */
public class MainController {

	private GameSystem gameSystem;

	private PlayerController playerController;

	private HardAi hardAi;

	private MediumAi mediumAi;

	private EasyAi easyAi;

	private CardController cardController;

	private GameController gameController;

	private IoController ioController;

	private HighscoreController highscoreController;

	/**
	 * Konstruktor.
	 */
	public MainController() {
		this.gameSystem = new GameSystem();
		this.playerController = new PlayerController(this);
		this.hardAi = new HardAi(this);
		this.mediumAi = new MediumAi(this);
		this.easyAi = new EasyAi(this);
		this.cardController = new CardController(this);
		this.gameController = new GameController(this);
		this.ioController = new IoController(this);
		this.highscoreController = new HighscoreController(this);

	}

	/**
	 *
	 * @return Gibt das Spielsystem zurück.
	 */
	public GameSystem getGameSystem() {
		return gameSystem;
	}

	public void setGameSystem(GameSystem gameSystem) {this.gameSystem = gameSystem;}

	/**
	 *
	 * @return Gibt den Spieler Controller zurück.
	 */
	public PlayerController getPlayerController() {
		return playerController;
	}

	/**
	 *
	 * @return Gibt die harte KI wieder.
	 */
	public HardAi getHardAi() {
		return hardAi;
	}

	/**
	 *
	 * @return Gibt die mittelschwere KI zurück.
	 */
	public MediumAi getMediumAi() {
		return mediumAi;
	}

	/**
	 *
	 * @return Gibt die einfache KI zurück.
	 */
	public EasyAi getEasyAi() {
		return easyAi;
	}

	/**
	 *
	 * @return Gibt den Karten Controller zurück.
	 */
	public CardController getCardController() {
		return cardController;
	}

	/**
	 *
	 * @return Gibt den Spiel Controller zurück.
	 */
	public GameController getGameController() {
		return gameController;
	}

	/**
	 *
	 * @return Gibt den IO Controller zurück.
	 */
	public IoController getIoController() {
		return ioController;
	}

	/**
	 *
	 * @return Gibt den Highscore Controller zurück.
	 */
	public HighscoreController getHighscoreController() {
		return highscoreController;
	}

}
