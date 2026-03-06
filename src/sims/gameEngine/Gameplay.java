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
    public static boolean gameplay = true;

    // Instantiate all the locations here to be used in gameplay loop
    // This avoids creation of a new location object at every location change
    public static Location kitchen = new Kitchen();
    public static Location bedroom = new Bedroom();
    public static Location livingroom = new LivingRoom();
    public static Location washroom = new Washroom();

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);  // instantiate the scanner class
        SimProfile currentSim = null;
        SimAction action = null;



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


        // current location is null at the start of the game because the Sim has not been assigned a location yet
        Location currentLocation = null;



        // everything above this line is to settle everything before the player commences


        while(gameplay){

            // Gameplay starts here
            System.out.println("-- Welcome to the Sims 4 CLI knockoff! --");
            System.out.println("1. View Needs");
            System.out.println("2. Go to the kitchen");
            System.out.println("3. Go to the bedroom");
            System.out.println("4. Go to the living room");
            System.out.println("5. Go to the washroom");
            System.out.println("6. Go to work");
            System.out.println("x. Terminate the game");
            System.out.println("Where do you want " + currentSim.getName() + " to go?");

            String locationChoice = scanner.nextLine();

            // Gameplay starts here

            switch(locationChoice){
                case "1":

                    currentSim.showNeeds();
                    break;

                case "2":

                    currentLocation = kitchen;
                    currentSim.moveTo(kitchen);
                    break;

                case "3":

                    currentLocation = bedroom;
                    currentSim.moveTo(bedroom);
                    break;

                case "4":

                    currentLocation = livingroom;
                    currentSim.moveTo(livingroom);
                    break;

                case "5":

                    currentLocation = washroom;
                    currentSim.moveTo(washroom);
                    break;

                case "6":

                    // work is a WIP
                    break;

                case "x":

                    gameplay = false;
                    System.out.println("Sorry to see you go but thank you for playing!");
                    break;

                default:
                    System.out.println("You can't do that!");
            }


            // Checks if player has chosen to exit the game
            if (!gameplay) {
                break;  // this will break the main gameplay loop and end the game
            }

            if (currentLocation == null){ // when the user selects 1. View Needs initially, the game does not enter the location loop which prevents NullPointerException error
                continue;
            }

            // This loop will run as long as the player is in the location, it will break when the player chooses to return to the navigation menu
            boolean inLocation = true;
            while (inLocation) {
                System.out.println("\n-- " + currentLocation.getLocationName() + " Actions --");
                // Show location options
                currentLocation.showOptions();


                // Location actions are handled in their respective classes, this is to avoid a long switch case in the gameplay class.
                inLocation = currentLocation.handleLocActions(currentSim, scanner);

                // Checking the needs decay - this does it after every action
                //currentSim.getNeeds().decay();

            }

        }// end of while(gameplay) loop

        scanner.close();

    }// end of main() loop
}// end of class
