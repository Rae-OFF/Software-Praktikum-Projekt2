package view.menu;

import controller.MainController;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Player;

import java.io.IOException;
import java.util.List;

public class HighscoreTableViewController extends BorderPane {

    private MainController mainController;

    private HomePageViewController homePage;

    private Image backgroundImage;

    @FXML
    private TableColumn<Player, String> colName;

    @FXML
    private TableColumn<Player, String> colPoints; //TODO Integer oder String?

    @FXML
    private TableColumn<Player, String> colWins;

    public HighscoreTableViewController(MainController mainController, HomePageViewController homePage){
        this.mainController = mainController;
        this.homePage = homePage;
        backgroundImage =  new Image(getClass().getResourceAsStream("/view/resources/backgroundImage.png"));
        Background background = new Background(new BackgroundImage(backgroundImage, //TODO Hintergrundbild einfügen
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1280, 720, false,
                        false, true, false)));
        this.setBackground(background);


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu/HighscoreTable.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        } catch (IOException error) {
            error.printStackTrace();
        }

    }

    @FXML
    void initialize(){
        List<Player> highscoreList = mainController.getHighscoreController().getHighscoreList();

        colName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        colPoints.setCellValueFactory(data -> new SimpleStringProperty(""+data.getValue().getScore())); //TODO verbesern?
        colWins.setCellValueFactory(data -> new SimpleStringProperty(""+data.getValue().getWins()));
    }

    @FXML
    void onClickGoBack(ActionEvent event) {
        HomePageViewController goBack = new HomePageViewController(mainController);
        Scene scene = new Scene(goBack, 1280, 720);

        Stage primaryStage = (Stage) this.getScene().getWindow();
        primaryStage.setScene(scene);
    }
}
