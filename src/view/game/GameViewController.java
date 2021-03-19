package view.game;

import controller.MainController;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.Move;

public class GameViewController extends StackPane {

    private GameFieldViewController gameField;

    private InGameViewController inGameMenu;

    private ImageView backgroundImage;

    private ImageView menu;

    public GameViewController(MainController mainController){
        backgroundImage =  new ImageView("view.resources.backgroundImage.png");
        menu =  new ImageView("view.resources.menuButton.png");
        gameField = new GameFieldViewController(mainController, mainController.getGameController().currentMove());
    }

    public void refresh(Move move){
        gameField.refresh(move);
    }
}
