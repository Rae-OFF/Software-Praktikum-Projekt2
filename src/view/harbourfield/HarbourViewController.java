package view.harbourfield;

import controller.MainController;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Card;
import model.Move;
import view.assets.CardImageViewController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HarbourViewController extends StackPane {

    private List<CardImageViewController> cardImageList = new ArrayList<>();

    /**
     * Konstruktor (stellt den Hafen dar).
     * @param controller
     *      MainController.
     * @param cards
     *      Karte.
     */
    public HarbourViewController(MainController controller, List<Card> cards){
        Rectangle rect = new Rectangle(50, 50, Color.BLUE);
        getChildren().add(rect);
        //cardImageList.addAll(cards.stream().map(CardImageViewController::new).collect(Collectors.toList()));
        getChildren().addAll(cardImageList);
        //CardImageViewController cardImage = new CardImageViewController(card);
        /*cardImageList.add(cardImage);
        cardImage.setTranslateX(200);
        getChildren().add(cardImage);*/
    }

    public void setCards(List<Card> cards){
        getChildren().removeAll(cardImageList);
        cardImageList.clear();

        cardImageList.addAll(cards.stream().map(CardImageViewController::new).collect(Collectors.toList()));
        getChildren().addAll(cardImageList);


    }
}

