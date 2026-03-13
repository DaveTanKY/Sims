package sims.entity;

import sims.career.Career;
import sims.needs.need;
import sims.world.Home;
import sims.world.Loc;

import java.util.HashMap;
import java.util.Map;

public class Sim {
    // PARENT CLASS
    private final String name;
    private final int UUID;
    private Career career;
    private int age;
    //simStats
    //relationship map
    private Map<String, need> needDict = new HashMap<>();
    //inventory
    private Home home;
    private Loc currentLocation;

    public Sim(String name, int UUID, int age){
        this.name = name;
        this.UUID = UUID;
        this.age = age;
        this.needDict.put("Hunger", new need(80, 10));
    }

    public String getName() {
        return name;
    }

    public int getUUID() {
        return UUID;
    }

    public Map<String, need> getNeeds() {
        return needDict;
    }

    public void setNeeds(String need, int value) {
        needDict.get(need).setValue(value);
    }

    public Loc getLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Loc currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home){
        this.home = home;
    }

    public Career getCareer()
    {
        return career;
    }

    public void setCareer(Career career)
    {
        this.career = career;
    }
}
