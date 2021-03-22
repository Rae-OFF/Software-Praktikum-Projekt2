package view.menu;

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
import view.game.GameViewController;

import java.io.IOException;

public class NewPlayerMenuViewController  extends BorderPane {

    private MainController mainController;

    private HomePageViewController homePage;

    @FXML
    private ComboBox<Player> boxPlayer2;

    @FXML
    private ComboBox<Player> boxPlayer1;

    @FXML
    private ComboBox<Player> boxPlayer3;

    @FXML
    private ComboBox<Player> boxPlayer4;

    @FXML
    private ComboBox<Player> boxPlayer5;

    @FXML
    private ComboBox<PlayerType> comboBoxNewPlayer1;

    @FXML
    private ComboBox<PlayerType> comboBoxNewPlayer2;

    @FXML
    private ComboBox<PlayerType> comboBoxNewPlayer3;

    @FXML
    private ComboBox<PlayerType> comboBoxNewPlayer4;

    @FXML
    private ComboBox<PlayerType> comboBoxNewPlayer5;

    @FXML
    private TextField textfieldPlayer1 = new TextField();

    @FXML
    private TextField textfieldPlayer2 = new TextField();

    @FXML
    private TextField textfieldPlayer3 = new TextField();

    @FXML
    private TextField textfieldPlayer4 = new TextField();

    @FXML
    private TextField textfieldPlayer5 = new TextField();

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

    @FXML
    private RadioButton defaultVariant;

    @FXML
    private RadioButton gameVariant;

    public NewPlayerMenuViewController(MainController mainController, HomePageViewController homePage){
        this.mainController = mainController;
        this.homePage = homePage;

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
    void initialize(){
        textfieldPlayer1.setVisible(false);
        textfieldPlayer2.setVisible(false);
        textfieldPlayer3.setVisible(false);
        textfieldPlayer4.setVisible(false);
        textfieldPlayer5.setVisible(false);
        comboBoxNewPlayer1.setVisible(false);
        comboBoxNewPlayer2.setVisible(false);
        comboBoxNewPlayer3.setVisible(false);
        comboBoxNewPlayer4.setVisible(false);
        comboBoxNewPlayer5.setVisible(false);

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

        comboBoxNewPlayer1.setButtonCell(playerTypeFactory.call(null));
        comboBoxNewPlayer1.setCellFactory(playerTypeFactory);

        comboBoxNewPlayer2.setButtonCell(playerTypeFactory.call(null));
        comboBoxNewPlayer2.setCellFactory(playerTypeFactory);

        comboBoxNewPlayer3.setButtonCell(playerTypeFactory.call(null));
        comboBoxNewPlayer3.setCellFactory(playerTypeFactory);

        comboBoxNewPlayer4.setButtonCell(playerTypeFactory.call(null));
        comboBoxNewPlayer4.setCellFactory(playerTypeFactory);

        comboBoxNewPlayer5.setButtonCell(playerTypeFactory.call(null));
        comboBoxNewPlayer5.setCellFactory(playerTypeFactory);
    }


    @FXML
    void onClickShowAddPlayerContext(ActionEvent event) {
        if(event.getTarget().equals(buttonP1)){
            boxPlayer1.setDisable(true);
            buttonP1.setDisable(true);
            textfieldPlayer1.setVisible(true);
            comboBoxNewPlayer1.setVisible(true);
            System.out.println("Button1");
        } else if(event.getTarget().equals(buttonP2)){
            boxPlayer2.setDisable(true);
            buttonP2.setDisable(true);
            textfieldPlayer2.setVisible(true);
            comboBoxNewPlayer2.setVisible(true);
            System.out.println("Button2");
        } else if(event.getTarget().equals(buttonP3)){
            boxPlayer3.setDisable(true);
            buttonP3.setDisable(true);
            textfieldPlayer3.setVisible(true);
            comboBoxNewPlayer3.setVisible(true);
            System.out.println("Button3");
        } else if(event.getTarget().equals(buttonP4)){
            boxPlayer4.setDisable(true);
            buttonP4.setDisable(true);
            textfieldPlayer4.setVisible(true);
            comboBoxNewPlayer4.setVisible(true);
            System.out.println("Button4");
        } else if(event.getTarget().equals(buttonP5)){
            boxPlayer5.setDisable(true);
            buttonP5.setDisable(true);
            textfieldPlayer5.setVisible(true);
            comboBoxNewPlayer5.setVisible(true);
            System.out.println("Button5");
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
    void onClickLoadDeck(ActionEvent event) {

    }

    @FXML
    void onClickStartGame(ActionEvent event) {
        //TODO mindestenes zwei Spieler
        if(defaultVariant.isSelected()){
            mainController.getGameSystem().getCurrentGame().setDefaultVariant(true);
        }   else if(gameVariant.isSelected()){
            mainController.getGameSystem().getCurrentGame().setDefaultVariant(false);
        }

        int count = 2;
       /*  int count = 0;
       if(!boxPlayer1.getSelectionModel().getSelectedItem().equals(null)&&!boxPlayer1.isDisabled()){
            count++;
        } else if(!boxPlayer2.getSelectionModel().getSelectedItem().equals(null)&&!boxPlayer2.isDisabled()){
            count++;
        } else if(!boxPlayer3.getSelectionModel().getSelectedItem().equals(null)&&!boxPlayer3.isDisabled()){
            count++;
        } else if(!boxPlayer4.getSelectionModel().getSelectedItem().equals(null)&&!boxPlayer3.isDisabled()){
            count++;
        } else if(!boxPlayer5.getSelectionModel().getSelectedItem().equals(null)&&!boxPlayer4.isDisabled()){
            count++;
        }*/

        //TODO neue Spieler abfangen

        if(count >= 2){
            GameViewController games = new GameViewController(mainController);
            Scene scene = new Scene(games, 1280, 720);

            Stage primaryStage = (Stage) this.getScene().getWindow();
            primaryStage.setScene(scene);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nicht genügend Spieler!");
            alert.setHeaderText(null);
            alert.setContentText("Es müssen mindestens 2 Spieler ausgewählt sein");

        }
    }
}
