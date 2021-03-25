package application.test;

import controller.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

class Tuple{
    int one;
    int two;
    public Tuple(int wins1, int wins2){
        one = wins1;
        two = wins2;
    }
    public int getOne(){
        return one;
    }

    public int getTwo(){
        return two;
    }
}

public class StartEasyAIGame extends Application {

    private MainController mainController;


    @Override
    public void start(Stage primaryStage) {
        try {
            MainController mainController = new MainController();
            this.mainController = mainController;

            oneGame();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void testAI(){
        int easyWins = 0;
        int medWins = 0;

        for(int i = 0; i < 10; i++){
            Tuple tuple = oneGame();
            easyWins += tuple.getOne();
            medWins += tuple.getTwo();
        }


        System.out.println("Easy: " + easyWins + " Medium: " + medWins);
    }

    public Tuple oneGame(){

        Player player2 = new Player("EASY", PlayerType.EASYAI);
        Player player1 = new Player("MEDIUM", PlayerType.EASYAI);

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        GameSystem gameSystem = new GameSystem();
        gameSystem.setPlayers(players);
        mainController.setGameSystem(gameSystem);
        GameController gameController = mainController.getGameController();
        String cardPilePath = "src/ressources/shuffled.csv";
        gameController.init(cardPilePath, players, false, true, false);

        return gameLoop();
    }

    public Tuple gameLoop(){
        GameController gameController = mainController.getGameController();

        PlayerController playerController = mainController.getPlayerController();

        EasyAi easyAi = mainController.getEasyAi();

        MediumAi mediumAI = mainController.getMediumAi();

        HardAi hardAi = mainController.getHardAi();

        CardController cardController = mainController.getCardController();

        IoController ioController = mainController.getIoController();

        int easyWins = 0;
        int medWins = 0;


        int i = 0;
        while(gameController.currentGameIsRunning()){

            PlayerState actor = gameController.getActor();

            Move lastMove = gameController.currentMove();

            ioController.log(lastMove,i);

            for(PlayerState player : lastMove.getPlayers()){

                if(player.getVictoryPoints() >= 12 && lastMove.getActivePlayer().getPlayer().equals(mainController.getGameSystem().getCurrentGame().getStartPlayer().getPlayer()) && lastMove.getActivePlayer().getPlayer().equals(lastMove.getActor().getPlayer())){
                    mainController.getIoController().log("_______________" + player.getPlayer().getName() + " WINS THE GAME!!!" + "______________");
                    if(player.getPlayer().getPlayerType().equals(PlayerType.EASYAI)){
                        easyWins++;
                    }
                    else if(player.getPlayer().getPlayerType().equals(PlayerType.MEDIUMAI)){
                        medWins++;
                    }

                    if(easyWins + medWins > 1){
                        System.out.println("BOTH_________________");
                    }
                    mainController.getGameSystem().getCurrentGame().setOngoing(false);
                }

            }

            Action action = null;

            Player player = actor.getPlayer();
            PlayerType type = player.getPlayerType();
            if(type.equals(PlayerType.EASYAI)){
                action = easyAi.getAction(lastMove);
            }
            else if(type.equals(PlayerType.MEDIUMAI)){
                action = mediumAI.getAction(lastMove);
            }
            else if(type.equals(PlayerType.HARDAI)){
                action = hardAi.getAction(lastMove);
            }

            playerController.executeAction(action);


            System.out.println("Round: " + i);
            i++;
        }

        System.out.println("Game finished");
        Move lastMove = gameController.currentMove();
        List<PlayerState> playerStates = lastMove.getPlayers();
        PlayerState winner = null;
        for(PlayerState state : playerStates){
            if(state.getVictoryPoints() >= 12){
                winner = state;
            }
        }

        if(winner != null){
            System.out.println("Winner: " + winner.getPlayer().getName());
        }
        ioController.log(lastMove,999);
        return new Tuple(easyWins,medWins);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
