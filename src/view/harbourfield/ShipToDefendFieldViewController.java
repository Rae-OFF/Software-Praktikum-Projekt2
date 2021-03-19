package view.harbourfield;

import controller.MainController;
import javafx.scene.layout.StackPane;
import model.Move;
import view.assets.CardImageViewController;

public class ShipToDefendFieldViewController extends StackPane {

    private CardImageViewController cardImage;

    public ShipToDefendFieldViewController(MainController controller, Move move){
        cardImage = new CardImageViewController(move.getShipToDefend());
    }
}
