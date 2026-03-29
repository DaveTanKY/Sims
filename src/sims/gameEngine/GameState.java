package sims.gameEngine;

import sims.actions.Activity;
import sims.actions.SkillManager;
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

/**
 * Represents the overall state of the game simulation.
 * <p>
 * The {@code GameState} class acts as the central container for all
 * active game entities, including Sims, homes, careers, and locations.
 * It provides a snapshot of the current simulation, allowing the game
 * engine to track, update, and persist progress across sessions.
 * </p>
 *
 * <h3>Responsibilities:</h3>
 * <ul>
 *   <li>Maintain a list of all active {@link Sim} objects in the game.</li>
 *   <li>Track homes, locations, and activities associated with Sims.</li>
 *   <li>Store global simulation data such as time, resources, and upgrades.</li>
 *   <li>Provide methods for querying and updating the current state.</li>
 *   <li>Serve as the entry point for saving and loading game progress.</li>
 * </ul>
 *
 * <h3>Usage Example:</h3>
 * <pre>
 * GameState gameState = new GameState();
 * List<Sim> sims = SimFactory.defaultGame();
 * gameState.setSimList(sims);
 *
 * // Query current Sims
 * for (Sim sim : gameState.getSimList()) {
 *     System.out.println(sim.getName() + " is part of the game.");
 * }
 * </pre>
 *
 * <p>
 * By centralizing all simulation data, {@code GameState} ensures consistency
 * across gameplay mechanics and provides a single source of truth for the
 * current game world.
 * </p>
 */
public class GameState {

    /**
     * Represents the current state of the game.
     * <p>Possible values could indicate different modes such as:
     * 0 = main menu, 1 = Choose sim menu, 2 = create sim menu, etc.</p>
     */
    private int gameState = 0;

    /**
     * Flag indicating whether the game loop is currently running.
     * <p>Set to {@code true} while the game is active, and {@code false} when the game should stop.</p>
     */
    private boolean gameRunning = true;

    /**
     * Tracks the current in-game year.
     * <p>Initialized to 2026 as the starting year of the simulation.</p>
     */
    private int year = 2026;

    /**
     * Tracks the current in-game month.
     * <p>Uses numeric values (1 = January, 2 = February, etc.).</p>
     */
    private int month = 1;

    /**
     * Tracks the number of days elapsed in the current month.
     * <p>Starts at 0 and increments as the simulation progresses.</p>
     */
    private int days = 0;

    /**
     * Tracks the current in-game hour of the day.
     * <p>Ranges from 0 to 23 to represent a 24-hour clock.</p>
     */
    private int hours = 0;
    /**
     * Tracks the current in-game minute of the hour.
     * <p>Ranges from 0 to 59 to represent minutes within an hour.</p>
     */
    private int minutes = 0;

