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
        SimAction action = null;
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

        System.out.println("What do you want to do first?");
        System.out.println("1. View Needs");
        System.out.println("2. Go to the kitchen");
        System.out.println("3. Go to the bedroom");
        System.out.println("4. Go to the living room");
        System.out.println("5. Go to the washroom");
        System.out.println("6. Go to work");


        while(gameplay){

            String locationChoice = scanner.nextLine();

            // Gameplay starts here

            switch(locationChoice){
                case "1":
                    currentSim.showNeeds();
                    break;
                case "2":
                    Location kitchen = new Kitchen();
                    currentSim.moveTo(kitchen);
                    currentSim.getRoom().showOptions();
                    break;
                case "3":
                    Location bedroom = new Bedroom();
                    currentSim.moveTo(bedroom);
                    currentSim.getRoom().showOptions();
                    break;
                case "4":
                    Location livingroom = new LivingRoom();
                    currentSim.moveTo(livingroom);
                    break;
                case "5":
                    Location washroom = new Washroom();
                    currentSim.moveTo(washroom);
                    break;
                case "6":
                    //
                    break;
                default:
                    System.out.println("You can't do that!");
            }





           // System.out.println("What would you want your Sim to do?");
            //System.out.println("1. View Needs");
            //System.out.println("2. Sleep");
            // choice
            // choice
            // choice
            // choice etc any actions yall want you can put here bah then update the switch case below accordingly

           // String actionChoice = scanner.nextLine();
           // SimAction action = null;

            // polymorphism is being applied here as 'action' is the common variable, but it does completely different things
            //switch(actionChoice){
            //    case "1":
             //       currentSim.showNeeds();
           //         break;
                  // at runtime, the currentSim variable points to a Sim object which inherits all the parents field/methods
                    // showNeeds() is inherited from SimProfile
             //   case "2":
                   // action = new Sleep(); // creates a new 'Sleep' object and stores inside the 'action' variable
              //      break;
              //case

              //case

              //case ...





            if (action != null){
                action.execute(currentSim);   // interface only runs when the 'action' variable has a value
            }


        }// end of while(gameplay) loop



    }// end of main() loop
}// end of class
