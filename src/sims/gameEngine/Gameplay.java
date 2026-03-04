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

        // Instantiate the locations here to be used in gameplay loop
        // This avoids creation of new location every location change
        Location kitchen = new Kitchen();
        Location bedroom = new Bedroom();
        Location livingRoom = new LivingRoom();
        Location washroom = new Washroom();

        // current location is null at the start of the game because the Sim has not been assigned a location yet
        Location currentLocation = null;

        while(gameplay){

            // Gameplay starts here
            System.out.println("-- Navigation Menu --");
            System.out.println("1. Go to the kitchen");
            System.out.println("2. Go to the bedroom");
            System.out.println("3. Go to the living room");
            System.out.println("4. Go to the washroom");
            System.out.println("5. Go to work");
            System.out.println("x. Exit game\n");

            System.out.println("Where do you want " + currentSim.getName() + " to go?");
            String locationChoice = scanner.nextLine();

            switch(locationChoice){
                case "1":
                    currentLocation = kitchen;
                    break;
                case "2":
                    currentLocation = bedroom;
                    break;
                case "3":
                    currentLocation = livingRoom;
                    break;
                case "4":
                    currentLocation = washroom;
                    break;
                case "5":
                    // case for work location
                    break;
                case "x":
                    gameplay = false;  // this will break the main gameplay loop and end the game
                    System.out.println("Thanks for playing!");
                    break;
            }

            // Checks if player has chosen to exit the game
            if (!gameplay) {
                break;  // this will break the main gameplay loop and end the game
            }
            
            // This loop will run as long as the player is in the location, it will break when the player chooses to return to the navigation menu
            boolean inLocation = true;
            while (inLocation) {
                System.out.println("\n-- " + currentLocation.getLocationName() + " Actions --");
                // Show location options
                currentLocation.showOptions();
                System.out.println("What do you want " + currentSim.getName() + " to do?");

                // Location actions are handled in their respective classes, this is to avoid a long switch case in the gameplay class.
                inLocation = currentLocation.handleLocActions(currentSim, scanner);
                
                // Checking the needs decay - this does it after every action
                //currentSim.getNeeds().decay();
                
            }
            
        }// end of while(gameplay) loop
        scanner.close();
    }// end of main() loop
}// end of class