    /**
     * Initializes the simulation by adding a default Sim and several OutsideLocation
     * instances with associated activities. This setup defines the environment in which
     * Sims can interact, including leisure, social, and work-related activities.
     *
     * <p>The following locations are created:</p>
     * <ul>
     *   <li><b>Gym</b> – contains a "Workout" activity.</li>
     *   <li><b>Park</b> – contains "Play ball games" and "Talk to friends" activities.</li>
     *   <li><b>Mall</b> – contains "Eat at Restaurant", "Arcade", and "Watch Movie" activities.</li>
     *   <li><b>IT Office</b>, <b>Bank</b>, <b>Hospital</b> – each contains a "Work" activity.</li>
     * </ul>
     *
     * <p>Each activity specifies its duration, the need it fulfills (e.g., Fun, Social, Hunger, Salary),
     * and the benefit values associated with completing it.</p>
     *
     * <p>This method demonstrates how to populate the repository of locations and activities
     * so that crawler Sims can explore and engage with different environments.</p>
     */
    public void startGame()
    {
        //Adds default sim to game
        simList = SimFactory.defaultGame();
        //Creates gym default location
        OutsideLocation gym = new OutsideLocation("Gym", "none");
        gym.addActivity(new Activity("Workout", 30, "Fun", 40));
        addLocation(gym);


        //Creates park default location
        OutsideLocation park = new OutsideLocation("Park","none");
        park.addActivity(new Activity("Play ball games", 90, "Fun", 60));
        park.addActivity(new Activity("Talk to friends", 60, "Social", 70));
        addLocation(park);


        //Creates mall default location
        OutsideLocation mall = new OutsideLocation("Mall","none");
        mall.addActivity(new Activity("Eat at Restaurant", 90, "Hunger", 100, 200));
        mall.addActivity(new Activity("Arcade", 30, "Fun", 40, 50));
        mall.addActivity(new Activity("Watch Movie", 120, "Fun", 70, 100));
        addLocation(mall);

        //Creates work locations for different sectors
        OutsideLocation office = new OutsideLocation("IT Office", "IT");
        OutsideLocation bank = new OutsideLocation("Bank", "Finance");
        OutsideLocation hospital = new OutsideLocation("Hospital", "Healthcare");

        //Creates default work activity
        Activity workActivity = new Activity("Work", (8 * 60), "Salary", 0);

        //Adds location to list
        hospital.addActivity(workActivity);
        bank.addActivity(workActivity);
        office.addActivity(workActivity);
        addLocation(office);
        addLocation(bank);
        addLocation(hospital);

        //moving default sims to another location.
        gym.moveTo(simList.get(1));
        park.moveTo(simList.get(2));
        gym.moveTo(simList.get(3));
        park.moveTo(simList.get(4));
        mall.moveTo(simList.get(5));
        mall.moveTo(simList.get(6));

    }


    /**
     * The currently active Sim in the game.
     */
    private Sim currentSim;

    /**
     * A list containing all Sims in the game.
     */
    private List<Sim> simList = new ArrayList<>();

    /**
     * A list containing all outside locations available in the game.
     */
    private List<OutsideLocation> outsideLocationList = new ArrayList<>();

    /**
     * Scanner object used for user input.
     */
    private Scanner scanner = new Scanner(System.in);


    /**
     * Retrieves the current state of the game.
     * <p>
     * The game state is represented as an integer, which can be used to
     * indicate different phases or modes of the game
     * </p>
     *
     * @return the current game state as an integer
     */
    public int getGameState() {
        return gameState;
    }

    /**
     * Adds a new Sim to the game.
            * <p>
     * This method appends the provided {@link Sim} object to the list of Sims
     * currently managed by the game. It allows new characters to be introduced
     * into the simulation.
     * </p>
            *
            * @param sim the Sim object to be added to the game
     */
    public void addSim(Sim sim)
    {
        simList.add(sim);
    }


    /**
     * Adds a new outside location to the game.
     * <p>
     * This method appends the provided {@link OutsideLocation} object to the list
     * of available outside locations. It allows the game world to expand by
     * introducing new areas that Sims can interact with.
     * </p>
     *
     * @param location the OutsideLocation object to be added to the game
     */
    public void addLocation(OutsideLocation location)
    {
        outsideLocationList.add(location);
    }

    /**
     * Checks whether the game is currently running.
     * <p>
     * This method returns the value of the {@code gameRunning} flag,
     * which indicates if the simulation is active. It can be used to
     * determine whether the game loop should continue executing or
     * if the game has been stopped/paused.
     * </p>
     *
     * @return {@code true} if the game is running, {@code false} otherwise
     */
    public boolean getGameRunning()
    {
        return gameRunning;
    }


    /**
     * Updates the current state of the game.
     * <p>
     * This method sets the {@code gameState} field to the specified value.
     * The state can be used to represent different phases or modes of the game
     * </p>
     *
     * @param state the new game state to be applied
     */
    public void setGameState(int state)
    {
        gameState = state;
    }

