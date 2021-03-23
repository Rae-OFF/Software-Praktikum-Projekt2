package controller;

import model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static model.ActionType.*;
import static model.PersonType.*;
import static model.PlayerType.HUMAN;
import static org.junit.Assert.*;

import controller.MainController;

import java.util.ArrayList;
import java.util.List;

/**
 * Test fuer CardController
 */
public class CardControllerTest {
    private TestFactory testFactory;

    private MainController mainController;
    private CardController cardController;
    private PlayerController playerController;
    private List<PlayerState> playerStateList;
    private GameSystem gameSystem;

    private List<Player> playerList;

    private Move move;
    private List<Move> moveList;
    private Game currentGame;
    private List<Action> actionList;
    private Action action;
    private CardFactory cardFactory;

    private CardStack harbour;
    private CardStack discardPile;
    private CardStack expendition;

    /**
     * Erstellt eine neue Testumgebung
     */
    @Before
    public void setUp() {
        testFactory = new TestFactory();

        mainController = new MainController();
        cardController = mainController.getCardController();
        playerController = mainController.getPlayerController();

        CardStack cardPile = CardFactory.newCardsWithSpecial();

        cardFactory = new CardFactory();

        playerList = TestFactory.getPlayers();
        playerStateList = TestFactory.getPlayerState();
        moveList = TestFactory.moves();
        move = moveList.get(0);
        // move = new Move(playerState1, true, playerState1, action);
        move.setPlayers(playerStateList);
        move.setActivePlayer(playerStateList.get(0));
        currentGame = new Game(true, playerStateList.get(0),true,playerList,move.getCardPile());

        harbour = new CardStack();
        discardPile = new CardStack();
        expendition = new CardStack();

        move.setHarbour(harbour);
        move.setCardPile(cardPile);
        move.setDiscardPile(discardPile);
        move.setExpeditionPile(expendition);

        actionList = TestFactory.actions();
        action = actionList.get(0);
        move.setAction(action);

        gameSystem = new GameSystem();
        mainController.setGameSystem(gameSystem);
        gameSystem.setCurrentGame(currentGame);

    }

    /**
     * Testet ob parameter null sind.
     * @throws NullPointerException
     *      Wirft eine NullPointerException.
     */

    @Test(expected = NullPointerException.class)
    public void execAdmiralNull() {
        mainController.getCardController().execAdmiral(null, null);
    }



    /**
     * Testet die Funktionalitaet von Admiral. der aktive Spieler hat Admiral
     */
    @Test
    public void execAdmiralHave() { // if Bedingung erfuellt
        move.getHarbour().getCards().add(cardFactory.generateBlueShips().get(0));
        move.getHarbour().getCards().add(cardFactory.generateRedShips().get(0));
        move.getHarbour().getCards().add(cardFactory.generateBlueShips().get(0));
        move.getHarbour().getCards().add(cardFactory.generatePirate().get(0));
        move.getHarbour().getCards().add(cardFactory.generateTrader().get(9));
        actionList.get(0).setAffectedCard(cardFactory.generateAdmiral().get(0));

        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 0);
        assertEquals(move.getCardPile().getCards().size(), 120);


