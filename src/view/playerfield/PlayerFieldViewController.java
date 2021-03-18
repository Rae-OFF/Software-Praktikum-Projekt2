package view.playerfield;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.Move;
import view.playerfield.CoinPileViewController;
import view.playerfield.IconsViewController;

public class PlayerFieldViewController extends StackPane {

    private IconsViewController icons;

    private CoinPileViewController coinPile;

    private ImageView playerFieldImage;

    private Label name;

    public void refresh(Move move){

    }
}
