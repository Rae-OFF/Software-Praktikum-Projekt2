package view.game;

import controller.MainController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Action;
import model.Move;
import view.GameViewAUI;
import view.events.ButtonClickEvent;
import view.events.ButtonClickEventHandler;
import view.menu.InGameMenuViewController;

import java.util.List;

public class GameViewController extends StackPane implements GameViewAUI {

    private GameFieldViewController gameField;

    private InGameViewController inGameMenu;

    private ImageView backgroundImage;

    private ImageView menu;

    private MainController mainController;

    public GameViewController(MainController mainController){
        super();
        this.mainController = mainController;
        GameViewController gameView = this;
        backgroundImage =  new ImageView("view/resources/backgroundImage.png");
        getChildren().add(backgroundImage);

        backgroundImage.setFitHeight(720);
        backgroundImage.setFitWidth(1280);
        //backgroundImage.fitHeightProperty().bind(heightProperty());
        //backgroundImage.fitWidthProperty().bind(widthProperty());

        gameField = new GameFieldViewController(mainController, mainController.getGameController().currentMove());
        getChildren().add(gameField);

        menu =  new ImageView("view/resources/menuButton.png");
        getChildren().add(menu);
        menu.setTranslateX(-565);
        menu.setTranslateY(-310);

        menu.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Stage window  = new Stage();
                window.initModality(Modality.APPLICATION_MODAL);
                window.setTitle("Menu");
                window.setWidth(1280);
                window.setHeight(720);

                InGameMenuViewController menu = new InGameMenuViewController(mainController, gameView);
                Scene scene = new Scene(menu, 1280, 720);
                window.setScene(scene);
                window.showAndWait();

                System.out.println("HIER IST EIN MENU!");
                //event.consume();
            }
        });

    }

    /*public void mouseEventDebugger(){
        this.getScene().addEventFilter(MouseEvent.MOUSE_RELEASED, (EventHandler<MouseEvent>) event -> {
            EventTarget comp = event.getTarget();
            System.out.println("## " + (comp != null ? comp.getClass().getSimpleName() : event.getClass().getSimpleName()) + " [" + event.getEventType() + "] ## Komponente: " + event.getTarget() + " --------> Details:" + event);
        });
    }*/

    public void mouseEventDebugger() {
        this.getScene().addEventFilter(MouseEvent.MOUSE_RELEASED, (EventHandler<MouseEvent>) event -> {
            EventTarget comp = event.getTarget();
            System.out.println("## " + (comp != null ? comp.getClass().getSimpleName() : event.getClass().getSimpleName()) + " [" + event.getEventType() + "] ## Komponente: " + event.getTarget() + " --------> Details:" + event);
            // print scene graph

            if (event.getTarget() instanceof Node) {
                Node node = null;
                do  {
                    node = node == null ? (Node) event.getTarget() : node.getParent();
                    System.out.println(node);
                } while (node.getParent() != null);
            }
        });
    }

    public void refresh(Move move){
        List<Action> posActions = mainController.getGameController().getPossibleActions(move);
        gameField.refresh(move, posActions);
        System.out.println("GameView refresh");
    }



}
