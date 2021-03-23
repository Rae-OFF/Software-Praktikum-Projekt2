package view.playerfield;

import controller.CardController;
import controller.MainController;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.*;
import view.assets.CardPileImageViewController;
import view.playerfield.CoinPileViewController;
import view.playerfield.IconsViewController;

import java.util.List;

public class PlayerFieldViewController extends StackPane {

    private List<IconsViewController> iconList;

    private CoinPileViewController coinPile;

    private Image playerFieldImage;

    private Label name;

    private Label victoryPoints;

    private MainController mainController;

    private PlayerState playerState;

    public PlayerFieldViewController(MainController mainController, PlayerState player) {
        super();
        this.mainController = mainController;
        playerState = player;

        // ggf. Datei umbenennen
        //playerFieldImage = new ImageView("view/resources/Playerfield.png");

        name = new Label(player.getPlayer().getName());
        victoryPoints = new Label("" + 0);
        coinPile = new CoinPileViewController(mainController, playerState);
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
    }

    public void refresh(Move move) {
        // iconList wird geleert und neu erstellt
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
        if(Integer.parseInt(coinPile.getPileLabel().toString()) != playerState.getCoins().getSize()){
            if(playerState.getCoins().getSize() == 0 && coinPile.getPileLabel() != null)
                coinPile.setPileImage(null);
            else if(playerState.getCoins().getSize() > 0 && coinPile.getPileLabel() == null)
                coinPile.setPileImage(new CardPileImageViewController());
            coinPile.setPileLabel(new PileLabelViewController(""+ playerState.getCoins().getSize()));
            }

    }
}
