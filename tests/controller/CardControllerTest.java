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

public class CardControllerTest {
    private TestFactory testFactory;

    private MainController mainController;
    private CardController cardController;
    private PlayerController playerController;
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

    private List<Card> cardsOfPlayer0;
    private List<Card> cardsOfPlayer2;
    private List<Card> cardsOfPlayer3;
    private List<Card> cardsOfPlayer4;
    private List<Card> cardsOfPlayer5;

    private CardStack cardPile;
    private List<Card> personCard;

    private CardFactory cardFactory;
    private List<Card> cardsOfcardFactory;

    private Person admiral;
    private Person jester;
    private Person blueTrader;
    private Person blueShip;

    private CardStack coinsOfPlayer;
    private CardStack harbour;
    private CardStack discardPile;
    private CardStack expendition;


    @Before
    public void setUp() {
        testFactory = new TestFactory();

        mainController = new MainController();
        cardController = new CardController(mainController);
        playerController = new PlayerController(mainController);


        CardStack cardPile = CardFactory.newCardsWithSpecial();
        cardFactory = new CardFactory();

        personCard = new ArrayList<>();
        playerList = new ArrayList<>();
        playerStateList = new ArrayList<>();
        harbour = new CardStack();
        discardPile = new CardStack();
        expendition = new CardStack();

        player0 = new Player("player0", HUMAN);
        player1 = new Player("player1", HUMAN);
        player2 = new Player("player2", HUMAN);
        player3 = new Player("player3", HUMAN);
        player4 = new Player("player4", HUMAN);

        playerState0 = new PlayerState(player0);
        playerState1 = new PlayerState(player1);
        playerState2 = new PlayerState(player2);
        playerState3 = new PlayerState(player3);
        playerState4 = new PlayerState(player4);


        Person person1 = new Person();
        person1.setValues(5, 1, 0);
        person1.setMetaData("Admiral", null, PersonType.ADMIRAL);
        playerState0.getCards().getCards().add(cardFactory.generateAdmiral().get(0)); // price:5, victoryPoint:1, swords:0


        //Cards von player1
        //Jester
        playerState1.getCards().getCards().add(cardFactory.generateJester().get(0));
        playerState1.getCards().getCards().add(cardFactory.generateJester().get(0)); // price:5, victoryPoint:1, swords:0
        //Cards von player2
        //Jester
        playerState2.getCards().getCards().add(cardFactory.generateTrader().get(0)); // green, price:3, victoryPoint:1, swords:0
        //Cards von player3
        //Jester
        playerState3.getCards().getCards().add(cardFactory.generateSailor().get(0)); // price:3, victoryPoint:1, swords:1
        //Cards von player4
        //Jester
        playerState4.getCards().getCards().add(cardFactory.generatePirate().get(0)); // price:7, victoryPoint:2, swords:2

        playerStateList.add(playerState0);
        playerStateList.add(playerState1);
        playerStateList.add(playerState2);
        playerStateList.add(playerState3);
        playerStateList.add(playerState4);

        playerList.add(player0);
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player4);

        mainController.getPlayerController().setPlayerOrder(playerList, false);

        move = new Move(playerState1, true, playerState1, action);
        move.setPlayers(playerStateList);
        move.setActivePlayer(playerState0);


        move.setHarbour(harbour);
        move.setCardPile(cardPile);
        move.setDiscardPile(discardPile);
        move.setExpeditionPile(expendition);


        gameSystem = new GameSystem();
        Game currentGame = new Game(true, playerState0, true, playerList, cardPile);
        mainController.setGameSystem(gameSystem);
        mainController.getGameSystem().setCurrentGame(currentGame);
        mainController.getGameSystem().getCurrentGame().setJesterEnabled(true);

