package view.harbourfield;

import controller.CardFactory;
import controller.MainController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Action;
import model.ActionType;
import model.CardStack;
import model.Move;
import view.assets.CardImageViewController;

import javax.swing.text.html.ImageView;
import java.util.List;


public class ShipToDefendFieldViewController extends StackPane {

    private CardImageViewController cardImage;

    private MainController mainController;

    private Button take;

    private Button defend;

    public ShipToDefendFieldViewController(MainController mainController){
        super();
        this.mainController = mainController;
        this.setAlignment(Pos.TOP_LEFT);
        this.setTranslateX(100);
        this.setTranslateY(320);
        this.setHeight(120);
        this.setWidth(80);

       /* Rectangle rect = new Rectangle(200, 200, Color.BEIGE);
        getChildren().add(rect);*/
        cardImage = new CardImageViewController(mainController.getGameController().currentMove().getShipToDefend());
        getChildren().add(cardImage);

        //defend Button
        defend = new Button("Abwehren");
        getChildren().add(defend);
        defend.setAlignment(Pos.TOP_LEFT);
        defend.setTranslateX(-40);
        defend.setTranslateY(120);
        defend.setMaxSize(75, 20);
        defend.setVisible(false);
        //accept button
        take = new Button("Aufnehmen");
        getChildren().add(take);
        take.setAlignment(Pos.TOP_LEFT);
        take.setTranslateX(40);
        take.setTranslateY(120);
        take.setMaxSize(80, 20);
        take.setVisible(false);


        //cardImage = new CardImageViewController(move.getShipToDefend());
        //CardStack stack = controller.CardFactory.newCardsWithoutSpecial(); //zum testen feste Karte übergeben
        //cardImage = new CardImageViewController(stack.getCards().get(0));
        //cardImage.setAlignment(Pos.TOP_LEFT);
        //getChildren().add(cardImage);


        /*cardImage = new CardImageViewController(null);
        getChildren().add(cardImage);*/

        /*cardImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Button defend = new Button("Abwehren");
                getChildren().add(defend);
                defend.setAlignment(Pos.TOP_LEFT);
                defend.setTranslateX(-40);
                defend.setTranslateY(120);
                defend.setMaxSize(75, 20);

                Button take = new Button("Aufnehmen");
                getChildren().add(take);
                take.setAlignment(Pos.TOP_LEFT);
                take.setTranslateX(40);
                take.setTranslateY(120);
                take.setMaxSize(80, 20);

                defend.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //Action defend = new Action(ActionType.DEFEND, move.getShipToDefend());
                        //mainController.getGameController().generateMove(move, defend);
                       // mainController.getPlayerController().executeAction(defend);
                        System.out.println("Schiff abgewehrt");
                    }
                });

                take.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                        System.out.println("Schiff aufgenommen");
                    }
                });*/

              /*  System.out.println("Defend Ship!");

            }
        });*/

    }

    public void refresh(Move move, List<Action> posAc){
        getChildren().remove(cardImage);
        cardImage = new CardImageViewController(move.getShipToDefend()); //TODO Karte anzeigen
        System.out.println(move.getShipToDefend());
        //CardStack stack = controller.CardFactory.newCardsWithoutSpecial(); //zum testen feste Karte übergeben
        //cardImage = new CardImageViewController(stack.getCards().get(0));
        getChildren().add(cardImage);
        cardImage.setAlignment(Pos.TOP_LEFT);

        defend.setOnMouseClicked(null);
        take.setOnMouseClicked(null);
        setVisible(false);

        for(Action action : posAc){
            if(action.getActionType().equals(ActionType.DEFEND)){
                setVisible(true);
                defend.setVisible(true);
                defend.setOnMouseClicked(event -> {
                    mainController.getPlayerController().executeAction(action);
                });


            }
            if(action.getActionType().equals(ActionType.ACCEPT_SHIP)){
                setVisible(true);
                take.setVisible(true);
                System.out.println("Want to take ship");
                take.setOnMouseClicked(event -> {
                    System.out.println("Take ship");
                    mainController.getPlayerController().executeAction(action);

                });

            }
        }
    }
}
