package view.assets;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardPileImageViewController  extends ImageView {

    private Image pileImage;

    public CardPileImageViewController(){
        pileImage = new Image("view.resoucres."); //TODO Dateinamen einfügen
    }

    public Image getPileImage() {
        return pileImage;
    }

    public void setPileImage(Image pileImage) {
        this.pileImage = pileImage;
    }
}
