package sims.world;

import sims.actions.Activity;
import sims.entity.Sim;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a home location in the Sims world.
 * A {@code Home} is a specialized type of {@link Loc} that contains
 * multiple {@link HomeLocation} areas (such as rooms or sections of the house).
 *
 * <p>This class manages the relationship between a home and its sub-locations,
 * and defines how Sims move into the home.</p>
 */
public class Home extends Loc {
    /** The list of sub-locations (e.g., rooms) that belong to this home. */
    private List<HomeLocation> homeLocation = new ArrayList<>();

    /**
     * Constructs a new {@code Home} with the specified name.
     *
     * @param homeName the name of the home
     */
    public Home(String homeName)
    {
        super(homeName);

    }

    /**
     * Returns the list of sub-locations that belong to this home.
     *
     * @return a list of {@link HomeLocation} objects
     */
    public List<HomeLocation> getHomeLocation() {
        return this.homeLocation;
    }

    /**
     * Adds a sub-location to this home and sets its parent reference.
     *
     * @param location the {@link HomeLocation} to add
     */
    public void addHomeLocation (HomeLocation location) {
        homeLocation.add(location);
        location.setHome(this);
    }

    /**
     * Moves a Sim into this home. The Sim is removed from its current location,
     * placed into the first available {@link HomeLocation}, and added to the
     * list of Sims present in this home if not already included.
     *
     * @param sim the {@link Sim} to move into the home
     */
    @Override
    public void moveTo(Sim sim){
        sim.getLocation().removeSim(sim);
        sim.setCurrentLocation(homeLocation.getFirst());
        if(!getLocSimList().contains(sim))
        {
            addSim(sim);
        }
    }
}
