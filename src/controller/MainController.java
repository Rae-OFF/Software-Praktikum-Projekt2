package controller;

import model.GameSystem;

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

	public GameSystem getGameSystem() {
		return gameSystem;
	}



	public MainController getMainController() {
		return mainController;
	}



	public PlayerController getPlayerController() {
		return playerController;
	}



	public AiController getAiController() {
		return aiController;
	}



	public HardAi getHardAi() {
		return hardAi;
	}



	public MediumAi getMediumAi() {
		return mediumAi;
	}


	public EasyAi getEasyAi() {
		return easyAi;
	}


	public CardController getCardController() {
		return cardController;
	}



	public GameController getGameController() {
		return gameController;
	}



	public IoController getIoController() {
		return ioController;
	}


	public HighscoreController getHighscoreController() {
		return highscoreController;
	}



	public MainController() {

	}
}
