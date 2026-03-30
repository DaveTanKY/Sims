package sims.gameEngine;

import sims.actions.Activity;
import sims.career.Career;
import sims.entity.Sim;
import sims.needs.Need;
import sims.world.Home;
import sims.world.HomeLocation;
import sims.world.HomeUpgrade;
import sims.world.OutsideLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Entry point for the Sims game engine.
 * The {@code Main} class initializes the {@link GameState}, starts the game,
 * and continuously updates the game loop while the game is running.
 *
 * <p>This class demonstrates a simple game loop structure:
 * <ul>
 *   <li>Create a new {@link GameState} instance</li>
 *   <li>Start the game</li>
 *   <li>Run updates while the game is active</li>
 * </ul>
 * </p>
 */
public class Main {
    /**
     * The main method that launches the Sims game.
     * It initializes the game state, starts the game, and runs
     * the update loop until the game is no longer running.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args)
    {
        GameState game = new GameState();
        game.startGame();
        while(game.getGameRunning())
        {
            game.update();
        }
    }
}
