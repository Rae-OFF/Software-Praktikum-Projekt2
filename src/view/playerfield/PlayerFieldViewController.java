package view.playerfield;

import controller.CardController;
import controller.MainController;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import model.*;
import view.assets.CardPileImageViewController;
import view.playerfield.CoinPileViewController;
import view.playerfield.IconsViewController;

import java.util.ArrayList;
import java.util.List;

public class PlayerFieldViewController extends StackPane {

    private List<IconsViewController> iconList = new ArrayList<>();;

    private CoinPileViewController coinPile;

    private ImageView playerFieldImage;

    private Label name;

    private Label victoryPoints;

    private MainController mainController;

    private PlayerState playerState;

    public PlayerFieldViewController(MainController mainController, PlayerState player, boolean horizontal) {
        this.mainController = mainController;
        this.setAlignment(Pos.TOP_LEFT);
        playerState = player;
        playerFieldImage = new ImageView("view/resources/PlayerField.png");
        playerFieldImage.setFitHeight(160);
        playerFieldImage.setFitWidth(350);
        name = new Label(player.getPlayer().getName());

        victoryPoints = new Label("" + 0);
        victoryPoints.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        victoryPoints.setTextFill(Color.rgb(50,50,50));
        victoryPoints.setPrefWidth(50);
        victoryPoints.setAlignment(Pos.CENTER);

        coinPile = new CoinPileViewController(mainController, playerState);

        //test values
        Person p = new Person();
        p.setMetaData("SETTLER", Colour.BLACK, PersonType.SETTLER);
        player.getCards().getCards().add(p);
        Person p2 = new Person();
        p2.setMetaData("PRIEST", Colour.BLACK, PersonType.PRIEST);
        player.getCards().getCards().add(p2);
        player.getCards().getCards().add(p2);
        player.getCards().getCards().add(p2);


        if(horizontal){
            victoryPoints.setTranslateX(304);
            victoryPoints.setTranslateY(7);
            coinPile.setTranslateX(20);
            coinPile.setTranslateY(20);
        }

        getChildren().addAll(name,playerFieldImage,coinPile,victoryPoints);
        drawIcons(player);

        victoryPoints.setOnMouseClicked(event -> test(victoryPoints));
        coinPile.setOnMouseClicked(event -> test(coinPile.getPileLabel()));
    }

    public void test(Label label){
        int i = Integer.parseInt(label.getText());
        if(i==15)
            i=0;
        else
            i++;
        label.setText(""+i);
    }

    public void drawIcons(PlayerState player) {
        List<Card> playerCards = player.getCards().getCards();
        // Settlers - Captains - Priests - Jack of All Trades - Trader - Sailor/Pirate - Admiral - Jester - Governor -
        //Mademoiselle


        boolean[] cardTypes = new boolean[10];
        for (Card card : playerCards) {
            if (card instanceof Person) {
                switch (((Person) card).getPersonType()) {
                    case SETTLER:
                        if (!cardTypes[0]) {
                            iconList.add(new IconsViewController(mainController, (Person) card, player));
                        }
                        cardTypes[0] = true;
                        break;
                    case CAPTAIN:
                        if (!cardTypes[1]) {
                            iconList.add(new IconsViewController(mainController, (Person) card, player));
                        }
                        cardTypes[1] = true;
                        break;
                    case PRIEST:
                        if (!cardTypes[2]) {
                            iconList.add(new IconsViewController(mainController, (Person) card, player));
                        }
                        cardTypes[2] = true;
                        break;
                    case JACK_OF_ALL_TRADES:
                        if (!cardTypes[3]) {
                            iconList.add(new IconsViewController(mainController, (Person) card, player));
                        }
                        cardTypes[3] = true;
                        break;
                    case TRADER:
                        if (!cardTypes[4]) {
                            iconList.add(new IconsViewController(mainController, (Person) card, player));
                        }
                        cardTypes[4] = true;
                        break;
                    case SAILOR:
                    case PIRATE:
                        if (!cardTypes[5]) {
                            iconList.add(new IconsViewController(mainController, (Person) card, player));
                        }
                        cardTypes[5] = true;
                        break;
                    case ADMIRAL:
                        if (!cardTypes[6]) {
                            iconList.add(new IconsViewController(mainController, (Person) card, player));
                        }
                        cardTypes[6] = true;
                        break;
                    case JESTER:
                        if (!cardTypes[7]) {
                            iconList.add(new IconsViewController(mainController, (Person) card, player));
                        }
                        cardTypes[7] = true;
                        break;
                    case GOVERNOR:
                        if (!cardTypes[8]) {
                            iconList.add(new IconsViewController(mainController, (Person) card, player));
                        }
                        cardTypes[8] = true;
                        break;
                    case MADEMOISELLE:
                        if (!cardTypes[9]) {
                            iconList.add(new IconsViewController(mainController, (Person) card, player));
                        }
                        cardTypes[9] = true;
                        break;

                }
            }
        }

        int i=0;
        for(int y = 0; y< 2; y++){
            for(int x = 0; x< 1; x++){
                if(iconList.get(i) != null){
                    iconList.get(i).setTranslateX(110+x*40);
                    iconList.get(i).setTranslateY(30+y*40);
                    getChildren().add(iconList.get(i));
                    i++;
                }
            }
        }
//        for(int i=0; i<iconList.size();i++){
//            iconList.get(i).setTranslateX(110+i*40);
//            iconList.get(i).setTranslateY(20);
//            getChildren().add(iconList.get(i));
//        }
    }

    public void refresh(Move move) {
        // iconList wird geleert und neu erstellt
        for (IconsViewController i: iconList) {
            getChildren().remove(i);
        }
        iconList.clear();
        drawIcons(playerState);
        //victoryPoints neu berechnen und setzen
        int vPoints = 0;
        for (Card card : playerState.getCards().getCards()){
            if(card instanceof Person){
                vPoints += ((Person) card).getVictoryPoints();
            } else if(card instanceof Expedition){
                vPoints += ((Expedition) card).getVictoryPoints();
            }
        }
        victoryPoints.setText(""+vPoints);

        // coinPile wird aktualisiert
        coinPile.setPileLabel(""+playerState.getCoins().getSize());
        coinPile.setCardVisibility(playerState.getCoins().getSize() > 0);


    }
}
