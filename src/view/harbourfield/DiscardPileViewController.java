package view.harbourfield;

import controller.MainController;
import javafx.scene.layout.StackPane;
import model.Move;
import view.assets.CardImageViewController;
import view.assets.CardPileImageViewController;

public class DiscardPileViewController extends StackPane {

    private CardImageViewController pileImage = new CardImageViewController(null);

    public DiscardPileViewController(MainController controller, Move move){
        if(move.getDiscardPile().getSize() > 0)
            pileImage = new CardImageViewController(move.getDiscardPile().getCards().get(0));

            getChildren().add(pileImage);
    }

    public void setPileImage(CardImageViewController pileImage) {
        if(pileImage!=null)
            this.pileImage = pileImage;
    }
}
