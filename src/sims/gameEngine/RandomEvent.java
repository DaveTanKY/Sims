package sims.gameEngine;

import sims.needs.*;
import sims.entity.*;

public class RandomEvent {

    public void FoodEvent(SimProfile currentSim){
        int number = Utility.randomNumberBetween(1, 100);
        if (number <= 20){

            Needs needs = currentSim.getNeeds();
            needs.setEnergy(needs.getEnergy() - 60);
            needs.setHunger(needs.getHunger() - 80);
            System.out.println(currentSim.getName() + " might have eaten something rotten...");
        }
    }

    public void CleanEvent(SimProfile currentSim){
        int number = Utility.randomNumberBetween(1, 100);
        if (number <= 20){

            Needs needs = currentSim.getNeeds();
            needs.setHygiene(needs.getHygiene() - 85);
            needs.setFun(needs.getFun() - 70);
            System.out.println(currentSim.getName() + " had to clean some filth that was laying around...");
        }
    }

    public void ToiletEvent(SimProfile currentSim){
        int number = Utility.randomNumberBetween(1, 100);
        if (number <= 15){

            Needs needs = currentSim.getNeeds();
            needs.setHygiene(needs.getHygiene() - 85);
            needs.setFun(needs.getFun() - 60);
            System.out.println(currentSim.getName() + " had to fix a leaky pipe...");
        }
    }
}