    /**
     * Updates the game flow based on the current state.
     * <p>
     * This method uses the {@code gameState} value to determine which
     * menu or action should be displayed or executed. Each state corresponds
     * to a different phase of the game:
     * </p>
     * <ul>
     *   <li>0 – Show the main menu</li>
     *   <li>1 – Show the choose Sim menu</li>
     *   <li>2 – Show the create Sim menu</li>
     *   <li>3 – Show the action menu</li>
     *   <li>4 – Show the move menu</li>
     *   <li>5 – Reserved/unused state</li>
     *   <li>6 – End the game</li>
     * </ul>
     * <p>
     * Implementing this switch-case structure ensures that the game responds
     * appropriately to user input and transitions smoothly between different
     * stages.
     * </p>
     */
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


    /**
     * Displays the main menu of the game and handles user input.
     * <p>
     * This method prints the main menu options to the console, allowing the player
     * to select actions such as choosing a character, creating a new character,
     * saving the game, or ending the game. Based on the player's choice, the
     * {@code gameState} is updated accordingly:
     * </p>
     * <ul>
     *   <li>1 – Select Character (transition to choose Sim menu)</li>
     *   <li>2 – Create Character (transition to create Sim menu)</li>
     *   <li>3 – Save Game (transition to end game state)</li>
     *   <li>4 – End Game (not yet implemented in this snippet)</li>
     * </ul>
     * <p>
     * The method uses {@code readInt} to validate user input and ensure the choice
     * falls within the available options.
     * </p>
     */
    public void showMainMenu()
    {
        System.out.println("This is the main menu");
        System.out.println("-------------Welcome to SIMS--------------");
        System.out.println("[1] Select Character");
        System.out.println("[2] Create Character");
        System.out.println("[3] End Game");
        int choice = readInt("Please input option: ", 3);
        switch(choice) {
            case 1:
                gameState = 1;
                break;
            case 2:
                gameState = 2;
                break;
            case 3:
                gameState = 6;
                break;
        }
    }

    /**
     * Displays the character selection menu and allows the player to choose a Sim.
     * <p>
     * This method prints a list of all available Sims from {@code simList},
     * prompting the player to select one. The chosen Sim becomes the
     * {@code currentSim}, and the {@code gameState} is updated to 3
     * (action menu). After updating the state, the {@code update()} method
     * is called to continue the game flow.
     * </p>
     * <p>
     * Menu flow:
     * <ul>
     *   <li>Displays all Sims with their names and corresponding index numbers.</li>
     *   <li>Prompts the player for input using {@code readInt}, ensuring the choice
     *       is within the valid range.</li>
     *   <li>Sets {@code currentSim} to the selected Sim.</li>
     *   <li>Transitions the game to the action menu (state 3).</li>
     * </ul>
     * </p>
     */
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

    /**
     * Displays the Sim creation menu and allows the player to create a new Sim.
     * <p>
     * This method guides the player through the process of creating a Sim by:
     * </p>
     * <ul>
     *   <li>Prompting for the Sim's name and age.</li>
     *   <li>Allowing the player to choose whether the Sim has a job.</li>
     *   <li>If a job is selected, prompting for a job sector (IT, Finance, Healthcare).</li>
     *   <li>Assigning the chosen career sector to the new {@link Career} object.</li>
     * </ul>
     * <p>
     * The method uses {@code readString} and {@code readInt} to validate user input
     * and ensure correct values are provided. Once complete, the new Sim can be
     * added to the game world with the specified attributes.
     * </p>
     */
    public void showCreateMenu() {
        System.out.println("-------------SIM Creation--------------");
        String name = readString("Please enter name of Sim: ");
        int age = readInt("Please enter age of Sim: ");
        System.out.println("[1] Sim does not have a job");
        System.out.println("[2] Sim has a job");
        int choice = readInt("Please input option: ");
        Career career = new Career();
        if(choice == 2)
        {
            System.out.println("------Job Sectors-----");
            System.out.println("[1] IT");
            System.out.println("[2] Finance");
            System.out.println("[3] Healthcare");
            int jobOption = 0;
            jobOption = readInt("Please choose job sector option: ", 3);

            switch (jobOption)
            {
                case 1:
                {
                   career.setSector("IT");
                   break;
                }
                case 2:
                {
                    career.setSector("Finance");
                    break;
                }
                case 3:
                {
                    career.setSector("Healthcare");
                    break;
                }
            }
            career.setSalary(readInt("Input initial Salary: "));
        }

        System.out.println("Your Sim is being created!");
        Home newHome = SimFactory.defaultHome(name);

        Sim newSim = SimFactory.createSim(name, simList.size(), age, newHome, career);
        simList.add(newSim);
        currentSim = newSim;
        gameState = 3;
    }

