package sims.entity;

import sims.actions.Activity;
import sims.career.Career;
import sims.needs.need;
import sims.world.Home;
import sims.world.HomeLocation;
import sims.world.Loc;

import java.util.HashMap;
import java.util.Map;


/**
 * Represents a Sim character in the Sims world.
 * A {@code Sim} has attributes such as name, age, career, needs, relationships,
 * financial status, and current location. Sims can perform activities, manage
 * their needs, earn money, and interact with other Sims.
 *
 * <p>This class provides methods to update needs, manage relationships,
 * handle career progression, and simulate autonomous behavior through
 * activities and decay mechanics.</p>
 */
public class Sim {
    /** The name of the Sim. */
    private final String name;

    /** The unique identifier for the Sim. */
    private final int UUID;

    /** The career associated with the Sim. */
    private Career career;

    /** The age of the Sim. */
    private int age;

    /** The time when the current activity ends. */
    private int activityEnd = -1;

    /** The dictionary of needs for this Sim. */
    private Map<String, need> needDict = new HashMap<>();

    /** The home associated with this Sim. */
    private Home home;

    /** The current location of the Sim. */
    private Loc currentLocation;

    /** The Sim's bank balance. */
    private double bank = 500;

    /** The relationships this Sim has with other Sims, keyed by UUID. */
    private Map<Integer, Relationship> relationshipMap = new HashMap<>();


    /**
     * Constructs a new {@code Sim} with the specified name, UUID, and age.
     *
     * @param name the name of the Sim
     * @param UUID the unique identifier of the Sim
     * @param age  the age of the Sim
     */
    public Sim(String name, int UUID, int age){
        this.name = name;
        this.UUID = UUID;
        this.age = age;

    }

    /** @return the time when the current activity ends */
    public int getActivityEnd()
    {
        return activityEnd;
    }

    /** @return the age of the Sim */
    public int getAge()
    {
        return  age;
    }

    /** @return the name of the Sim */
    public String getName() {
        return name;
    }


    /** @return the unique identifier of the Sim */
    public int getUUID() {
        return UUID;
    }


    /** @return the dictionary of needs for this Sim */
    public Map<String, need> getNeeds() {
        return needDict;
    }


    /** @return the Sim's bank balance */
    public double getBank()
    {
        return bank;
    }

    /**
     * Updates the Sim's bank balance by subtracting a cost.
     *
     * @param cost the amount to subtract
     */
    public void updateBank (double cost)
    {
        bank -= cost;
    }

    /**
     * Adds a relationship to this Sim.
     *
     * @param uuid         the UUID of the other Sim
     * @param relationship the relationship object
     */
    public void addRelationship(int uuid, Relationship relationship)
    {
        relationshipMap.put(uuid, relationship);
    }

    /** @return the map of relationships */
    public Map<Integer, Relationship> getRelationshipMap() {
        return relationshipMap;
    }


    /**
     * Updates a need or salary for this Sim.
     * If the need is not "Salary", adjusts the need value.
     * If the need is "Salary", updates the bank balance and career progression.
     *
     * @param need  the need to update
     * @param value the value to adjust
     */
    public void updateNeeds(String need, double value) {
        System.out.println(activityEnd);
        if(need != "Salary")
        {
            needDict.get(need).setValue(value);
        }
        else
        {
            System.out.println("Bank previous : " + bank);
            bank += career.getSalary()+career.getBonus();
            career.earnXP();
            System.out.println("Bank after : " + bank);
        }

    }

    /**
     * Sets the dictionary of needs for this Sim.
     *
     * @param needDict the map of needs
     */
    public void setNeeds(Map<String, need> needDict){
        this.needDict = needDict;
    }

    /** @return the current location of the Sim */
    public Loc getLocation() {
        return currentLocation;
    }

    /**
     * Sets the current location of the Sim.
     *
     * @param currentLocation the new location
     */
    public void setCurrentLocation(Loc currentLocation) {
        this.currentLocation = currentLocation;
    }

    /** @return the home associated with this Sim */
    public Home getHome() {
        return home;
    }

    /**
     * Sets the home associated with this Sim.
     *
     * @param home the new home
     */
    public void setHome(Home home){
        this.home = home;
    }

    /** @return the career of the Sim */
    public Career getCareer()
    {
        return career;
    }

    /**
     * Sets the career of the Sim.
     *
     * @param career the new career
     */
    public void setCareer(Career career)
    {
        this.career = career;
    }


    /**
     * Sets the end time of the current activity.
     *
     * @param duration the duration of the activity
     * @param time     the current world time
     */
    public void setActivityEnd(int duration, int time)
    {
        activityEnd = time + duration;
    }


    /**
     * Automatically performs an activity that satisfies a given need.
     * The Sim moves to the appropriate home location and executes the activity.
     *
     * @param need the need to satisfy
     * @param time the current world time
     */
    public void autoPlay(String need, int time) {

        for (HomeLocation homeloc : home.getHomeLocation())
        {
            for(Activity activity : homeloc.getActivity())
            {
                if (activity.getImpactedNeed() == need)
                {
                    homeloc.moveTo(this);
                    activity.performActivity(this);
                    activityEnd = time + activity.getDuration();
                    //testing code
                    System.out.println(this.getName() + "Performed : " + activity.getName() + " at world time : " + time + ". activity end time : " + activityEnd) ;
                    return;
                }
            }

        }
    }

    /**
     * Applies decay to the Sim's needs. If a need falls below its threshold
     * and the Sim is not currently busy, the Sim will automatically perform
     * an activity to satisfy that need.
     *
     * @param need       the need to exclude from decay
     * @param currentSim the Sim currently being updated
     * @param time       the current world time
     */
    public void performDecay(String need, Sim currentSim, int time)
    {
            for (Map.Entry<String, need> entry : needDict.entrySet()) {
                if (currentSim == this) {
                    if (entry.getKey() != need) {
                        entry.getValue().performDecay();
                    }
                } else {
                    //System.out.println("Current Sim: " + this.getName() + "\nCurrent Need: " + entry.getKey() + ": " + entry.getValue().getValue() + "\nCurrent Time : " + time + "\nActivity end time : " + activityEnd);
                    if (entry.getValue().performDecay() && time > activityEnd) {
                        autoPlay(entry.getKey(), time);
                    }
                }
            }

    }
}
