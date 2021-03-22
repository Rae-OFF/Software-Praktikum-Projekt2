package view.menu;

import application.Main;
import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Player;

import java.io.IOException;

public class ChoosePlayerMenuViewController extends BorderPane {

    private MainController mainController;

    private HomePageViewController homePage;

    @FXML
    private ComboBox<Player> boxPlayer1;

    @FXML
    private ComboBox<Player> boxPlayer2;

    @FXML
    private ComboBox<Player> boxPlayer3;

    @FXML
    private ComboBox<Player> boxPlayer4;

    @FXML
    private ComboBox<Player> boxPlayer5;

    public ChoosePlayerMenuViewController(MainController mainController, HomePageViewController homePage){
        this.mainController = mainController;
        this.homePage = homePage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu/ChoosePlayerMenu.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try{
            loader.load();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    @FXML
    void inititalize(){
        Callback<ListView<Player>, ListCell<Player>> playerFactory = (item -> new ListCell<>() {
            @Override
            protected void updateItem(Player item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    /*if(item.getPlayerType().equals("EASYAI")||item.getPlayerType().equals("MEDIUMAI")||
                            item.getPlayerType().equals("HARDAI")){
                        setText(item.getName() + " " + item.getPlayerType().toString());
                    } else setText(item.getName());*/ //TODO Flag für AI Spieler
                    setText(item.getName());
                }
            }
        });

        boxPlayer1.setButtonCell(playerFactory.call(null));
        boxPlayer1.setCellFactory(playerFactory);

        boxPlayer2.setButtonCell(playerFactory.call(null));
        boxPlayer2.setCellFactory(playerFactory);

        boxPlayer3.setButtonCell(playerFactory.call(null));
        boxPlayer3.setCellFactory(playerFactory);

        boxPlayer4.setButtonCell(playerFactory.call(null));
        boxPlayer4.setCellFactory(playerFactory);

        boxPlayer5.setButtonCell(playerFactory.call(null));
        boxPlayer5.setCellFactory(playerFactory);



    }

    @FXML
    void onClickShowAddPlayerContext(ActionEvent event) {
        //Dynamisch
    }

    @FXML
    void onClickStartGame(ActionEvent event) {

    }

    @FXML
    void onClickGoBack(ActionEvent event) {
        HomePageViewController goBack = new HomePageViewController(mainController);
        Scene scene = new Scene(goBack, 1280, 720);

        Stage primaryStage = (Stage) goBack.getScene().getWindow(); //TODO auf Hauptmenü zurückführen
        primaryStage.setScene(scene);
    }

    @FXML
    void onClickLoadDeck(ActionEvent event) {
        int numberOfPlayers = 0;
        //mainController.getIoController().loadCardDeck("", numberOfPlayers);

    }

}
