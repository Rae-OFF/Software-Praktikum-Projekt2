package view.menu;

import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageViewController extends BorderPane {

    private MainController mainController;

    private HomePageViewController homePage;

    public HomePageViewController(MainController mainController){
        this.mainController = mainController;
        homePage = this;
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
        ((Stage) this.getScene().getWindow()).close();
    }

    @FXML
    void onClickHighscore(ActionEvent event) {
        HighscoreTableViewController highscore = new HighscoreTableViewController(mainController, homePage);
        Scene scene = new Scene(highscore, 1280, 720);

        Stage primaryStage = (Stage) homePage.getScene().getWindow();
        primaryStage.setScene(scene);
    }

    @FXML
    void onClickLade(ActionEvent event) {
        GamesListViewController games = new GamesListViewController(mainController, homePage);
        Scene scene = new Scene(games, 1280, 720);

        Stage primaryStage = (Stage) homePage.getScene().getWindow();
        primaryStage.setScene(scene);

    }

    @FXML
    void onClickNeuesSpiel(ActionEvent event) {
        NewPlayerMenuViewController choosePlayer = new NewPlayerMenuViewController(mainController, homePage);
        //ChoosePlayerMenuViewController choosePlayer = new ChoosePlayerMenuViewController(mainController, homePage);
        Scene scene = new Scene(choosePlayer, 1280, 720);

        Stage primaryStage = (Stage) homePage.getScene().getWindow();
        primaryStage.setScene(scene);

    }
}
