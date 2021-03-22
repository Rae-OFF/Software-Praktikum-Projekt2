package view.menu;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;
import model.Player;

public class PlayersListViewController extends BorderPane {


    public class PleaseProvideControllerClassName {

        @FXML
        private ListView<Player> PlayersList; // Value injected by FXMLLoader

        @FXML
        void onClickReturn(ActionEvent event) {

        }

    }

}
