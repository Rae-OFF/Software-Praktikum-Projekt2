package view.menu;

import controller.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import model.Game;
import model.Player;

import java.io.IOException;

public class GamesListViewController extends BorderPane {

    private MainController mainController;

    private HomePageViewController homePage;
    @FXML
    private ListView<Game> gamesList;

    public GamesListViewController(MainController mainController, HomePageViewController homePage){
        this.mainController = mainController;
        this.homePage = homePage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu/GamesList.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }


    @FXML
    void onClickGoBack(ActionEvent event) {
        HomePageViewController goBack = new HomePageViewController(mainController);
        Scene scene = new Scene(goBack, 1280, 720);

        Stage primaryStage = (Stage) this.getScene().getWindow();
        primaryStage.setScene(scene);
    }

    @FXML
    void onClickLoadGame(ActionEvent event) {

    }


}
