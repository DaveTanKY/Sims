package sims.world;

import sims.gameEngine.*;
import java.util.Scanner;
import sims.entity.*;
import sims.actions.*;

public class LivingRoom extends Location{
    public LivingRoom(){
        super("LivingRoom");
    }

    @Override
    public void showOptions(){

        System.out.println(" ");
        System.out.println("1. View Needs");
        System.out.println("2. Browse the internet on your computer");
        System.out.println("3. Watch TV");
        System.out.println("4. Socialise with a friend over the phone");
        System.out.println("5. Go to the bedroom");
        System.out.println("6. Go to the kitchen");
        System.out.println("7. Go to the washroom");
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
                return true; // stays within the current room loop

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

                currentSim.moveTo(Gameplay.washroom);
                return false;

            case "8":

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
