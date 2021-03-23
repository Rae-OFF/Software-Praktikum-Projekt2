package view.harbourfield;

import controller.MainController;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import model.Card;
import model.Move;
import view.assets.CardImageViewController;

import java.util.ArrayList;
import java.util.List;

public class ExpeditionsViewController extends StackPane {

    private List<CardImageViewController> cardImageList = new ArrayList<>();

    public ExpeditionsViewController(MainController controller, List<Card> expedition){
        super();
        //cardImage = new CardImageViewController(expedition);
        this.setAlignment(Pos.TOP_LEFT);
        this.setTranslateX(270);
        this.setTranslateY(25);

        this.setCards(expedition);
    }

    public void setCards(List<Card> cards){
        getChildren().removeAll(cardImageList);
        cardImageList.clear();
        int cardCount = 0;

        if(cards != null){
            for(Card card : cards){
                CardImageViewController cardImage = new CardImageViewController(card);
                cardImageList.add(cardImage);

                cardImage.setAlignment(Pos.TOP_LEFT);
                cardImage.setTranslateX(cardCount * 90); //Width 80, Height 120
                System.out.println(cardImage.getTranslateX()+" "+ cardCount + " " + cardImageList.size() + " " + cards.size());
                //cardImage.setTranslateY(y+cardCount*130);
                cardCount++;
            }
            getChildren().addAll(cardImageList);
        }

    }

}
