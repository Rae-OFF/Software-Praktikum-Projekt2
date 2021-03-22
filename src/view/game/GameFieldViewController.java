package view.game;

import controller.MainController;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.Move;
import model.PlayerState;
import view.harbourfield.HarbourFieldViewController;
import view.playerfield.PlayerFieldViewController;

import java.util.List;

public class GameFieldViewController extends StackPane {

    private List<PlayerFieldViewController> playerFields;

    private HarbourFieldViewController harbourField;

    private ImageView undo;

    private ImageView redo;

    private ImageView hint;

    public GameFieldViewController(MainController mainController, Move move){
        for(PlayerState player : move.getPlayers()){
            playerFields.add(new PlayerFieldViewController(mainController,player));
        }

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
        undo.setTranslateX(-360); //TODO undo an Parent orientieren
        undo.setTranslateY(-270);
        redo.setFitHeight(50);
        redo.setFitWidth(50);
        redo.setTranslateX(-310); //TODO redo an Parent orientieren
        redo.setTranslateY(-270);
        hint.setFitHeight(50);
        hint.setFitWidth(50);
        hint.setTranslateX(-240); //TODO hint an Parent orientieren
        hint.setTranslateY(-270);
        harbourField.setTranslateX(0);
        harbourField.setTranslateY(-120);

        undo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("UNDO!");
            }
        });

        redo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("REDO!");
            }
        });

        hint.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("HINT!");
            }
        });


    }

    public void refresh(Move move){
        for(PlayerFieldViewController player : playerFields){
            player.refresh(move);
        }
        harbourField.refresh(move);
    }
}
