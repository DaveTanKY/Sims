package sims.actions;

import sims.entity.Sim;

public class Activity {
    private String name;
    private int duration;
    private final String impactedNeed;
    private final int value;

    public Activity(String name, int duration, String impactedNeed, int value){
        this.name = name;
        this.duration = duration;
        this.impactedNeed = impactedNeed;
        this.value = value;
    }

    public String getImpactedNeed() {
        return impactedNeed;
    }

    public void performActivity(Sim sim) {
        sim.updateNeeds(impactedNeed, value);
    }

    public String getName() {
        return name;
    }

    public int getDuration () {
        return duration;
    }

    public int getValue () {
        return value;
    }
}
