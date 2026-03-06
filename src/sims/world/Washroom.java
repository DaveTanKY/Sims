package sims.world;

import sims.gameEngine.*;
import java.util.Scanner;
import sims.entity.*;
import sims.actions.*;

public class Washroom extends Location{
    public Washroom(){
        super("Washroom");
    }

    @Override
    public void showOptions(){

        System.out.println(" ");
        System.out.println(" ");
        System.out.println("1. View Needs");
        System.out.println("2. Take a steamy bath");
        System.out.println("3. Take a rinse");
        System.out.println("4. Use the toilet");
        System.out.println("5. Go to the bedroom");
        System.out.println("6. Go to the kitchen");
        System.out.println("7. Go to the living room");
        System.out.println("8. Go to work");
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

                //action = new ;
                return true;

            case "3":

                //action = new ;
                return true;

            case "4":

                //action = new ;
                return true;

            case "5":

                currentSim.moveTo(Gameplay.bedroom);
                return false;

            case "6":

                currentSim.moveTo(Gameplay.kitchen);
                return false;

            case "7":

                currentSim.moveTo(Gameplay.livingroom);
                return false;

            case "8":

                //work action is not done
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
