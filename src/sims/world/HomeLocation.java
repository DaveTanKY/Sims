package sims.world;

import sims.actions.Activity;
import sims.entity.Sim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a specific sub-location within a {@link Home}, such as a room or area.
 * A {@code HomeLocation} extends {@link Loc} and can contain upgrades, while
 * maintaining a reference to its parent {@link Home}.
 *
 * <p>This class manages upgrades, provides access to Sims present in the parent
 * home, and defines how Sims move into this sub-location.</p>
 */
public class HomeLocation extends Loc{
    /** The list of upgrades applied to this home location. */
    private List<HomeUpgrade> upgradeList = new ArrayList<>();

    /** The parent home that this location belongs to. */
    private Home home;

    /**
     * Constructs a new {@code HomeLocation} with the specified name and parent home.
     *
     * @param locName the name of the home location
     * @param home    the parent {@link Home} this location belongs to
     */
    public HomeLocation(String locName, Home home) {
        super(locName);
        this.home = home;
    }

    /**
     * Adds an upgrade to this home location.
     *
     * @param upgrade the {@link HomeUpgrade} to add
     */
    public void addUpgrade(HomeUpgrade upgrade)
    {
        upgradeList.add(upgrade);
    }

    /**
     * Returns the list of upgrades applied to this home location.
     *
     * @return a list of {@link HomeUpgrade} objects
     */
    public List<HomeUpgrade> getUpgradeList() {
        return upgradeList;
    }

    /**
     * Returns the parent home of this location.
     *
     * @return the parent {@link Home}
     */
    public Home getHome()
    {
        return home;
    }

    /**
     * Returns the list of Sims currently present in the parent home.
     * This overrides the base implementation to delegate to the home.
     *
     * @return a list of Sims in the parent home
     */
    public List<Sim> getLocSimList()
    {
        return home.getLocSimList();
    }


    /**
     * Removes a Sim from the parent home and prints the list of Sims
     * before and after removal for debugging purposes.
     *
     * @param sim the {@link Sim} to remove
     */
    public void removeSim(Sim sim)
    {
        for(Sim test : home.getLocSimList())
        {
            System.out.println("Home list Before : " + test.getName());
        }
        home.removeSim(sim);
        for(Sim test : home.getLocSimList())
        {
            System.out.println("Home list after : " + test.getName());
        }
    }

    /**
     * Moves a Sim into this home location. The Sim's current location is updated,
     * and the Sim is added to the parent home if not already present.
     *
     * @param sim the {@link Sim} to move into this location
     */
    @Override
    public void moveTo(Sim sim) {
        sim.setCurrentLocation(this);
        if(!home.getLocSimList().contains(sim))
        {
            home.addSim(sim);
        }
    }

    /**
     * Sets the parent home of this location.
     *
     * @param home the new parent {@link Home}
     */
    public void setHome(Home home)
    {
        this.home = home;
    }
}

