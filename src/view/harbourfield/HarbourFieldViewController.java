package view.harbourfield;

import controller.MainController;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.*;
import view.assets.CardImageViewController;
import view.assets.CardPileImageViewController;

import java.util.ArrayList;
import java.util.List;


public class HarbourFieldViewController extends StackPane {

    private MainController mainController;
    private CardPileImageViewController cardPile;
    private CardImageViewController discardPile;
    private List<ExpeditionsViewController> openExpeditions = new ArrayList<>();
    private HarbourViewController harbour;
    private ShipToDefendFieldViewController shipToDefend;
    private ImageView harbourImage;
    private ImageView skip;

    public HarbourFieldViewController(MainController mainController, Move move) {
        this.mainController = mainController;
        harbourImage = new ImageView("view/resources/Harbourfield.png");
        getChildren().add(harbourImage);
        harbourImage.setFitWidth(860);
        harbourImage.setFitHeight(470);

        harbour = new HarbourViewController(mainController, null);
        harbour.setAlignment(Pos.CENTER);
        getChildren().add(harbour);

        skip =  new ImageView("view/resources/skipButton.png");
        skip.setFitWidth(160);
        skip.setFitHeight(160);
        skip.setTranslateX(650);
        skip.setTranslateY(380);
        skip.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Action skip = new Action(ActionType.SKIP, null);
                mainController.getPlayerController().executeAction(skip); //TODO Skip Testen
                System.out.println("SKIP!");
            }
        });

        cardPile = new CardPileImageViewController();
        cardPile.setFitHeight(120);
        cardPile.setFitWidth(80);
        cardPile.setTranslateX(140);
        cardPile.setTranslateY(180);
        cardPile.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Action drawCard = new Action(ActionType.DRAW_CARD, move.getCardPile().pop());
                mainController.getPlayerController().executeAction(drawCard); //TODO drawCard testen
                harbour = new HarbourViewController(mainController, null);
                harbour.setAlignment(Pos.CENTER);
                getChildren().add(harbour);

                System.out.println("Draw card!");
            }
        });


        discardPile = new CardImageViewController(move.getDiscardPile().peek());
        discardPile.setTranslateX(-350);
        discardPile.setTranslateY(5);


        getChildren().add(skip);
        getChildren().add(cardPile);
        getChildren().add(discardPile);
    }

    public void refresh(Move move) {

        // CardPile wird aktualisiert


        //DiscardPile wird aktualisiert
        discardPile = new CardImageViewController(move.getDiscardPile().peek());

        // shipToDefend wird aktualisiert
        shipToDefend = new ShipToDefendFieldViewController(mainController,move);

        List<Card> expeditionCards = move.getExpeditionPile().getCards();
        List<Card> harbourCards = move.getHarbour().getCards();
        // openExpeditions wird aktualisiert
        openExpeditions.clear();
        for(Card card : expeditionCards){
            openExpeditions.add(new ExpeditionsViewController(mainController,card));
        }
        //harbour wird aktualisiert
        //harbour.clear(); //TODO anpassen
        /*for(Card card : harbourCards){
            harbour.add(new HarbourViewController(mainController,card));
        }
*/

    }
}