    /**
     * Displays the action menu for the currently selected Sim and handles player input.
     * <p>
     * This method provides the player with a variety of options depending on the Sim's
     * current location, available activities, upgrades, and relationships. It dynamically
     * builds a menu of actions and executes the chosen option.
     * </p>
     *
     * <h3>Features:</h3>
     * <ul>
     *   <li>Displays the Sim's current location.</li>
     *   <li>Shows the current time and Sim statistics.</li>
     *   <li>Lists other Sims present in the same location.</li>
     *   <li>Builds a list of available activities from the location and unlocked upgrades.</li>
     *   <li>Allows the player to purchase home upgrades to unlock new activities.</li>
     *   <li>Provides interaction options with other Sims (talk, add friend).</li>
     *   <li>Provides interaction options with existing friends (message, visit).</li>
     *   <li>Allows the player to move to a different location.</li>
     *   <li>Allows the player to exit back to the main menu.</li>
     * </ul>
     *
     * <h3>Game State Transitions:</h3>
     * <ul>
     *   <li>Move Location → {@code gameState = 4}</li>
     *   <li>Exit to Main Menu → {@code gameState = 0}</li>
     *   <li>Other actions may trigger activities, relationship changes, or upgrades.</li>
     * </ul>
     *
     * <p>
     * The method uses a {@link Map} of menu options to {@link Runnable} actions,
     * ensuring that each choice executes the correct behavior. Input validation
     * is handled via {@code readInt}, and insufficient funds are checked before
     * performing activities or purchasing upgrades.
     * </p>
     */
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

        System.out.println("\n-------------Please choose action for SIM--------------");

        Map<Integer, Runnable> actions = new HashMap<>();
        int optionIndex = 1;
        System.out.println("\n[" + optionIndex + "] Move Location");
        actions.put(optionIndex++, () -> gameState = 4);

