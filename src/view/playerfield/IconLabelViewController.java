package view.playerfield;

import javafx.scene.control.Label;
import model.PersonType;

public class IconLabelViewController extends Label {

    private Label label;

    public IconLabelViewController(String numberOf){
        label = new Label(numberOf);
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
}
