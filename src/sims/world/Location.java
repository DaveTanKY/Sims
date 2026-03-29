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

    /**
     * Retrieves the list of activities available in this context.
     * <p>
     * Implementations of this method should return all {@link Activity}
     * objects currently associated with the entity (e.g., a {@link HomeLocation}).
     * Each activity represents an action a Sim can perform, such as eating,
     * sleeping, or socializing, and is tied to a specific need category.
     * </p>
     *
     * <h3>Usage Example:</h3>
     * <pre>
     * List<Activity> activities = livingRoom.getActivity();
     * for (Activity activity : activities) {
     *     System.out.println(activity.getName());
     * }
     * </pre>
     *
     * <p>
     * The returned list may be empty if no activities are currently available,
     * or it may grow as upgrades are added to the location. This provides a
     * flexible way to query and interact with the Sim’s environment.
     * </p>
     *
     * @return a list of {@link Activity} objects available in this entity
     */
    List<Activity> getActivity();

    /**
     * Retrieves the list of Sims currently located in this context.
     * <p>
     * Implementations of this method should return all {@link Sim} objects
     * that are present in the associated location (e.g., a {@link HomeLocation}).
     * This allows the game to track which Sims are interacting within a space
     * at any given time.
     * </p>
     *
     * <h3>Usage Example:</h3>
     * <pre>
     * List<Sim> simsInLivingRoom = livingRoom.getLocSimList();
     * for (Sim sim : simsInLivingRoom) {
     *     System.out.println(sim.getName() + " is here.");
     * }
     * </pre>
     *
     * <p>
     * The returned list may be empty if no Sims are currently in the location.
     * It can grow or shrink dynamically as Sims move between locations.
     * </p>
     *
     * @return a list of {@link Sim} objects currently in this location
     */
    List<Sim> getLocSimList();


    /**
     * Removes the specified Sim from this location.
     * <p>
     * Implementations of this method should update the internal list of Sims
     * (e.g., {@code locSimList}) by removing the given {@link Sim} object.
     * This reflects the Sim leaving the location, ensuring that the list
     * accurately represents which Sims are currently present.
     * </p>
     *
     * <h3>Usage Example:</h3>
     * <pre>
     * livingRoom.removeSim(alice);
     * // Alice is no longer tracked as being in the living room
     * </pre>
     *
     * <p>
     * If the specified Sim is not present in the location, the method should
     * have no effect. This provides safe handling for cases where removal
     * is attempted on a Sim not currently in the list.
     * </p>
     *
     * @param sim the {@link Sim} to remove from this location
     */
    void removeSim(Sim sim);


    /**
     * Retrieves the name of this entity.
     * <p>
     * Implementations of this method should return the identifying name
     * associated with the object (e.g., a {@link Sim}, {@link HomeLocation},
     * or {@link Activity}). The name is typically used for display,
     * personalization, or referencing the entity within the game.
     * </p>
     *
     * <h3>Usage Example:</h3>
     * <pre>
     * String simName = sim.getName();
     * System.out.println("Sim's name: " + simName);
     * </pre>
     *
     * @return the name of this entity as a {@link String}
     */
    String getName();


    /**
     * Moves the specified Sim into this location.
     * <p>
     * Implementations of this method should update the internal list of Sims
     * (e.g., {@code locSimList}) by adding the given {@link Sim} object.
     * This reflects the Sim entering the location, ensuring that the list
     * accurately represents which Sims are currently present.
     * </p>
     *
     * <h3>Usage Example:</h3>
     * <pre>
     * livingRoom.moveTo(alice);
     * // Alice is now tracked as being in the living room
     * </pre>
     *
     * <p>
     * If the specified Sim is already present in the location, the method
     * should avoid duplication. This provides safe handling for cases where
     * multiple move attempts are made for the same Sim.
     * </p>
     *
     * @param sim the {@link Sim} to move into this location
     */
    void moveTo(Sim sim);

}
