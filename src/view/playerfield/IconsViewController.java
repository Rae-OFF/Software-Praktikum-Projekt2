package view.playerfield;

import controller.MainController;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Person;
import model.PersonType;
import model.PlayerState;
import view.assets.IconSymbolViewController;
import view.playerfield.IconLabelViewController;

public class IconsViewController extends Group {

    private Label iconLabel;

    private IconSymbolViewController iconSymbol;

    public IconsViewController(MainController controller, Person personCard, PlayerState player){
        int amount = controller.getCardController().getAmountOf(personCard.getPersonType(), player);
        iconLabel = new Label(""+amount);
        iconLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
        iconSymbol = new IconSymbolViewController(personCard);
        iconLabel.setAlignment(Pos.TOP_LEFT);
        iconLabel.setTranslateX(25);
        iconLabel.setTranslateY(25);
        iconSymbol.setFitHeight(30);
        iconSymbol.setFitWidth(30);
        getChildren().addAll(iconSymbol,iconLabel);
    }


    public Label getIconLabel() {
        return iconLabel;
    }

    public void setIconLabel(Label iconLabel) {
        this.iconLabel = iconLabel;
    }

    public ImageView getIconSymbol() {
        return iconSymbol;
    }

    public void setIconSymbol(IconSymbolViewController iconSymbol) {
        this.iconSymbol = iconSymbol;
    }
}
