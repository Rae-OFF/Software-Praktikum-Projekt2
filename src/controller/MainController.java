package controller;

import model.GameSystem;

/**
 * MainController, verwaltet die anderen Controller.
 */
public class MainController {

	private GameSystem gameSystem;

	private MainController mainController;

	private PlayerController playerController;

	private AiController aiController;

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

	}

	/**
	 *
	 * @return Gibt das Spielsystem zurück.
	 */
	public GameSystem getGameSystem() {
		return gameSystem;
	}

	/**
	 *
	 * @return Gibt den MainController zurück.
	 */
	public MainController getMainController() {
		return mainController;
	}

	/**
	 *
	 * @return Gibt den Spieler Controller zurück.
	 */
	public PlayerController getPlayerController() {
		return playerController;
	}

	/**
	 *
	 * @return Gibt den KI Controller zurück.
	 */
	public AiController getAiController() {
		return aiController;
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
