package sims.gameEngine;

import sims.actions.Activity;
import sims.career.Career;
import sims.entity.Relationship;
import sims.entity.Sim;
import sims.needs.need;
import sims.world.*;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.stream.Collectors;

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
        addLocation(new OutsideLocation("IT Office", "Title", "Developer"));
        addLocation(new OutsideLocation("Server Room", "Sector", "IT"));
        addLocation(new OutsideLocation("Studio Room", "Sector", "Media"));


        OutsideLocation gym = new OutsideLocation("Gym", "none", "none");
        gym.addActivity(new Activity("Workout", 30, "Fun", 40));

        OutsideLocation park = new OutsideLocation("Park", "none", "none");
        park.addActivity(new Activity("Play ball games", 90, "Fun", 60));
        park.addActivity(new Activity("Talk to friends", 60, "Social", 70));
        addLocation(park);

        OutsideLocation mall = new OutsideLocation("Mall", "none", "none");
        mall.addActivity(new Activity("Eat at Restaurant", 90, "Hunger", 100, 200));
        mall.addActivity(new Activity("Arcade", 30, "Fun", 40, 50));
        mall.addActivity(new Activity("Watch Movie", 120, "Fun", 70, 100));
        addLocation(mall);

        OutsideLocation work = new OutsideLocation("Office", "Title", "Developer");
        Activity workActivity = new Activity("Work", (8 * 60), "Salary", 0);

        work.addActivity(workActivity);
        addLocation(work);
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
                break;
            case 6:
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
        System.out.println("[3] Save Game");
        System.out.println("[4] End Game");
        int choice = readInt("Please input option : ", 4);
        switch(choice) {
            case 1:
                gameState = 1;
                break;
            case 2:
                gameState = 2;
                break;
            case 3:
                SaveGame.saveGame(currentSim, "test.txt");
                break;
            case 4:
                gameState = 6;
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
        // Display location info
        if (currentSim.getLocation() instanceof HomeLocation) {
            System.out.println("\nYour current location is " +
                    ((HomeLocation) currentSim.getLocation()).getHome().getName() +
                    " : " + currentSim.getLocation().getName());
        } else {
            System.out.println("\nYour current location is " + currentSim.getLocation().getName());
        }

        showTime();
        showStats(currentSim);

        // Sims in same location
        List<Sim> simLocList = currentSim.getLocation().getLocSimList()
                .stream()
                .filter(sim -> !sim.equals(currentSim))
                .collect(Collectors.toList());

        if (!simLocList.isEmpty()) {
            System.out.println("\n-------------Sims in the same location--------------");
            simLocList.forEach(sim -> System.out.println(sim.getName()));
        }

        // Build activity and upgrade lists
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

        // Print menu
        System.out.println("\n-------------Please choose action for SIM--------------");

        Map<Integer, Runnable> actions = new HashMap<>();
        int optionIndex = 1;
        System.out.println("\n[" + optionIndex + "] Move Location");
        actions.put(optionIndex++, () -> gameState = 4);


/*
        for (int i = 0; i < activityList.size(); i++) {
            Activity activity = activityList.get(i);
            if ("Salary".equals(activity.getImpactedNeed())) {
                System.out.println("[" + (i + 2) + "] " + activity.getName() +
                        " - " + activity.getImpactedNeed() + " : " +
                        getStringTime(activity.getDuration()) + " : $" +
                        (currentSim.getCareer().getSalary() + currentSim.getCareer().getBonus()) +
                        " + ( Career Bonus : " + currentSim.getCareer().getLevel() + "% )");
            } else {
                System.out.println("[" + (i + 2) + "] " + activity.getName() +
                        " - " + activity.getImpactedNeed() + " : " +
                        getStringTime(activity.getDuration()) + " - " +
                        activity.getValue() + " Stats");
            }
        }

 */

        for(Activity activity : activityList)
        {
            System.out.println("[" + optionIndex + "] " + activity.getName() + " - " + activity.getImpactedNeed());
            int idx = optionIndex++;
            actions.put(idx, ()-> {
                if(currentSim.getBank() >= activity.getCost()) {
                    activity.performActivity(currentSim);
                    decayLoop(activity.getDuration(), activity.getImpactedNeed());
                }
                else
                {
                    System.out.println("Insufficient Funds!");
                }
            });
        }


        if (!upgradeOption.isEmpty() && currentSim.getHome() == ((HomeLocation) currentSim.getLocation()).getHome()) {
            System.out.println("\n----------Purchase Upgrades to unlock activities!----------");
            for (HomeUpgrade option : upgradeOption) {
                System.out.println("[" + optionIndex + "] Purchase " + option.getName() +
                        " ( $" + option.getPrice() + " ) to unlock \n- " +
                        option.getActivity().getName() + " : " +
                        option.getActivity().getImpactedNeed() + " + " +
                        option.getActivity().getValue() + " Stats");
                optionIndex++;
            }
        }


        if(!simLocList.isEmpty())
        {
            System.out.println("[" + optionIndex + "] Interact with other Sims");
            actions.put(optionIndex, () -> {
                System.out.println("\nChoose a Sim to interact with:");
                for (int i = 0; i < simLocList.size(); i++) {
                    System.out.println("[" + (i + 1) + "] " + simLocList.get(i).getName());
                }
                int simChoice = readInt("Select Sim: ", simLocList.size());
                Sim chosenSim = simLocList.get(simChoice - 1);
                int optionList = 1;
                System.out.println("[1] Talk to " + chosenSim.getName());
                if(!currentSim.getRelationshipMap().containsKey(chosenSim.getUUID()))
                {
                    System.out.println("[2] Add " + chosenSim.getName() + " as friend");
                    optionList ++;
                }
                int interactChoice = readInt("Select choice: ", optionList);
                switch (interactChoice)
                {
                    case 1:
                        Activity interactActivity = new Activity("Talk" , 10, "Social", 30);
                        interactActivity.performActivity(currentSim);
                        interactActivity.performActivity(chosenSim);
                        decayLoop(interactActivity.getDuration(), interactActivity.getImpactedNeed());
                        break;
                    case 2:
                        Relationship newRelationship = new Relationship(currentSim, chosenSim);
                        currentSim.addRelationship(chosenSim.getUUID(), newRelationship);
                        chosenSim.addRelationship(currentSim.getUUID(), newRelationship);
                        break;
                }
            });
            optionIndex ++;
        }


        if(!currentSim.getRelationshipMap().isEmpty())
        {
            System.out.println("[" + optionIndex + "] Interact with friends");
            actions.put(optionIndex, () -> {
                System.out.println("\nChoose a Friend to interact with:");
                List<Integer> friendIds = new ArrayList<>(currentSim.getRelationshipMap().keySet());
                for (int i = 0; i < friendIds.size(); i++) {
                    Relationship rel = currentSim.getRelationshipMap().get(friendIds.get(i));
                    Sim friend = rel.getOtherSim(currentSim); // helper method to get the other Sim
                    System.out.println("[" + (i + 1) + "] " + friend.getName() + " - Friendship lvl: " + rel);
                }
                int friendChoice = readInt("Select Friend: ", friendIds.size());
                Relationship chosenRel = currentSim.getRelationshipMap().get(friendIds.get(friendChoice - 1));
                Sim friend = chosenRel.getOtherSim(currentSim);



                // Example: increase friendship level

                int optionList = 1;
                System.out.println("[1] Message " + friend.getName());
                if(friend.getHome().getHomeLocation().contains(friend.getLocation()))
                {
                    System.out.println("[2] Visit " + friend.getHome().getName());
                    optionList ++;
                }
                int friendActivity = readInt("Input : ", optionList);
                switch (friendActivity)
                {
                    case 1:
                        Activity messageFriend = new Activity("Message Friend", 10, "Social", 20);
                        messageFriend.performActivity(currentSim);
                        messageFriend.performActivity(friend);
                        decayLoop(messageFriend.getDuration(), messageFriend.getImpactedNeed());
                        chosenRel.increaseFriendship();
                        break;
                    case 2:
                        friend.getHome().moveTo(currentSim);
                        break;
                }
            });
            optionIndex++;

        }

        System.out.println("[" + optionIndex + "] Exit to main menu");

        // Build actions map
        for (int i = 0; i < activityList.size(); i++) {
            int index = i + 2;
            Activity activity = activityList.get(i);
            actions.put(index, () -> {
                if (currentSim.getBank() >= activity.getCost()) {
                    activity.performActivity(currentSim);
                    decayLoop(activity.getDuration(), activity.getImpactedNeed());
                } else {
                    System.out.println("Insufficient Funds!");
                }
            });
        }
        int upgradeStart = activityList.size() + 2;
        if (!upgradeOption.isEmpty() && currentSim.getHome() == ((HomeLocation) currentSim.getLocation()).getHome()) {

            for (int i = 0; i < upgradeOption.size(); i++) {
                int index = upgradeStart + i;
                HomeUpgrade upgrade = upgradeOption.get(i);
                actions.put(index, () -> {
                    if (currentSim.getBank() >= upgrade.getPrice()) {
                        currentSim.updateBank(upgrade.getPrice());
                        upgrade.purchaseUpgrade();
                    } else {
                        System.out.println("Insufficient Funds!");
                    }
                });
            }
        }
        actions.put(optionIndex, () -> gameState = 0); // Exit
        // Execute choice
        int choice = readInt("\nInput : ", optionIndex);
        actions.getOrDefault(choice, () -> System.out.println("Invalid choice." )).run();
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
            System.out.println(entry.getKey() + " : " + (String.format("%.2f", entry.getValue().getValue())) + "/100");
        }
    }

    public void showTime()
    {
        String test = String.format("%02d:%02d", hours,minutes);
        System.out.println("Current Time: " + test);
        System.out.println("Int time : " + getIntTime());
    }


    public void decayLoop (int duration, String need)
    {
            for (int i = 0; i < duration; i++) {
                for (Sim sim : simList) {
                    updateTime();
                    sim.performDecay(need, currentSim, getIntTime());
                }
            }

    }

    public int getIntTime()
    {
            return (hours * 60) + (minutes);
    }

    public String getStringTime(int minutes)
    {
        int hour;
        int day;
        if(minutes > 60)
        {
           hour = minutes / 60;
           minutes = minutes % 60;
           if(hour > 24)
           {
               day = hour / 24;
               hour = hour % 24;
               return String.format("%02d Day : %02d Hours : %02d Minutes", day, hour, minutes);
           }
           return String.format("%02d Hours : %02d Minutes",hour, minutes);
        }
        return String.format("%02d Minutes", minutes);
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
