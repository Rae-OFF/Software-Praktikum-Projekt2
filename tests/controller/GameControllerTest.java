package controller;

import model.*;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static model.ActionType.*;
import static model.Colour.*;
import static org.junit.Assert.*;

public class GameControllerTest {

    private MainController mainController;
    private GameController controller;

    private List<Move> moves;
    private GameSystem gameSystem;
    private Game game;
    private List<Player> players;
    private List<PlayerState> states;
    private CardStack stack;
    private List<Action> actions;


    @Before
    public void setUp() {

      mainController = new MainController();
      controller = mainController.getGameController();
      stack = CardFactory.newCardsWithoutSpecial();
      gameSystem = new GameSystem();
      mainController.setGameSystem(gameSystem);

        players = TestFactory.getPlayers();
        states = TestFactory.getPlayerState();
        moves = TestFactory.moves();
        actions = TestFactory.actions();
        game = new Game(true,states.get(0), true, players,stack);
        gameSystem.setCurrentGame(game);
        game.setMoves(moves);
        game.setPlayerStates(states);
        //game.setPlayers(players);


    }
    /**
     * constructortest - null.
     */

// TODO: kein null pointer
    @Test//(expected = NullPointerException.class)
    public void testConstructorNull() {

        new GameController(null);

    }
    /**
     * Konstruktortest.
     */

    @Test
    public void testConstructor() {

        MainController mainController = new MainController();
        GameController gameController = new GameController(mainController);

        try {
            Field fieldMainController = GameController.class.getDeclaredField("mainController");
            fieldMainController.setAccessible(true);
            MainController mainControllerField = (MainController) fieldMainController.get(gameController);
            assertNotNull(mainControllerField);
            assertEquals(mainController, mainControllerField);
        } catch (Exception exception) {
            System.out.println("Diese Exception sollte niemals auftreten.");
            exception.printStackTrace();
        }
    }
    /**
     * method currentMove() test.
     * check if it's returns lastMove
     */

    @Test
    public void testCurrentMove() {

        game.setLastMove(moves.get(1));
        assertEquals(controller.currentMove(), game.getLastMove());

    }
    /**
     * method popCardPile test.
     * check if it's returns same amount that removed.
     */
    @Test
    public void testPopCardPile() {

       assertEquals(controller.popCardPile(moves.get(0), 2).size(), 2);


    }
    /**
     * method popCardPile with Null parameter for Move test.
     *
     */
    @Test(expected = NullPointerException.class)
    public void testPopCardPileInteger() {

        assertNull(controller.popCardPile(null, 200));

    }

    /**
     * method popCardPile with empty Pile, check if the discard pile shuffles automatically.
     *
     */
    @Test   // test failed to call inner method shuffleDiscardPile in order to get new draw card pile to pop
    public void testPopCardPileEmpty() {

        moves.get(0).getCardPile().getCards().clear();
        moves.get(0).setDiscardPile(stack);
         controller.shuffleDiscardPile(moves.get(0));   // manually called method
        assertEquals(controller.popCardPile(moves.get(0), 2).size(), 2);
    }

    /**
     *
     * change Actor to next index
     */

    @Test
    public void testChangeActor() {

        Move move = moves.get(0);

        move.setPlayers(states);
        move.setActor(states.get(4));
        game.setLastMove(move);

        controller.changeActor(move);
        assertEquals(states.indexOf (controller.getActor()),0);

    }

    /**
     * testet parameter move als null
     */
    @Test (expected = NullPointerException.class)
    public void testChangeActorNull() {

        controller.changeActor(null);

    }
    /**
     * change Active player to next index
     */
    @Test
    public void testChangeActivePlayer() {

        Move move = moves.get(0);

        move.setPlayers(states);
        move.setActivePlayer(states.get(3));
        game.setLastMove(move);

        controller.changeActivePlayer(move);

        assertEquals(states.indexOf (controller.currentMove().getActivePlayer()),4);

    }
    /**
     * testet parameter move als null
     */

    @Test(expected = NullPointerException.class)
    public void testChangeActivePlayerNull() {
        controller.changeActivePlayer(null);

    }

