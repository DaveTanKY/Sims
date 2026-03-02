package sims.gameEngine;

import java.util.Scanner;
import sims.career.*;       // the * is to include all classes under sims.[package name]
import sims.entity.*;       // I foresee that we will have many classes under one sub-package so this is easier
import sims.food.*;
//import sims.gameObject.*;  // fyi I commented both gameObject and world imports because java does not compile if there are no classes inside
import sims.gameEngine.*;
import sims.needs.*;
import sims.world.*;
import sims.actions.*;

public class Gameplay {
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);  // instantiate the scanner class
        SimProfile currentSim = null;
        boolean gameplay = true;  // gameplay loop will always be true so it runs infinitely

        // this segment of the code is to settle everything before the player commences

        while(currentSim == null){
            System.out.println("Select a Sim: ");
            System.out.println("1. Darius");
            System.out.println("2. Timothy");
            String simchoice = scanner.nextLine();

            switch(simchoice){
                case "1":
                    currentSim = new Sim1();
                    break;
                case "2":
                    currentSim = new Sim2();
                    break;
                default:
                    System.out.println("Please enter a valid number: ");
            }
        }




        while(gameplay){

            // Gameplay starts here
            System.out.println("What would you want your Sim to do?");
            System.out.println("1. View Needs");
            System.out.println("2. Sleep");
            // choice
            // choice
            // choice
            // choice etc any actions yall want you can put here bah then update the switch case below accordingly

            String actionChoice = scanner.nextLine();
            SimAction action = null;

            // polymorphism is being applied here as 'action' is the common variable, but it does completely different things
            switch(actionChoice){
                case "1":
                    currentSim.showNeeds();
                    break;
                    // at runtime, the currentSim variable points to a Sim object which inherits all the parents field/methods
                    // showNeeds() is inherited from SimProfile
                case "2":
                    action = new Sleep(); // creates a new 'Sleep' object and stores inside the 'action' variable
                    break;
              //case

              //case

              //case ...
            }

            if (action != null){
                action.execute(currentSim);   // interface only runs when the 'action' variable has a value
            }


        }// end of while(gameplay) loop


        scanner.close();
    }// end of main() loop
}// end of class
