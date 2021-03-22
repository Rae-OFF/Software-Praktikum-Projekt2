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
     *
     * @throws Exception
     *      Moegliche Exception.
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

        gameSystem = new GameSystem();
        mainController.setGameSystem(gameSystem);
        gameSystem.setCurrentGame(currentGame);
        mainController.getGameSystem().getCurrentGame().setJesterEnabled(true);

    }

    /**
     * Testet ob parameter null sind.
     * @throws NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void execAdmiralNull() {
        mainController.getCardController().execAdmiral(null, null);
    }

    /**
     * Testet die Funktionalitaet von Admiral. der aktive Spieler hat Admiral
     */
    @Test
    public void execAdmiral() { // if Bedingung erfuellt
        move.getHarbour().getCards().add(cardFactory.generateBlueShips().get(0));
        move.getHarbour().getCards().add(cardFactory.generateRedShips().get(0));
        move.getHarbour().getCards().add(cardFactory.generateBlueShips().get(0));
        move.getHarbour().getCards().add(cardFactory.generatePirate().get(0));
        move.getHarbour().getCards().add(cardFactory.generateTrader().get(9));
        actionList.get(0).setAffectedCard(cardFactory.generateAdmiral().get(0));

        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 0);
        assertEquals(move.getCardPile().getCards().size(), 120);


        mainController.getCardController().execAdmiral(move, actionList.get(0));

        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 2);
        assertEquals(move.getCardPile().getCards().size(), 118);

    }

    /**
     * Testet die Funktionalitaet von Admiral. der aktive Spieler hat kein Admiral
     */
    @Test
    public void execAdmiral1() { // else erfuellt
        move.setActivePlayer(playerStateList.get(0));
        move.getHarbour().getCards().add(cardFactory.generateBlueShips().get(0));
        move.getHarbour().getCards().add(cardFactory.generateRedShips().get(0));
        move.getHarbour().getCards().add(cardFactory.generateBlueShips().get(0));
        move.getHarbour().getCards().add(cardFactory.generatePirate().get(0));

        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 0);
        assertEquals(move.getCardPile().getCards().size(), 120);
        mainController.getCardController().execAdmiral(move, actionList.get(0));
        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 0);
        assertEquals(move.getCardPile().getCards().size(), 120);
    }


    /**
     * Testet ob parameter null sind.
     * @throws NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void execJesterNull() {
        mainController.getCardController().execJester(null, null);
    }

    /**
     * Testet die Funktionalitaet von Jester. der aktive Spieler hat Jester
     */
    @Test
    public void execJester() { // if Bedingung. if JESTER is at player's hand, all these players get 1 coin
        move.setActivePlayer(playerStateList.get(0));
        move.setPhase1(true);
        action.setActionType(SKIP);
        playerStateList.get(0).getCards().getCards().add(cardFactory.generateJester().get(0)); // player 0: 2 Jester, Player 1: 1 Jester


        mainController.getGameSystem().getCurrentGame().setJesterEnabled(true);
        assertEquals(playerStateList.get(0).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 5);
        assertEquals(playerStateList.get(2).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(3).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(4).getCoins().getSize(), 0);

        mainController.getCardController().execJester(move, action);

        assertEquals(playerStateList.get(0).getCoins().getSize(), 2);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 6);
        assertEquals(playerStateList.get(2).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(3).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(4).getCoins().getSize(), 0);
    }

    /**
     * Testet die Funktionalitaet von Jester. der aktive Spieler hat kein Jester
     */
    @Test
    public void execJester1() { // else Bedingung.in phase 2, only active player get 1 coin
        move.setActivePlayer(playerStateList.get(0));
        move.setPhase1(false);
        action.setActionType(SKIP);
        currentGame.setJesterEnabled(true);

        assertEquals(playerStateList.get(0).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 4);
        assertEquals(playerStateList.get(2).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(3).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(4).getCoins().getSize(), 0);

        mainController.getCardController().execJester(move, action);

        assertEquals(playerStateList.get(0).getCoins().getSize(), 1);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 4);
        assertEquals(playerStateList.get(2).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(3).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(4).getCoins().getSize(), 0);
    }


    /**
     * Testet ob parameter null sind.
     * @throws NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void execTraderNull(){
        mainController.getCardController().execTrader(null, null);
    }

    /**
     * Testet die Funktionalitaet von Jester. der aktive Spieler hat Trader
     */
    @Test
    public void execTrader() { // active Player has Trader
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
    public void execTrader1() { // active Player has not Trader
        move.setActivePlayer(playerStateList.get(1));
        action.setAffectedCard(cardFactory.generateGreenShips().get(0)); //greenTrader
        move.getActivePlayer().getCards().getCards().add(cardFactory.generateTrader().get(0));// greenTrader

        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 0);

        mainController.getCardController().execTrader(move, action);
        assertEquals(playerStateList.get(0).getCoins().getCards().size(), 0);
    }



    /**
     * Testet ob parameter null sind.
     * @throws NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void drawCardNull(){
        mainController.getCardController().drawCard(null, null);
    }

    /**
     * Testet das erfolgreiche drawCard. der aktive Spieler hat eine TaxCard mit maxSwords aufgedeckt.
     */
    @Test
    public void drawCard() { //if(card instanceof TaxIncrease ),if(((TaxIncrease) card).isTypeSwords())
        move.setActivePlayer(playerStateList.get(0));
        move.getCardPile().getCards().add(0, cardFactory.generateTaxIncrease().get(2));

        assertEquals(playerStateList.get(0).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 4);
        assertEquals(playerStateList.get(2).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(3).getCoins().getSize(), 0);
        assertEquals(playerStateList.get(4).getCoins().getSize(), 0);

        mainController.getCardController().drawCard(move, action);

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
    public void drawCard1() { //if(card instanceof TaxIncrease ),else.
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
    public void drawCard2() { // if(card instanceof Expedition){
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
    public void drawCard3() { // if(card instanceof Ship). isZonked()==false
        move.setActivePlayer(playerStateList.get(0));
        move.getCardPile().getCards().add(0, cardFactory.generateBlueShips().get(0));
        move.setActor(playerStateList.get(0));
        action.setAffectedCard(cardFactory.generateBlueShips().get(0)); // coins: 1
        move.getHarbour().getCards().add(cardFactory.generateGreenShips().get(0));

        assertEquals(move.getHarbour().getCards().size(), 1);
        mainController.getCardController().drawCard(move, action);
        assertEquals(move.getHarbour().getCards().size(), 2);
    }

    /**
     * Testet das erfolgreiche drawCard.
     * der aktive Spieler hat eine PersonCard aufgedeckt.
     */
    @Test
    public void drawCard4() { // person
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
    public void coinsMoreThan12() {
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
    public void coinsMoreThan12_1() { //no one more than 12
        move.setActivePlayer(playerStateList.get(3));
        assertEquals(playerStateList.get(1).getCoins().getSize(), 4);
        mainController.getCardController().coinsMoreThan12(move);
        assertEquals(playerStateList.get(1).getCoins().getSize(), 4);
    }

    /**
     * Testet ob parameter null sind.
     * @throws NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void taxIncreaseOfMinShieldsNull() {
        mainController.getCardController().taxIncreaseOfMinShields(null, null);
    }

    /**
     * Testet die Funktionalitaet von TaxCard mit minShields
     */
    @Test
    public void taxIncreaseOfMinShields() {

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
     * Testet ob parameter null sind.
     * @throws NullPointerException
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

        move.setActivePlayer(playerStateList.get(0));
        action.setAffectedCard(cardFactory.generateTaxIncrease().get(2));// maxSword

        assertEquals(playerStateList.get(2).getCoins().getCards().size(),0);
        mainController.getCardController().taxIncreaseOfMaxSwords(move, action);
        assertEquals(playerStateList.get(2).getCoins().getCards().size(),1);

    }

    /**
     * Testet ob parameter null sind.
     * @throws NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void takeShipNull() {
        mainController.getCardController().takeShip(null, null);
    }

    /**
     * Testet das erfolgreiche Aufnahme von shipCard.(aktive plager == actor)
     */
    @Test
    public void takeShip() { // if active place wants to take the ship
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
    public void takeShip1() { // actor is not activePlayer
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
     */
    @Test(expected = NullPointerException.class)
    public void buyPersonNull() {
        mainController.getCardController().buyPerson(null, null);
    }

    /**
     * Testet das erfolgreiche Aufnahme von personCard.(aktive plager != actor)
     */
    @Test
    public void buyPerson() { // actor is not active Player
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
    public void buyPerson1() { // actor is active Player
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
        for (int i = 0; i < 5; i++) { // Swords: 10
            playerStateList.get(0).getCards().getCards().add(cardFactory.generatePirate().get(0));
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
        action.setActionType(DEFEND);
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
    public void startExpedition() {
        List<Card> materials = new ArrayList<>();
        materials.add(cardFactory.generateSettlerCaptain().get(0));//settler
        materials.add(cardFactory.generateSettlerCaptain().get(1));//captain
        materials.add(cardFactory.generateJackOfAllTrader().get(1));//JackOfAllTrader

        action.setActionType(START_EXPEDITION);
        action.setMaterials(materials);
        move.setActivePlayer(playerStateList.get(3)); // 1 settler, 1 jackofalltrader
        action.setAffectedCard(cardFactory.generateExpedition(true).get(5)); // 2 Captain, 1 SETTLER

        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(3), move),3);
        mainController.getCardController().startExpedition(move,action);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerStateList.get(3), move),7);
    }

/*
    @Test
    public void startExpedition1() { //without jackofalltrader
        List<Card> priest = new ArrayList<>();
        move.getExpeditionPile().getCards().add(cardFactory.generateExpedition(false).get(4)); // 2 Captain, 1 SETTLER

        priest.add(cardFactory.generatePriest().get(0));
        priest.add(cardFactory.generatePriest().get(0));
        action.setActionType(START_EXPEDITION);
        action.setMaterials(priest);

        playerStateList.get(0).getCards().getCards().add(cardFactory.generatePriest().get(0));
        playerStateList.get(0).getCards().getCards().add(cardFactory.generatePriest().get(0));
        move.setActivePlayer(playerStateList.get(3)); // 1 settler, 1 jackofalltrader
        action.setAffectedCard(cardFactory.generateSettlerCaptain().get(1)); // 1 captain.

        System.out.println(cardFactory.generateExpedition(false).get(2).toString());

        assertEquals(mainController.getPlayerController().getVictoryPoints(playerState3, move),3);
        mainController.getCardController().startExpedition(move,action);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerState3, move),7);
    }

 */


    /**
     * Testet ob die Methode die Anzahl von personCard zurueckgeben kann, die ein Spieler besitzt.
     */
    @Test
    public void getAmountOf(){//
        assertEquals(mainController.getCardController().getAmountOf(JESTER,playerStateList.get(0)),1);
    }


}

