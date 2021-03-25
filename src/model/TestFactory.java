package model;

import controller.CardFactory;
import model.*;

import static model.ActionType.*;
import static model.PlayerType.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Daten zum Testen.
 */
public class TestFactory {

    /**
     * Erstellt eine Liste von Spielern.
     * @return Gibt eine Liste von Spielern zurück.
     */
    public static List<Player> getPlayers() {


        List<Player> players = new ArrayList<>();

        players.add(new Player("player0",HUMAN));
        players.add(new Player("player1",HUMAN));
        players.add(new Player("player2",HUMAN));
        players.add(new Player("player3",HUMAN));
        players.add(new Player("AI",EASYAI));

        return players;
    }

    /**
     * Erstellt Spielerzustände mit Handkarten.
     * @return Gibt eine Liste von Spielerzuständen zurück.
     */
    public static List<PlayerState> getPlayerState() {

        List<PlayerState> states = new ArrayList<>();

        CardFactory cardFactory = new CardFactory();
//get all players' name and type, add them into states list
        for (int i = 0; i < 5; i++) {

            states.add(new PlayerState(getPlayers().get(i)));
            states.get(i).setPlayer(getPlayers().get(i));

        }

  //generate cards for player 0;
        CardStack stackPlayer0 = states.get(0).getCards();
        List<Card> cards0 = stackPlayer0.getCards();

        cards0.add(cardFactory.generateAdmiral().get(0));  //victoryPoint: 1
        cards0.add(cardFactory.generateTrader().get(0));  //green, 3 coins
        cards0.add(cardFactory.generateJester().get(0));  //victoryPoint: 1
        cards0.add(cardFactory.generateMademoiselles().get(3)); //victoryPoint: 3

//generate cards for player 1;
        CardStack stackPlayer1 = states.get(1).getCards();
        List<Card> cards1 = stackPlayer1.getCards();

        cards1.add(cardFactory.generateSailor().get(0));  //victoryPoint: 1, sword:1
        cards1.add(cardFactory.generateGovernor().get(0));  //victoryPoint: 0
        cards1.add(cardFactory.generateJester().get(0));  //victoryPoint: 2

        CardStack coins = states.get(1).getCoins();
        coins.setCards(cards0);

//generate cards for player 2;
        CardStack stackPlayer2 = states.get(2).getCards();
        List<Card> cards2 = stackPlayer2.getCards();

        cards2.add(cardFactory.generateTrader().get(5));  //red, 3 coins
        cards2.add(cardFactory.generatePirate().get(1));  //victoryPoint: 2, sword 2
        cards2.add(cardFactory.generatePriest().get(0));  //victoryPoint: 1

//generate cards for player 3;
        CardStack stackPlayer3 = states.get(3).getCards();
        List<Card> cards3 = stackPlayer3.getCards();

        cards3.add(cardFactory.generateTrader().get(5));  //red, 3 coins
        cards3.add(cardFactory.generateSettlerCaptain().get(1));  //victoryPoint: 1, Settler
        cards3.add(cardFactory.generateJackOfAllTrader().get(0));  //victoryPoint: 1

//generate cards for player 4;
        CardStack stackPlayer4 = states.get(4).getCards();
        List<Card> cards4 = stackPlayer4.getCards();

        cards4.add(cardFactory.generatePriest().get(0));  //victoryPoint: 1
        cards4.add(cardFactory.generateSettlerCaptain().get(7));  //victoryPoint: 1, Captain
        cards4.add(cardFactory.generateAdmiral().get(0));  //victoryPoint: 1

        return states;
    }

    /**
     * Erstellt Züge.
     * @return Gibt eine Liste von Zügen zurück.
     */
    public static List<Move> moves(){

        List<Move> moves = new ArrayList<>();
        List<PlayerState> getPlayerState = getPlayerState();

        CardStack cardPile = CardFactory.newCardsWithoutSpecial();

        Move move = new Move(getPlayerState.get(0), true, getPlayerState.get(0), actions().get(0));
        Move lastMove = new Move(getPlayerState.get(4), true, getPlayerState.get(4), actions().get(2));
        Move currentMove = new Move(getPlayerState.get(1), true, getPlayerState.get(2), actions().get(1));
        currentMove.setCardPile(cardPile);
        currentMove.setActivePlayer(getPlayerState().get(2));
        currentMove.setDiscardPile(cardPile);
        currentMove.setHarbour(cardPile);

        moves.add(currentMove);
        moves.add(move);
        moves.add(lastMove);

        return moves;
    }

    /**
     * Erstellt Aktionen.
     * @return Gibt eine Liste von Aktionen zurück.
     */
    public static List<Action> actions(){

        List<Action> actions = new ArrayList<>();
        CardFactory cardFactory = new CardFactory();

        Card ship = cardFactory.generateYellowShips().get(0); // new Ship(Colour.YELLOW, 1, 1);
        Card admiral = cardFactory.generateAdmiral().get(0); //5 coins, victoryPoint 1
        //Person capitain =


        Action drawCard = new Action(DRAW_CARD, ship);
        Action takeShip = new Action(TAKE_SHIP, ship);
        Action buyPerson = new Action(BUY_PERSON, admiral);

        actions.add(drawCard);
        actions.add(takeShip);
        actions.add(buyPerson);

      return actions;
    }

}



