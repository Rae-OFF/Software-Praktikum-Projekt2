package view.playerfield;

import javafx.scene.control.Label;

public class PileLabelViewController extends Label {

    private Label label;

    public PileLabelViewController(String amount){
        Label label = new Label(amount);
    }


    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
}
