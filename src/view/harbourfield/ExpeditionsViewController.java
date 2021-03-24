package view.harbourfield;

import controller.MainController;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.Action;
import model.ActionType;
import model.Card;
import model.Move;
import view.assets.CardImageViewController;

import java.util.ArrayList;
import java.util.List;

public class ExpeditionsViewController extends StackPane {

    private List<CardImageViewController> cardImageList = new ArrayList<>();

    private MainController mainController;

    public ExpeditionsViewController(MainController controller, List<Card> expedition, Move move){
        super();
        mainController = controller;
        //cardImage = new CardImageViewController(expedition);
        this.setAlignment(Pos.TOP_LEFT);
        this.setTranslateX(270);
        this.setTranslateY(25);

        /*this.setCards(expedition);
        if(move != null && move.getActivePlayer().equals(move.getActor())){
            this.callExpedition(expedition);
        }*/
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
                cardCount++;
            }
            getChildren().addAll(cardImageList);
        }

    }

    //sollte nicht mehr nötig sein
   /* public void callExpedition(List<Card> card){
        for(CardImageViewController exp : cardImageList){
            exp.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Button start = new Button("Start Expedition");
                    getChildren().add(start);
                    start.setAlignment(Pos.TOP_LEFT);
                    start.setTranslateX(0);
                    start.setTranslateY(120);
                    //mainController.getGameController().currentMove().getHarbour().push(card);
                    System.out.println("call Expedition"+ exp.getCardImage().getImage().getUrl());
                }
            });
        }
    }*/

    public void refresh(Move move, List<Action> posAc){
        List<Card> cards = move.getExpeditionPile().getCards();
        setCards(cards);

        int cardCount = 0;
        for(Card card : cards){
            CardImageViewController cardImage = new CardImageViewController(card);
            cardImageList.add(cardImage);
            cardImage.setAlignment(Pos.TOP_LEFT);
            cardImage.setTranslateX(cardCount * 90); //Width 80, Height 120
            System.out.println("Expedition: " + cardImage.getTranslateX()+" "+ cardCount + " " + cardImageList.size() + " " + cards.size());
            for(Action action : posAc){
                if(action.getActionType().equals(ActionType.START_EXPEDITION)){
                    if(card == action.getAffectedCard()){
                        cardImage.setOnMouseClicked(event -> {
                            System.out.println("Supposed to start Expedition");
                            mainController.getPlayerController().executeAction(action);
                        });

                    }
                }
            }
            cardCount++;
        }
        getChildren().addAll(cardImageList);
    }

}
