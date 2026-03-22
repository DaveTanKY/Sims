package sims.entity;

import sims.actions.Activity;
import sims.career.Career;
import sims.needs.need;
import sims.world.Home;
import sims.world.HomeLocation;
import sims.world.Loc;

import java.util.HashMap;
import java.util.Map;

public class Sim {
    // PARENT CLASS
    private final String name;
    private final int UUID;
    private Career career;
    private int age;
    private int activityEnd = -1;
    //simStats
    //relationship map
    private Map<String, need> needDict = new HashMap<>();
    //inventory
    private Home home;
    private Loc currentLocation;
    private double bank;
    private Map<Integer, Relationship> relationshipMap = new HashMap<>();

    public Sim(String name, int UUID, int age){
        this.name = name;
        this.UUID = UUID;
        this.age = age;
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

    public double getBank()
    {
        return bank;
    }

    public void updateBank (double cost)
    {
        bank -= cost;
    }

    public void addRelationship(int uuid, Relationship relationship)
    {
        relationshipMap.put(uuid, relationship);
    }

    public Map<Integer, Relationship> getRelationshipMap() {
        return relationshipMap;
    }

    public void updateNeeds(String need, double value) {
        System.out.println(activityEnd);
        if(need != "Salary")
        {
            needDict.get(need).setValue(value);
        }
        else
        {
            System.out.println("Bank previous : " + bank);
            bank += career.getSalary()+career.getBonus();
            career.earnXP();
            System.out.println("Bank after : " + bank);
        }

    }

    public void setNeeds(Map<String, need> needDict){
        this.needDict = needDict;
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

    public void setActivityEnd(int duration, int time)
    {
        activityEnd = time + duration;
    }

    public void autoPlay(String need, int time) {

        for (HomeLocation homeloc : home.getHomeLocation())
        {
            for(Activity activity : homeloc.getActivity())
            {
                if (activity.getImpactedNeed() == need)
                {
                    homeloc.moveTo(this);
                    activity.performActivity(this);
                    activityEnd = time + activity.getDuration();
                    System.out.println(this.getName() + "Performed : " + activity.getName() + " at world time : " + time + ". activity end time : " + activityEnd) ;
                    return;
                }
            }

        }
    }

    public void performDecay(String need, Sim currentSim, int time)
    {
            for (Map.Entry<String, need> entry : needDict.entrySet()) {
                if (currentSim == this) {
                    if (entry.getKey() != need) {
                        entry.getValue().performDecay();
                    }
                } else {
                    //System.out.println("Current Sim: " + this.getName() + "\nCurrent Need: " + entry.getKey() + ": " + entry.getValue().getValue() + "\nCurrent Time : " + time + "\nActivity end time : " + activityEnd);
                    if (entry.getValue().performDecay() && time > activityEnd) {
                        autoPlay(entry.getKey(), time);
                    }
                }
            }

    }
}
