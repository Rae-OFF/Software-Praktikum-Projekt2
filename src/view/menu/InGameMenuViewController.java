package view.menu;

import application.Main;
import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class InGameMenuViewController extends BorderPane {

        private MainController mainController;

        public InGameMenuViewController(MainController mainController){
                this.mainController = mainController;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu/Menu.fxml"));
                loader.setRoot(this);
                loader.setController(this);
                try{
                        loader.load();
                } catch (IOException error) {
                        error.printStackTrace();
                }

        }

        @FXML
        void onClickStop(ActionEvent event) {
                mainController.getIoController().save();
                Stage primaryStage = (Stage) this.getScene().getWindow();
                //primaryStage.setScene(); //Hauptmenü einbinden
        }

        @FXML
        void onClickWeiterSpielen(ActionEvent event) {
                ((Stage) this.getScene().getWindow()).close();
        }


}
