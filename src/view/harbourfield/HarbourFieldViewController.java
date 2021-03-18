package view.harbourfield;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.Move;


public class HarbourFieldViewController extends StackPane {

    private CardPileViewController cardPile;

    private DiscardPileViewController discardPile;

    private ExpeditionsViewController openExpeditions;

    private HarbourViewController harbour;

    private ShipToDefendFieldViewController shipToDefend;

    private ImageView harbourImage;

    private Button skip;

    public void refresh(Move move){

    }
}
