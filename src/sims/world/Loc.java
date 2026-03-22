package sims.world;
import sims.actions.Activity;
import  sims.entity.Sim;
import java.util.ArrayList;
import java.util.List;

public abstract class Loc {
    private String name;
    private List<Activity> activityList = new ArrayList<>();
    private List<Sim> locSimList = new ArrayList<>();

    public Loc(String locName)
    {
        this.name = locName;
    }
    public List<Activity> getActivity() {
        return this.activityList;
    }

    public void addSim(Sim sim)
    {
        locSimList.add(sim);
    }

    public List<Sim> getLocSimList()
    {
        return locSimList;
    }

    public void removeSim(Sim sim)
    {
         locSimList.remove(sim);
    }

    public void addActivity(Activity activity) {
        this.activityList.add(activity);
    }

    public String getName() {
        return name;
    }

    public abstract void moveTo(Sim sim);

    public void setName(String name) {
        this.name = name;
    }
}
