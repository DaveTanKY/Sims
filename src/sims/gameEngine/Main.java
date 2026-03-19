package sims.gameEngine;

import sims.actions.Activity;
import sims.career.Career;
import sims.entity.Sim;
import sims.needs.need;
import sims.world.Home;
import sims.world.HomeLocation;
import sims.world.HomeUpgrade;
import sims.world.OutsideLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args)
    {
        GameState game = new GameState();

        game.addLocation(new OutsideLocation("Park", "none", "none"));
        game.addLocation(new OutsideLocation("Gym", "none", "none"));
        game.addLocation(new OutsideLocation("IT Office", "Title", "Developer"));
        game.addLocation(new OutsideLocation("Server Room", "Sector", "IT"));
        game.addLocation(new OutsideLocation("Studio Room", "Sector", "Media"));


        //creating home location activities
        Activity eatSandwich = new Activity("Sandwich", 30, "Hunger", 30);
        Activity eatPizza = new Activity("Pizza", 10, "Hunger", 40);
        Activity drinkCoffee = new Activity("Coffee", 10, "Energy", 20);
        Activity eatCake = new Activity("Cake", 20, "Hunger", 50);
        Activity watchTv = new Activity("Watch TV", 30, "Fun", 40);
        Activity sleep = new Activity("Sleep",  100, "Energy", 80);
        Activity nap = new Activity("Nap", 30, "Energy", 40);




        //Creating careers
        Career it = new Career("Developer", "IT", 2000);
        Career music = new Career("Producer", "Media", 1500);



        //creating Sim
        Sim david = new Sim("David", 2, 23);
        Sim dave = new Sim("Dave", 1, 25);

        //Creating home
        Home home = new Home("Dave's Home");
        Home home2 = new Home("David's Home");


        //creating Home locations
        HomeLocation livingRoom = new HomeLocation("Living Room", home);
        HomeLocation bedroom = new HomeLocation("Bedroom", home);
        HomeLocation kitchen = new HomeLocation("Kitchen", home);

        HomeLocation livingRoom2 = new HomeLocation("Living Room", home2);
        HomeLocation bedroom2 = new HomeLocation("Bedroom", home2);
        HomeLocation kitchen2 = new HomeLocation("Kitchen", home2);

        //creating homeupgrades
        HomeUpgrade oven = new HomeUpgrade("Kitchen", "Oven", 150, eatPizza);
        HomeUpgrade coffeeMachine = new HomeUpgrade("Kitchen", "Coffee Machine", 200, drinkCoffee);
        HomeUpgrade sofa = new HomeUpgrade("Bedroom", "Sofa", 300, nap);

        //adding activity to homelocation
        livingRoom.addActivity(watchTv);
        kitchen.addActivity(eatSandwich);
        bedroom.addActivity(sleep);

        livingRoom2.addActivity(watchTv);
        kitchen2.addActivity(eatSandwich);
        bedroom2.addActivity(sleep);



        //adding upgrades to home
        bedroom.addUpgrade(sofa);
        kitchen.addUpgrade(coffeeMachine);
        kitchen.addUpgrade(oven);

        bedroom2.addUpgrade(sofa);
        kitchen2.addUpgrade(coffeeMachine);



        //adding homelocation to home
        home.addHomeLocation(livingRoom);
        home.addHomeLocation(bedroom);
        home.addHomeLocation(kitchen);


        home2.addHomeLocation(livingRoom2);
        home2.addHomeLocation(bedroom2);
        home2.addHomeLocation(kitchen2);





        //setting sim settings
        dave.setHome(home);
        dave.setCurrentLocation(dave.getHome().getHomeLocation().getFirst());
        dave.setCareer(it);

        david.setHome(home2);
        david.setCurrentLocation(david.getHome().getHomeLocation().getFirst());
        david.setCareer(music);


        HashMap<String, need> needDict = new HashMap<>();
        needDict.put("Energy", new need());
        needDict.put("Social", new need());
        needDict.put("Hygiene", new need());
        needDict.put("Hunger", new need());
        needDict.put("Fun", new need());
        needDict.put("Bladder", new need());
        dave.setNeeds(needDict);


        //adding sim to list
        game.addSim(dave);
        game.addSim(david);

        while(game.getGameRunning())
        {
            game.update();
        }
    }
}
