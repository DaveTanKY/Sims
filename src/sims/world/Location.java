package sims.world;

import sims.entity.*;
import java.util.Scanner;

public abstract class Location {
    private final String name;

    public Location(String name){
        this.name = name;
    }

    public String getLocationName(){
        return name;
    }

    public abstract void showOptions();

    /* This method will be used to handle location actions.
       Will return false if player wants to return to navigation menu */
    public abstract boolean handleLocActions(SimProfile sim, Scanner scanner);


}
