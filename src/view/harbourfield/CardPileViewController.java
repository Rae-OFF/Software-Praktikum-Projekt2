package view.harbourfield;

import controller.MainController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.Move;
import view.assets.CardImageViewController;
import view.assets.CardPileImageViewController;

public class CardPileViewController extends StackPane {

    private CardPileImageViewController pileImage;

    public CardPileViewController(MainController controller, Move move){
        this.setPickOnBounds(false);
        this.setWidth(80);
        this.setHeight(120);

        if(move.getCardPile().getSize() >= 0){
            pileImage = new CardPileImageViewController();
            pileImage.setFitWidth(80);
            pileImage.setFitHeight(120);
            pileImage.setOnMouseClicked((event -> {
                System.out.println("draw Card");
            }));


            getChildren().add(pileImage);
        }
    }

}
