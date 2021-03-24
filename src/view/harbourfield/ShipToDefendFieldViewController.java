package view.harbourfield;

import controller.CardFactory;
import controller.MainController;
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


public class ShipToDefendFieldViewController extends StackPane {

    private CardImageViewController cardImage;

    public ShipToDefendFieldViewController(MainController mainController, Move move){
        super();
        this.setAlignment(Pos.TOP_LEFT);
        this.setTranslateX(100);
        this.setTranslateY(320);
        this.setHeight(120);
        this.setWidth(80);

        cardImage = new CardImageViewController(move.getShipToDefend());
        //CardStack stack = controller.CardFactory.newCardsWithoutSpecial(); //zum testen feste Karte übergeben
        //cardImage = new CardImageViewController(stack.getCards().get(0));
        cardImage.setAlignment(Pos.TOP_LEFT);
        getChildren().add(cardImage);


        cardImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //TODO Abfrage ob beide angezeigt werden sollen
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
                        Action defend = new Action(ActionType.DEFEND, move.getShipToDefend());
                        //mainController.getGameController().generateMove(move, defend);
                        mainController.getPlayerController().executeAction(defend);
                        System.out.println("Schiff abgewehrt");
                    }
                });

                take.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //TODO in GUI auf Hafen verschieben
                        System.out.println("Schiff aufgenommen");
                    }
                });

                System.out.println("Defend Ship!");

            }
        });


    }
}
