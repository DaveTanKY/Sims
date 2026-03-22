package sims.gameEngine;

import sims.actions.Activity;
import sims.career.Career;
import sims.entity.Sim;
import sims.needs.need;
import sims.world.Home;
import sims.world.HomeLocation;
import sims.world.HomeUpgrade;

import java.util.HashMap;
import java.util.Map;

public class SimFactory {
    //simfactory to create home upgrades, home location, home and sim

    public static Sim defaultGame()
    {
        Career career = new Career();
        career.setTitle("Developer");
        career.setSector("IT");
        career.setSalary(5000);
        Home newHome = defaultHome("Dave");
        return createSim("Dave", 0, 24, newHome,career);
    }

    public static Home defaultHome(String name)
    {
        Home home = new Home(name + "'s Home");

        //Hunger
        Activity eatSandwich = new Activity("Sandwich", 10, "Hunger", 30);
        //Upgrades
        Activity eatPizza = new Activity("Pizza", 10, "Hunger", 40);


        //Fun
        Activity watchTv = new Activity("Watch TV", 20, "Fun", 40);
        //Upgrades
        Activity cardGames = new Activity("Play Card Games", 15, "Fun", 20);

        //Energy
        Activity sleep = new Activity("Sleep",  100, "Energy", 100);
        //Upgrades
        Activity nap = new Activity("Nap", 30, "Energy", 40);
        Activity drinkCoffee = new Activity("Coffee", 10, "Energy", 20);

        //Social
        Activity socialMedia = new Activity("Scroll Social Media", 15, "Social", 30);

        //Hygiene
        Activity shower = new Activity("Shower", 20, "Hygiene", 80);

        //Bladder
        Activity useToilet = new Activity("Use Toilet", 10, "Bladder", 100);

        HomeLocation bathroom = new HomeLocation("Bathroom", home);
        HomeLocation livingRoom = new HomeLocation("Living Room", home);
        HomeLocation bedroom = new HomeLocation("Bedroom", home);
        HomeLocation kitchen = new HomeLocation("Kitchen", home);

        HomeUpgrade oven = new HomeUpgrade("Kitchen", "Oven", 150, eatPizza);
        HomeUpgrade coffeeMachine = new HomeUpgrade("Kitchen", "Coffee Machine", 200, drinkCoffee);
        HomeUpgrade sofa = new HomeUpgrade("Bedroom", "Sofa", 300, nap);
        HomeUpgrade cards = new HomeUpgrade("Living Room", "Cards", 50, cardGames);

        livingRoom.addActivity(watchTv);
        livingRoom.addActivity(socialMedia);

        kitchen.addActivity(eatSandwich);

        bedroom.addActivity(sleep);

        bathroom.addActivity(shower);
        bathroom.addActivity(useToilet);

        bedroom.addUpgrade(sofa);
        kitchen.addUpgrade(coffeeMachine);
        kitchen.addUpgrade(oven);
        livingRoom.addUpgrade(cards);


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
        home.addHomeLocation(bathroom);
        return home;
    }

    public static Sim createSim (String name, int simListSize, int age, Home home, Career career)
    {
        Sim sim = new Sim(name, simListSize, age);
        sim.setNeeds(createNeeds());
        sim.setHome(home);
        sim.setCurrentLocation(home.getHomeLocation().getFirst());
        sim.setCareer(career);
        return sim;
    }

    public static Map<String, need> createNeeds ()
    {
        HashMap<String, need> needDict = new HashMap<>();
        needDict.put("Hunger", new need(0.2));
        needDict.put("Hygiene", new need(0.01));
        needDict.put("Energy", new need(0.3));
        needDict.put("Bladder", new need(0.02));
        needDict.put("Fun", new need(0.005));
        needDict.put("Social", new need(0.003));
        return needDict;
    }
}
