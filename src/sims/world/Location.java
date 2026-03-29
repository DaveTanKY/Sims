package sims.world;

import sims.actions.Activity;
import sims.entity.Sim;

import java.util.List;


/**
 * Represents a location within the Sims world.
 * <p>
 * A {@code Location} defines the contract for places that Sims can occupy
 * and interact with. Implementing classes should provide details about
 * available activities, Sims present at the location, and movement logic.
 * </p>
 */
public interface Location {

    List<Activity> getActivity();
    List<Sim> getLocSimList();
    void removeSim(Sim sim);
    String getName();
    void moveTo(Sim sim);

}