    /**
     *testet different action type called by the method finishRound
     *
     */
    @Test
    public void finishRound() {

        Move move = moves.get(0); move.setPlayers(states);

        move.setPlayers(states);
        int n = states.indexOf(move.getActivePlayer());
        move.getAction().setActionType(DRAW_CARD);
        controller.finishRound(move);
        if(controller.isZonked(move) ) {
            assertEquals(states.indexOf(move.getActivePlayer()), (n+1)% states.size());
        }

        move.getAction().setActionType(BUY_PERSON);
        controller.finishRound(move);
        assertFalse(move.isPhase1());

        move.setPhase1(true);
        move.getAction().setActionType(START_EXPEDITION);
        controller.finishRound(move);
        assertFalse(move.isPhase1());


        move.getAction().setActionType(SKIP);
        move.setActor(move.getActivePlayer());

        controller.finishRound(move);
        assertEquals(states.indexOf(move.getActivePlayer()), (n+1)% states.size());
//in case of Defend and accept ship, nothing will happen, tests with result of same actor
        move.getAction().setActionType(DEFEND);
        int m = states.indexOf(move.getActor());
        controller.finishRound(move);
        assertEquals(states.indexOf(move.getActor()), m);

        move.getAction().setActionType(ACCEPT_SHIP);
        controller.finishRound(move);
        assertEquals(states.indexOf(move.getActor()), m);
    }

    /**
     * test when players order random,
     */
    @Test
    public void initRandomPlayer() {

        List<Player> oldOrder = players;

        controller.init(null,players,true,false,true);

        for(int i=0; i< players.size();i++){

            if(oldOrder.get(i)!= players.get(i)){

                assertNotSame(oldOrder.get(i).getName(), players.get(i).getName());
            }
        }

    }
    /**
     * test when players order not random,
     */
    @Test
    public void initNotRandomPlayer() {

        List<Player> oldOrder = players;

        controller.init(null,players,true,true,false);

        for(int i=0; i<players.size(); i++) {

            assertEquals(oldOrder.get(i).getName(), players.get(i).getName());
        }

    }
    /**
     * test parameter null
     */
    @Test(expected = NullPointerException.class)
    public void initNull() {

        controller.init(null,null,false,false,false);

    }

    /**
     * testet the current game is ongoing
     */
    @Test
    public void currentGameIsRunning(){
        assertTrue(game.isOngoing());

    }

    /**
     *
     * test generateMove with draw card and take ship
     */

    @Test
    public void generateMoveDrawTake() {
//test Draw card
        moves.get(0).setPlayers(states);
        Move nextMove= controller.generateMove(moves.get(0), actions.get(0)); //with ActionType DRAW CARD

        assertEquals(nextMove.getAction().getActionType(), DRAW_CARD);
 // test action Take Ship with buy Limit 5
        nextMove.setBuyLimit(5);
        actions.get(0).setActionType(TAKE_SHIP);
        nextMove = controller.generateMove(nextMove, actions.get(0));
        assertEquals(controller.generateMove(nextMove, actions.get(0)).getAction().getActionType(), TAKE_SHIP);
        assertEquals(nextMove.getBuyLimit(), 4);

    }
    /**
     *
     * test generateMove with buy person
     */

    @Test // need continue...
    public void generateMoveBuyPerson() {

        Move move = moves.get(0);
        move.setPlayers(states);
        move.setActor(move.getActor());

        move.getActor().setCoins(stack);

      /* Move nextMove= controller.generateMove(move, actions.get(2)); //with ActionType BUY_PERSON, set buy limit to 4
        nextMove.setBuyLimit(4);
        nextMove = controller.generateMove(nextMove, actions.get(2));
        assertEquals(nextMove.getBuyLimit(), 3);  */

    }
    /**
     *
     * test generateMove with accept ship
     */
    @Test
    public void generateMoveAcceptShip() {
        moves.get(0).setPlayers(states);
        actions.get(0).setActionType(ACCEPT_SHIP);
        Move nextMove= controller.generateMove(moves.get(0), actions.get(0));

        assertEquals(controller.generateMove(nextMove, actions.get(0)).getAction().getActionType(), ACCEPT_SHIP);
        assertNull(nextMove.getShipToDefend());
    }
    /**
     *
     * test generateMove with action type Defend, Skip, Start expedition
     */

