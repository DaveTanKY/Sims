package sims.world;

import sims.actions.Activity;

public class HomeUpgrade {
    private String location;
    private String name;
    private int price;
    private Boolean hasUpgraded;
    private Activity action;

    public HomeUpgrade(String location, String name, int price, Activity action) {
        this.location = location;
        this.name = name;
        this.price = price;
        hasUpgraded = false;
        this.action = action;
    }


    public Boolean getUpgrade() {
        return hasUpgraded;
    }

    public String getName() {
        return name;
    }

    public int getPrice () {
        return price;
    }

    public void purchaseUpgrade(){
        hasUpgraded = true;
    }

    public String getLocation() {
        return location;
    }

    public Activity getActivity() {
        return action;
    }


}
