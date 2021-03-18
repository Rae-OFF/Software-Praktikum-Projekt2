package view.game;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import model.Move;
import view.harbourfield.HarbourFieldViewController;
import view.playerfield.PlayerFieldViewController;

public class GameFieldViewController extends StackPane {

    private PlayerFieldViewController playerField;

    private HarbourFieldViewController harbourField;

    private Button undo;

    private Button redo;

    private Button hint;

    public void refresh(Move move){

    }
}
