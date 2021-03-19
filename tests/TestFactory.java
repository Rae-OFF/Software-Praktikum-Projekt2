
import controller.CardController;
import controller.CardFactory;
import model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static model.ActionType.SKIP;
import static model.PlayerType.HUMAN;
import static org.junit.Assert.*;

import controller.MainController;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class TestFactory {
    private MainController mainController;
    private CardController cardController;
    private List<PlayerState> playerStateList;
    private GameSystem gameSystem;

    private List<Player> playerList;
    private Move move;
    private Action action;

    private Player player0;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;

    private PlayerState playerState0;
    private PlayerState playerState1;
    private PlayerState playerState2;
    private PlayerState playerState3;
    private PlayerState playerState4;

    private CardStack cardStack;
    private List<Card> personCard;
    private Card admiral;
    private Card jester;
    private Card trader;

    public TestFactory() {
    }

    public void initGame(){

        mainController = new MainController();
        cardController = new CardController(mainController);
        CardStack cardPile = CardFactory.newCardsWithSpecial();
        //int i = 1;
        //System.out.println(cardPile.getCards().size());;


        personCard = new ArrayList<>();
        cardStack = new CardStack();
        playerList = new ArrayList<>();
        playerStateList = new ArrayList<>();
        ;
        player0 = new Player("player0",HUMAN);
        player1 = new Player("player1",HUMAN);
        player2 = new Player("player2",HUMAN);
        player3 = new Player("player3",HUMAN);
        player4 = new Player("player4",HUMAN);

        playerState0 = new PlayerState(player0);
        playerState1 = new PlayerState(player1);
        playerState2 = new PlayerState(player2);
        playerState3 = new PlayerState(player3);
        playerState4 = new PlayerState(player4);

        playerStateList.add(0,playerState0);
        playerStateList.add(1,playerState1);
        playerStateList.add(2,playerState2);
        playerStateList.add(3,playerState3);
        playerStateList.add(4,playerState4);

        playerList.add(0,player0);
        playerList.add(1,player1);
        playerList.add(2,player2);
        playerList.add(3,player3);
        playerList.add(4,player4);


        //Cards von player0

        CardStack cardsOfPlayer0 = new CardStack();
        cardsOfPlayer0.getCards().add(cardPile.getCards().get(0));
        cardsOfPlayer0.getCards().add(cardPile.getCards().get(1));
        cardsOfPlayer0.getCards().add(cardPile.getCards().get(2));

        //Cards von player1
        CardStack cardsOfPlayer1 = new CardStack();
        //cardsOfPlayer1.getCards().add(cardPile.getCards().get(3));
        //cardsOfPlayer1.getCards().add(cardPile.getCards().get(4));
        //cardsOfPlayer1.getCards().add(cardPile.getCards().get(5));
        //cardsOfPlayer1.getCards().add(cardPile.getCards().get(50));


        /*
        for(Card card : cardPile.getCards()){
            if (card instanceof Person && ((Person) card).getPersonType().equals(PersonType.TRADER) && ((Person) card).getColour().equals(Colour.BLUE)) {

                cardsOfPlayer1.getCards().add(cardPile.getCards().get(cardPile.getCards().indexOf(card)));
                System.out.println(i+ "" +((Person) card).getPersonType() + " " + ((Person) card).getColour());
                i++;
            }
        }

         */




        playerState0.setCards(cardsOfPlayer0);
        playerState1.setCards(cardsOfPlayer1);


        gameSystem = new GameSystem();
        Game currentGame = new Game(true,playerState0,true,playerList,cardPile);
        mainController.setGameSystem(gameSystem);
        mainController.getGameSystem().setCurrentGame(currentGame);
        mainController.getGameSystem().getCurrentGame().setJesterEnabled(true);

        //blueShip in hand
        action = new Action(SKIP,cardPile.getCards().get(1));
        /*
        for( Card ship : cardPile.getCards()){
            if(ship instanceof Ship){
                action.setAffectedCard(cardPile.getCards().get(cardPile.getCards().indexOf(ship)));
                break;
            }
        }

         */

        move = new Move(playerState1,true, playerState1, action);
        move.setPlayers(playerStateList);
        //this.players = new ArrayList<>();
        move.setHarbour(null);
        move.setCardPile(cardPile);
        move.setDiscardPile(null);
        move.setExpeditionPile(null);



        //playerState1.setCards(cardStack);
        //playerState1.setPlayer(player1);
        //move.setActivePlayer(playerState1);

        move.getActivePlayer().getCoins().getCards().add(admiral);
        //move.getActivePlayer().getCoins().getCards().add(admiral);

    }


}