        for(Activity activity : activityList)
        {
            if(activity.getImpactedNeed() != "Salary") {


                SkillManager skill = currentSim.getSkillMap().get(activity.getImpactedNeed());
                System.out.println("[" + optionIndex + "] " + activity.getName() + " ("+ getStringTime(activity.getDuration()) + ") : " + activity.getImpactedNeed() + " + " + activity.getValue() + " (Skill level: " + skill.getLevel() + " - Bonus stats gained: " + (skill.getLevel() * 5)+ ")");
            }
            else
            {
                Career career = currentSim.getCareer();
                System.out.println("[" + optionIndex + "] " + activity.getName() + " (" + getStringTime(activity.getDuration()) + ") : Earn $" + currentSim.getCareer().getSalary() + " + (Career Level: " + currentSim.getCareer().getLevel() + " -  Bonus: $" + currentSim.getCareer().getBonus() + ")");
            }
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
                    System.out.println("[" + (i + 1) + "] " + friend.getName() + " - Friendship lvl: " + rel.getFriendshipLevel());
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



    /**
     * Displays the location movement menu and allows the player to move the current Sim.
     * <p>
     * This method builds a list of possible destinations based on the Sim's current
     * location and career requirements:
     * </p>
     * <ul>
     *   <li>If the Sim is in a {@link HomeLocation}, all other rooms in the home are listed.</li>
     *   <liIf the Sim is outside, their home is added as a destination.</li>
     *   <li>{@link OutsideLocation}s are added if they either have no requirement
     *       or match the Sim's career sector.</li>
     * </ul>
     *
     * <p>
     * The menu is printed with numbered options, and the player selects a destination
     * using {@code readInt}. The chosen location’s {@code moveTo} method is then called
     * to move the Sim. After moving, the {@code gameState} is set to 3 (action menu),
     * and {@code update()} is invoked to continue the game flow.
     * </p>
     *
     * <h3>Game State Transition:</h3>
     * <ul>
     *   <li>After moving → {@code gameState = 3} (Action Menu)</li>
     * </ul>
     */
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
                if (loc.getRequirement() == "none") {
                    menuList.add(loc);
                } else if (loc.getRequirement() == currentSim.getCareer().getSector()) {
                    menuList.add(loc);
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


    /**
     * Reads an integer value from user input with validation.
     * <p>
     * This method displays the given prompt and waits for the user to enter
     * an integer. Input is validated to ensure that:
     * </p>
     * <ul>
     *   <li>The entered value is an integer (non-numeric input is rejected).</li>
     *   <li>The value is greater than zero (non-positive values are rejected).</li>
     * </ul>
     * <p>
     * If invalid input is detected, the user is prompted again until a valid
     * integer is entered. The method uses a {@code Scanner} instance to read
     * input from the console.
     * </p>
     *
     * @param prompt the message displayed to the user before input
     * @return a positive integer entered by the user
     */
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

    /**
     * Reads an integer value from user input with validation against a range of options.
     * <p>
     * This method displays the given prompt and waits for the user to enter
     * an integer. Input is validated to ensure that:
     * </p>
     * <ul>
     *   <li>The entered value is an integer (non-numeric input is rejected).</li>
     *   <li>The value is greater than zero.</li>
     *   <li>The value does not exceed the specified number of options.</li>
     * </ul>
     * <p>
     * If invalid input is detected, the user is prompted again until a valid
     * integer is entered. The method uses a {@code Scanner} instance to read
     * input from the console.
     * </p>
     *
     * @param prompt  the message displayed to the user before input
     * @param options the maximum valid option number (upper bound of the range)
     * @return a positive integer between 1 and {@code options}, inclusive
     */
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

    /**
     * Reads a string value from user input.
     * <p>
     * This method displays the given prompt and waits for the user to enter
     * a line of text. The input is read using a {@code Scanner} instance
     * and returned as a {@code String}.
     * </p>
     *
     * @param prompt the message displayed to the user before input
     * @return the string entered by the user
     */
    public String readString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    /**
     * Displays the current statistics of the specified Sim.
     * <p>
     * This method prints the Sim's name followed by each of their needs
     * and the corresponding values. The values are formatted to two decimal
     * places and shown as a fraction of 100, representing the Sim's status
     * in different areas (e.g., hunger, energy, social).
     * </p>
     *
     * <h3>Output Example:</h3>
     * <pre>
     * Alice's Stats
     * Social : 75.50/100
     * Hunger : 60.25/100
     * Energy : 90.00/100
     * Bladder : 80.00/100
     * Hygiene : 50.00/100
     * Fun : 95.00/100
     * </pre>
     *
     * @param sim the Sim whose statistics are to be displayed
     */
    public void showStats(Sim sim)
    {
        System.out.println(sim.getName() + "'s Stats");
        for (Map.Entry<String, need> entry : sim.getNeeds().entrySet()) {
            System.out.println(entry.getKey() + " : " + (String.format("%.2f", entry.getValue().getValue())) + "/100");
        }
    }

    /**
     * Displays the current in-game time.
     * <p>
     * This method formats the {@code hours} and {@code minutes} fields into
     * a standard 24-hour clock format (HH:mm) and prints it to the console.
     * It also prints the integer representation of the time by calling
     * {@code getIntTime()}, which can be useful for calculations or comparisons.
     * </p>
     *
     * <h3>Output Example:</h3>
     * <pre>
     * Current Time: 09:05
     * Int time : 905
     * </pre>
     */
    public void showTime()
    {
        String test = String.format("%02d:%02d", hours,minutes);
        System.out.println("Current Time: " + test);
        //System.out.println("Int time : " + getIntTime());
    }


    /**
     * Runs the decay loop for all Sims over a specified duration.
     * <p>
     * This method simulates the passage of time in the game by repeatedly
     * updating the in-game clock and applying need decay to each Sim.
     * The decay is applied to the specified need type for all Sims in
     * {@code simList}, using the current Sim and the integer time value
     * as context.
     * </p>
     *
     * <h3>Behavior:</h3>
     * <ul>
     *   <li>For each unit of {@code duration}, the in-game time is updated.</li>
     *   <li>Each Sim in {@code simList} has {@code performDecay} called
     *       for the given need.</li>
     *   <li>The {@code getIntTime()} method is used to provide the current
     *       time in integer format to the decay logic.</li>
     * </ul>
     *
     * @param duration the number of time units to simulate
     * @param need     the type of need to decay (e.g., Hunger, Energy, Social)
     */
    public void decayLoop (int duration, String need)
    {
            for (int i = 0; i < duration; i++) {
                for (Sim sim : simList) {
                    updateTime();
                    sim.performDecay(need, currentSim, getIntTime());
                }
            }
    }

    /**
     * Returns the current in-game time as an integer value.
     * <p>
     * The time is calculated by converting the {@code hours} and {@code minutes}
     * fields into total minutes since midnight:
     * </p>
     *
     * <pre>
     * intTime = (hours * 60) + minutes
     * </pre>
     *
     * <p>
     * This integer representation of time is useful for calculations,
     * comparisons, and passing into methods that require a numeric time format
     * (e.g., decay loops or activity scheduling).
     * </p>
     *
     * @return the current time in minutes since midnight
     */
    public int getIntTime()
    {
            return (hours * 60) + (minutes);
    }

    /**
     * Converts a time value in minutes into a formatted string.
     * <p>
     * This method takes a total number of minutes and converts it into
     * hours and days where applicable:
     * </p>
     * <ul>
     *   <li>If the value is less than 60, it is displayed as minutes only.</li>
     *   <li>If the value is 60 or more, it is converted into hours and minutes.</li>
     *   <li>If the value exceeds 24 hours, it is converted into days, hours, and minutes.</li>
     * </ul>
     *
     * <h3>Output Examples:</h3>
     * <pre>
     * getStringTime(45)   → "45 Minutes"
     * getStringTime(125)  → "02 Hours : 05 Minutes"
     * getStringTime(1500) → "01 Day : 01 Hours : 00 Minutes"
     * </pre>
     *
     * @param minutes the total time in minutes
     * @return a formatted string representing the time in days, hours, and minutes
     */
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

    /**
     * Updates the in-game clock by advancing time.
     * <p>
     * This method increments the {@code minutes} field and carries over values
     * into higher units of time as needed:
     * </p>
     * <ul>
     *   <li>Minutes → Hours (every 60 minutes)</li>
     *   <li>Hours → Days (every 24 hours)</li>
     *   <li>Days → Months (every 31 days, simplified assumption)</li>
     *   <li>Months → Years (every 12 months)</li>
     * </ul>
     *
     * <p>
     * The rollover logic ensures that the in-game time remains consistent and
     * progresses naturally. For simplicity, months are assumed to have 31 days.
     * </p>
     *
     * <h3>Example:</h3>
     * <pre>
     * Initial: 23 hours, 59 minutes
     * After updateTime():
     *   → 0 hours, 0 minutes, +1 day
     * </pre>
     */
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

    /**
     * Ends the game by stopping the main loop.
     * <p>
     * This method sets the {@code gameRunning} flag to {@code false},
     * which signals the game engine to terminate execution. Once called,
     * the game will no longer continue updating or processing menus.
     * </p>
     *
     * <h3>Usage:</h3>
     * <ul>
     *   <li>Typically invoked when the player selects "End Game" from the main menu.</li>
     *   <li>Ensures a clean exit from the game loop without abrupt termination.</li>
     * </ul>
     */
    public void endGame()
    {
        gameRunning = false;
    }
}
