package view.harbourfield;

import controller.MainController;
import javafx.scene.layout.StackPane;
import model.Move;
import view.assets.CardImageViewController;
import view.assets.CardPileImageViewController;

public class DiscardPileViewController extends StackPane {

    private CardImageViewController pileImage;

    public DiscardPileViewController(MainController controller, Move move){
        if(move.getCardPile().getSize() > 0)
            pileImage = new CardImageViewController(move.getDiscardPile().getCards().get(0));
    }

    public void setPileImage(CardImageViewController pileImage) {
        this.pileImage = pileImage;
    }
}
