package view.menu;

import application.Main;
import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Player;
import model.PlayerType;

import java.io.IOException;

public class ChoosePlayerMenuViewController extends BorderPane { //TODO ChoosePlayerMenu entfernen, wird nicht gebraucht

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

    @FXML
    private ComboBox<PlayerType> boxPlayerType;

    @FXML
    private Button buttonP1;

    @FXML
    private Button buttonP2;

    @FXML
    private Button buttonP3;

    @FXML
    private Button buttonP4;

    @FXML
    private Button buttonP5;

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
                    } else setText(item.getName());*/
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

        Callback<ListView<PlayerType>, ListCell<PlayerType>> playerTypeFactory = (item -> new ListCell<>() {
            @Override
            protected void updateItem(PlayerType item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    //TODO Playertypes einfügen
                    setText(item.toString());
                }
            }
        });

        boxPlayerType.setButtonCell(playerTypeFactory.call(null));
        boxPlayerType.setCellFactory(playerTypeFactory);

    }

    @FXML
    void onClickShowAddPlayerContext(ActionEvent event) {

        if(event.getTarget().equals(buttonP1)){
            boxPlayer1.setDisable(true);
            buttonP1.setDisable(true);
            TextField newP1 = new TextField();
            newP1.setPromptText("Spielername");
            newP1.setAlignment(buttonP1.getAlignment());
            HBox hBox1 = new HBox();
            hBox1.getChildren().add(newP1);
            this.getChildren().add(hBox1);
            System.out.println("Button1");
        } else if(event.getTarget().equals(buttonP2)){
            boxPlayer2.setDisable(true);
            buttonP2.setDisable(true);
            TextField newP2 = new TextField();
            newP2.setPromptText("Spielername");
            newP2.setAlignment(Pos.CENTER_LEFT);
            System.out.println("Button2");
        } else if(event.getTarget().equals(buttonP3)){
            boxPlayer3.setDisable(true);
            buttonP3.setDisable(true);
            TextField newP3 = new TextField();
            newP3.setPromptText("Spielername");
            newP3.setAlignment(Pos.CENTER_LEFT);
            System.out.println("Button3");
        } else if(event.getTarget().equals(buttonP4)){
            boxPlayer4.setDisable(true);
            buttonP4.setDisable(true);
            TextField newP4 = new TextField();
            newP4.setPromptText("Spielername");
            newP4.setAlignment(Pos.CENTER_LEFT);
            System.out.println("Button4");
        } else if(event.getTarget().equals(buttonP5)){
            boxPlayer5.setDisable(true);
            buttonP5.setDisable(true);
            TextField newP5 = new TextField();
            newP5.setPromptText("Spielername");
            newP5.setAlignment(Pos.CENTER_LEFT);
            System.out.println("Button5");
        }

        //String test = event.getTarget().toString();

    }

    @FXML
    void onClickStartGame(ActionEvent event) {

    }

    @FXML
    void onClickGoBack(ActionEvent event) {
        HomePageViewController goBack = new HomePageViewController(mainController);
        Scene scene = new Scene(goBack, 1280, 720);

        Stage primaryStage = (Stage) this.getScene().getWindow();
        primaryStage.setScene(scene);
    }

    @FXML
    void onClickLoadDeck(ActionEvent event) {
        int numberOfPlayers = 0;
        //mainController.getIoController().loadCardDeck("", numberOfPlayers);

    }

}
