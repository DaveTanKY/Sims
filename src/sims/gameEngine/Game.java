package sims.gameEngine;

import sims.actions.Activity;
import sims.career.Career;
import sims.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    private static List<OutsideLocation> locationList = new ArrayList<>();
    private static Sim currentSim;

    public static void addLocation(OutsideLocation location)
    {
        locationList.add(location);
    }

    public static void main(String[] args) {


        //creating outside location and adding to list
        addLocation(new OutsideLocation("Park", "none", "none"));
        addLocation(new OutsideLocation("Gym", "none", "none"));
        addLocation(new OutsideLocation("IT Office", "Title", "Developer"));
        addLocation(new OutsideLocation("Server Room", "Sector", "IT"));
        addLocation(new OutsideLocation("Studio Room", "Sector", "Media"));


        //creating home location activities
        Activity eatSandwich = new Activity("Sandwich", 30, "Hunger", 30);
        Activity eatPizza = new Activity("Pizza", 10, "Hunger", 40);
        Activity drinkCoffee = new Activity("Coffee", 10, "Energy", 20);
        Activity eatCake = new Activity("Pizza", 20, "Hunger", 50);
        Activity watchTv = new Activity("Watch TV", 30, "Fun", 40);
        Activity sleep = new Activity("Sleep",  100, "Energy", 80);
        Activity nap = new Activity("Nap", 30, "Energy", 40);





        List<Sim> simList = new ArrayList<>();



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

        //creating homeupgrades
        HomeUpgrade oven = new HomeUpgrade("Kitchen", "Oven", 150, eatPizza);
        HomeUpgrade coffeeMachine = new HomeUpgrade("Kitchen", "Coffee Machine", 200, drinkCoffee);
        HomeUpgrade sofa = new HomeUpgrade("Bedroom", "Sofa", 300, nap);

        //adding activity to homelocation
        livingRoom.addActivity(watchTv);
        kitchen.addActivity(eatSandwich);
        bedroom.addActivity(sleep);


        //adding upgrades to home
        bedroom.addUpgrade(sofa);
        kitchen.addUpgrade(coffeeMachine);
        kitchen.addUpgrade(oven);



        //adding homelocation to home
        home.addHomeLocation(livingRoom);
        home.addHomeLocation(bedroom);
        home.addHomeLocation(kitchen);


        home2.addHomeLocation(livingRoom);
        home2.addHomeLocation(bedroom);
        home2.addHomeLocation(kitchen);





        //setting sim settings
        dave.setHome(home);
        dave.setCurrentLocation(dave.getHome().getHomeLocation().getFirst());
        dave.setCareer(it);

        david.setHome(home2);
        david.setCurrentLocation(david.getHome().getHomeLocation().getFirst());
        david.setCareer(music);








        //adding sim to list
        simList.add(dave);
        simList.add(david);

        for(Sim sim : simList)
        {
            Home home3 = new Home(sim.getName() + "'s" + " Home");
            home3.addHomeLocation(livingRoom);
            home3.addHomeLocation(kitchen);
            home3.addHomeLocation(bedroom);
            sim.setHome(home3);
            sim.setCurrentLocation(home3.getHomeLocation().getFirst());
        }

        Boolean gameRunning = true;


        Scanner scanner = new Scanner(System.in);

        int currentState = 0;
        while(gameRunning)
        {
            //GameStates 0 = main menu, 1 = create character
            if(currentState == 0)
            {
                System.out.println("-------------Welcome to SIMS--------------");
                System.out.println("[1] Select Character");
                System.out.println("[2] Create Character");

                if (scanner.hasNextInt()) {
                    int choice = scanner.nextInt();
                    if(choice == 1)
                    {
                        currentState = 1;
                    } else if (choice == 2) {
                        currentState = 2;
                    }

                }

            }


            if(currentState == 1)
            {
                System.out.println("-------------Character Select--------------");
                System.out.println("Please choose Character to play below.");

                for(int i = 0; i < simList.size(); i++)
                {
                    Sim sim = simList.get(i);
                    System.out.print("\n[" + (i+1)+ "] " + sim.getName());
                }
                System.out.println("\n\nInput : ");

                if (scanner.hasNextInt()) {
                    int choice = scanner.nextInt();
                    if(choice <= simList.size() && choice > 0)
                    {
                        currentSim = simList.get(choice-1);
                        currentState = 3;
                    }
                }
            }

            if(currentState == 2)
            {
                System.out.println("-------------Character Creation--------------");
                System.out.println("Please enter Sim name : ");
                scanner.nextLine();
                String name = scanner.nextLine();

                System.out.println("Please enter Sim age");
                int age = 0;
                Sim newSim = new Sim(name,simList.size(), age);
                Home testhome = new Home(newSim.getName() + "'s Home");

                Activity eatBread = new Activity("Bread", 30, "Hunger", 30);
                Activity bakeCake = new Activity("Cake", 30, "Hunger", 50);
                HomeUpgrade testupgrade = new HomeUpgrade("Kitchen","Bakery oven", 300, bakeCake);

                String kitchenLocationtest = scanner.nextLine();
                HomeLocation testHomeloc = new HomeLocation(kitchenLocationtest,testhome);
                testHomeloc.addActivity(eatBread);
                testHomeloc.addUpgrade(testupgrade);
                testhome.addHomeLocation(testHomeloc);

                newSim.setHome(testhome);
                newSim.setCurrentLocation(newSim.getHome().getHomeLocation().getFirst());
                simList.add(newSim);
                currentState = 0;



                //Use sim factory to create new instance of sim
                //Objects that need to be passed in to sim factory
                //Sim
                //name, uuid, age
                //career
                //title, sector, salary
                //home
                //homename(default to simname's home )
                //homelocation
                //homelocation list, homelocation activities, home location upgrades
                //
            }

            if(currentState == 3)
            {
                if(currentSim.getLocation() instanceof HomeLocation)
                {
                    System.out.println("\nYour current location is " + ((HomeLocation) currentSim.getLocation()).getHome().getName() + " : " + currentSim.getLocation().getName());
                }
                else
                {
                    System.out.println("\nYour current location is " + currentSim.getLocation().getName());
                }

                System.out.println("\n-------------Please choose action for SIM--------------");
                System.out.println("\n[1] Move Location");

                List<Activity> activityList = new ArrayList<>();
                List<HomeUpgrade> upgradeOption = new ArrayList<>();
                activityList = currentSim.getLocation().getActivity();

                if(currentSim.getLocation() instanceof HomeLocation)
                {
                    List<HomeUpgrade> upgradeList = ((HomeLocation) currentSim.getLocation()).getUpgradeList();

                    for(HomeUpgrade upgrade : upgradeList)
                    {
                        if (upgrade.getUpgrade())
                        {
                            activityList.add(upgrade.getActivity());
                        }
                        else
                        {
                            upgradeOption.add(upgrade);
                        }
                    }
                }
                if(activityList != null) {
                    for (int i = 0; i < activityList.size(); i++) {
                        System.out.print("[" + (i + 2) + "] " + activityList.get(i).getName() + " - " + activityList.get(i).getImpactedNeed() + " + " + activityList.get(i).getValue());
                    }
                }
                int count = activityList.size() + 2;
                System.out.println("\n\nPurchase Upgrades to unlock activities!");
                for(HomeUpgrade option : upgradeOption)
                {
                    System.out.println("[" + (count) + "] " + "Purchase " + option.getName()+ " ( $" + option.getPrice() + " ) " + " to unlock \n" + option.getActivity().getName() + " : " + option.getActivity().getImpactedNeed() + " + " + option.getActivity().getValue() + "\n");
                    count += 1;
                }


                if (scanner.hasNextInt()) {
                    int choice = scanner.nextInt();
                    if(choice < currentSim.getLocation().getActivity().size()+2 && choice > 0)
                    {
                        if (choice == 1)
                        {
                            currentState = 4;
                        }
                    }
                }
            }
            if(currentState == 4)
            {
                List<Loc> menuList = new ArrayList<>();
                System.out.println("\n--------------Please choose location to move to--------------");
                if (currentSim.getLocation() instanceof HomeLocation)
                {
                    for (int i = 0; i < ((HomeLocation) currentSim.getLocation()).getHome().getHomeLocation().size(); i++) {
                        if (((HomeLocation) currentSim.getLocation()).getHome().getHomeLocation().get(i) != currentSim.getLocation()) {
                            menuList.add(((HomeLocation) currentSim.getLocation()).getHome().getHomeLocation().get(i));
                        }
                    }
                }
                else
                {
                    menuList.add(currentSim.getHome());
                }

                for (OutsideLocation loc : locationList) {
                    if (loc != currentSim.getLocation()) {
                        if (loc.getComparison() == "none") {
                            menuList.add(loc);
                        } else if (loc.getComparison() == "Title") {
                            if (loc.getRequirement() == currentSim.getCareer().getTitle()) {
                                menuList.add(loc);
                            }
                        } else if (loc.getComparison() == "Sector") {
                            if (loc.getRequirement() == currentSim.getCareer().getSector()) {
                                menuList.add(loc);
                            }
                        }
                    }
                }
                for (int i = 0; i < menuList.size(); i++) {
                    System.out.println("[" + (i + 1) + "] " + menuList.get(i).getName());
                }
                if (scanner.hasNextInt()) {
                    int choice = scanner.nextInt();
                    if(choice <= menuList.size() && choice > 0)
                    {
                        Loc destination = menuList.get(choice - 1);
                        destination.moveTo(currentSim);
                        currentState = 3;
                    }
                }

            }
        }
    }
}
