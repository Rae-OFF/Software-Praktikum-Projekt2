package controller;

import model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class HighscoreControllerTest {

    private List<Player> players = new ArrayList<>();

    private List<PlayerState> playerStates = new ArrayList<>();

    private MainController mainController;
    /**
     * Testumgebung.
     */
    @Before
    public void setUp(){
        mainController = new MainController();
        //List<Player> players = new ArrayList<>()

        Player player0 = new Player("Player0", PlayerType.HUMAN);
        player0.setScore(24);
        player0.setWins(2);
        Player player1 = new Player("Player1", PlayerType.HUMAN);
        player1.setScore(12);
        player1.setWins(1);
        Player player2 = new Player("Player2", PlayerType.HUMAN);
        player2.setScore(55);
        player2.setWins(3);
        Player player3 = new Player("Player3", PlayerType.HUMAN);
        player3.setScore(9);
        player3.setWins(0);

        PlayerState firstPlayer = new PlayerState(player0);
        PlayerState secondPlayer = new PlayerState(player1);
        PlayerState thirdPlayer = new PlayerState(player2);
        PlayerState fourthPlayer = new PlayerState(player3);

        CardStack cards = new CardStack();

        players.add(player0);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        playerStates.add(firstPlayer);
        playerStates.add(secondPlayer);
        playerStates.add(thirdPlayer);
        playerStates.add(fourthPlayer);

        GameSystem system = new GameSystem();
        Game game = new Game(true, firstPlayer, true, players, cards);
        game.setHighscoreEnabled(true);


    }

    /**
     * Konstruktor Test.
     */
    @Test(expected = NullPointerException.class)
    public void testHighscoreControllerKonstruktorNull(){
        new HighscoreController(null);
    }

    /**
     * Testet das erfolgreiche holen der Highscoreliste.
     */
    @Test
    public void getHighscoreListTest(){
        mainController.getGameSystem().setHighscoreList(players);
        List<Player> highscoreList = mainController.getHighscoreController().getHighscoreList();
        assertEquals(players, highscoreList);
    }

    /**
     *Testet das Verhalten wenn eine leere Liste übergeben wird.
     */
    @Test
    public void getHighscoreListEmptyTest(){
        List<Player> emptyList = new ArrayList<>();
        mainController.getGameSystem().setHighscoreList(emptyList);
        List<Player> highscoreList = mainController.getHighscoreController().getHighscoreList();
        assert(highscoreList.isEmpty());
    }

    /**
     * Testet das Verhalten bei Übergeben eines <em>null</em> Objekts.
     */
    @Test
    public void getHighscoreListNullTest(){
        mainController.getGameSystem().setHighscoreList(null);
        List<Player> highscoreList = mainController.getHighscoreController().getHighscoreList();
        assertNull(highscoreList);
    }

    /**
     * Testet das erfolgreiches hinzufügen eines Spielers zu der Liste.
     */
    @Test
    public void addPlayerScoreTest(){
        Player newPlayer = new Player("NewPlayer", PlayerType.HUMAN);
        List<Player> newPlayerList = new ArrayList<>();
        newPlayerList.addAll(players);
        newPlayerList.add(newPlayer);
        mainController.getGameSystem().setHighscoreList(players);
        mainController.getHighscoreController().addPlayerScore(newPlayer);
        assertEquals(newPlayerList, mainController.getHighscoreController().getHighscoreList());

    }

    /**
     * Testet Fehler bei Übergeben von Parameter <em>null</em> beim hinzufügen des PLayers.
     */
    @Test(expected = NullPointerException.class)
    public void addPlayerScoreNullTest(){
        mainController.getGameSystem().setHighscoreList(players);
        mainController.getHighscoreController().addPlayerScore(null);
    }
}
