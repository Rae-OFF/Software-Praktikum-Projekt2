package view.menu;

import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class NewPlayerMenuViewController {

    private MainController mainController;
    @FXML
    private ComboBox<?> boxPlayer2;

    @FXML
    private ComboBox<?> boxPlayer1;

    @FXML
    private ComboBox<?> boxPLayer3;

    @FXML
    private ComboBox<?> boxPlayer4;

    @FXML
    private ComboBox<?> boxPlayer5;

    @FXML
    private ComboBox<?> comboBoxNewPlayer1;

    @FXML
    private ComboBox<?> comboBoxNewPlayer2;

    @FXML
    private ComboBox<?> comboBoxNewPlayer3;

    @FXML
    private ComboBox<?> comboBoxNewPlayer4;

    @FXML
    private ComboBox<?> comboBoxNewPlayer5;

    @FXML
    private TextField textfieldPlayer1;

    @FXML
    private TextField textfieldPlayer2;

    @FXML
    private TextField textfieldPlayer3;

    @FXML
    private TextField textfieldPlayer4;

    @FXML
    private TextField textfieldPlayer5;

    public NewPlayerMenuViewController(MainController mainController){
        this.mainController = mainController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu/NewPlayerMenu.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    @FXML
    void onClickShowAddPlayerContext(ActionEvent event) {

    }

    @FXML
    void onClickStartGame(ActionEvent event) {

    }
}
