package sims.world;

import sims.entity.Sim;

import java.util.Map;
import java.util.HashMap;

public class OutsideLocation extends Loc{

    private String comparison;
    private String requirement;

    public OutsideLocation(String name, String comparison, String requirement)
    {
        super(name);
        this.comparison = comparison;
        this.requirement = requirement;
    }

    @Override
    public void moveTo(Sim sim) {
        sim.setCurrentLocation(this);
    }

    public String getComparison()
    {
        return comparison;
    }

    public String getRequirement ()
    {
        return requirement;
    }
}
