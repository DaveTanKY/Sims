package sims.gameEngine;

import sims.actions.Activity;
import sims.actions.SkillManager;
import sims.career.Career;
import sims.entity.Sim;
import sims.needs.need;
import sims.world.Home;
import sims.world.HomeLocation;
import sims.world.HomeUpgrade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimFactory {
    //simfactory to create home upgrades, home location, home and sim

    public static List<Sim> defaultGame()
    {
        List<Sim> simList = new ArrayList<>();

        Career career = new Career();
        career.setSector("IT");
        career.setSalary(5000);
        Home newHome = defaultHome("Dave");
        Sim dave = createSim("Dave", 0, 24, newHome, career);
        dave.setSkill(createSkills());
        dave.setNeeds(createNeeds());

        Career career1 = new Career();
        career1.setSector("IT");
        career1.setSalary(4000);
        Home newHome1 = defaultHome("Tim");
        Sim tim = createSim("Tim", 1, 23, newHome1, career1);
        tim.setSkill(createSkills());
        tim.setNeeds(createNeeds());

        Career career2 = new Career();
        career2.setSector("IT");
        career2.setSalary(4500);
        Home newHome2 = defaultHome("Darius");
        Sim darius = createSim("Darius", 2, 25, newHome2, career2);
        darius.setSkill(createSkills());
        darius.setNeeds(createNeeds());

        Career career3 = new Career();
        career3.setSector("Healthcare");
        career3.setSalary(4600);
        Home newHome3 = defaultHome("Rance");
        Sim rance = createSim("Rance", 3, 23, newHome3, career3);
        rance.setSkill(createSkills());
        rance.setNeeds(createNeeds());

        Career career4 = new Career();
        career4.setSector("Healthcare");
        career4.setSalary(4300);
        Home newHome4 = defaultHome("Reggie");
        Sim reggie = createSim("Reggie", 4, 25, newHome4, career4);
        reggie.setSkill(createSkills());
        reggie.setNeeds(createNeeds());

        Career career5 = new Career();
        career5.setSector("Finance");
        career5.setSalary(4200);
        Home newHome5 = defaultHome("Eliyaz");
        Sim eliyaz = createSim("Eliyaz", 5, 25, newHome5, career5);
        eliyaz.setSkill(createSkills());
        eliyaz.setNeeds(createNeeds());

        Career career6 = new Career();
        career6.setSector("Finance");
        career6.setSalary(4700);
        Home newHome6 = defaultHome("Thou Yong");
        Sim thouyong = createSim("Thou Yong", 1, 25, newHome6, career6);
        thouyong.setSkill(createSkills());
        thouyong.setNeeds(createNeeds());


        simList.add(dave);
        simList.add(tim);
        simList.add(darius);
        simList.add(rance);
        simList.add(reggie);
        simList.add(eliyaz);
        simList.add(thouyong);


        return simList;
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
        sim.setSkill(createSkills());
        return sim;
    }

    public static Map<String, SkillManager> createSkills()
    {
        HashMap<String, SkillManager> skillmap = new HashMap<>();
        skillmap.put("Hunger", new SkillManager());
        skillmap.put("Hygiene", new SkillManager());
        skillmap.put("Energy", new SkillManager());
        skillmap.put("Bladder", new SkillManager());
        skillmap.put("Fun", new SkillManager());
        skillmap.put("Social", new SkillManager());
        return skillmap;
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
