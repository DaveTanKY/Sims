package sims.world;

import sims.entity.*;

import java.util.ArrayList;
import java.util.List;

public class HomeLocation extends Loc{
    private List<HomeUpgrade> upgradeList = new ArrayList<>();
    private Home home;

    public HomeLocation(String locName, Home home) {
        super(locName);
        this.home = home;
    }


    public void addUpgrade(HomeUpgrade upgrade)
    {
        upgradeList.add(upgrade);
    }

    public List<HomeUpgrade> getUpgradeList() {
        return upgradeList;
    }

    public Home getHome()
    {
        return home;
    }

    @Override
    public void moveTo(Sim sim) {
        sim.setCurrentLocation(this);
    }
    public void setHome(Home home)
    {
        this.home = home;
    }
}

