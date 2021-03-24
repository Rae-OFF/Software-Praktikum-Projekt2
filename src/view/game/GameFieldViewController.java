package view.game;

import controller.MainController;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.Action;
import model.Move;
import model.PlayerState;
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
        super();
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
        harbourField.setAlignment(Pos.TOP_LEFT);
        harbourField.setTranslateX(210);
        harbourField.setTranslateY(0);

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


    }

    public void refresh(Move move, List<Action> posAc){
        for(PlayerFieldViewController player : playerFields){
            player.refresh(move, posAc);
        }
        harbourField.refresh(move, posAc);
        System.out.println("GameFieldView refresh");
    }
}
