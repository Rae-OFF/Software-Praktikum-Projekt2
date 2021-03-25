package view.playerfield;

import controller.MainController;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.PlayerState;
import view.assets.CardPileImageViewController;

public class CoinPileViewController extends Group {

    private PileLabelViewController pileLabel;

    private CardPileImageViewController pileImage;

    public CoinPileViewController(MainController controller, PlayerState player){
        int coinAmount = player.getCoins().getSize();
        pileLabel = new PileLabelViewController(""+0);
        pileLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        pileLabel.setTranslateX(15);
        pileLabel.setTranslateY(80);
        pileLabel.setTextFill(Color.GOLD);
        pileLabel.setPrefWidth(50);
        pileLabel.setAlignment(Pos.CENTER);
        pileImage = new CardPileImageViewController();
        pileImage.setFitHeight(120);
        pileImage.setFitWidth(80);

        if(coinAmount>0)
            pileImage.setVisible(true);
        else
            pileImage.setVisible(true);


        getChildren().add(pileImage);
        getChildren().add(pileLabel);
    }

    public PileLabelViewController getPileLabel() {
        return pileLabel;
    }

    public void setPileLabel(String label) {
        this.pileLabel.setText(label);
    }

    public void setCardVisibility(boolean visibility) {
        this.pileImage.setVisible(visibility);
    }
}
