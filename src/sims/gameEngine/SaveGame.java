package sims.gameEngine;

import sims.entity.Sim;
import sims.world.Loc;

import javax.management.relation.Relation;
import java.io.*;

public class SaveGame {
    public static void saveGame(Sim sim, String filename)
    {
        saveSim(sim,filename);
    }

    public static void saveSim(Sim sim, String filename)
    {
        try(PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Sim Name: " + sim.getName());
            writer.println("Sim UUID: " + sim.getUUID());
            writer.println("Sim Age: " + sim.getAge());
            writer.println("Sim activityEnd: " + sim.getActivityEnd());
            saveHomeLocation(sim.getLocation(), writer);
            writer.println("Location: " + sim.getLocation().getName());
            writer.println("Bank: " + sim.getBank());
            writer.println("Stats:");
            writer.println("Social: " + sim.getNeeds().get("Social").getValue());
            writer.println("Hunger: " + sim.getNeeds().get("Hunger"));
            writer.println("Energy: " + sim.getNeeds().get("Energy"));
            writer.println("Bladder: " + sim.getNeeds().get("Bladder"));
            writer.println("Hygiene: " + sim.getNeeds().get("Hygiene"));
            writer.println("Fun: " + sim.getNeeds().get("Fun"));
        }
        catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    public static void saveHomeLocation(Loc location, PrintWriter writer)
    {
        writer.println("Location Name test: " + location.getName());
        writer.println("");
    }
}
