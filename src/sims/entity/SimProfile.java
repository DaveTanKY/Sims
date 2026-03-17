package sims.entity;

import sims.needs.Needs;
import sims.career.Career;
import sims.world.Home;
import sims.world.Loc;
import java.util.HashMap;
import java.util.Map;

public class SimProfile {

    // PARENT CLASS
    private final String name;
    private final Needs needs;
    private final int UUID;
    /* encapsulation, child classes cannot see the Needs class so only SimProfile has access to
    the Needs class. This prevents the child classes from tampering with the Needs class.*/
    private Career career;
    private Home home;
    private Loc currentLocation;


    public SimProfile(String name, Career career, Needs customNeeds, int UUID){
        this.name = name;
        this.UUID = UUID;
        this.career = career;
        this.needs = customNeeds;

    }

    public String getName(){
        return name; // returns the name of the current Sim
    }

    public int getUUID(){
        return UUID;
    }

    public Career getCareer(){
        return career;
    }

    public void setCareer(Career career){
        this.career = career;
    }

    public Loc getLocation(){
        return currentLocation;
    }

    public void setCurrentLocation(Loc currentLocation){
        this.currentLocation = currentLocation;
    }

    public Home getHome(){
        return home;
    }

    public void setHome(Home home){
        this.home = home;
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
        System.out.println(" ");
    }

    public void setNeeds(String needName, int value) {
        switch (needName.toLowerCase()) {
            case "energy":
                needs.setEnergy(needs.getEnergy() + value);
                break;
            case "social":
                needs.setSocial(needs.getSocial() + value);
                break;
            case "hygiene":
                needs.setHygiene(needs.getHygiene() + value);
                break;
            case "bladder":
                needs.setBladder(needs.getBladder() + value);
                break;
            case "hunger":
                needs.setHunger(needs.getHunger() + value);
                break;
            case "fun":
                needs.setFun(needs.getFun() + value);
                break;
            default:
                System.out.println("Invalid need: " + needName);
        }
    }



}
