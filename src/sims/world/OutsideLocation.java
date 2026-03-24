package sims.world;

import sims.entity.Sim;

import java.util.Map;
import java.util.HashMap;

/**
 * Represents an outside location in the Sims world.
 * An {@code OutsideLocation} is a specialized type of {@link Loc} that
 * exists outside of a home or indoor environment. It can have requirements
 * or comparisons that define conditions for Sims to interact with or move into it.
 *
 * <p>This class manages Sims moving into the outside location and provides
 * access to its comparison and requirement properties.</p>
 */
public class OutsideLocation extends Loc{

    /** A comparison condition associated with this outside location. */
    private String comparison;

    /** A requirement condition associated with this outside location. */
    private String requirement;

    /**
     * Constructs a new {@code OutsideLocation} with the specified name,
     * comparison, and requirement.
     *
     * @param name        the name of the outside location
     * @param comparison  the comparison condition for this location
     * @param requirement the requirement condition for this location
     */
    public OutsideLocation(String name, String comparison, String requirement)
    {
        super(name);
        this.comparison = comparison;
        this.requirement = requirement;
    }

    /**
     * Moves a Sim into this outside location. The Sim is removed from its
     * current location, updated to this location, and added to the list of Sims
     * present here if not already included.
     *
     * @param sim the {@link Sim} to move into this location
     */
    @Override
    public void moveTo(Sim sim) {
        sim.getLocation().removeSim(sim);
        sim.setCurrentLocation(this);
        if(!getLocSimList().contains(sim))
        {
            addSim(sim);
        }
    }

    /**
     * Returns the comparison condition associated with this location.
     *
     * @return the comparison string
     */
    public String getComparison()
    {
        return comparison;
    }

    /**
     * Returns the requirement condition associated with this location.
     *
     * @return the requirement string
     */
    public String getRequirement ()
    {
        return requirement;
    }
}
