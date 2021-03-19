package view.harbourfield;

import controller.MainController;
import javafx.scene.layout.StackPane;
import model.Card;
import model.Move;
import view.assets.CardImageViewController;

public class HarbourViewController extends StackPane {

    private CardImageViewController cardImage;

    public HarbourViewController(MainController controller, Card card){
        Move move = controller.getGameController().currentMove();
        cardImage = new CardImageViewController(card);
    }
}
