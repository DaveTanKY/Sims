package sims.world;

public abstract class Location {
    private final String name;

    public Location(String name){
        this.name = name;
    }

    public String getLocationName(){
        return name;
    }

    public abstract void showOptions();

}
