package view.harbourfield;

import controller.MainController;
import javafx.geometry.Pos;
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
        super();
        //Rectangle rect = new Rectangle(50, 50, Color.BLUE);
        //getChildren().add(rect);
        this.setAlignment(Pos.TOP_LEFT);
        this.setTranslateX(270);
        this.setTranslateY(180);

        this.setCards(cards);

    }

    public void setCards(List<Card> cards){
        getChildren().removeAll(cardImageList);
        cardImageList.clear();

        //cardImageList.addAll(cards.stream().map(CardImageViewController::new).collect(Collectors.toList()));
        //getChildren().addAll(cardImageList);

        int x = 0;
        int y = 0;
        this.setTranslateX(270);
        this.setTranslateY(180);
        int cardCount = 0;
        if (cards != null) {
            for(Card card : cards){
                if(cardCount < 6){
                    CardImageViewController cardImage = new CardImageViewController(card);
                    cardImageList.add(cardImage);

                    cardImage.setAlignment(Pos.TOP_LEFT);
                    cardImage.setTranslateX(cardCount * 90); //Width 80, Height 120
                    System.out.println(cardImage.getTranslateX()+" "+ cardCount + " " + cardImageList.size() + " " + cards.size());
                    //cardImage.setTranslateY(y+cardCount*130);
                    cardCount++;
                } else {
                    CardImageViewController cardImage = new CardImageViewController(card);
                    cardImageList.add(cardImage);
                    cardImage.setAlignment(Pos.TOP_LEFT);
                    cardImage.setTranslateX(0 * 90); //Width 80, Height 120
                    cardImage.setTranslateY(y+130);

                }

            }
            getChildren().addAll(cardImageList);
        }


    }
}

