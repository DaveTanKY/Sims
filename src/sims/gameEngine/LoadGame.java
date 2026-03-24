package sims.gameEngine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoadGame {
    public static void loadGame(String filename)
    {
        try(BufferedReader reader = new BufferedReader(new FileReader(filename)))
        {
            String line;
            String name = null;
            String location = null;
            double bank = 0;

            double social = 0, hunger = 0, energy = 0 , bladder = 0, hygiene = 0, fun = 0;

            while((line = reader.readLine()) != null)
            {
                if (line.startsWith("Sim Name:")) {
                    name = line.split(":")[1].trim();
                } else if (line.startsWith("Location:")) {
                    location = line.split(":")[1].trim();
                } else if (line.startsWith("Bank:")) {
                    bank = Double.parseDouble(line.split(":")[1].trim());
                } else if (line.startsWith("Social:")) {
                    social = Double.parseDouble(line.split(":")[1].trim());
                } else if (line.startsWith("Hunger:")) {
                    hunger = Double.parseDouble(line.split(":")[1].trim());
                } else if (line.startsWith("Energy:")) {
                    energy = Double.parseDouble(line.split(":")[1].trim());
                } else if (line.startsWith("Bladder:")) {
                    bladder = Double.parseDouble(line.split(":")[1].trim());
                } else if (line.startsWith("Hygiene:")) {
                    hygiene = Double.parseDouble(line.split(":")[1].trim());
                } else if (line.startsWith("Fun:")) {
                    fun = Double.parseDouble(line.split(":")[1].trim());
                }
            }

            System.out.println("Game loaded from " + filename);
        }
        catch (IOException e)
        {
            System.out.println("Error loading game: " + e.getMessage());
        }
    }
}
