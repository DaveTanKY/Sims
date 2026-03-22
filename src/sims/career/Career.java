package sims.career;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

// PARENT CLASS

public class Career{

    private String title;
    private String sector;
    private int salary;
    private int level;
    private int xp;

    public Career(){
        title = "Unemployed";
        sector = "Unemployed";
        salary = 0;
        level = 1;
        xp = 0;
    }

    public Career(String title, String sector, int salary) {
        this.title = title;
        this.sector = sector;
        this.salary = salary;
        level = 1;
        xp = 0;
    }

    public int getLevel()
    {
        return level;
    }

    public double getBonus()
    {
        return salary * ((double) level /100);
    }
    public void earnXP()
    {
        xp += 20;
        if(xp >= 100)
        {
            level ++;
            xp %= 100;
        }
    }

    public String getTitle()
    {
        return title;
    }

    public String getSector()
    {
        return sector;
    }

    public int getSalary() {
        return salary;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setSector(String sector)
    {
        this.sector = sector;
    }

    public void setSalary(int salary)
    {
        this.salary = salary;
    }
}