        //greenShip in hand
        action = new Action(SKIP, cardFactory.generateGreenShips().get(0));

    }

    @After
    public void tearDown() {
    }

    @Test(expected = NullPointerException.class)
    public void execAdmiralNull() throws NullPointerException{
        mainController.getCardController().execAdmiral(null, null);
    }

    @Test
    public void execAdmiral() { // if Bedingung erfuellt
        move.setActivePlayer(playerState0);
        move.getHarbour().getCards().add(cardFactory.generateBlueShips().get(0));
        move.getHarbour().getCards().add(cardFactory.generateRedShips().get(0));
        move.getHarbour().getCards().add(cardFactory.generateBlueShips().get(0));
        move.getHarbour().getCards().add(cardFactory.generatePirate().get(0));
        move.getHarbour().getCards().add(cardFactory.generateTrader().get(9));

        assertEquals(playerState0.getCoins().getCards().size(), 0);
        System.out.println("CardPile length after draw" + " " + move.getCardPile().getCards().size());
        assertEquals(move.getCardPile().getCards().size(), 120);

        mainController.getCardController().execAdmiral(move, action);

        System.out.println(playerState0.getCoins().getCards().size());
        assertEquals(playerState0.getCoins().getCards().size(), 2);
        System.out.println("CardPile length after draw" + " " + move.getCardPile().getCards().size());
        assertEquals(move.getCardPile().getCards().size(), 118);

    }

    @Test
    public void execAdmiral1() { // else erfuellt
        move.setActivePlayer(playerState0);
        move.getHarbour().getCards().add(cardFactory.generateBlueShips().get(0));
        move.getHarbour().getCards().add(cardFactory.generateRedShips().get(0));
        move.getHarbour().getCards().add(cardFactory.generateBlueShips().get(0));
        move.getHarbour().getCards().add(cardFactory.generatePirate().get(0));

        assertEquals(playerState0.getCoins().getCards().size(), 0);
        assertEquals(move.getCardPile().getCards().size(), 120);
        mainController.getCardController().execAdmiral(move, action);
        System.out.println(playerState0.getCoins().getCards().size());
        assertEquals(playerState0.getCoins().getCards().size(), 0);
        assertEquals(move.getCardPile().getCards().size(), 120);
    }

    @Test(expected = NullPointerException.class)
    public void execJesterNull() throws NullPointerException{
        mainController.getCardController().execJester(null, null);
    }

    @Test
    public void execJester() { // if Bedingung. if JESTER is at player's hand, all these players get 1 coin
        move.setActivePlayer(playerState1);
        move.setPhase1(true);
        action.setActionType(SKIP);
        playerState0.getCards().getCards().add(cardFactory.generateJester().get(0));

        mainController.getGameSystem().getCurrentGame().setJesterEnabled(true);
        assertEquals(playerState0.getCoins().getSize(), 0);
        assertEquals(playerState1.getCoins().getSize(), 0);
        assertEquals(playerState2.getCoins().getSize(), 0);
        assertEquals(playerState3.getCoins().getSize(), 0);
        assertEquals(playerState4.getCoins().getSize(), 0);

        System.out.println("coins vor execJester()" + " " + move.getActivePlayer().getCoins().getSize());
        mainController.getCardController().execJester(move, action);

        System.out.println("coins nach execJester()" + " " + move.getActivePlayer().getCoins().getSize());
        assertEquals(playerState0.getCoins().getSize(), 1);
        assertEquals(playerState1.getCoins().getSize(), 2);
        assertEquals(playerState2.getCoins().getSize(), 0);
        assertEquals(playerState3.getCoins().getSize(), 0);
        assertEquals(playerState4.getCoins().getSize(), 0);
    }

    @Test
    public void execJester1() { // else Bedingung.in phase 2, only active player get 1 coin
        move.setActivePlayer(playerState1);
        move.setPhase1(false);
        action.setActionType(SKIP);

        mainController.getGameSystem().getCurrentGame().setJesterEnabled(true);
        assertEquals(playerState1.getCoins().getSize(), 0);

        System.out.println("coins vor execJester()" + " " + move.getActivePlayer().getCoins().getSize());
        mainController.getCardController().execJester(move, action);

        System.out.println("coins nach execJester()" + " " + move.getActivePlayer().getCoins().getSize());
        assertEquals(playerState1.getCoins().getSize(), 1);
    }

    @Test(expected = NullPointerException.class)
    public void execTraderNull() throws NullPointerException{
        mainController.getCardController().execTrader(null, null);
    }

    @Test
    public void execTrader() {
        move.setActivePlayer(playerState2);
        action.setAffectedCard(cardFactory.generateGreenShips().get(0)); //greenTrader
        move.getActivePlayer().getCards().getCards().add(cardFactory.generateTrader().get(0));// greenTrader

        assertEquals(playerState2.getCoins().getCards().size(), 0);

        mainController.getCardController().execTrader(move, action);
        assertEquals(playerState2.getCoins().getCards().size(), 2);
    }

    @Test(expected = NullPointerException.class)
    public void drawCardNull() throws NullPointerException{
        mainController.getCardController().drawCard(null, null);
    }

    @Test
    public void drawCard() { //if(card instanceof TaxIncrease )
        move.setActivePlayer(playerState3);
        move.getCardPile().getCards().add(0, cardFactory.generatePirate().get(2));
        // move.getCardPile().getCards().add(0,cardFactory.generateTaxIncrease().get(0));// minShield
        playerState3.getCards().getCards().add(cardFactory.generatePirate().get(2));// maxShield
        action.setAffectedCard(cardFactory.generatePirate().get(2));

        assertEquals(move.getHarbour().getCards().size(), 0);
        System.out.println(move.getHarbour().getCards().size());
        mainController.getCardController().drawCard(move, action);
        assertEquals(move.getHarbour().getCards().size(), 1);
        System.out.println(move.getHarbour().getCards().size());

    }

    @Test
    public void drawCard1() { // if(card instanceof Expedition){
        move.setActivePlayer(playerState3);
        move.getCardPile().getCards().add(0, cardFactory.generatePirate().get(2));
        // move.getCardPile().getCards().add(0,cardFactory.generateTaxIncrease().get(0));// minShield
        playerState3.getCards().getCards().add(cardFactory.generatePirate().get(2));// maxShield
        action.setAffectedCard(cardFactory.generatePirate().get(2));

        assertEquals(move.getHarbour().getCards().size(), 0);
        System.out.println(move.getHarbour().getCards().size());
        mainController.getCardController().drawCard(move, action);
        assertEquals(move.getHarbour().getCards().size(), 1);
        System.out.println(move.getHarbour().getCards().size());

    }
    /*

    @Test
    public void drawCard2() { // if(card instanceof Ship)
        move.setActivePlayer(playerState3);
        move.getCardPile().getCards().add(0, cardFactory.generateBlueShips().get(2));
        move.getHarbour();
        // move.getCardPile().getCards().add(0,cardFactory.generateTaxIncrease().get(0));// minShield
        playerState3.getCards().getCards().add(cardFactory.generatePirate().get(2));// maxShield
        action.setAffectedCard(cardFactory.generatePirate().get(2));

        assertEquals(move.getHarbour().getCards().size(), 0);
        System.out.println(move.getHarbour().getCards().size());
        mainController.getCardController().drawCard(move, action);
        assertEquals(move.getHarbour().getCards().size(), 1);
        System.out.println(move.getHarbour().getCards().size());

    }

     */

    @Test
    public void drawCard3() { // person
        move.setActivePlayer(playerState3);
        move.getCardPile().getCards().add(0, cardFactory.generatePirate().get(2));
        // move.getCardPile().getCards().add(0,cardFactory.generateTaxIncrease().get(0));// minShield
        playerState3.getCards().getCards().add(cardFactory.generatePirate().get(2));// maxShield
        action.setAffectedCard(cardFactory.generatePirate().get(2));

        assertEquals(move.getHarbour().getCards().size(), 0);
        System.out.println(move.getHarbour().getCards().size());
        mainController.getCardController().drawCard(move, action);
        assertEquals(move.getHarbour().getCards().size(), 1);
        System.out.println(move.getHarbour().getCards().size());

    }

    @Test(expected = NullPointerException.class)
    public void coinsMoreThan12Null() throws NullPointerException{
        mainController.getCardController().coinsMoreThan12(null); //TODO coinsMoreThan12 anpassen (keine Action)
    }

    @Test
    public void coinsMoreThan12() {
        move.setActivePlayer(playerState3);
        action.setAffectedCard(cardFactory.generateTaxIncrease().get(0));
        playerState0.getCoins().getCards().addAll(cardFactory.generateBlueShips());
        playerState0.getCoins().getCards().addAll(cardFactory.generateYellowShips());

        for (PlayerState playerState : playerStateList) {
            System.out.println("Vor: " + playerState.getCoins().getCards().size());
        }
        assertEquals(playerState0.getCoins().getSize(), 20);
        mainController.getCardController().coinsMoreThan12(move); //coinsMoreThan12
        assertEquals(playerState0.getCoins().getSize(), 10);
        for (PlayerState playerState : playerStateList) {
            System.out.println("Nach: " + playerState.getCoins().getCards().size());
        }
    }

    @Test(expected = NullPointerException.class)
    public void taxIncreaseOfMinShieldsNull() throws NullPointerException{
        mainController.getCardController().taxIncreaseOfMinShields(null, null);
    }

    @Test
    public void taxIncreaseOfMinShields() {

        move.setActivePlayer(playerState3);
        playerState3.getCards().getCards().add(cardFactory.generateSailor().get(0));
        playerState3.getCards().getCards().add(cardFactory.generateSailor().get(0));
        playerState3.getCards().getCards().add(cardFactory.generateSailor().get(0));
        action.setAffectedCard(cardFactory.generateTaxIncrease().get(0));// minShield
        //action.setAffectedCard(cardFactory.generateTaxIncrease().get(2));// maxSword

        for (PlayerState playerState : playerStateList) {
            System.out.println("Vor: " + playerState.getCoins().getCards().size());
        }
        System.out.println();
        System.out.println(move.getCardPile().getCards().size());
        assertEquals(playerState0.getCoins().getSize(), 0);
        assertEquals(playerState1.getCoins().getSize(), 0);
        assertEquals(playerState2.getCoins().getSize(), 0);
        assertEquals(playerState3.getCoins().getSize(), 0);
        assertEquals(playerState4.getCoins().getSize(), 0);
        mainController.getCardController().taxIncreaseOfMinShields(move, action);
        System.out.println("move: " + move.getPlayers().get(3).getPlayer().getName());
        System.out.println("victoryPoints von player 3: " + mainController.getPlayerController().getVictoryPoints(playerState3, move));

        for (PlayerState playerState : playerStateList) {
            System.out.println(playerState.getCards().getCards());
        }
        for (PlayerState playerState : playerStateList) {
            System.out.println("Nach: " + playerState.getCoins().getCards().size());
        }
        System.out.println(move.getCardPile().getCards().size());
        assertEquals(playerState0.getCoins().getSize(), 1);
        assertEquals(playerState1.getCoins().getSize(), 0);
        assertEquals(playerState2.getCoins().getSize(), 1);
        assertEquals(playerState3.getCoins().getSize(), 0);
        assertEquals(playerState4.getCoins().getSize(), 0);
    }

    @Test(expected = NullPointerException.class)
    public void taxIncreaseOfMaxSwordsNull() throws NullPointerException{
        mainController.getCardController().taxIncreaseOfMaxSwords(null, null);
    }

    @Test
    public void taxIncreaseOfMaxSwords() {

        move.setActivePlayer(playerState3);
        playerState3.getCards().getCards().add(cardFactory.generatePirate().get(0));
        playerState3.getCards().getCards().add(cardFactory.generateSailor().get(0));
        playerState3.getCards().getCards().add(cardFactory.generatePirate().get(0));
        //action.setAffectedCard(cardFactory.generateTaxIncrease().get(0));// minShield
        action.setAffectedCard(cardFactory.generateTaxIncrease().get(2));// maxSword

        System.out.println("-------------------------------");
        int maxSwords = 0;
        int playerSwords = 0;
        List<Integer> swordlist = new ArrayList<>();
        for (PlayerState playerState : move.getPlayers()) {
            List<Card> playerHand = playerState.getCards().getCards();
            for (Card card : playerHand) {

                if (card instanceof Person && (((Person) card).getPersonType().equals(SAILOR) || ((Person) card).getPersonType().equals(PIRATE))) {
                    playerSwords += ((Person) card).getSwords();
                }
            }
            swordlist.add(playerSwords);  // create a list number of swords of all each player

            if (maxSwords <= playerSwords) {     //get the maximum number
                maxSwords = playerSwords;
            }
        }
        for (int i = 0; i < move.getPlayers().size(); i++) {         // check again in players list, get the one that has minShield or maxSwords
            if (swordlist.get(i) == maxSwords) {

                move.getPlayers().get(i).getCoins().getCards().add(move.getCardPile().pop());
            }
        }
        System.out.println("maxSwords: " + maxSwords);
        for (PlayerState playerState : playerStateList) {
            System.out.println("BBB: " + playerState.getCoins().getCards().size());
        }
        System.out.println("-------------------------------");

        for (PlayerState playerState : playerStateList) {
            System.out.println("Vor: " + playerState.getCoins().getCards().size());
        }
        System.out.println();
        System.out.println(move.getCardPile().getCards().size());
        mainController.getCardController().taxIncreaseOfMaxSwords(move, action);
        System.out.println("move: " + move.getPlayers().get(3).getPlayer().getName());
        System.out.println("victoryPoints von player 3: " + mainController.getPlayerController().getVictoryPoints(playerState3, move));

        for (PlayerState playerState : playerStateList) {
            System.out.println(playerState.getCards().getCards());
            System.out.println("Nach: " + playerState.getCoins().getCards().size());
            System.out.println();
        }
        System.out.println(move.getCardPile().getCards().size());
    }

    @Test(expected = NullPointerException.class)
    public void takeShipNull() throws NullPointerException{
        mainController.getCardController().takeShip(null, null);
    }

    @Test
    public void takeShip() { // actor is activePlayer
        move.setActivePlayer(playerState0);
        move.setActor(playerState0);
        action.setAffectedCard(cardFactory.generateBlueShips().get(0));

        assertEquals(playerState0.getCoins().getSize(), 0);
        assertEquals(playerState1.getCoins().getSize(), 0);
        mainController.getCardController().takeShip(move, action);

        assertEquals(playerState0.getCoins().getSize(), 1);
        assertEquals(playerState1.getCoins().getSize(), 0);
    }

    @Test
    public void takeShip1() { // actor is not activePlayer
        move.setActivePlayer(playerState0);
        move.setActor(playerState1);
        action.setAffectedCard(cardFactory.generateBlueShips().get(0));

        assertEquals(playerState0.getCoins().getSize(), 0);
        assertEquals(playerState1.getCoins().getSize(), 0);
        mainController.getCardController().takeShip(move, action);

        assertEquals(playerState0.getCoins().getSize(), 1);
        assertEquals(playerState1.getCoins().getSize(), 1);
    }

    @Test(expected = NullPointerException.class)
    public void buyPersonNull() throws NullPointerException{
        mainController.getCardController().buyPerson(null, null);
    }

    @Test
    public void buyPerson() { // ???? Frage
        move.setActivePlayer(playerState1);
        move.setActor(playerState0);
        action.setAffectedCard(cardFactory.generateAdmiral().get(0)); // personPrice 5
        System.out.println(cardFactory.generateAdmiral().get(0));

        for (int i = 0; i < 15; i++) { // Coins of actor 15;
            playerState0.getCoins().getCards().add(cardFactory.generateBlueShips().get(0));
        }

        assertEquals(playerState0.getCoins().getSize(), 15);
        assertEquals(playerState1.getCoins().getSize(), 0);
        mainController.getCardController().buyPerson(move, action);
        assertEquals(playerState0.getCoins().getSize(), 10);
        assertEquals(playerState1.getCoins().getSize(), 1);
    }

    @Test(expected = NullPointerException.class)
    public void defendNull() throws NullPointerException{
        mainController.getCardController().defend(null, null);
    }

    @Test
    public void defend() { // defend Bedingung erfuellt
        for (int i = 0; i < 5; i++) { // Swords: 10
            playerState0.getCards().getCards().add(cardFactory.generatePirate().get(0));
        }
        //System.out.println(cardFactory.generateBlueShips().get(0));
        move.setActivePlayer(playerState0);
        action.setAffectedCard(cardFactory.generateBlueShips().get(0)); // coins: 1 , force: 1;
        action.setActionType(DEFEND);
        assertEquals(move.getDiscardPile().getSize(), 0);
        assertEquals(move.getHarbour().getCards().size(), 0);


        mainController.getCardController().defend(move, action);
        assertEquals(move.getDiscardPile().getSize(), 1);
        assertEquals(move.getHarbour().getCards().size(), 0);

    }

    @Test
    public void nichtDefend() { // defend Bedingung nicht erfuellt
        //System.out.println(cardFactory.generateBlueShips().get(0));
        move.setActivePlayer(playerState0);
        action.setAffectedCard(cardFactory.generateBlueShips().get(0)); // coins: 1 , force: 1;
        action.setActionType(DEFEND);
        assertEquals(move.getDiscardPile().getSize(), 0);
        assertEquals(move.getHarbour().getCards().size(), 0);


        mainController.getCardController().defend(move, action);
        assertEquals(move.getDiscardPile().getSize(), 0);
        assertEquals(move.getHarbour().getCards().size(), 1);

    }

    @Test
    public void defendBlackShip100() {
        for (int i = 0; i < 5; i++) { // Swords: 10
            playerState0.getCards().getCards().add(cardFactory.generatePirate().get(0));
        }
        move.setActivePlayer(playerState0);
        action.setAffectedCard(cardFactory.generateBlackShips().get(9)); // coins: 1 , force: 1;
        action.setActionType(DEFEND);

        assertEquals(move.getDiscardPile().getSize(), 0);
        assertEquals(move.getHarbour().getCards().size(), 0);

        mainController.getCardController().defend(move, action);
        assertEquals(move.getDiscardPile().getSize(), 0);
        assertEquals(move.getHarbour().getCards().size(), 1);

    }

    @Test
    public void startExpedition() {
        playerState3.getCards().getCards().add(cardFactory.generatePriest().get(0));
        playerState3.getCards().getCards().add(cardFactory.generatePriest().get(0));
        move.setActivePlayer(playerState3);
        action.setAffectedCard(cardFactory.generateExpedition(false).get(3));
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerState3, move),3);
        mainController.getCardController().startExpedition(move,action);
        assertEquals(mainController.getPlayerController().getVictoryPoints(playerState3, move),3);
    }

    @Test
    public void getAmountOf(){
        assertEquals(mainController.getCardController().getAmountOf(JESTER,playerState1),2);
    }
}

