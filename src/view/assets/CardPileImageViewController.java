package view.assets;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardPileImageViewController  extends ImageView {

    private Image pileImage;

    public CardPileImageViewController(){
        //super("view/resources/CoinCard.png");
        pileImage = new Image("view/resources/CoinCard.png"); //TODO Dateinamen einfügen
        this.setImage(pileImage);
    }

    public Image getPileImage() {
        return pileImage;
    }

    public void setPileImage(Image pileImage) {
        this.pileImage = pileImage;
    }
}
