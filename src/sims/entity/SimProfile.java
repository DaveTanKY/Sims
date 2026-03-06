package sims.entity;

import sims.needs.Needs;
import sims.career.Career;
import sims.world.Location;

public abstract class SimProfile {

    // PARENT CLASS
    private final String name;
    private final Needs needs;
    /* encapsulation, child classes cannot see the Needs class so only SimProfile has access to
    the Needs class. This prevents the child classes from tampering with the Needs class.*/
    private Career career;
    private Location room;

    public SimProfile(String name, Career career, Needs customNeeds){
        this.name = name;
        this.career = career;
        this.needs = customNeeds;

    }

    public String getName(){
        return name; // returns the name of the current Sim
    }

    public Career getCareer(){
        return career;
    }

    public void setCareer(Career career){
        this.career = career;
    }

    public Needs getNeeds(){
        return needs; // returns the needs of the current Sim
    }

    public void showNeeds() {
        System.out.println(name + "'s Needs:");
        System.out.println("Energy: " + needs.getEnergy());
        System.out.println("Hunger: " + needs.getHunger());
        System.out.println("Fun: " + needs.getFun());
        System.out.println("Social: " + needs.getSocial());
        System.out.println("Hygiene: " + needs.getHygiene());
        System.out.println("Bladder: " + needs.getBladder());
    }

    public void moveTo(Location room){
        this.room = room;
        System.out.println(name + " has have moved to " + room.getLocationName());
    }

    public Location getRoom(){
        return room;
    }


}
