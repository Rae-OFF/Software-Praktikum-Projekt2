package view.harbourfield;

import controller.MainController;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.Card;
import model.Move;
import model.PlayerState;
import view.assets.CardPileImageViewController;

import java.util.List;


public class HarbourFieldViewController extends StackPane {

    private MainController mainController;
    private CardPileViewController cardPile;
    private DiscardPileViewController discardPile;
    private List<ExpeditionsViewController> openExpeditions;
    private List<HarbourViewController> harbour;
    private ShipToDefendFieldViewController shipToDefend;
    private ImageView harbourImage;
    private ImageView skip;

    public HarbourFieldViewController(MainController mainController, Move move) {
        this.mainController = mainController;
        harbourImage = new ImageView("view/resources/Harbourfield.png");
        getChildren().add(harbourImage);
        harbourImage.setFitWidth(860);
        harbourImage.setFitHeight(470);

        skip =  new ImageView("view/resources/SkipButton.png");
        skip.setFitWidth(160);
        skip.setFitHeight(160);
        skip.setTranslateX(310);
        skip.setTranslateY(230);
        skip.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("SKIP!");
            }
        });

        cardPile = new CardPileViewController(mainController, move);
        cardPile.setTranslateX(-250);
        cardPile.setTranslateY(5);

        cardPile.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("draw card!");
            }
        });

        discardPile = new DiscardPileViewController(mainController, move);
        discardPile.setTranslateX(-350);
        discardPile.setTranslateY(5);

        getChildren().add(skip);
        getChildren().add(cardPile);
        getChildren().add(discardPile);
    }

    public void refresh(Move move) {

        // CardPile wird aktualisiert


        //DiscardPile wird aktualisiert
        discardPile = new DiscardPileViewController(mainController, move);

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
        harbour.clear();
        for(Card card : harbourCards){
            harbour.add(new HarbourViewController(mainController,card));
        }


    }
}
