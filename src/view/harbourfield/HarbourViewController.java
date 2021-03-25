package view.harbourfield;

import application.Main;
import controller.MainController;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Action;
import model.ActionType;
import model.Card;
import model.Move;
import view.assets.CardImageViewController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HarbourViewController extends StackPane {

    private List<CardImageViewController> cardImageList = new ArrayList<>();

    private MainController mainController;

    /**
     * Konstruktor (stellt den Hafen dar).
     * @param mainController
     *      MainController.
     */
    public HarbourViewController(MainController mainController){
        super();
        this.setPickOnBounds(false);
        this.mainController = mainController;
        /*Rectangle rect = new Rectangle(50, 50, Color.BLUE);
        rect.setHeight(120);
        rect.setWidth(80);*/
        /*getChildren().add(rect);*/
        this.setAlignment(Pos.TOP_LEFT);
        this.setTranslateX(280);
        this.setTranslateY(0);

        //this.setCards(cards);

    }

    public void setCards(List<Card> cards){
        getChildren().removeAll(cardImageList);
        cardImageList.clear();

        //cardImageList.addAll(cards.stream().map(CardImageViewController::new).collect(Collectors.toList()));
        //getChildren().addAll(cardImageList);

        int x = 0;
        int y = 0;
        /*this.setTranslateX(270);
        this.setTranslateY(180);*/
        int cardCount1 = 0;
        int cardCount2 = 0;
        if (cards != null) {
            for(Card card : cards){
                if(cardCount1 < 6){
                    CardImageViewController cardImage = new CardImageViewController(card);
                    cardImageList.add(cardImage);

                    cardImage.setAlignment(Pos.TOP_LEFT);
                    cardImage.setTranslateX(x+(cardCount1 * 90)); //Width 80, Height 120
                    System.out.println(cardImage.getTranslateX()+" "+ cardCount1 + " " + cardImageList.size() + " " + cards.size());
                    //cardImage.setTranslateY(y+cardCount*130);
                    cardCount1++;
                } else {
                    CardImageViewController cardImage = new CardImageViewController(card);
                    cardImageList.add(cardImage);
                    cardImage.setAlignment(Pos.TOP_LEFT);
                    cardImage.setTranslateX(cardCount2 * 90); //Width 80, Height 120
                    cardImage.setTranslateY(y+200);
                    cardCount2++;
                }

            }
            getChildren().addAll(cardImageList);
        }


    }

    public void refresh(Move move, List<Action> posAc){
        List<Card> cards = move.getHarbour().getCards();
        setCards(cards);
        System.out.println("Hafensize: " + move.getHarbour().getSize());

        /*for(Card card : wie oben hinzugefügt, prüfen ob clickhändler sein muss) //TODO der teil in setcard?
            for(Action action : posAc)
                if(action.getActionType().equals(ActionType.BUY_PERSON) || take person)
                if(card.equals(action.getAffectedCard()) //ggf über == prüfen)
                    card.clickhandler
                            mainController.executeAction())
        }*/
        //take ship, buy person
        getChildren().removeAll(cardImageList);
        cardImageList.clear();
        int x = 0;
        int y = 0;
        int cardCount1 = 0;
        int cardCount2 = 0;
        for(Card card : cards){
            if(cardCount1 < 6){
                CardImageViewController cardImage = new CardImageViewController(card);
                cardImageList.add(cardImage);

                cardImage.setAlignment(Pos.TOP_LEFT);
                cardImage.setTranslateX(cardCount1 * 90); //Width 80, Height 120
                //System.out.println(cardImage.getTranslateX()+" "+ cardCount + " " + cardImageList.size() + " " + cards.size());
                //cardImage.setTranslateY(cardCounty+180);
                for(Action action : posAc){
                    if(action.getActionType().equals(ActionType.BUY_PERSON)||action.getActionType().equals(ActionType.TAKE_SHIP)){
                        if(card == action.getAffectedCard()){
                            cardImage.setOnMouseClicked(event -> {
                                System.out.println("Take Ship or Buy Person");
                                mainController.getPlayerController().executeAction(action);
                                cardImage.setOpacity(1.0);
                            });
                        }
                    }
                }
                cardCount1++;

            } else if(cardCount2 < 6) {
                CardImageViewController cardImage = new CardImageViewController(card);
                cardImageList.add(cardImage);
                cardImage.setAlignment(Pos.TOP_LEFT);
                cardImage.setTranslateX(0);
                cardImage.setTranslateX(cardCount2 * 90); //Width 80, Height 120
                cardImage.setTranslateY(y+150);
                for(Action action : posAc){
                    if(action.getActionType().equals(ActionType.BUY_PERSON)||action.getActionType().equals(ActionType.TAKE_SHIP)){
                        if(card == action.getAffectedCard()){
                            cardImage.setOnMouseClicked(event -> {
                                mainController.getPlayerController().executeAction(action);
                                cardImage.setOpacity(1.0);
                            });
                        }
                    }
                }
                cardCount2++;

            }

        }
        getChildren().addAll(cardImageList);
    }
}

