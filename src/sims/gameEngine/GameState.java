package sims.gameEngine;

import sims.actions.Activity;
import sims.career.Career;
import sims.entity.Sim;
import sims.needs.need;
import sims.world.*;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class GameState {

    private int gameState = 0;
    private boolean gameRunning = true;
    private int year = 2026;
    private int month = 1;
    private int days = 0;
    private int hours = 0;
    private int minutes = 0;

    public void startGame()
    {
        addSim(SimFactory.defaultGame());
        addLocation(new OutsideLocation("Park", "none", "none"));
        addLocation(new OutsideLocation("Gym", "none", "none"));
        addLocation(new OutsideLocation("IT Office", "Title", "Developer"));
        addLocation(new OutsideLocation("Server Room", "Sector", "IT"));
        addLocation(new OutsideLocation("Studio Room", "Sector", "Media"));
    }

    private Sim currentSim;
    private List<Sim> simList = new ArrayList<>();
    private List<OutsideLocation> outsideLocationList = new ArrayList<>();

    private Scanner scanner = new Scanner(System.in);
    public int getGameState() {
        return gameState;
    }

    public void addSim(Sim sim)
    {
        simList.add(sim);
    }
    public void addLocation(OutsideLocation location)
    {
        outsideLocationList.add(location);
    }
    public boolean getGameRunning()
    {
        return gameRunning;
    }
    public void setGameState(int state)
    {
        gameState = state;
    }

    public void update() {
        switch(gameState)
        {
            case 0:
                showMainMenu();
                break;
            case 1:
                showChooseSimMenu();
                break;
            case 2:
                showCreateMenu();
                break;
            case 3:
                showActionMenu();
                break;
            case 4:
                showMoveMenu();
                break;
            case 5:
                endGame();
                break;
        }
    }

    public void showMainMenu()
    {
        System.out.println("This is the main menu");
        System.out.println("-------------Welcome to SIMS--------------");
        System.out.println("[1] Select Character");
        System.out.println("[2] Create Character");
        System.out.println("[3] End Game");
        int choice = readInt("Please input option : ", 3);
        switch(choice) {
            case 1:
                gameState = 1;
                break;
            case 2:
                gameState = 2;
                break;
            case 3:
                gameState = 5;
                break;
        }
    }

    public void showChooseSimMenu()
    {
        System.out.println("-------------Character Select--------------");
        System.out.println("Please choose Character to play below.");

        for(int i = 0; i < simList.size(); i++)
        {
            Sim sim = simList.get(i);
            System.out.print("\n[" + (i+1)+ "] " + sim.getName());
        }
        int choice = readInt("\nInput : ", simList.size());
        currentSim = simList.get(choice-1);
        gameState = 3;
        update();
    }

    public void showCreateMenu() {
        System.out.println("-------------SIM Creation--------------");
        String name = readString("Please enter name of Sim: ");
        int age = readInt("Please enter age of Sim: ");
        System.out.println("[1] Sim does not have a job");
        System.out.println("[2] Sim has a job");
        int choice = readInt("Please enter choice accordingly: ");
        Career career = new Career();
        if(choice == 2)
        {
            career.setTitle(readString("Please enter job title: "));
            career.setSector(readString("Please enter job sector: "));
            career.setSalary(readInt("Please enter job salary: "));
        }

        System.out.println("Your Sim is being created!");
        Home newHome = SimFactory.defaultHome(name);
        Sim newSim = SimFactory.createSim(name, simList.size(), age, newHome, career);
        simList.add(newSim);
        currentSim = newSim;
        gameState = 3;
    }

    public void showActionMenu() {
        if (currentSim.getLocation() instanceof HomeLocation) {
            System.out.println("\nYour current location is " + ((HomeLocation) currentSim.getLocation()).getHome().getName() + " : " + currentSim.getLocation().getName());
        } else {
            System.out.println("\nYour current location is " + currentSim.getLocation().getName());
        }

        showTime();
        showStats(currentSim);

        System.out.println("\n-------------Please choose action for SIM--------------");
        System.out.println("\n[1] Move Location");
        List<Activity> activityList = new ArrayList<>(currentSim.getLocation().getActivity());
        List<HomeUpgrade> upgradeOption = new ArrayList<>();
        if (currentSim.getLocation() instanceof HomeLocation) {
            List<HomeUpgrade> upgradeList = ((HomeLocation) currentSim.getLocation()).getUpgradeList();
            for (HomeUpgrade upgrade : upgradeList) {
                if (upgrade.getUpgrade()) {
                    activityList.add(upgrade.getActivity());
                } else {
                    upgradeOption.add(upgrade);
                }
            }
        }
        for (int i = 0; i < activityList.size(); i++) {
            System.out.print("[" + (i + 2) + "] " + activityList.get(i).getName() + " - " + activityList.get(i).getImpactedNeed() + " + " + activityList.get(i).getValue() + "\n");
        }
        int count = activityList.size() + 2;
        if (!upgradeOption.isEmpty()) {
            System.out.println("\nPurchase Upgrades to unlock activities!");
            for (HomeUpgrade option : upgradeOption) {
                System.out.println("[" + (count) + "] " + "Purchase " + option.getName() + " ( $" + option.getPrice() + " ) " + " to unlock \n" + option.getActivity().getName() + " : " + option.getActivity().getImpactedNeed() + " + " + option.getActivity().getValue() + "\n");
                count += 1;
            }
        }

        System.out.println("[" + (activityList.size()+upgradeOption.size()+2) + "] Exit to main menu");
        int choice = readInt("Input : ", activityList.size() + upgradeOption.size() + 2);

        if(choice == 1)
        {
            gameState = 4;
        }
        else if(choice <= activityList.size() + 1)
        {
            //execute activity list action
            Activity selectedActivity = activityList.get(choice - 2);
            selectedActivity.performActivity(currentSim);

            decayLoop(selectedActivity.getDuration(), selectedActivity.getImpactedNeed());
            for(Sim sim : simList)
            {
                /*
                sim.performDecay(selectedActivity.getDuration(), selectedActivity.getImpactedNeed(), currentSim);
            */
            }

            decayLoop(selectedActivity.getDuration(), selectedActivity.getImpactedNeed());

        }
        else if(choice <= activityList.size() + upgradeOption.size() + 1)
        {
            //execute purchasing of upgrade
            HomeUpgrade selectedUpgrade = upgradeOption.get(choice - activityList.size()-2);
            selectedUpgrade.purchaseUpgrade();
        }
        else
        {
            gameState = 0;
        }
    }

    public void showMoveMenu()
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
        for (OutsideLocation loc : outsideLocationList) {
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

        int choice = readInt("Input : ", menuList.size());

        Loc destination = menuList.get(choice -1);
        destination.moveTo(currentSim);
        gameState = 3;
        update();
    }


    public int readInt(String prompt)
    {
        System.out.println(prompt);
        while (true) {
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input!");
                scanner.nextLine();
                System.out.println(prompt);
                continue;
            }
            int value = scanner.nextInt();
            scanner.nextLine();
            if (value > 0) {
                return value;
            } else {
                System.out.println("Input must be non negative!");
                System.out.println(prompt);
            }
        }
    }

    public int readInt(String prompt, int options)
    {
        System.out.println(prompt);
        while (true) {
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input!");
                scanner.nextLine();
                System.out.println(prompt);
                continue;
            }
            int value = scanner.nextInt();
            scanner.nextLine();

            if (value <= options && value > 0) {
                return value;
            } else {
                System.out.println("Input must be 1 - " + options);
                System.out.println(prompt);
            }
        }
    }

    public String readString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    public void showStats(Sim sim)
    {
        System.out.println(sim.getName() + "'s Stats");
        for (Map.Entry<String, need> entry : sim.getNeeds().entrySet()) {
            System.out.println(entry.getKey() + " : " + (entry.getValue().getValue()) + "/100");
        }
    }

    public void showTime()
    {
        String test = String.format("%02d:%02d", hours,minutes);
        System.out.println("Current Time: " + test);
    }


    public void decayLoop (int duration, String need)
    {
        for(int i = 0; i < duration; i ++)
        {
            for(Sim sim : simList)
            {
                updateTime();
                sim.performDecay(need, currentSim, getIntTime());
            }
        }
    }

    public int getIntTime()
    {
            return (days * 24) + (hours * 24) + (minutes);
    }

    public void updateTime()
    {

        // Add minutes
        minutes ++;
        // Carry over to hours
        hours += minutes / 60;
        minutes = minutes % 60;

        // Carry over to days
        days += hours / 24;
        hours = hours % 24;

        // Carry over to months (assuming 31 days per month for simplicity)
        month += days / 31;
        days = days % 31;

        // Carry over to years
        year += month / 12;
        month = (month % 12 == 0) ? 12 : month % 12;
    }

    public void endGame()
    {
        gameRunning = false;
    }
}
