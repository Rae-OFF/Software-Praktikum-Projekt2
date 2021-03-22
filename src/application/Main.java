package application;
	
import controller.CardFactory;
import controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
import view.game.GameViewController;


import java.util.ArrayList;
import java.util.List;

import static model.ActionType.*;
import static model.PlayerType.EASYAI;
import static model.PlayerType.HUMAN;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//Zum Testen
			MainController mainController = new MainController();
			//GameSystem system = mainController.getGameSystem();
			List<Player> players = getPlayers();
			//List<PlayerState> playerStates = getPlayerState();
			//List<Move> moves = movs();
			//List<Action> actions = actions();
			//Move move = new Move(playerStates.get(0), true, playerStates.get(0), actions().get(0));
			//Game game = new Game(true,playerStates.get(0), true, players, playerStates.get(0).getCards());
			//system.setCurrentGame(game);
			mainController.getGameController().init(null, players, true, true, false);
			//system.getCurrentGame().setLastMove(move);



			//Eigentlicher Code
			GameViewController gameViewController = new GameViewController(mainController);
			Scene scene = new Scene(gameViewController,1280,720);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();

			System.out.println("Spiel läuft!");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public static List<Player> getPlayers() {


		List<Player> players = new ArrayList<>(5);

		Player player0 = new Player("player0",HUMAN);
		Player player1 = new Player("player1",HUMAN);
		Player player2 = new Player("player2",HUMAN);
		Player player3 = new Player("player3",HUMAN);
		Player player4 = new Player("AI",EASYAI);

		players.add(player0);
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);

		return players;
	}

	public static List<PlayerState> getPlayerState() {

		List<PlayerState> players = new ArrayList<>();

		CardFactory cardFactory = new CardFactory();
//get all players' name and type, add them into players list
		for (int i = 0; i < 5; i++) {

			PlayerState player = new PlayerState(getPlayers().get(i));
			players.add(player);
		}

		//generate cards for player 0;
		CardStack p0 = players.get(0).getCards();
		List<Card> cards0 = p0.getCards();

		cards0.add(cardFactory.generateAdmiral().get(0));  //victoryPoint: 1
		cards0.add(cardFactory.generateTrader().get(0));  //green, 3 coins
		cards0.add(cardFactory.generateJester().get(0));  //victoryPoint: 1
		cards0.add(cardFactory.generateMademoiselles().get(3)); //victoryPoint: 3

//generate cards for player 1;
		CardStack p1 = players.get(0).getCards();
		List<Card> cards1 = p1.getCards();

		cards1.add(cardFactory.generateSailor().get(0));  //victoryPoint: 1, sword:1
		cards1.add(cardFactory.generateGovernor().get(0));  //victoryPoint: 0
		cards1.add(cardFactory.generateJester().get(0));  //victoryPoint: 2

//generate cards for player 2;
		CardStack p2 = players.get(0).getCards();
		List<Card> cards2 = p2.getCards();

		cards2.add(cardFactory.generateTrader().get(5));  //red, 3 coins
		cards2.add(cardFactory.generatePirate().get(1));  //victoryPoint: 2, sword 2
		cards2.add(cardFactory.generatePriest().get(0));  //victoryPoint: 1

//generate cards for player 3;
		CardStack p3 = players.get(0).getCards();
		List<Card> cards3 = p3.getCards();

		cards3.add(cardFactory.generateTrader().get(5));  //red, 3 coins
		cards3.add(cardFactory.generateSettlerCaptain().get(1));  //victoryPoint: 1, Settler
		cards3.add(cardFactory.generateJackOfAllTrader().get(0));  //victoryPoint: 1

//generate cards for player 4;
		CardStack p4 = players.get(0).getCards();
		List<Card> cards4 = p4.getCards();

		cards4.add(cardFactory.generatePriest().get(0));  //victoryPoint: 1
		cards4.add(cardFactory.generateSettlerCaptain().get(7));  //victoryPoint: 1, Capitain
		cards4.add(cardFactory.generateAdmiral().get(0));  //victoryPoint: 1

		return players;
	}

	public static List<Move> movs(){

		List<Move> moves = new ArrayList<>();

		Move move = new Move(getPlayerState().get(0), true, getPlayerState().get(0), actions().get(0));
		Move currentMove = new Move(getPlayerState().get(1), false, getPlayerState().get(1), actions().get(1));

		//moves.add(move);
		moves.add(currentMove);

		return moves;
	}


	public static List<Action> actions(){

		List<Action> actions = new ArrayList<>();
		CardFactory cardFactory = new CardFactory();

		Ship ship = new Ship(Colour.YELLOW, 1, 1);
		Card admiral = cardFactory.generateAdmiral().get(0); //5 coins, victoryPoint 1


		Action drawCard = new Action(DRAW_CARD, ship);
		Action takeShip = new Action(TAKE_SHIP, ship);
		Action buyPerson = new Action(BUY_PERSON, admiral);

		actions.add(drawCard);
		actions.add(takeShip);
		actions.add(buyPerson);

		return actions;
	}
}