        mainController.getCardController().execAdmiral(move, playerStateList.get(0));

        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 2);
        assertEquals(move.getCardPile().getCards().size(), 118);

    }

    /**
     * Testet die Funktionalitaet von Admiral. der aktive Spieler hat kein Admiral
     */
    @Test
    public void execAdmiralNotHave() { // else erfuellt
        //move.setActivePlayer(playerStateList.get(0));
        move.getHarbour().getCards().add(cardFactory.generateBlueShips().get(0));
        move.getHarbour().getCards().add(cardFactory.generateRedShips().get(0));
        move.getHarbour().getCards().add(cardFactory.generateBlueShips().get(0));
        move.getHarbour().getCards().add(cardFactory.generatePirate().get(0));
        actionList.get(0).setAffectedCard(cardFactory.generateAdmiral().get(0));

        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 0);
        assertEquals(move.getCardPile().getCards().size(), 120);
        mainController.getCardController().execAdmiral(move, playerStateList.get(0));
        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 0);
        assertEquals(move.getCardPile().getCards().size(), 120);
    }

    @Test
    public void execAdmiralNotHave1() { // else erfuellt
        move.setActivePlayer(playerStateList.get(1));
        move.getHarbour().getCards().add(cardFactory.generateBlueShips().get(0));
        move.getHarbour().getCards().add(cardFactory.generateRedShips().get(0));
        move.getHarbour().getCards().add(cardFactory.generateBlueShips().get(0));
        move.getHarbour().getCards().add(cardFactory.generatePirate().get(0));
        move.getHarbour().getCards().add(cardFactory.generateBlueShips().get(0));
        actionList.get(0).setAffectedCard(cardFactory.generateJester().get(0));

        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 0);
        assertEquals(move.getCardPile().getCards().size(), 120);
        mainController.getCardController().execAdmiral(move, playerStateList.get(0));
        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 2);
        assertEquals(move.getCardPile().getCards().size(), 118);
    }

    @Test
    public void execAdmiralNotHave2() { // else erfuellt
        move.setActivePlayer(playerStateList.get(1));
        move.getHarbour().getCards().add(cardFactory.generateBlueShips().get(0));
        move.getHarbour().getCards().add(cardFactory.generateRedShips().get(0));
        move.getHarbour().getCards().add(cardFactory.generateBlueShips().get(0));
        move.getHarbour().getCards().add(cardFactory.generatePirate().get(0));
        move.getHarbour().getCards().add(cardFactory.generateBlueShips().get(0));
        actionList.get(0).setAffectedCard(cardFactory.generateBlueShips().get(0));

        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 0);
        assertEquals(move.getCardPile().getCards().size(), 120);
        mainController.getCardController().execAdmiral(move, playerStateList.get(0));
        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 2);
        assertEquals(move.getCardPile().getCards().size(), 118);
    }


    /**
     * Testet ob parameter null sind.
     * @throws NullPointerException
     *      Wirft eine NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void execJesterNull() {
        mainController.getCardController().execJester(null);
    }


    /**
     * Testet die Funktionalitaet von Jester. der aktive Spieler hat Jester
     */
    @Test
    public void execJesterHave() { // if Bedingung. if JESTER is at player's hand, all these players get 1 coin
        move.setActivePlayer(playerStateList.get(0));
        move.setPhase1(true);
        action.setActionType(SKIP);
        playerStateList.get(0).getCards().getCards().add(cardFactory.generateJester().get(0)); // player 0: 2 Jester, Player 1: 1 Jester


        //mainController.getGameSystem().getCurrentGame().setJesterEnabled(true);
        assertEquals(playerStateList.get(0).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 5);
        assertEquals(playerStateList.get(2).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(3).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(4).getCoins().getSize(), 0);

        mainController.getCardController().execJester(move);

        assertEquals(playerStateList.get(0).getCoins().getSize(), 3);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 5);
        assertEquals(playerStateList.get(2).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(3).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(4).getCoins().getSize(), 0);
    }

    /**
     * Testet die Funktionalitaet von Jester. der aktive Spieler hat kein Jester
     */
    @Test
    public void execJesterNotHave() { // else Bedingung.in phase 2, only active player get 1 coin
        move.setActivePlayer(playerStateList.get(0));
        move.setPhase1(false);
        action.setActionType(SKIP);
        //currentGame.setJesterEnabled(true);

        assertEquals(playerStateList.get(0).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 4);
        assertEquals(playerStateList.get(2).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(3).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(4).getCoins().getSize(), 0);

        mainController.getCardController().execJester(move);

        assertEquals(playerStateList.get(0).getCoins().getSize(), 2);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 4);
        assertEquals(playerStateList.get(2).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(3).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(4).getCoins().getSize(), 0);
    }

    @Test
    public void execJesterNotSkip() { // else Bedingung.in phase 2, only active player get 1 coin
        move.setActivePlayer(playerStateList.get(0));
        move.setPhase1(false);
        action.setActionType(DRAW_CARD);
        //currentGame.setJesterEnabled(true);

        assertEquals(playerStateList.get(0).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 4);
        assertEquals(playerStateList.get(2).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(3).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(4).getCoins().getSize(), 0);

        mainController.getCardController().execJester(move);

        assertEquals(playerStateList.get(0).getCoins().getSize(), 2);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 4);
        assertEquals(playerStateList.get(2).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(3).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(4).getCoins().getSize(), 0);
    }

    @Test
    public void execJesterNoEnabled() { // else Bedingung.in phase 2, only active player get 1 coin
        move.setActivePlayer(playerStateList.get(0));
        move.setPhase1(false);
        action.setActionType(DRAW_CARD);
        //currentGame.setJesterEnabled(false);

        assertEquals(playerStateList.get(0).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 4);
        assertEquals(playerStateList.get(2).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(3).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(4).getCoins().getSize(), 0);

        mainController.getCardController().execJester(move);

        assertEquals(playerStateList.get(0).getCoins().getSize(), 2);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 4);
        assertEquals(playerStateList.get(2).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(3).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(4).getCoins().getSize(), 0);
    }

    @Test
    public void execJesterNoPerson() { // else Bedingung.in phase 2, only active player get 1 coin
        move.setActivePlayer(playerStateList.get(3));
        move.setPhase1(true);
        action.setActionType(SKIP);
        //currentGame.setJesterEnabled(true);
        for(PlayerState playerState : playerStateList){
            for(Card card: playerState.getCards().getCards()){
                if(card instanceof Person ){
                    playerState.getCards().getCards().clear();
                    playerState.getCards().getCards().add(cardFactory.generateExpedition(true).get(0));
                }
            }
        }

        assertEquals(playerStateList.get(0).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 1);
        assertEquals(playerStateList.get(2).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(3).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(4).getCoins().getSize(), 0);

        mainController.getCardController().execJester(move);

        assertEquals(playerStateList.get(0).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 1);
        assertEquals(playerStateList.get(2).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(3).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(4).getCoins().getSize(), 0);
    }




    /**
     * Testet ob parameter null sind.
     * @throws NullPointerException
     *      Wirft eine NullPointerException.
     */

    @Test(expected = NullPointerException.class)
    public void execTraderNull(){
        mainController.getCardController().execTrader(null, null);
    }



    /**
     * Testet die Funktionalitaet von Jester. der aktive Spieler hat Trader
     */
    @Test
    public void execTraderHave() { // active Player has Trader
        move.setActivePlayer(playerStateList.get(0));
        action.setAffectedCard(cardFactory.generateGreenShips().get(0)); //greenTrader
        move.getActivePlayer().getCards().getCards().add(cardFactory.generateTrader().get(0));// greenTrader

        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 0);

        mainController.getCardController().execTrader(move, action);
        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 2);
    }

    /**
     * Testet die Funktionalitaet von Jester. der aktive Spieler hat kein Trader
     */
    @Test
    public void execTraderNotHave() { // active Player has not Trader
        move.setActivePlayer(playerStateList.get(1));
        action.setAffectedCard(cardFactory.generateAdmiral().get(0)); //greenTrader
        move.getActivePlayer().getCards().getCards().add(cardFactory.generateRedShips().get(0));// redShip

        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 0);

        mainController.getCardController().execTrader(move, action);
        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 0);
    }

    /**
     * Testet die Funktionalitaet von Jester. der aktive Spieler hat kein Trader
     */
    @Test
    public void execTraderNoShip() { // active Player has not Trader
        move.setActivePlayer(playerStateList.get(1));
        action.setAffectedCard(cardFactory.generateGreenShips().get(0)); //greenTrader
        move.getActivePlayer().getCards().getCards().add(cardFactory.generateTrader().get(0));// greenTrader

        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 0);

        mainController.getCardController().execTrader(move, action);
        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 0);
    }

    /**
     * Testet die Funktionalitaet von Jester. der aktive Spieler hat kein Trader
     */
    @Test
    public void execTraderNoSameColor() { // active Player has not Trader
        move.setActivePlayer(playerStateList.get(1));
        action.setAffectedCard(cardFactory.generateYellowShips().get(0)); //greenTrader
        move.getActivePlayer().getCards().getCards().add(cardFactory.generateTrader().get(0));// greenTrader

        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 0);

        mainController.getCardController().execTrader(move, action);
        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 0);
    }

    /**
     * Testet die Funktionalitaet von Jester. keine PersonCard.
     */
    @Test
    public void execTraderNoPersonCard() { // active Player has not Trader
        move.setActivePlayer(playerStateList.get(1));
        action.setAffectedCard(cardFactory.generateYellowShips().get(0));
        for(PlayerState playerState : playerStateList){
            for(Card card: playerState.getCards().getCards()){
                if(card instanceof Person ){
                    playerState.getCards().getCards().clear();
                    playerState.getCards().getCards().add(cardFactory.generateExpedition(true).get(0));
                }
            }
        }

        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 0);

        mainController.getCardController().execTrader(move, action);
        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 0);
    }



    /**
     * Testet ob parameter null sind.
     * @throws NullPointerException
     *      Wirft eine NullPointerException.
     */

    @Test(expected = NullPointerException.class)
    public void drawCardNull(){
        mainController.getCardController().drawCard(null, null);
    }



    /**
     * Testet das erfolgreiche drawCard. der aktive Spieler hat eine TaxCard mit maxSwords aufgedeckt.
     */
    @Test
    public void drawCardTaxMaxSwords() { //if(card instanceof TaxIncrease ),if(((TaxIncrease) card).isTypeSwords())
        move.setActivePlayer(playerStateList.get(0));
        action.setAffectedCard(cardFactory.generateTaxIncrease().get(2));

        assertEquals(playerStateList.get(0).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 4);
        assertEquals(playerStateList.get(2).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(3).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(4).getCoins().getSize(), 0);

        mainController.getCardController().taxIncreaseOfMaxSwords(move, action);

        assertEquals(playerStateList.get(0).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 4);
        assertEquals(playerStateList.get(2).getCoins().getSize(), 1);
        assertEquals(playerStateList.get(3).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(4).getCoins().getSize(), 0);
    }

    /**
     * Testet das erfolgreiche drawCard. der aktive Spieler hat eine TaxCard mit maxSwords aufgedeckt.
     */
    @Test
    public void drawCardNoTaxMaxSwords() { //if(card instanceof TaxIncrease ),if(((TaxIncrease) card).isTypeSwords())
        move.setActivePlayer(playerStateList.get(0));
        action.setAffectedCard(cardFactory.generateRedShips().get(2));

        assertEquals(playerStateList.get(0).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 4);
        assertEquals(playerStateList.get(2).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(3).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(4).getCoins().getSize(), 0);

        mainController.getCardController().taxIncreaseOfMaxSwords(move, action);

        assertEquals(playerStateList.get(0).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 4);
        assertEquals(playerStateList.get(2).getCoins().getSize(), 1);
        assertEquals(playerStateList.get(3).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(4).getCoins().getSize(), 0);
    }

    /**
     * Testet das erfolgreiche drawCard. der aktive Spieler hat eine TaxCard mit maxSwords aufgedeckt.
     */
    @Test
    public void drawCardNoSwords() { //if(card instanceof TaxIncrease ),if(((TaxIncrease) card).isTypeSwords())
        move.setActivePlayer(playerStateList.get(0));
        action.setAffectedCard(cardFactory.generateTaxIncrease().get(0));

        assertEquals(playerStateList.get(0).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 4);
        assertEquals(playerStateList.get(2).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(3).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(4).getCoins().getSize(), 0);

        mainController.getCardController().taxIncreaseOfMaxSwords(move, action);

        assertEquals(playerStateList.get(0).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 4);
        assertEquals(playerStateList.get(2).getCoins().getSize(), 1);
        assertEquals(playerStateList.get(3).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(4).getCoins().getSize(), 0);
    }

    /**
     * Testet das erfolgreiche drawCard. der aktive Spieler hat eine TaxCard mit minShield aufgedeckt.
     */
    @Test
    public void drawCardTaxMinShield() { //if(card instanceof TaxIncrease ),else.
        move.setActivePlayer(playerStateList.get(0));
        move.getCardPile().getCards().add(0, cardFactory.generateTaxIncrease().get(0));
        action.setAffectedCard(cardFactory.generateTaxIncrease().get(0));
        //victoryPoints: player0:6, player1:2, player2:4, player3:3, player4:3

        assertEquals(playerStateList.get(0).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 4);
        assertEquals(playerStateList.get(2).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(3).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(4).getCoins().getSize(), 0);

        mainController.getCardController().drawCard(move, action);

        assertEquals(playerStateList.get(0).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 5);
        assertEquals(playerStateList.get(2).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(3).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(4).getCoins().getSize(), 0);
    }

    /**
     * Testet das erfolgreiche drawCard.
     * der aktive Spieler hat eine TaxCard mit Expedition aufgedeckt.
     */
    @Test
    public void drawCardExpedition() { // if(card instanceof Expedition){
        move.setActivePlayer(playerStateList.get(3));
        move.getCardPile().getCards().add(0, cardFactory.generateExpedition(true).get(5));

        assertEquals(move.getExpeditionPile().getCards().size(), 0);

        mainController.getCardController().drawCard(move, action);

        assertEquals(move.getExpeditionPile().getCards().size(), 1);

    }

    /**
     * Testet das erfolgreiche drawCard.
     * der aktive Spieler hat eine ShipCard aufgedeckt, die die Bedingung isZonked() erfuellt.
     */
    @Test
    public void drawCardShipIsZonked() { // if(card instanceof Ship). isZonked()==false
        move.setActivePlayer(playerStateList.get(0));
        move.getCardPile().getCards().add(0, cardFactory.generateBlueShips().get(0));
        move.setActor(playerStateList.get(0));
        action.setAffectedCard(cardFactory.generateBlueShips().get(0)); // coins: 1
        move.getHarbour().getCards().add(cardFactory.generateGreenShips().get(0));

        assertEquals(move.getHarbour().getCards().size(), 1);
        mainController.getCardController().drawCard(move, action);
        assertEquals(move.getHarbour().getCards().size(), 1); //2
    }

    /**
     * Testet das erfolgreiche drawCard.
     * der aktive Spieler hat eine PersonCard aufgedeckt.
     */
    @Test
    public void drawCardPerson() { // person
        move.setActivePlayer(playerStateList.get(0));
        move.getCardPile().getCards().add(0, cardFactory.generatePirate().get(2));
        playerStateList.get(0).getCards().getCards().add(cardFactory.generatePirate().get(2));
        action.setAffectedCard(cardFactory.generatePirate().get(2));

        assertEquals(move.getHarbour().getCards().size(), 0);
        mainController.getCardController().drawCard(move, action);
        assertEquals(move.getHarbour().getCards().size(), 1);

    }

    /**
     * Testet ob parameter null sind.
     * @throws NullPointerException
     *      Wirft eine NullPointerException.
     */

    @Test(expected = NullPointerException.class)
    public void coinsMoreThan12Null(){
        mainController.getCardController().coinsMoreThan12(null);
    }



    /**
     * Testet, wenn ein Spieler mit 12 oder mehr coins haben,
     * wird die abgerundete Hälfte ihres Vermögens erfolgreich abgezogen.
     */
    @Test
    public void coinsMoreThan12Have() {
        move.setActivePlayer(playerStateList.get(3));
        playerStateList.get(0).getCoins().getCards().addAll(cardFactory.generateBlueShips());
        playerStateList.get(0).getCards().getCards().addAll(cardFactory.generateYellowShips());

        assertEquals(playerStateList.get(1).getCoins().getSize(), 14);
        mainController.getCardController().coinsMoreThan12(move);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 7);
    }

    /**
     * Testet, wenn kein Spieler mit 12 oder mehr coins haben,
     * wird die Anzahl von coins nicht veraendert.
     */
    @Test
    public void coinsMoreThan12NoOneHave() { //no one more than 12
        move.setActivePlayer(playerStateList.get(3));
        assertEquals(playerStateList.get(1).getCoins().getSize(), 4);
        mainController.getCardController().coinsMoreThan12(move);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 4);
    }

    /**
     * Testet ob parameter null sind.
     * @throws NullPointerException
     *      Wirft eine NullPointerException.
     *//*
    @Test(expected = NullPointerException.class)
    public void taxIncreaseOfMinShieldsNull() {
        mainController.getCardController().taxIncreaseOfMinShields(null, null);
    }


    /**
     * Testet die Funktionalitaet von TaxCard mit minShields
     */
    @Test
    public void taxIncreaseOfMinShields() {
        List<PlayerState> newPlayerStateList = new ArrayList<>();
        newPlayerStateList.add(TestFactory.getPlayerState().get(3));
        newPlayerStateList.add(TestFactory.getPlayerState().get(4));
        move.setPlayers(newPlayerStateList);
        //action.setAffectedCard(cardFactory.generateTaxIncrease().get(0));
        //victoryPoints: player0:6, player1:2, player2:4, player3:3, player4:3

        assertEquals(newPlayerStateList.get(0).getCoins().getSize(), 0);
        assertEquals(newPlayerStateList.get(1).getCoins().getSize(), 0);

        mainController.getCardController().taxIncreaseOfMinShields(move, action);

        assertEquals(newPlayerStateList.get(0).getCoins().getSize(), 1);
        assertEquals(newPlayerStateList.get(1).getCoins().getSize(), 1);
    }

    /**
     * Testet ob parameter null sind.
     * @throws NullPointerException
     *      Wirft eine NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void taxIncreaseOfMaxSwordsNull() {
        mainController.getCardController().taxIncreaseOfMaxSwords(null, null);
    }


    /**
     * Testet die Funktionalitaet von TaxCard mit maxSwords
     */
    @Test
    public void taxIncreaseOfMaxSwords() {
        //victoryPoints: player0:6, player1:2, player2:4, player3:3, player4:3
        List<PlayerState> newPlayerList = new ArrayList<>();
        newPlayerList.add(TestFactory.getPlayerState().get(3));
        newPlayerList.add(TestFactory.getPlayerState().get(4));
        move.setPlayers(newPlayerList);

        assertEquals(newPlayerList.get(0).getCoins().getCards().size(),0);
        mainController.getCardController().taxIncreaseOfMaxSwords(move, action);
        assertEquals(newPlayerList.get(1).getCoins().getCards().size(),1);

    }


    /**
     * Testet keine PersonCard.
     */
    @Test
    public void TaxIncreaseNoPerson() {
        List<PlayerState> newPlayerStateList = new ArrayList<>();
        newPlayerStateList.add(TestFactory.getPlayerState().get(3));
        newPlayerStateList.add(TestFactory.getPlayerState().get(4));
        move.setPlayers(newPlayerStateList);
        newPlayerStateList.add(TestFactory.getPlayerState().get(0));
        newPlayerStateList.get(0).getCards().getCards().clear();
        newPlayerStateList.get(1).getCards().getCards().clear();
        newPlayerStateList.get(0).getCards().getCards().add(cardFactory.generateRedShips().get(0));


        move.setActivePlayer(newPlayerStateList.get(0));
        action.setAffectedCard(cardFactory.generateTaxIncrease().get(0));

        assertEquals(playerStateList.get(0).getCoins().getSize(), 0);

        mainController.getCardController().taxIncreaseOfMaxSwords(move, action);

        assertEquals(playerStateList.get(0).getCoins().getSize(), 0);
    }

    /**
     * Testet ob parameter null sind.
     * @throws NullPointerException
     *      Wirft eine NullPointerException.
     */

    @Test(expected = NullPointerException.class)
    public void takeShipNull() {
        mainController.getCardController().takeShip(null, null);
    }



    /**
     * Testet das erfolgreiche Aufnahme von shipCard.(aktive plager == actor)
     */
    @Test
    public void takeShip_ActivePlayerIsActor() { // if active place wants to take the ship
        move.setActivePlayer(playerStateList.get(0));
        move.setActor(playerStateList.get(0));
        action.setAffectedCard(cardFactory.generateBlueShips().get(0)); // coins: 1

        assertEquals(playerStateList.get(0).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 4);
        mainController.getCardController().takeShip(move, action);

        assertEquals(playerStateList.get(0).getCoins().getSize(), 1);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 4);
    }

    /**
     * Testet das erfolgreiche Aufnahme von shipCard.(aktive plager != actor)
     */
    @Test
    public void takeShip_ActivePlayerIsNotActor() { // actor is not activePlayer
        move.setActivePlayer(playerStateList.get(0));
        move.setActor(playerStateList.get(1));
        action.setAffectedCard(cardFactory.generateRedShips().get(1));// coins: 2

        assertEquals(playerStateList.get(0).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 4);
        mainController.getCardController().takeShip(move, action);

        assertEquals(playerStateList.get(0).getCoins().getSize(), 1);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 5);
    }

    /**
     * Testet ob parameter null sind.
     * @throws NullPointerException
     *      Wirft eine NullPointerException.
     */

    @Test(expected = NullPointerException.class)
    public void buyPersonNull() {
        mainController.getCardController().buyPerson(null, null);
    }

    /**
     * Testet das erfolgreiche Aufnahme von personCard.(aktive plager != actor)
     */
    @Test
    public void buyPerson_ActivePlayerIsNotActor() { // actor is not active Player
        move.setActivePlayer(playerStateList.get(0));
        move.setActor(playerStateList.get(1));

        playerStateList.get(1).getCoins().getCards().add(cardFactory.generateRedShips().get(1)); //redShip: 2 coins. player 1 has now 6 coins
        playerStateList.get(1).getCoins().getCards().add(cardFactory.generateRedShips().get(1));
        move.getHarbour().getCards().add(cardFactory.generateAdmiral().get(0));
        action.setAffectedCard(cardFactory.generateAdmiral().get(0)); // personPrice: 5

        assertEquals(playerStateList.get(0).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 6);
        assertEquals(harbour.getCards().size(), 1);
        assertEquals(discardPile.getCards().size(), 0);

        mainController.getCardController().buyPerson(move, action);

        assertEquals(playerStateList.get(0).getCoins().getSize(), 1);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 0);
        assertEquals(harbour.getCards().size(), 0);
        assertEquals(discardPile.getCards().size(), 5);

    }

    /**
     * Testet das erfolgreiche Aufnahme von personCard.(aktive plager == actor)
     */
    @Test
    public void buyPerson_ActivePlayerIsActor() { // actor is active Player
        move.setActivePlayer(playerStateList.get(1));
        move.setActor(playerStateList.get(1));

        playerStateList.get(1).getCoins().getCards().add(cardFactory.generateRedShips().get(1)); //redShip: 2 coins. player 1 has now 6 coins
        playerStateList.get(1).getCoins().getCards().add(cardFactory.generateRedShips().get(1));
        move.getHarbour().getCards().add(cardFactory.generateAdmiral().get(0));
        action.setAffectedCard(cardFactory.generateAdmiral().get(0)); // personPrice: 5

        assertEquals(playerStateList.get(0).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 6);
        assertEquals(harbour.getCards().size(), 1);
        assertEquals(discardPile.getCards().size(), 0);

        mainController.getCardController().buyPerson(move, action);

        assertEquals(playerStateList.get(0).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 1);
        assertEquals(harbour.getCards().size(), 0);
        assertEquals(discardPile.getCards().size(), 5);

    }

    /**
     * Testet ob parameter null sind.
     * @throws NullPointerException
     *      Wirft eine NullPointerException.
     */
    @Test(expected = NullPointerException.class)
    public void defendNull() throws NullPointerException{
        mainController.getCardController().defend(null, null);
    }



    /**
     * Testet das erfolgreiche Abwehren von ship.
     */
    @Test
    public void defend() { // defend Bedingung erfuellt
        for (int i = 0; i < 2; i++) { // Swords: 10
            playerStateList.get(0).getCards().getCards().add(cardFactory.generatePirate().get(0));
            playerStateList.get(0).getCards().getCards().add(cardFactory.generateSailor().get(0));
        }
        move.setActivePlayer(playerStateList.get(0));
        action.setAffectedCard(cardFactory.generateBlueShips().get(0)); // coins: 1 , force: 1;
        action.setActionType(DEFEND);
        assertEquals(move.getDiscardPile().getSize(), 0);
        assertEquals(move.getHarbour().getCards().size(), 0);
        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 0);

        mainController.getCardController().defend(move, action);

        assertEquals(move.getDiscardPile().getSize(), 1);
        assertEquals(move.getHarbour().getCards().size(), 0);
        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 0);
    }

    /**
     * Testet das Abwehren von ship.(defend Bedingung nicht erfuellt)
     */
    @Test
    public void nichtDefend() { // defend Bedingung nicht erfuellt
        move.setActivePlayer(playerStateList.get(0));
        action.setAffectedCard(cardFactory.generateBlueShips().get(0)); // coins: 1 , force: 1;
        action.setActionType(DRAW_CARD);
        assertEquals(move.getDiscardPile().getSize(), 0);
        assertEquals(move.getHarbour().getCards().size(), 0);

        mainController.getCardController().defend(move, action);

        assertEquals(move.getDiscardPile().getSize(), 0);
        assertEquals(move.getHarbour().getCards().size(), 1);
    }

    /**
     * Testet das Abwehren von ship mit nicht reichte Swords.
     */
    @Test
    public void defend_NotEnoughSwords() { // defend Bedingung erfuellt
        move.setActivePlayer(playerStateList.get(0));
        action.setAffectedCard(cardFactory.generateBlueShips().get(0)); // coins: 1 , force: 1;
        action.setActionType(DEFEND);
        assertEquals(move.getDiscardPile().getSize(), 0);
        assertEquals(move.getHarbour().getCards().size(), 0);
        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 0);

        mainController.getCardController().defend(move, action);

        assertEquals(move.getDiscardPile().getSize(), 0);
        assertEquals(move.getHarbour().getCards().size(), 1);
        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 0);
    }

    /**
     * Testet das Abwehren von ship.keine Person
     */
    @Test
    public void DefendNoPerson() { // defend Bedingung nicht erfuellt
        List<PlayerState> newPlayerStateList = new ArrayList<>();
        newPlayerStateList.add(TestFactory.getPlayerState().get(3));
        newPlayerStateList.add(TestFactory.getPlayerState().get(4));
        newPlayerStateList.add(TestFactory.getPlayerState().get(0));
        newPlayerStateList.get(0).getCards().getCards().clear();
        newPlayerStateList.get(1).getCards().getCards().clear();
        newPlayerStateList.get(0).getCards().getCards().add(cardFactory.generateRedShips().get(0));

        move.setPlayers(newPlayerStateList);
        move.setActivePlayer(newPlayerStateList.get(0));
        action.setAffectedCard(cardFactory.generateBlueShips().get(0)); // coins: 1 , force: 1;
        action.setActionType(DRAW_CARD);
        assertEquals(move.getDiscardPile().getSize(), 0);
        assertEquals(move.getHarbour().getCards().size(), 0);

        mainController.getCardController().defend(move, action);

        assertEquals(move.getDiscardPile().getSize(), 0);
        assertEquals(move.getHarbour().getCards().size(), 1);
    }

    /**
     * Testet das Abwehren von blackShip mit Force 100.
     */
    @Test
    public void defendBlackShip100() {
        for (int i = 0; i < 5; i++) { // Swords: 10
            playerStateList.get(0).getCards().getCards().add(cardFactory.generatePirate().get(0));
        }
        move.setActivePlayer(playerStateList.get(0));
        action.setAffectedCard(cardFactory.generateBlackShips().get(9)); // coins: 1 , force: 1;
        action.setActionType(DEFEND);

        assertEquals(move.getDiscardPile().getSize(), 0);
        assertEquals(move.getHarbour().getCards().size(), 0);

        mainController.getCardController().defend(move, action);
        assertEquals(move.getDiscardPile().getSize(), 0);
        assertEquals(move.getHarbour().getCards().size(), 1);

    }

    /**
     * Testet eine erfolgreiche Expendition.
     */
    @Test
    public void startExpedition_WithJackCard_3materials() {
        List<Card> materials = new ArrayList<>();

        action.setActionType(START_EXPEDITION);
        action.setMaterials(materials);
        move.setActivePlayer(playerStateList.get(3)); // 1 settler, 1 jackofalltrader
        action.setAffectedCard(cardFactory.generateExpedition(true).get(5)); // 2 Captain, 1 SETTLER

        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(3), move),3);
        mainController.getCardController().startExpedition(move,action);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(3), move),3);
    }

    /**
     * Testet eine erfolgreiche Expendition.
     */
    @Test// falsche Ergebnis
    public void startExpedition_WithJackCard_2Settler() {
        playerStateList.get(3).getCards().getCards().add(cardFactory.generateSettlerCaptain().get(0));
        action.setActionType(START_EXPEDITION);
        move.setActivePlayer(playerStateList.get(3)); // trader captain jack

        for (Card card : playerStateList.get(3).getCards().getCards()) {
            System.out.println(card.toString());
        }
        System.out.println();
        action.setAffectedCard(cardFactory.generateExpedition(true).get(1)); // 2 settler, victoryPoints:4
        //System.out.println(cardFactory.generateExpedition(true).get(1));
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(0), move), 6);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(1), move), 2);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(2), move), 4);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(3), move), 4);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(4), move), 3);
        mainController.getCardController().startExpedition(move, action);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(0), move), 6);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(1), move), 2);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(2), move), 4);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(3), move), 4);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(4), move), 3);
        for (Card card : playerStateList.get(3).getCards().getCards()) {
            System.out.println(card.toString());
        }
    }


    /**
     * Testet eine erfolgreiche Expendition.
     */

    @Test
    public void startExpedition_WithoutJackCard_2Priest() {
        List<Card> materials = new ArrayList<>();

        action.setActionType(START_EXPEDITION);
        action.setMaterials(materials);
        move.setActivePlayer(playerStateList.get(4)); // 1 priest, 1 captain, 1 Admiral
        playerStateList.get(4).getCards().getCards().add(cardFactory.generatePriest().get(0));

        action.setAffectedCard(cardFactory.generateExpedition(true).get(2)); // 2 priest, sumVictoryPoints:3
        for (Card card: playerStateList.get(4).getCards().getCards()) {
            System.out.println(card.toString());
        }
        System.out.println();

        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(0), move),6);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(1), move),2);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(2), move),4);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(3), move),3);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(4), move),4);
        mainController.getCardController().startExpedition(move,action);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(0), move),6);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(1), move),2);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(2), move),4);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(3), move),3);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(4), move),4);
        for (Card card: playerStateList.get(4).getCards().getCards()) {
            System.out.println(card.toString());
        }
        System.out.println();
    }

    /**
     * Testet keine Expedition.
     */

    @Test
    public void NotStartExpedition() {
        List<Card> materials = new ArrayList<>();

        action.setActionType(DEFEND);
        action.setMaterials(materials);
        move.setActivePlayer(playerStateList.get(4)); // 1 priest, 1 captain, 1 Admiral
        playerStateList.get(4).getCards().getCards().add(cardFactory.generatePriest().get(0));

        action.setAffectedCard(cardFactory.generateExpedition(true).get(2)); // 2 priest, sumVictoryPoints:3
        for (Card card: playerStateList.get(4).getCards().getCards()) {
            System.out.println(card.toString());
        }
        System.out.println();

        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(0), move),6);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(1), move),2);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(2), move),4);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(3), move),3);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(4), move),4);
        mainController.getCardController().startExpedition(move,action);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(0), move),6);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(1), move),2);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(2), move),4);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(3), move),3);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(4), move),4);
        for (Card card: playerStateList.get(4).getCards().getCards()) {
            System.out.println(card.toString());
        }
        System.out.println();
    }

    /**
     * Testet Expedition mit keine Priest und Settler.
     */

    @Test
    public void StartExpedition() {
        List<Card> materials = new ArrayList<>();

        action.setActionType(START_EXPEDITION);
        move.setActivePlayer(playerStateList.get(0));
        action.setAffectedCard(cardFactory.generateExpedition(true).get(0)); // 0 priest, 0 Settler

        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(0), move),6);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(1), move),2);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(2), move),4);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(3), move),3);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(4), move),3);
        mainController.getCardController().startExpedition(move,action);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(0), move),6);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(1), move),2);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(2), move),4);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(3), move),3);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(4), move),3);
    }

    /**
     * Testet Expedition. keine Person.
     */

    @Test
    public void StartExpeditionNoPerson() {
        List<PlayerState> newPlayerStateList = new ArrayList<>();
        newPlayerStateList.add(TestFactory.getPlayerState().get(3));
        newPlayerStateList.add(TestFactory.getPlayerState().get(4));

        newPlayerStateList.get(0).getCards().getCards().clear();
        newPlayerStateList.get(1).getCards().getCards().clear();
        newPlayerStateList.get(0).getCards().getCards().add(cardFactory.generateRedShips().get(0));
        move.setPlayers(newPlayerStateList);
        move.setActor(newPlayerStateList.get(0));

        action.setActionType(START_EXPEDITION);
        move.setActivePlayer(newPlayerStateList.get(0));
        action.setAffectedCard(cardFactory.generateExpedition(true).get(0)); // 0 priest, 0 Settler

        assertEquals(mainController.getPlayerController().getVictoryPoints(newPlayerStateList.get(0), move),0);
        mainController.getCardController().startExpedition(move,action);
        assertEquals(mainController.getPlayerController().getVictoryPoints(newPlayerStateList.get(0), move),4);
    }

    /**
     * Testet ob die Methode die Anzahl von personCard zurueckgeben kann, die ein Spieler besitzt.
     */
    @Test
    public void getAmountOf(){
        assertEquals(mainController.getCardController().getAmountOf(JESTER,playerStateList.get(0)),1);
    }

    /**
     * Testet ob die Methode die Anzahl von personCard zurueckgeben kann, die ein Spieler besitzt.
     */
    @Test
    public void getAmountOfNoPerson(){
        List<PlayerState> newPlayerStateList = new ArrayList<>();
        newPlayerStateList.add(TestFactory.getPlayerState().get(3));
        newPlayerStateList.add(TestFactory.getPlayerState().get(4));
        move.setPlayers(newPlayerStateList);
        newPlayerStateList.add(TestFactory.getPlayerState().get(0));
        newPlayerStateList.get(0).getCards().getCards().clear();
        newPlayerStateList.get(1).getCards().getCards().clear();
        newPlayerStateList.get(0).getCards().getCards().add(cardFactory.generateRedShips().get(0));
        assertEquals(mainController.getCardController().getAmountOf(JESTER,newPlayerStateList.get(0)),0);
    }


}