    @Test
    public void generateMoveTheRest() {

        CardFactory card = new CardFactory(); moves.get(0).setPlayers(states);


        Move nextMove= new Move(moves.get(0));

        actions.get(0).setActionType(DEFEND);
        assertEquals(controller.generateMove(nextMove, actions.get(0)).getAction().getActionType(), DEFEND);

        actions.get(0).setActionType(SKIP);
        assertEquals(controller.generateMove(nextMove, actions.get(0)).getAction().getActionType(), SKIP);

        Action action = new Action(START_EXPEDITION, card.generateExpedition(true).get(5));
        action.setMaterials(card.generatePirate());
        assertEquals(controller.generateMove(nextMove, action).getAction().getActionType(), START_EXPEDITION);

    }
    /**
     *
     * test generateMove with null parameter move
     */
    @Test (expected = NullPointerException.class)
    public void generateMoveNullMove() {

        actions.get(0).setActionType(TAKE_SHIP);

        controller.generateMove(null, actions.get(0));

    }
    /**
     *
     * test generateMove with null parameter action
     */
    @Test (expected = NullPointerException.class)
    public void generateMoveNullAction() {
        controller.generateMove(moves.get(0), null);
    }
    /**
     *
     * test generateMove with both null parameter
     */
    @Test (expected = NullPointerException.class)
    public void generateMoveNull() {
        controller.generateMove(null, null);
    }
    /**
     *
     * test action type defend and ACCEPT_SHIP
     */
    @Test
    public void getPossibleActionsDefend() {

        Move move = moves.get(0);
        move.setShipToDefend(new Ship(BLUE, 2, 1));

        assertEquals(controller.getPossibleActions(move).get(0).getActionType(), DEFEND);
        assertEquals(controller.getPossibleActions(move).get(1).getActionType(), ACCEPT_SHIP);
    }

    /**
     *
     * test action type draw card
     */
    @Test
    public void getPossibleActionsDrawCard() {

        Move move = moves.get(0);   //under this move, is player1 with 3 coins by CoinStack
        move.setActor(states.get(0));
        move.setActivePlayer(states.get(0));
        move.setPhase1(true);
        if(!controller.isZonked(move)) {
            assertEquals(controller.getPossibleActions(move).get(0).getActionType(), DRAW_CARD);
        }
    }

    /**
     *
     * test action type buy person and take ship
     */
    @Test
    public void getPossibleActionsBuyPersonTakeShip() {

        Move move = moves.get(0);   //under this move, is player1 with 3 coins by CoinStack
        move.setHarbour(states.get(1).getCards());  // added a Sailor, price 3, the other 2 cards are over 3
        move.getHarbour().getCards().add(new Ship(BLUE, 2, 1));
        move.setBuyLimit(2);
        move.setShipToDefend(null);

        assertEquals(controller.getPossibleActions(move).get(0).getActionType(), TAKE_SHIP);
        assertEquals(controller.getPossibleActions(move).get(1).getActionType(), BUY_PERSON);
    }
    /**
     *
     * test with parameter null
     */
    @Test(expected = NullPointerException.class)
    public void getPossibleActionsNull() {

        assertNull(controller.getPossibleActions(null));
    }

    /**
     *
     * test undo() - new move will be the one before current move
     */
    @Test
    public void undo() {

        game.setLastMove(moves.get(moves.size()-1));

        int index = moves.indexOf(game.getLastMove());

        controller.undo(moves.get(0));
        assertEquals(moves.indexOf(controller.currentMove()), index-1);

        // here tests: when the move list has only one move, nothing should happens
        List<Move> move = new ArrayList<>();
        move.add(moves.get(1));
        game.setMoves(move);
        game.setLastMove(move.get(0));

        controller.undo(moves.get(0));

        assertEquals(move.indexOf(controller.currentMove()), 0);

    }

