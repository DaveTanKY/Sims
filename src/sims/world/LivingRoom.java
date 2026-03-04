package sims.world;

import java.util.Scanner;

import sims.actions.SimAction;
import sims.actions.Sleep;
import sims.entity.SimProfile;

public class LivingRoom extends Location{
    public LivingRoom(){
        super("LivingRoom");
    }

    @Override
    public void showOptions(){
        System.out.println("1. View Needs");
        System.out.println("2. Browse the internet on your computer");
        System.out.println("3. Watch TV");
        System.out.println("4. Socialise with a friend over the phone");
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
