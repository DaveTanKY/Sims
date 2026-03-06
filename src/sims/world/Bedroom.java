package sims.world;

import java.util.Scanner;
import sims.entity.*;
import sims.actions.*;

public class Bedroom extends Location{
    public Bedroom(){
        super("Bedroom");
    }

    @Override
    public void showOptions(SimProfile currentSim){
        Scanner scanner = new Scanner(System.in);
        boolean inRoom = true;

        while(inRoom) {
            System.out.println(" ");
            System.out.println(" ");
            System.out.println("What would you like to do?");
            System.out.println("1. View Needs");
            System.out.println("2. Go to sleep");
            System.out.println("3. Go to the kitchen");
            System.out.println("4. Go to the living room");
            System.out.println("5. Go to the washroom");
            System.out.println("6. Go to work");

            String choice  = scanner.nextLine();

            switch(choice){
                case "1": // show needs

                    currentSim.showNeeds();
                    break;

                case "2": // create a new Sleep action object from the SimAction parent class and execute the action as per action subclass

                    new Sleep().execute(currentSim);
                    break;

                case "3": // go to the kitchen

                    currentSim.moveTo(new Kitchen());
                    currentSim.getRoom().showOptions(currentSim);
                    inRoom = false; // break out of the current room's loop
                    break;

                case "4": // go to the living room

                    currentSim.moveTo(new LivingRoom());
                    currentSim.getRoom().showOptions(currentSim);
                    inRoom = false;
                    break;

                case "5": // go to the washroom

                    currentSim.moveTo(new Washroom());
                    currentSim.getRoom().showOptions(currentSim);
                    inRoom = false;
                    break;

                case "6": // go to work
                    //
                default:
                    System.out.println("You can't do that!");
            }
        }
    }
}
