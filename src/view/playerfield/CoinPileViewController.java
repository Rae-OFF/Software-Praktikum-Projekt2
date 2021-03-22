package view.playerfield;

import controller.MainController;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import model.PlayerState;
import view.assets.CardPileImageViewController;

public class CoinPileViewController extends Group {

    private PileLabelViewController pileLabel;

    private CardPileImageViewController pileImage;

    public CoinPileViewController(MainController controller, PlayerState player){
        int coinAmount = player.getCoins().getSize();
        pileLabel = new PileLabelViewController(""+coinAmount);
        if(coinAmount>0)
            pileImage = new CardPileImageViewController();

    }

    public PileLabelViewController getPileLabel() {
        return pileLabel;
    }

    public void setPileLabel(PileLabelViewController pileLabel) {
        this.pileLabel = pileLabel;
    }

    public CardPileImageViewController getPileImage() {
        return pileImage;
    }

    public void setPileImage(CardPileImageViewController pileImage) {
        this.pileImage = pileImage;
    }
}
