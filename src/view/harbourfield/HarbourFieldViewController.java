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
        getChildren().add(skip);
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
    }

    public void refresh(Move move) {

        // CardPile wird aktualisiert
        if(move.getCardPile().getSize()== 0) {
            cardPile.setPileImage(null);
        }else {
            cardPile.setPileImage(new CardPileImageViewController());
        }

        //DiscardPile wird aktualisiert
        discardPile = new DiscardPileViewController(mainController, move);

        // shipToDefend wird aktualisiert
        shipToDefend = new ShipToDefendFieldViewController(mainController,move);

        List<Card> expeditionCards = move.getExpeditionPile().getCards();
        List<Card> harbourCards = move.getHarbour().getCards();
        // openExpeditions wird aktualisiert
        openExpeditions = null;
        for(Card card : expeditionCards){
            openExpeditions.add(new ExpeditionsViewController(mainController,card));
        }
        //harbour wird aktualisiert
        harbour = null;
        for(Card card : harbourCards){
            harbour.add(new HarbourViewController(mainController,card));
        }


    }
}
