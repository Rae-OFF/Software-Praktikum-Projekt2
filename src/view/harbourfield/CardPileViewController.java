package view.harbourfield;

import controller.MainController;
import javafx.scene.layout.StackPane;
import model.Move;
import view.assets.CardImageViewController;
import view.assets.CardPileImageViewController;

public class CardPileViewController extends StackPane {

    private CardPileImageViewController pileImage;

    public CardPileViewController(MainController controller, Move move){
        if(move.getCardPile().getSize() > 0)
            pileImage = new CardPileImageViewController();
    }

    public void setPileImage(CardPileImageViewController pileImage) {
        this.pileImage = pileImage;
    }
}