    @Test //not clear, after undo, the rest moves after last move will be deleted, can not redo.....
    public void redo() {

        game.setLastMove(moves.get(2));
        System.out.println(moves.indexOf(game.getLastMove()));
        controller.undo(moves.get(0));
        System.out.println(moves.indexOf(game.getLastMove()));

        controller.redo(moves.get(0));

        System.out.println(moves.indexOf(game.getLastMove()));

    }

    /**
     * test isShuffleCards() als true
     */
    @Test
    public void shuffleDiscardPileTrue() {

        game.setShuffleCards(true);
        Move move = moves.get(0);
        move.setDiscardPile(stack);
        controller.shuffleDiscardPile(move);

        assertEquals(stack, move.getCardPile());  //set the discard pile, return a card pile same as discard pile

    }
    /**
     * test isShuffleCards() als false
     */
    @Test
    public void shuffleDiscardPileFalse() {

        game.setShuffleCards(false);
        Move move = moves.get(0);
        CardStack disCard = TestFactory.getPlayerState().get(0).getCards();
        move.setDiscardPile(disCard);
        CardStack init = new CardStack();
        List<Card> initCards = init.getCards();

        //suppose the pile has one card, otherwise leads to nullPointer using method
        CardStack pile = new CardStack();
        pile.getCards().add(new Ship(BLUE, 2,2));
        move.setCardPile(pile);
//setup initial cards with different order of discard pile
        initCards.add(disCard.getCards().get(3));
        initCards.add(disCard.getCards().get(1));
        initCards.add(disCard.getCards().get(0));
        initCards.add(disCard.getCards().get(2));

        game.setInitCardPile(init);
        controller.shuffleDiscardPile(move);

//after using method, the shuffled card should be same order as initial cards
        for(int i=0; i<4; i++) {

        assertEquals(initCards.get(i), move.getCardPile().getCards().get(i+1));
        }

        assertEquals(disCard.getSize(), 0);//the discard pile is 0 afterwards
    }

    /**
     * tests methode with null
     */

    @Test (expected = NullPointerException.class)
    public void shuffleDiscardPileNull() {

      controller.shuffleDiscardPile(null)  ;

    }

    /**
     *
     * tests if end of the game players' score are in hightscore list
     */
    @Test  //need to revise the mothod, which add doubled players into list without filter
    public void finishGame() {


        moves.get(0).setPlayers(states);


//no player has victory point more than 12
        for(int j=0; j<players.size();j++){

            states.get(j).setPlayer(players.get(j));
            assertTrue(states.get(j).getVitoryPoints()<12);

        }

        gameSystem.setHighscoreList(new ArrayList<>());
        controller.finishGame(moves.get(0));
        List<Player> scores = mainController.getHighscoreController().getHighscoreList();

//scores are the same as original victory point

        for(int i=0; i< scores.size();i++){
          assertEquals(states.get(i).getVitoryPoints(), scores.get(i).getScore());
        }
    }

    /**
     * tests when harbour has same color ship;
     * tests when no color ship appears
     */
    @Test
    public void isZonked() {

        CardStack harbour = moves.get(0).getHarbour();
        harbour.getCards().add(new Ship(YELLOW,1,1));
        Card zonked = new Ship(YELLOW,2,2);
        harbour.getCards().add(zonked);

   // in case of true
        assertTrue(controller.isZonked(moves.get(0)));
        assertEquals(harbour.getSize(),0);

// in case of false - different ship color
        Card zonked1= new Ship(GREEN,1,1);
        harbour.getCards().add(zonked1);
        assertFalse(controller.isZonked(moves.get(0)));


    }

    /**
     * return active player
     */

    @Test
    public void getActivePlayer(){
        game.setLastMove(moves.get(2));
        moves.get(2).setActivePlayer(states.get(3));
        assertEquals(controller.getActivePlayer(), moves.get(2).getActivePlayer());

    }

    /**
     * return actor
     */
    @Test
    public void getActor(){

        game.setLastMove(moves.get(2));
        moves.get(2).setActor(states.get(4));
        assertEquals(controller.getActor(), moves.get(2).getActor());

    }
}