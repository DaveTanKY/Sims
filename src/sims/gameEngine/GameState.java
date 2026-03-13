package sims.gameEngine;

import sims.actions.Activity;
import sims.entity.Sim;
import sims.world.HomeLocation;
import sims.world.HomeUpgrade;
import sims.world.Loc;
import sims.world.OutsideLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class GameState {

    private int gameState = 0;
    private boolean gameRunning = true;

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
            case 1:
                showChooseSimMenu();
            case 2:
                showCreateMenu();
            case 3:
                showActionMenu();
            case 4:
                showMoveMenu();
            case 5:
                endGame();
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
        switch(choice){
            case 1:
                gameState = 1;
            case 2:
                gameState = 2;
            case 3:
                gameState = 5;
        }
        update();
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
        System.out.println("Ths is the create menu");
    }

    public void showActionMenu() {
        if (currentSim.getLocation() instanceof HomeLocation) {
            System.out.println("\nYour current location is " + ((HomeLocation) currentSim.getLocation()).getHome().getName() + " : " + currentSim.getLocation().getName());
        } else {
            System.out.println("\nYour current location is " + currentSim.getLocation().getName());
        }

        System.out.println("\n-------------Please choose action for SIM--------------");
        System.out.println("\n[1] Move Location");

        List<Activity> activityList = new ArrayList<>();
        List<HomeUpgrade> upgradeOption = new ArrayList<>();
        activityList = currentSim.getLocation().getActivity();
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
        if (activityList != null) {
            for (int i = 0; i < activityList.size(); i++) {
                System.out.print("[" + (i + 2) + "] " + activityList.get(i).getName() + " - " + activityList.get(i).getImpactedNeed() + " + " + activityList.get(i).getValue());
            }
        }
        int count = activityList.size() + 2;
        if (upgradeOption.size() > 0) {
            System.out.println("\n\nPurchase Upgrades to unlock activities!");
            for (HomeUpgrade option : upgradeOption) {
                System.out.println("[" + (count) + "] " + "Purchase " + option.getName() + " ( $" + option.getPrice() + " ) " + " to unlock \n" + option.getActivity().getName() + " : " + option.getActivity().getImpactedNeed() + " + " + option.getActivity().getValue() + "\n");
                count += 1;
            }
        }
        int choice = readInt("Input : ", activityList.size() + upgradeOption.size() + 1);

        if(choice == 1)
        {
            gameState = 4;
        }
        else if(choice <= activityList.size() + 1)
        {
            //execute activity list action
            Activity selectedActivity = activityList.get(choice - 2);
        }
        else
        {
            //execute purchasing of upgrade
            HomeUpgrade selectedUpgrade = upgradeOption.get(choice - activityList.size()-2);
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

    public void endGame()
    {
        gameRunning = false;
    }
}
