package sims.world;
import sims.actions.Activity;
import  sims.entity.Sim;
import java.util.ArrayList;
import java.util.List;


/**
 * An abstract class used to represent a location in the Sims world. A location has a name,
 * a list of activities that can be performed there, and a list of Sims
 * currently present in the location.
 *
 * <p>This class provides methods to add and remove Sims, manage activities,
 * and define movement behavior through the abstract {@link #moveTo(Sim)} method.
 * Subclasses should implement specific movement logic depending on the type of location.</p>
 */
public abstract class Loc {
    /** The name of the location. */
    private String name;

    /** The list of activities available at this location. */
    private List<Activity> activityList = new ArrayList<>();

    /**The list of Sims currently present at this location/ */
    private List<Sim> locSimList = new ArrayList<>();

    /**
     *  Constructs a new location with the specified name.
     *
     * @param locName the name of the location
     */
    public Loc(String locName)
    {
        this.name = locName;
    }

    /**
     * Returns the list of activities available at this location.
     *
     * @return a list of activities
     */
    public List<Activity> getActivity() {
        return this.activityList;
    }

    /**
     * Adds a Sim to this location.
     *
     * @param sim the Sim to add
     */
    public void addSim(Sim sim)
    {
        locSimList.add(sim);
    }

    /**
     * Returns the list of Sims currently present at this location.
     *
     * @return a list of Sims
     */
    public List<Sim> getLocSimList()
    {
        return locSimList;
    }

    /**
     * Removes a Sim from this location.
     *
     * @param sim the Sim to remove
     */
    public void removeSim(Sim sim)
    {
         locSimList.remove(sim);
    }

    /**
     * Adds an activity to this location.
     *
     * @param activity the activity to add
     */
    public void addActivity(Activity activity) {
        this.activityList.add(activity);
    }

    /**
     * Returns the name of this location.
     *
     * @return the location name
     */
    public String getName() {
        return name;
    }

    /**
     * Defines how a Sim moves to this location.
     * Subclasses must implement specific movement behavior.
     *
     * @param sim the Sim that is moving to this location
     */
    public abstract void moveTo(Sim sim);

    /**
     * Sets the name of this location.
     *
     * @param name the new name of the location
     */
    public void setName(String name) {
        this.name = name;
    }
}
