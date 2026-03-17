package sims.gameEngine;

import sims.actions.Activity;
import sims.career.Career;
import sims.entity.*;
import sims.needs.Needs;
import sims.world.Home;
import sims.world.HomeLocation;
import sims.world.HomeUpgrade;

public class SimFactory {
    //simfactory to create home upgrades, home location, home and sim

    public static Home defaultHome(String name){

        Home home = new Home(name + "'s Home");

        Activity eatSandwich = new Activity("Sandwich", 30, "Hunger", 30);
        Activity eatPizza = new Activity("Pizza", 10, "Hunger", 40);
        Activity drinkCoffee = new Activity("Coffee", 10, "Energy", 20);
        Activity eatCake = new Activity("Cake", 20, "Hunger", 50);
        Activity watchTv = new Activity("Watch TV", 30, "Fun", 40);
        Activity sleep = new Activity("Sleep",  100, "Energy", 80);
        Activity nap = new Activity("Nap", 30, "Energy", 40);

        HomeLocation livingRoom = new HomeLocation("Living Room", home);
        HomeLocation bedroom = new HomeLocation("Bedroom", home);
        HomeLocation kitchen = new HomeLocation("Kitchen", home);
        HomeLocation washroom = new HomeLocation("Washroom", home);

        //upgrades for the kitchen
        HomeUpgrade pizzaOven = new HomeUpgrade("Kitchen", "Pizza Oven", 250, eatPizza);
        HomeUpgrade coffeeMachine = new HomeUpgrade("Kitchen", "Coffee Machine", 200, drinkCoffee);
        HomeUpgrade oven = new HomeUpgrade("Kitchen", "Oven", 200, eatCake);

        //upgrades for the bedroom
        HomeUpgrade sofa = new HomeUpgrade("Bedroom", "Sofa", 400, nap);
        HomeUpgrade kingBed = new HomeUpgrade("Bedroom", "King Bed", 1300, sleep);

        //upgrades for the washroom

        //upgrades for the livingroom

        livingRoom.addActivity(watchTv);
        kitchen.addActivity(eatSandwich);
        bedroom.addActivity(sleep);



        bedroom.addUpgrade(sofa);
        kitchen.addUpgrade(coffeeMachine);
        kitchen.addUpgrade(oven);


        //create activities
        //create home locations
        //create home upgrades
        //add activities to upgrade
        //add activities to home location
        //add activities to upgrade
        //add upgrades to home location
        //add home location to home
        //add home to sim
        home.addHomeLocation(livingRoom);
        home.addHomeLocation(bedroom);
        home.addHomeLocation(kitchen);
        home.addHomeLocation(washroom);
        return home;
    }

    public static SimProfile createSim(String name, int simListSize, int age, Home home, Career career)
    {
        Needs needs = new Needs(50, 50, 50,50,50,50); // default needs created
        SimProfile profile = new SimProfile(name, career, needs, simListSize);
        profile.setHome(home);
        profile.setCurrentLocation(home.getHomeLocation().getFirst());
        return profile;
    }
}
