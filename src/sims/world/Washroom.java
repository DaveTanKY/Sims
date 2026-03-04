package sims.world;

import java.util.Scanner;

import sims.actions.SimAction;
import sims.actions.Sleep;
import sims.entity.SimProfile;

public class Washroom extends Location{
    public Washroom(){
        super("Washroom");
    }

    @Override
    public void showOptions(){
        System.out.println("1. View Needs");
        System.out.println("2. Take a steamy bath");
        System.out.println("3. Take a rinse");
        System.out.println("4. Use the toilet");
        // Returns to navigation menu if user wants to go another location
        System.out.println("x. Return to navigation menu\n");
    }

    public boolean handleLocActions(SimProfile sim, Scanner scanner) {
        String actionChoice = scanner.nextLine();
        SimAction action = null;

        switch(actionChoice) {
            case "1":
                sim.showNeeds();
                break;
            case "2":
                //action = new ;
                break;
            case "3":
                //action = new ;
                break;
            case "4":
                //action = new ;
                break;

            case "x":
                return false; // tells Gameplay to exit the inner inLocation loop
            default:
                System.out.println("Please enter a valid number.");
        }

        if (action != null) {
            action.execute(sim);
        }
        return true; // stay in location


    }
}
