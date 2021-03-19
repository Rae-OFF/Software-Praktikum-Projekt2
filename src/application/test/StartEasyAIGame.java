package application.test;

import controller.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class StartEasyAIGame extends Application {

    private MainController mainController;


    @Override
    public void start(Stage primaryStage) {
        try {
            MainController mainController = new MainController();

            this.mainController = mainController;

            Player player1 = new Player("Eazy E1", PlayerType.EASYAI);
            Player player2 = new Player("Eazy E2", PlayerType.EASYAI);

            List<Player> players = new ArrayList<>();
            players.add(player1);
            players.add(player2);

            GameSystem gameSystem = new GameSystem();
            gameSystem.setPlayers(players);
            mainController.setGameSystem(gameSystem);
            GameController gameController = mainController.getGameController();
            gameController.init(null, players, false, false, false);

            gameLoop();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void gameLoop(){
        GameController gameController = mainController.getGameController();

        PlayerController playerController = mainController.getPlayerController();

        EasyAi easyAi = mainController.getEasyAi();

        CardController cardController = mainController.getCardController();

        IoController ioController = mainController.getIoController();


        int i = 0;
        while(gameController.currentGameIsRunning()){

            PlayerState actor = gameController.getActor();

            Move lastMove = gameController.currentMove();
            ioController.log(lastMove,i);
            Action action = null;

            Player player = actor.getPlayer();
            PlayerType type = player.getPlayerType();
            if(type == PlayerType.EASYAI){
                action = easyAi.getAction(lastMove);
            }

            playerController.executeAction(action);

            System.out.println("Round: " + i);
            i++;



/*            try{
                sleep(5000);
            }
            catch (Exception e){
                e.printStackTrace();
            }*/




        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
