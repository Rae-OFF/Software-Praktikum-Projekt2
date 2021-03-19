package view.game;

import controller.MainController;
import javafx.scene.image.ImageView;
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
        undo =  new ImageView("view.resources.undoButton.png");
        redo =  new ImageView("view.resources.redoButton.png");
        hint =  new ImageView("view.resources.hintButton.png");
    }

    public void refresh(Move move){
        for(PlayerFieldViewController player : playerFields){
            player.refresh(move);
        }
        harbourField.refresh(move);
    }
}
