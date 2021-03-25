package view.game;

import controller.MainController;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.Move;
import model.Player;
import model.PlayerState;
import model.PlayerType;
import view.harbourfield.HarbourFieldViewController;
import view.playerfield.PlayerFieldViewController;

import java.util.ArrayList;
import java.util.List;

public class GameFieldViewController extends StackPane {

    private List<PlayerFieldViewController> playerFields = new ArrayList<>();

    private HarbourFieldViewController harbourField;

    private ImageView undo;

    private ImageView redo;

    private ImageView hint;

    public GameFieldViewController(MainController mainController, Move move){



       /* for(PlayerState player : move.getPlayers()){
            playerFields.add(new PlayerFieldViewController(mainController,player));
        }*/

        harbourField = new HarbourFieldViewController(mainController,move);
        getChildren().add(harbourField);
        undo =  new ImageView("view/resources/undoButton.png");
        getChildren().add(undo);
        redo =  new ImageView("view/resources/redoButton.png");
        getChildren().add(redo);
        hint =  new ImageView("view/resources/hintButton.png");
        getChildren().add(hint);

        undo.setFitHeight(50);
        undo.setFitWidth(50);
        undo.setTranslateX(-360);
        undo.setTranslateY(-270);
        redo.setFitHeight(50);
        redo.setFitWidth(50);
        redo.setTranslateX(-310);
        redo.setTranslateY(-270);
        hint.setFitHeight(50);
        hint.setFitWidth(50);
        hint.setTranslateX(-240);
        hint.setTranslateY(-270);
        harbourField.setTranslateX(0);
        harbourField.setTranslateY(-120);

        undo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mainController.getGameController().undo(move); //TODO undo testen
                System.out.println("UNDO!");
            }
        });

        redo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mainController.getGameController().redo(move); //TODO redo testen
                System.out.println("REDO!");
            }
        });

        hint.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mainController.getPlayerController().getHint(move); //TODO hint testen
                System.out.println("HINT!");
            }
        });

        playerFields.add(new PlayerFieldViewController(mainController, new PlayerState(new Player("P1", PlayerType.HUMAN)), true));
        playerFields.get(0).setTranslateX(50);
        playerFields.get(0).setTranslateY(560);
        getChildren().add(playerFields.get(0));

        playerFields.add(new PlayerFieldViewController(mainController, new PlayerState(new Player("P2", PlayerType.HUMAN)), true));
        playerFields.get(1).setTranslateX(450);
        playerFields.get(1).setTranslateY(560);
        getChildren().add(playerFields.get(1));

        playerFields.add(new PlayerFieldViewController(mainController, new PlayerState(new Player("P3", PlayerType.HUMAN)), true));
        playerFields.get(2).setTranslateX(850);
        playerFields.get(2).setTranslateY(560);
        getChildren().add(playerFields.get(2));
    }

    public void refresh(Move move){
        for(PlayerFieldViewController player : playerFields){
            player.refresh(move);
        }
        harbourField.refresh(move);
    }
}
