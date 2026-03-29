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

    /**
     * Initializes a default game state with a predefined list of Sims.
     * <p>
     * This method creates several {@link Sim} objects with preset attributes,
     * including names, ages, homes, careers, skills, and needs. Each Sim is
     * assigned a {@link Career} sector (IT, Finance, Healthcare) with a salary,
     * and a default home created via {@code defaultHome}. Skills and needs are
     * initialized using helper methods {@code createSkills()} and
     * {@code createNeeds()}.
     * </p>
     *
     * <h3>Default Sims Created:</h3>
     * <ul>
     *   <li>Dave – IT sector, salary 5000</li>
     *   <li>Tim – IT sector, salary 4000</li>
     *   <li>Darius – IT sector, salary 4500</li>
     *   <li>Rance – Healthcare sector, salary 4600</li>
     *   <li>Reggie – Healthcare sector, salary 4300</li>
     *   <li>Eliyaz – Finance sector, salary 4200</li>
     *   <li>Thou Yong – Finance sector, salary 4700</li>
     * </ul>
     *
     * <p>
     * The created Sims are added to a {@link List} and returned, providing
     * a ready-to-play starting point for the game.
     * </p>
     *
     * @return a list of default Sims with predefined attributes
     */
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


    /**
     * Creates a default home for a Sim with predefined locations, activities, and upgrades.
     * <p>
     * This method initializes a {@link Home} object with a set of standard
     * {@link HomeLocation}s (Bathroom, Living Room, Bedroom, Kitchen), each
     * containing default activities. It also defines several {@link HomeUpgrade}s
     * that unlock additional activities when purchased.
     * </p>
     *
     * <h3>Default Activities:</h3>
     * <ul>
     *   <li>Kitchen – Eat Sandwich (Hunger)</li>
     *   <li>Living Room – Watch TV (Fun), Scroll Social Media (Social)</li>
     *   <li>Bedroom – Sleep (Energy)</li>
     *   <li>Bathroom – Shower (Hygiene), Use Toilet (Bladder)</li>
     * </ul>
     *
     * <h3>Available Upgrades:</h3>
     * <ul>
     *   <li>Oven (Kitchen) – Unlocks Pizza activity</li>
     *   <li>Coffee Machine (Kitchen) – Unlocks Coffee activity</li>
     *   <li>Sofa (Bedroom) – Unlocks Nap activity</li>
     *   <li>Cards (Living Room) – Unlocks Play Card Games activity</li>
     * </ul>
     *
     * <p>
     * After creating activities and upgrades, they are assigned to the appropriate
     * home locations. Finally, all locations are added to the home, which is returned
     * for assignment to a Sim.
     * </p>
     *
     * @param name the name of the Sim, used to personalize the home name
     * @return a {@link Home} object with default locations, activities, and upgrades
     */
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

    /**
     * Creates and initializes a new Sim with the specified attributes.
     * <p>
     * This method constructs a {@link Sim} object with a given name, ID, and age.
     * It then assigns the Sim a {@link Home}, sets their current location to the
     * first available {@link HomeLocation}, and associates them with a {@link Career}.
     * Additionally, the Sim is initialized with default needs and skills using
     * helper methods {@code createNeeds()} and {@code createSkills()}.
     * </p>
     *
     * <h3>Initialization Steps:</h3>
     * <ul>
     *   <li>Create a new {@link Sim} with name, ID, and age.</li>
     *   <li>Assign default needs via {@code createNeeds()}.</li>
     *   <li>Set the Sim’s home and current location (first location in the home).</li>
     *   <li>Assign the specified career.</li>
     *   <li>Initialize default skills via {@code createSkills()}.</li>
     * </ul>
     *
     * @param name        the Sim’s name
     * @param simListSize the Sim’s unique identifier or index in the Sim list
     * @param age         the Sim’s age
     * @param home        the Sim’s home environment
     * @param career      the Sim’s career assignment
     * @return a fully initialized {@link Sim} object
     */
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

    /**
     * Creates and initializes a default skill map for a Sim.
     * <p>
     * This method constructs a {@link HashMap} where each key represents
     * a specific need category (e.g., Hunger, Hygiene, Energy, Bladder, Fun, Social),
     * and each value is a new {@link SkillManager} instance responsible for
     * tracking and managing the Sim's skill progression in that category.
     * </p>
     *
     * <h3>Default Skills Created:</h3>
     * <ul>
     *   <li>Hunger</li>
     *   <li>Hygiene</li>
     *   <li>Energy</li>
     *   <li>Bladder</li>
     *   <li>Fun</li>
     *   <li>Social</li>
     * </ul>
     *
     * <p>
     * The returned map provides a consistent baseline skill set for each Sim,
     * ensuring that all primary needs are represented and can be managed
     * throughout gameplay.
     * </p>
     *
     * @return a map of need categories to their corresponding {@link SkillManager} objects
     */
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


    /**
     * Creates and initializes a default needs map for a Sim.
     * <p>
     * This method constructs a {@link HashMap} where each key represents
     * a specific need category (e.g., Hunger, Hygiene, Energy, Bladder, Fun, Social),
     * and each value is a new {@link need} object initialized with a decay rate.
     * The decay rate determines how quickly the need decreases over time in the
     * simulation, requiring the Sim to perform activities to replenish it.
     * </p>
     *
     * <h3>Default Needs and Decay Rates:</h3>
     * <ul>
     *   <li>Hunger – 0.2</li>
     *   <li>Hygiene – 0.01</li>
     *   <li>Energy – 0.3</li>
     *   <li>Bladder – 0.02</li>
     *   <li>Fun – 0.005</li>
     *   <li>Social – 0.003</li>
     * </ul>
     *
     * <p>
     * The returned map provides a consistent baseline set of needs for each Sim,
     * ensuring that all primary aspects of well-being are represented and can be
     * managed throughout gameplay.
     * </p>
     *
     * @return a map of need categories to their corresponding {@link need} objects
     */
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
