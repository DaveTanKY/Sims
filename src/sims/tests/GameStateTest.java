package sims.tests;

import sims.gameEngine.GameState;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class GameStateTest {

    private GameState gameState;

    @Before
    public void setUp() {
        gameState = new GameState();
    }

    @Test
    public void testSetGameState() {
        gameState.setGameState(3);
        assertEquals(3, gameState.getGameState());
    }

    @Test
    public void testGetGameRunning() {
        assertTrue(gameState.getGameRunning());
    }

    @Test
    public void testEndGame() {
        assertTrue(gameState.getGameRunning());
        gameState.endGame();
        assertFalse(gameState.getGameRunning());
    }

    @Test
    public void testUpdateTime() {
        int initialTime = gameState.getIntTime();
        gameState.updateTime();
        assertEquals(initialTime + 1, gameState.getIntTime());
    }

    @Test
    public void testGetIntTime() {
        // Assuming initial time is 0
        assertEquals(0, gameState.getIntTime());
    }

    @Test
    public void testGetStringTime() {
        assertEquals("45 Minutes", gameState.getStringTime(45));
        assertEquals("02 Hours : 05 Minutes", gameState.getStringTime(125));
        assertEquals("01 Day : 01 Hours : 00 Minutes", gameState.getStringTime(1500));
    }
}