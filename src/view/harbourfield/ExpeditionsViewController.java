package view.harbourfield;

import controller.MainController;
import javafx.scene.layout.StackPane;
import model.Card;
import model.Move;
import view.assets.CardImageViewController;

import java.util.List;

public class ExpeditionsViewController extends StackPane {

    private CardImageViewController cardImage;

    public ExpeditionsViewController(MainController controller, Card expedition){
        super();
        Move move = controller.getGameController().currentMove();
        cardImage = new CardImageViewController(expedition);
    }

}
