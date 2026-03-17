package sims.world;

import sims.entity.Sim;

import java.util.ArrayList;
import java.util.List;

public class Home extends Loc {
    private List<HomeLocation> homeLocation = new ArrayList<>();
    public Home(String homeName)
    {
        super(homeName);

    }

    public List<HomeLocation> getHomeLocation() {
        return this.homeLocation;
    }

    public void addHomeLocation (HomeLocation location) {
        homeLocation.add(location);
        location.setHome(this);
    }

    @Override
    public void moveTo(Sim sim){
        sim.setCurrentLocation(homeLocation.getFirst());
    }
}
