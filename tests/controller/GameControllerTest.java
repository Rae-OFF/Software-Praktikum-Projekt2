package controller;

import model.*;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static model.ActionType.*;
import static model.Colour.*;
import static model.PersonType.*;
import static org.junit.Assert.*;

/**
 * Test für den GameController.
 */
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
    private CardFactory cardFactory;

    /**
     * Erstellt Testumgebung.
     */
    @Before
    public void setUp() {

      mainController = new MainController();
      controller = mainController.getGameController();
      stack = CardFactory.newCardsWithoutSpecial();
      gameSystem = new GameSystem();
      mainController.setGameSystem(gameSystem);
      cardFactory = new CardFactory();
        players = TestFactory.getPlayers();
        states = TestFactory.getPlayerState();
        moves = TestFactory.moves();
        moves.get(0).setPlayers(states);
        actions = TestFactory.actions();
        game = new Game(true,states.get(0), true, players,stack);
        gameSystem.setCurrentGame(game);
        game.setMoves(moves);
        game.setPlayerStates(states);

    }
    /**
     * constructortest - null.
     */

// ?: kein null pointer
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

        CardStack cardStack = moves.get(0).getCardPile();
        assertNotNull(cardStack.peek());
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
    @Test
    public void testPopCardPileEmpty() {

        CardStack cardStack = moves.get(0).getCardPile();
        cardStack.getCards().clear();
        assertNull(cardStack.peek());
        moves.get(0).setDiscardPile(stack);
        controller.popCardPile(moves.get(0), 12);
        assertTrue(cardStack.getSize()!=0);
        assertEquals(controller.popCardPile(moves.get(0), 10).size(), 10);
    }

    /**
     *
     * change Actor to next index
     */

    @Test
    public void testChangeActor() {

        Move move = moves.get(0);

        move.setActor(states.get(4));
        game.setLastMove(move);

        controller.changeActor(move);
        assertEquals(states.indexOf (controller.getActor()),0);
        assertEquals(move.getBuyLimit(),1);
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

        move.setActivePlayer(states.get(3));
        game.setLastMove(move);
        controller.changeActivePlayer(move);

        assertEquals(states.indexOf (controller.currentMove().getActivePlayer()),4);
        assertTrue(move.isPhase1());
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

        Move move = moves.get(0);
        game.setLastMove(move); move.setCardPile(stack);
        move.setActivePlayer(states.get(2));
   //setup harbour cards, so will be zonked
        CardStack harbour = move.getHarbour();
        harbour.getCards().clear();
        harbour.setCards(cardFactory.generateBlueShips());
        int n = states.indexOf(move.getActivePlayer());
        move.getAction().setActionType(DRAW_CARD);

        controller.finishRound(move);
        assertEquals(states.indexOf(move.getActivePlayer()), (n+1)% states.size());

//in case of Defend and accept ship, nothing will happen, tests with result of same actor
        move.getAction().setActionType(DEFEND);
        harbour.setCards(cardFactory.generateRedShips());
        int m = states.indexOf(move.getActivePlayer());
        controller.finishRound(move);
        assertEquals(states.indexOf(move.getActivePlayer()),(m+1)% states.size());

        harbour.setCards(cardFactory.generateGreenShips());
        move.getAction().setActionType(ACCEPT_SHIP);
        int j = states.indexOf(move.getActivePlayer());
        controller.finishRound(move);
        assertEquals(states.indexOf(move.getActivePlayer()), (j+1)% states.size());


        move.getAction().setActionType(BUY_PERSON);
        controller.finishRound(move);
        assertFalse(move.isPhase1());

        move.setPhase1(true);
        move.getAction().setActionType(START_EXPEDITION);
        controller.finishRound(move);
        assertFalse(move.isPhase1());

        move.getAction().setActionType(SKIP);
        move.setActivePlayer(states.get(4));
        move.setActor(states.get(4));
        int old = states.indexOf(move.getActivePlayer());

        controller.finishRound(move);
        assertEquals(states.indexOf(move.getActor()), (old+1)% states.size());



        move.getAction().setActionType(SHUFFLE);System.out.println(move.getAction().getActionType() );
        controller.finishRound(move);
        assertEquals(move.getAction().getActionType(),SHUFFLE);

    }


    /**
     *
     * tests if end of the game players' score are in hightscore list
     */
    @Test  //need to revise the mothod, which add doubled players into list without filter
    public void finishGame() {


        List<Integer> wins = new ArrayList<>();
        states.get(0).getCards().setCards(cardFactory.generateExpedition(false));
        for(int j=0; j<players.size();j++){
            states.get(j).setPlayer(players.get(j));
            wins.add(players.get(j).getWins());
        }

        gameSystem.setHighscoreList(new ArrayList<>());
        controller.finishGame(moves.get(0));
        List<Player> scores = mainController.getHighscoreController().getHighscoreList();

//scores are the same as original victory point, who has more than 12, get wins+1

        for(int i=0; i< scores.size();i++){
            assertEquals(states.get(i).getVictoryPoints(), scores.get(i).getScore());

            if(states.get(i).getVictoryPoints()>12){
                assertEquals(players.get(i).getWins(), wins.get(i)+1);
            }
        }
    }
    /**
     * test when players order random,
     */
    @Test(expected = NullPointerException.class)
    public void init2Players() {

        List<Player> player = new ArrayList<>();
        player.add(players.get(1));
        player.add(players.get(2));
        controller.init(null,player,true,false,true);
        game.getCardPile().setCards(cardFactory.generateRedShips());
        controller.init("--",player,true,false,true);
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
     * testet the current game is ongoing
     */
    @Test
    public void currentGameIsRunning(){

        game.setOngoing(false);
        assertFalse(controller.currentGameIsRunning() );

        game.setOngoing(true);
        assertTrue(controller.currentGameIsRunning());

    }

    /**
     *
     * test generateMove with draw card and take ship
     */

    @Test
    public void generateMoveDrawTake() {
//test Draw card

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


        move.setActor(move.getActor());
        move.getActor().setCoins(stack);
        ((Person)actions.get(2).getAffectedCard()).setPrice(1);
        Move nextMove= controller.generateMove(move, actions.get(2)); //with ActionType BUY_PERSON, set buy limit to 4
        nextMove.setBuyLimit(4);
        nextMove = controller.generateMove(nextMove, actions.get(2));
        assertEquals(nextMove.getBuyLimit(), 3);

    }
    /**
     *
     * test generateMove with accept ship
     */
    @Test
    public void generateMoveAcceptShip() {

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
        move.setShipToDefend(new Ship(BLUE, 2, 0));
        assertEquals(controller.getPossibleActions(move).get(0).getActionType(), DEFEND);

        move.setShipToDefend(new Ship(BLUE, 2, 4));
        assertNotEquals(controller.getPossibleActions(move).get(0).getActionType(), DEFEND);

    }

    /**
     *
     * test action type draw card
     */
    @Test
    public void getPossibleActionsDrawCard() {

        Move move = moves.get(0);   //under this move, is player1 with 3 coins by CoinStack
        move.setActor(states.get(0));
        move.setActivePlayer(move.getActor());
        move.setPhase1(true);
        move.getHarbour().getCards().clear();
        move.getHarbour().getCards().add(new Ship(BLACK,1,1));

            assertEquals(controller.getPossibleActions(move).get(0).getActionType(), DRAW_CARD);
        move.getHarbour().setCards(cardFactory.generateBlueShips());
        assertNotEquals(controller.getPossibleActions(move).get(0).getActionType(), DRAW_CARD);
        move.setPhase1(false);
        assertNotEquals(controller.getPossibleActions(move).get(0).getActionType(), DRAW_CARD);
        move.setPhase1(true);
        move.getDiscardPile().getCards().clear();
        move.getCardPile().getCards().clear();
        assertNotEquals(controller.getPossibleActions(move).get(0).getActionType(), DRAW_CARD);
        move.setActivePlayer(states.get(2));
        assertNotEquals(controller.getPossibleActions(move).get(0).getActionType(), DRAW_CARD);
    }
    /**
     *
     * test action type buy person and take ship
     */
    @Test
    public void getPossibleActionsTakeShip() {

        Move move = moves.get(0);   //under this move, is player1 with 3 coins by CoinStack
        move.getHarbour().getCards().clear();
        move.getHarbour().getCards().add(new Ship(BLUE, 2, 1));
        move.setShipToDefend(new Ship(BLUE, 2, 1));
        move.setBuyLimit(2);

        assertNotEquals(controller.getPossibleActions(move).get(0).getActionType(), TAKE_SHIP);
        move.setBuyLimit(-7);
        assertNotEquals(controller.getPossibleActions(move).get(0).getActionType(), TAKE_SHIP);

        move.setShipToDefend(null); move.setBuyLimit(0);
        assertNotEquals(controller.getPossibleActions(move).get(0).getActionType(), TAKE_SHIP);
        move.setBuyLimit(2);
        assertEquals(controller.getPossibleActions(move).get(0).getActionType(), TAKE_SHIP);

    }
    /**
     *
     * test action type buy person
     */
    @Test
    public void getPossibleActionsBuyPerson() {

        Move move = moves.get(0);   //under this move, is player1 with 3 coins by CoinStack
        move.setActor(states.get(0));
        move.setActivePlayer(states.get(0));
        move.setPhase1(false);

        move.setHarbour(states.get(2).getCards());  // added a Sailor, price 3, the other 2 cards are over 3
//when player's money less than price
        int price = ((Person)move.getHarbour().getCards().get(0)).getPrice();
        move.getActor().getCards().getCards().add(cardFactory.generateMademoiselles().get(2));
        move.getActor().getCards().getCards().add(cardFactory.generateMademoiselles().get(2));
        move.getActor().getCards().getCards().add(cardFactory.generateMademoiselles().get(2));

        assertFalse((price < move.getActivePlayer().getCoins().getSize()));
        assertNotEquals(controller.getPossibleActions(move).get(0).getActionType(), BUY_PERSON);
  //when player's money more than price
        move.setBuyLimit(2);
        move.getActor().setCoins(stack);
        assertTrue((price < stack.getSize()));
        assertEquals(controller.getPossibleActions(move).get(0).getActionType(), BUY_PERSON);
//here added in the list 2nd time Buy person since the coin size is bigger than price even when actor!=active player
        move.setActivePlayer(states.get(3));
        controller.getPossibleActions(move);
        assertTrue((price+1 < stack.getSize()));
        assertEquals(controller.getPossibleActions(move).get(1).getActionType(), BUY_PERSON);
    }

    /**
     *
     * test action type start Expedition
     */
    @Test
    public void getPossibleActionsExpedition() {
//tests only if active player same as actor is -- 1st "if" condition
        Move move = moves.get(0);

        move.setPlayers(states);
        move.setActivePlayer(states.get(3));
        move.setActor(states.get(1));
        assertNotEquals(controller.getPossibleActions(move).get(0).getActionType(), START_EXPEDITION);

        move.setActor(states.get(1));
        assertNotEquals(controller.getPossibleActions(move).get(0).getActionType(), START_EXPEDITION);


    }

    /**
     * Testet ob eine Expedition möglich ist.
     */
    @Test
    public void checkExpeditionPossible(){

        Move move = moves.get(0);   //under this move,  active player has one Priest, one Pirate.

        move.setPlayers(states);
        move.setActivePlayer(states.get(3));
        move.setActor(states.get(3));
        PlayerState activePlayer = move.getActivePlayer();

        activePlayer.setCoins(stack);

        List<Card> exped = new ArrayList<>();
        move.getExpeditionPile().setCards(exped);
        exped.add(cardFactory.generateJester().get(0));

        Map<PersonType, Integer> requirements = new HashMap<>();
        requirements.put(CAPTAIN, 1);
        requirements.put(SETTLER, 1);
        requirements.put(PRIEST, 1);
        requirements.put(JACK_OF_ALL_TRADES, 0);

        Expedition expedition = new Expedition(requirements,3,5);
        exped.add(expedition); // special card

        List<Card> cards = move.getActivePlayer().getCards().getCards();
        //when the requirements fulfilled
        assertFalse(controller.checkExpeditionPossible(activePlayer, expedition));
   //     assertNotEquals(controller.getPossibleActions(move).get(0).getActionType(), START_EXPEDITION);

        cards.add(cardFactory.generatePriest().get(0));
        cards.add(cardFactory.generatePriest().get(0));
        cards.add(cardFactory.generateSettlerCaptain().get(0));
        cards.add(cardFactory.generateSettlerCaptain().get(0));
        cards.add(cardFactory.generateSettlerCaptain().get(1));
        cards.add(cardFactory.generateSettlerCaptain().get(1));
        assertTrue(controller.checkExpeditionPossible(move.getActivePlayer(), expedition));
        assertEquals(controller.getPossibleActions(move).get(1).getActionType(), START_EXPEDITION);
//when the requirements not fulfilled
        cards.clear();
        cards.addAll(cardFactory.generateRedShips());
        assertNotEquals(controller.getPossibleActions(move).get(0).getActionType(), START_EXPEDITION);

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

    /**
     * Testet ob redo erfolgreich durchgeführt wird.
     */
    @Test //not clear, after undo, the rest moves after last move will be deleted, can not redo.....
    public void redo() {
        game.setMoves(moves);
        game.setLastMove(moves.get(1));
        int n = moves.lastIndexOf(controller.currentMove());
        assertTrue(n < moves.size()-1);

        controller.redo(moves.get(0));
        assertEquals(moves.indexOf(game.getLastMove()), n+1);

        game.setLastMove(moves.get(2)); int m = moves.lastIndexOf(controller.currentMove());
        assertFalse(m < moves.size()-1);
        controller.redo(moves.get(0));
    }

    /**
     * test isShuffleCards() als true
     */
    @Test
    public void shuffleDiscardPileTrue() {

        game.setShuffleCards(true);
        Move move = moves.get(0);
        move.getCardPile().getCards().clear();
        move.setDiscardPile(stack);
        int n = stack.getSize();
        controller.shuffleDiscardPile(move);

        assertEquals(n, move.getCardPile().getSize());  //set the discard pile, return a card pile same as discard pile

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
     * tests when harbour has same color ship;
     * tests when no color ship appears
     */
    @Test
    public void isZonked() {

        CardStack harbour = moves.get(0).getHarbour();
        harbour.getCards().clear();
        assertFalse(controller.isZonked(moves.get(0)));
// in case of false - different ship color
        harbour.getCards().add(new Ship(YELLOW,1,1));
        Ship zonked = new Ship(GREEN,2,2);
        harbour.getCards().add(zonked);
        assertFalse(controller.isZonked(moves.get(0)));
   // in case of true
        harbour.getCards().add(new Ship(YELLOW,2,2));

        assertTrue(controller.isZonked(moves.get(0)));
        assertEquals(harbour.getSize(),0);
 // in case of no Ship card

        harbour.getCards().add(cardFactory.generateJester().get(0));
        harbour.getCards().add(new Ship(YELLOW,2,2));
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