package sims.gameEngine;

import sims.actions.Activity;
import sims.career.Career;
import sims.entity.Sim;
import sims.needs.need;
import sims.world.Home;
import sims.world.HomeLocation;
import sims.world.HomeUpgrade;
import sims.world.OutsideLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
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
