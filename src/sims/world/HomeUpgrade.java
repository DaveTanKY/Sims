package sims.world;

import sims.actions.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an upgrade that can be applied to a {@link HomeLocation}.
 * A {@code HomeUpgrade} has a name, price, associated location, and an
 * activity that becomes available once the upgrade is purchased.
 *
 * <p>This class tracks whether the upgrade has been purchased and provides
 * access to its properties such as name, price, and related activity.</p>
 */
public class HomeUpgrade {
    /** The location (e.g., room name) where this upgrade applies. */
    private String location;

    /** The name of the upgrade. */
    private String name;

    /** The price of the upgrade. */
    private int price;

    /** Flag indicating whether the upgrade has been purchased. */
    private Boolean hasUpgraded;

    /** The activity associated with this upgrade. */
    private Activity action;

    /**
     * Constructs a new {@code HomeUpgrade} with the specified details.
     *
     * @param location the location where the upgrade applies
     * @param name     the name of the upgrade
     * @param price    the price of the upgrade
     * @param action   the activity unlocked by this upgrade
     */
    public HomeUpgrade(String location, String name, int price, Activity action) {
        this.location = location;
        this.name = name;
        this.price = price;
        hasUpgraded = false;
        this.action = action;
    }


    /**
     * Returns whether this upgrade has been purchased.
     *
     * @return {@code true} if purchased, {@code false} otherwise
     */
    public Boolean getUpgrade() {
        return hasUpgraded;
    }

    /**
     * Returns the name of this upgrade.
     *
     * @return the upgrade name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of this upgrade.
     *
     * @return the upgrade price
     */
    public int getPrice () {
        return price;
    }

    /**
     * Marks this upgrade as purchased.
     */
    public void purchaseUpgrade(){
        hasUpgraded = true;
    }

    /**
     * Returns the location where this upgrade applies.
     *
     * @return the location name
     */
    public String getLocation() {
        return location;
    }

    /**
     * Returns the activity associated with this upgrade.
     *
     * @return the related {@link Activity}
     */
    public Activity getActivity() {
        return action;
    }


}
