package view.menu;

import application.Main;
import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.game.GameViewController;

import java.io.IOException;

public class InGameMenuViewController extends BorderPane {

        private MainController mainController;
        private GameViewController gameView;

        public InGameMenuViewController(MainController mainController, GameViewController gameView){
                this.mainController = mainController;
                this.gameView = gameView;
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

                HomePageViewController home = new HomePageViewController(mainController);
                Scene scene = new Scene(home, 1280, 720);

                Stage thisWindow = (Stage) this.getScene().getWindow();
                thisWindow.close();

                Stage primaryStage = (Stage) gameView.getScene().getWindow();
                primaryStage.setScene(scene);


        }

        @FXML
        void onClickWeiterSpielen(ActionEvent event) {
                ((Stage) this.getScene().getWindow()).close();
        }


}
