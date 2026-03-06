package sims.world;

import sims.gameEngine.*;
import java.util.Scanner;
import sims.entity.*;
import sims.actions.*;

public class Bedroom extends Location{
    public Bedroom(){
        super("Bedroom");
    }

    @Override
    public void showOptions() {

        System.out.println(" ");
        System.out.println("1. View Needs");
        System.out.println("2. Go to sleep");
        System.out.println("3. Go to the kitchen");
        System.out.println("4. Go to the living room");
        System.out.println("5. Go to the washroom");
        System.out.println("6. Go to work");
        System.out.println("x. Terminate the game");

    }

    @Override
    public boolean handleLocActions(SimProfile currentSim, Scanner scanner) {

        System.out.println("What do you want " + currentSim.getName() + " to do?");
        String actionChoice = scanner.nextLine();

        switch(actionChoice) {
            case "1":

                currentSim.showNeeds();
                return true;

            case "2":

                // action
                return true;

            case "3":

                currentSim.moveTo(Gameplay.kitchen);
                return false;

            case "4":

                currentSim.moveTo(Gameplay.livingroom);
                return false;

            case "5":

                currentSim.moveTo(Gameplay.washroom);
                return false;

            case "6":

                //work action not done
                return false;

            case "x":

                Gameplay.gameplay = false;
                return false; // tells Gameplay to exit the inner inLocation loop

            default:

                System.out.println("I don't think your Sim can do that. Pick something else!");
                return true; // stays within the current room loop

        }
    }
}
