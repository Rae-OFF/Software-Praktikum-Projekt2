package view.playerfield;

import controller.MainController;
import javafx.scene.Group;
import model.Person;
import model.PersonType;
import model.PlayerState;
import view.assets.IconSymbolViewController;
import view.playerfield.IconLabelViewController;

public class IconsViewController extends Group {

    private IconLabelViewController iconLabel;

    private IconSymbolViewController iconSymbol;

    public IconsViewController(MainController controller, Person personCard, PlayerState player){
        int amount = controller.getCardController().getAmountOf(personCard.getPersonType(), player);
        iconLabel = new IconLabelViewController(""+amount);
        iconSymbol = new IconSymbolViewController(personCard);
    }


    public IconLabelViewController getIconLabel() {
        return iconLabel;
    }

    public void setIconLabel(IconLabelViewController iconLabel) {
        this.iconLabel = iconLabel;
    }

    public IconSymbolViewController getIconSymbol() {
        return iconSymbol;
    }

    public void setIconSymbol(IconSymbolViewController iconSymbol) {
        this.iconSymbol = iconSymbol;
    }
}
