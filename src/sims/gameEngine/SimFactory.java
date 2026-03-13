package sims.gameEngine;

import sims.actions.Activity;
import sims.career.Career;
import sims.entity.Sim;
import sims.world.Home;
import sims.world.HomeLocation;
import sims.world.HomeUpgrade;

public class SimFactory {
    //simfactory to create home upgrades, home location, home and sim

    public static Home defaultHome(String name)
    {

        Home home = new Home(name + "'s Home");

        Activity eatSandwich = new Activity("Sandwich", 30, "Hunger", 30);
        Activity eatPizza = new Activity("Pizza", 10, "Hunger", 40);
        Activity drinkCoffee = new Activity("Coffee", 10, "Energy", 20);
        Activity eatCake = new Activity("Pizza", 20, "Hunger", 50);
        Activity watchTv = new Activity("Watch TV", 30, "Fun", 40);
        Activity sleep = new Activity("Sleep",  100, "Energy", 80);
        Activity nap = new Activity("Nap", 30, "Energy", 40);

        HomeLocation livingRoom = new HomeLocation("Living Room", home);
        HomeLocation bedroom = new HomeLocation("Bedroom", home);
        HomeLocation kitchen = new HomeLocation("Kitchen", home);

        HomeUpgrade oven = new HomeUpgrade("Kitchen", "Oven", 150, eatPizza);
        HomeUpgrade coffeeMachine = new HomeUpgrade("Kitchen", "Coffee Machine", 200, drinkCoffee);
        HomeUpgrade sofa = new HomeUpgrade("Bedroom", "Sofa", 300, nap);

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
        return home;
    }

    public static Sim createSim (String name, int simListSize, int age, Home home, Career career)
    {
        Sim sim = new Sim(name, simListSize, age);
        sim.setHome(home);
        sim.setCurrentLocation(home.getHomeLocation().getFirst());
        sim.setCareer(career);
        return sim;
    }
}
