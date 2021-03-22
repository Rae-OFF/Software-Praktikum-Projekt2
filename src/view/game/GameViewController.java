package view.game;

import controller.MainController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.Move;
import view.events.ButtonClickEvent;
import view.events.ButtonClickEventHandler;

public class GameViewController extends StackPane {

    private GameFieldViewController gameField;

    private InGameViewController inGameMenu;

    private ImageView backgroundImage;

    private ImageView menu;

    public GameViewController(MainController mainController){

        //TODO Fabian fragen: auslagern in initialize auslagern oder nicht?
        backgroundImage =  new ImageView("view/resources/backgroundImage.png");
        getChildren().add(backgroundImage);

        backgroundImage.fitHeightProperty().bind(heightProperty());
        backgroundImage.fitWidthProperty().bind(widthProperty());

        gameField = new GameFieldViewController(mainController, mainController.getGameController().currentMove());
        getChildren().add(gameField);

        menu =  new ImageView("view/resources/menuButton.png");
        getChildren().add(menu);
        menu.setTranslateX(-565);
        menu.setTranslateY(-310);

        menu.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("HIER IST EIN MENU!");
                //event.consume();
            }
        });

    }

    public void refresh(Move move){
        gameField.refresh(move);
    }



}
