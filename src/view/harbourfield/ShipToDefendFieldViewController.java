package view.harbourfield;

import controller.MainController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.Action;
import model.ActionType;
import model.Move;
import view.assets.CardImageViewController;

import java.util.List;


public class ShipToDefendFieldViewController extends StackPane {

    private CardImageViewController cardImage;

    private MainController mainController;

    private Button accept;

//    private ImageView accept;

    private Button defend;

    public ShipToDefendFieldViewController(MainController mainController){
        super();
        this.mainController = mainController;
        this.setAlignment(Pos.TOP_LEFT);
        this.setTranslateX(100);
        this.setTranslateY(320);
        this.setHeight(120);
        this.setWidth(80);

       /* Rectangle rect = new Rectangle(200, 200, Color.BEIGE);
        getChildren().add(rect);*/

        //defend Button
        defend = new Button("Abwehren");
        getChildren().add(defend);
        defend.setAlignment(Pos.TOP_LEFT);
        defend.setTranslateX(-40);
        defend.setTranslateY(120);
        defend.setMaxSize(75, 20);
        defend.setVisible(false);

        //accept button
        accept = new Button("Aufnehmen");
        getChildren().add(accept);
        accept.setAlignment(Pos.TOP_LEFT);
        accept.setTranslateX(40);
        accept.setTranslateY(120);
        accept.setMaxSize(80, 20);
        accept.setVisible(false);
        /*accept = new ImageView("view/resources/redoButton.png");
        accept.setFitHeight(50);
        accept.setFitWidth(50);
        accept.setTranslateX(40);
        accept.setTranslateY(120);
        accept.setVisible(false);
        getChildren().add(accept);*/

        cardImage = new CardImageViewController(null);
        getChildren().add(cardImage);

    }

    public void refresh(Move move, List<Action> posAc){
        getChildren().remove(cardImage);
        cardImage = new CardImageViewController(move.getShipToDefend());
        System.out.println(move.getShipToDefend());
        //CardStack stack = controller.CardFactory.newCardsWithoutSpecial(); //zum testen feste Karte übergeben
        //cardImage = new CardImageViewController(stack.getCards().get(0));
        getChildren().add(cardImage);
        cardImage.setAlignment(Pos.TOP_LEFT);

        defend.setOnMouseClicked(null);
        accept.setOnMouseClicked(null);
        setVisible(false);

        for(Action action : posAc){
            if(action.getActionType().equals(ActionType.DEFEND)){
                setVisible(true);
                defend.setVisible(true);
                defend.setOnMouseClicked(event -> {
                    mainController.getPlayerController().executeAction(action);
                });

            }
            if(action.getActionType().equals(ActionType.ACCEPT_SHIP)){
                setVisible(true);
                accept.setVisible(true);
                //mainController.getPlayerController().executeAction(action);
                System.out.println("Want to take ship");
                accept.setOnMouseClicked(event -> {
                    System.out.println("Take ship");
                    mainController.getPlayerController().executeAction(action); //TODO onClick funktional kriegen

                });

            }
        }
    }
}
