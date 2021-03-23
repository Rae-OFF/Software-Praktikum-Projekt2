package view.harbourfield;

import controller.CardFactory;
import controller.MainController;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
    private ExpeditionsViewController openExpeditions;
    private HarbourViewController harbour;
    private ShipToDefendFieldViewController shipToDefend;
    private ImageView harbourImage;
    private ImageView skip;

    public HarbourFieldViewController(MainController mainController, Move move) {
        super();
        this.mainController = mainController;
        initialize(move);
    }

    public void initialize(Move move){
        harbourImage = new ImageView("view/resources/Harbourfield.png");
        getChildren().add(harbourImage);
        harbourImage.setFitWidth(860);
        harbourImage.setFitHeight(470);

        harbour = new HarbourViewController(mainController, null);
        harbour.setAlignment(Pos.CENTER);
        getChildren().add(harbour);

        openExpeditions = new ExpeditionsViewController(mainController, null);
        openExpeditions.setAlignment(Pos.TOP_LEFT);
        getChildren().add(openExpeditions);

        shipToDefend = new ShipToDefendFieldViewController(mainController, move);
        shipToDefend.setAlignment(Pos.TOP_LEFT);
        getChildren().add(shipToDefend);


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
                refresh(move);
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
                mainController.getGameController().generateMove(move, drawCard); //TODO drawCard testen

                //Zum Testen
                CardStack stack = controller.CardFactory.newCardsWithoutSpecial();
                Card card = stack.getCards().get(stack.getSize()-6);
                Card card1 = stack.getCards().get(stack.getSize()-5);
                Card card2 = stack.getCards().get(stack.getSize()-4);
                List<Card> exp = new ArrayList<>();
                exp.add(card);
                exp.add(card1);
                exp.add(card2);

                //harbour = new HarbourViewController(mainController, stack.popList(7));
               harbour =  new HarbourViewController(mainController, move.getHarbour().getCards());
                getChildren().add(harbour);


                //openExpeditions = new ExpeditionsViewController(mainController, exp);
                openExpeditions = new ExpeditionsViewController(mainController, move.getExpeditionPile().getCards());
                getChildren().add(openExpeditions);

                shipToDefend = new ShipToDefendFieldViewController(mainController, move);
                getChildren().add(shipToDefend);
                System.out.println("Draw card!");
            }
        });

        discardPile = new CardImageViewController(move.getDiscardPile().peek());
        discardPile.setAlignment(Pos.TOP_LEFT);
        discardPile.setTranslateX(40);
        discardPile.setTranslateY(180);

        //Zum Testen
        CardStack stack = controller.CardFactory.newCardsWithoutSpecial();
        Card card = stack.getCards().get(stack.getSize()-6);
        Card card1 = stack.getCards().get(stack.getSize()-5);
        Card card2 = stack.getCards().get(stack.getSize()-4);
        List<Card> exp = new ArrayList<>();
        exp.add(card);
        exp.add(card1);
        exp.add(card2);
        openExpeditions.callExpedition(exp);

        getChildren().add(discardPile);
        getChildren().add(skip);
        getChildren().add(cardPile);
    }

    public void refresh(Move move) {

        // CardPile wird aktualisiert


        //DiscardPile wird aktualisiert
        getChildren().remove(discardPile);
        discardPile = new CardImageViewController(move.getDiscardPile().peek());
        getChildren().add(discardPile);

        // shipToDefend wird aktualisiert
        getChildren().remove(shipToDefend);
        shipToDefend = new ShipToDefendFieldViewController(mainController,move);


        //ShipToDefend, Harbour und Expedition werden on Click refreshed ? //TODO wann muss der cardPile refresht werden?
        //List<Card> expeditionCards = move.getExpeditionPile().getCards();
        //List<Card> harbourCards = move.getHarbour().getCards();
        // openExpeditions wird aktualisiert
        //openExpeditions.clear();
        //for(Card card : expeditionCards){
        //   openExpeditions.add(new ExpeditionsViewController(mainController,card));
        //}
        //harbour wird aktualisiert
        //getChildren().remove(harbour);

        initialize(move);

    }
}
