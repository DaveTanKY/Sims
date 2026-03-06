package sims.world;

import sims.gameEngine.*;
import java.util.Scanner;
import sims.entity.*;
import sims.actions.*;

public class Kitchen extends Location{
    public Kitchen(){
        super("Kitchen");
    }

    @Override
    public void showOptions(){

        System.out.println(" ");
        System.out.println("1. View Needs");
        System.out.println("2. Eat something");
        System.out.println("3. Go to the bedroom");
        System.out.println("4. Go to the living room");
        System.out.println("5. Go to the washroom");
        System.out.println("6. Go to work");
        System.out.println("x. Terminate the game");

    }

    @Override
    public boolean handleLocActions(SimProfile currentSim, Scanner scanner) {

        System.out.println("What do you want " + currentSim.getName() + " to do?");
        String actionChoice = scanner.nextLine();

        switch(actionChoice){
            case "1":

                currentSim.showNeeds();
                return true; // stays within the current room loop

            case "2":

                // eat action
                return true; // stays within the current room loop

            case "3":

                currentSim.moveTo(Gameplay.bedroom);
                return false; // exits the current room loop

            case "4":

                currentSim.moveTo(Gameplay.livingroom);
                return false; // exits the current room loop

            case "5":

                currentSim.moveTo(Gameplay.washroom);
                return false; // exits the current room loop

            case "6":

                // work is not done
                return false; // exits the current room loop

            case "x":

                Gameplay.gameplay = false; // forces the gameplay variable to be false so the game forcibly ends
                return false; // exits the current room loop

            default:

                System.out.println("I don't think your Sim can do that. Pick something else!");
                return true; // stays within the current room loop

            }
    }
}
