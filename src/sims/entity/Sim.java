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

    public void updateNeeds(String need, double value) {
        needDict.get(need).setValue(value);
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

    public void autoPlay(String need) {

        for (HomeLocation homeloc : home.getHomeLocation())
        {
            for(Activity activity : homeloc.getActivity())
            {
                if (activity.getImpactedNeed() == need)
                {
                    homeloc.moveTo(this);
                    activity.performActivity(this);

                    System.out.println(this.getName() + "Performed : " + activity.getName());
                }
            }

        }
    }

    public void performDecay(int duration, String need, Sim currentSim)
    {
        for(Map.Entry<String , need> entry : needDict.entrySet())
        {
            if(currentSim == this)
            {
                if(entry.getKey() != need)
                {
                    for (int i = 0; i< duration; i++)
                    {
                        entry.getValue().performDecay();
                    }
                }
            }
            else
            {
                for (int i =0;i < duration; i++)
                {
                    if(entry.getValue().performDecay())
                    {
                        autoPlay(entry.getKey());
                    }
                }

            }
        }
    }
}
