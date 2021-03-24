package controller;

import model.*;
import org.junit.Before;
import org.junit.Test;
import view.GameViewAUI;

import java.util.List;

import static model.ActionType.DRAW_CARD;
import static org.junit.Assert.*;

public class PlayerControllerTest {

    private MainController mainController;
    private PlayerController playerController;
    private GameSystem gameSystem;
    private Game game;
    private List<Move> moves;
    private Move move;
    private Action action;
    private List<Player> players;
    private List<PlayerState> states;
    private CardStack stack;

    @Before
    public void setUp() throws Exception {

        mainController = new MainController();
        gameSystem = new GameSystem();
        mainController.setGameSystem(gameSystem);
        playerController = mainController.getPlayerController();
        states = TestFactory.getPlayerState();
        players = TestFactory.getPlayers();
        moves = TestFactory.moves();
        move = moves.get(0);
        move.setPlayers(states);
        action = TestFactory.actions().get(2);
        stack = CardFactory.newCardsWithoutSpecial();
        game = new Game(true,states.get(0), true, players,stack);
        gameSystem.setCurrentGame(game);
        game.setMoves(moves);
        game.setPlayerStates(states);

    }

    /**
     *
     * testet executeAction method, with GameViewAUI null or not null
     */

    @Test
    public void executeAction() {

        GameViewAUI gameViewAUI = mainController.getGameViewAUI();
        mainController.setGameViewAUI( gameViewAUI);
        game.setLastMove(move);
        action.setActionType(DRAW_CARD);
        move.setCardPile(stack);

        playerController.executeAction(action);
        mainController.setGameViewAUI(null);
        playerController.executeAction(action);


    }

    /**
     *
     * tests players order random and not random
     */

    @Test
    public void setPlayerOrder() {


        List<Player> playerOrder = players;
        List<Player> newOrder = playerController.setPlayerOrder(playerOrder,true);

        for(int i=0;i < newOrder.size(); i++){

            if(playerOrder.get(i) != newOrder.get(i)){
                assertNotSame(playerOrder.get(i),newOrder.get(i));
            }
        }

        List<Player> newOrder2 = playerController.setPlayerOrder(playerOrder,false);

        for(int i=0;i < newOrder2.size(); i++){
                assertEquals(playerOrder.get(i),newOrder2.get(i));
        }

    }

    /**
     * return null value
     */
    @Test
    public void getHint() {

        assertNull(playerController.getHint(move));
    }

    /**
     * return player's victory points
     */
    @Test
    public void getVictoryPoints() {

        PlayerState player = states.get(3);
        int points = player.getVictoryPoints();

        Expedition card = (Expedition) stack.getCards().get(118);
        player.getCards().getCards().add(card);
        assertEquals(playerController.getVictoryPoints(player,move), points+card.getVictoryPoints());

    }
    /**
     * return player's coins number
     */
    @Test
    public void getCoins() {

        PlayerState player = states.get(1);
        CardStack coin = player.getCoins();

        assertEquals(playerController.getCoins(player,move),coin.getSize());
    }
    /**
     * return player's expedition card number
     */
    @Test
    public void countExpeditions() {
        PlayerState player = states.get(2);

        Expedition card = (Expedition) stack.getCards().get(118);
        player.getCards().getCards().add(card);

        assertEquals(playerController.countExpeditions(player,move),1);

    }
}