package sims.actions;

import sims.entity.Sim;

import java.util.List;

/**
 * Represents an activity that a Sim can perform in the Sims world.
 * An {@code Activity} has a name, duration, impacted need, value, and optional cost.
 * Activities affect a Sim's needs and may also incur a financial cost.
 *
 * <p>This class provides methods to perform the activity, retrieve its properties,
 * and apply its effects to a Sim.</p>
 */
public class Activity {
    /** The name of the activity. */
    private final String name;

    /** The duration of the activity in world time units. */
    private final int duration;

    /** The need impacted by this activity (e.g, Hunger, Energy). */
    private final String impactedNeed;

    /** The value by which the impacted need is modified. */
    private final int value;

    /** The cost to perform activity. */
    private final double cost;


    /**
     * Constructs an {@code Activity} with no financial cost.
     *
     * @param name         the name of the activity
     * @param duration     the duration of the activity in world time units
     * @param impactedNeed the need impacted by this activity (e.g., Hunger, Energy)
     * @param value        the value by which the impacted need is modified
     */
    public Activity(String name, int duration, String impactedNeed, int value){
        this.name = name;
        this.duration = duration;
        this.impactedNeed = impactedNeed;
        this.value = value;
        cost = 0;
    }


    /**
     * Constructs an {@code Activity} with a specified financial cost.
     *
     * @param name         the name of the activity
     * @param duration     the duration of the activity in world time units
     * @param impactedNeed the need impacted by this activity (e.g., Hunger, Energy)
     * @param value        the value by which the impacted need is modified
     * @param cost         the cost to perform the activity
     */
    public Activity(String name, int duration, String impactedNeed, int value, double cost){
        this.name = name;
        this.duration = duration;
        this.impactedNeed = impactedNeed;
        this.value = value;
        this.cost = cost;
    }


    /**
     * Returns the financial cost of performing this activity.
     *
     * @return the cost of the activity
     */
    public double getCost()
    {
        return cost;
    }

    /**
     * Placeholder method for awarding experience points (XP) to a Sim
     * when performing this activity. Currently not implemented.
     */
    public void gainXP()
    {

    }

    /**
     * Returns the need impacted by this activity.
     *
     * @return the impacted need (e.g., Hunger, Energy)
     */
    public String getImpactedNeed() {
        return impactedNeed;
    }


    /**
     * Performs the activity on the given Sim, updating the Sim's needs
     * based on the impacted need and value.
     *
     * @param sim the Sim performing the activity
     */
    public void performActivity(Sim sim) {
        sim.updateNeeds(impactedNeed, value);
    }


    /**
     * Returns the name of the activity.
     *
     * @return the activity name
     */
    public String getName() {
        return name;
    }


    /**
     * Returns the duration of the activity in world time units.
     *
     * @return the activity duration
     */
    public int getDuration () {
        return duration;
    }

    /**
     * Returns the value by which the impacted need is modified.
     *
     * @return the activity's effect value
     */
    public int getValue () {
        return value;
    }
}
