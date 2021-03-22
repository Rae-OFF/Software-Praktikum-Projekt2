package view.menu;

import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class HomePageViewController extends BorderPane {

    private MainController mainController;
    public HomePageViewController(MainController mainController){
        this.mainController = mainController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu/HomePage.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        } catch (IOException error) {
            error.printStackTrace();
        }

    }

    @FXML
    void onClickBeenden(ActionEvent event) {

    }

    @FXML
    void onClickHighscore(ActionEvent event) {

    }

    @FXML
    void onClickLade(ActionEvent event) {

    }

    @FXML
    void onClickNeuesSpiel(ActionEvent event) {

    }
}
