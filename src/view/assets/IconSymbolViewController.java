package view.assets;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Person;
import model.PersonType;

public class IconSymbolViewController  extends ImageView {

    private Image iconSymbol;

    public IconSymbolViewController(Person personCard){
        String fileName = personCard.getPersonType().toString();
        if(personCard.getPersonType().equals(PersonType.TRADER)){
          String colour = personCard.getColour().toString();
          fileName = fileName + "_" + colour;
        }
        iconSymbol = new Image("view/resources/" +fileName + ".png");
        this.setImage(iconSymbol);
    }

    public Image getIconSymbol() {
        return iconSymbol;
    }

    public void setIconSymbol(Image iconSymbol) {
        this.iconSymbol = iconSymbol;
    }
}
