package sims.career;

import sims.actions.ProgressionInterface;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a career path for a Sim.
 * A {@code Career} defines the Sim's job title, sector, salary, level, and experience points (XP).
 * Sims can earn XP through work, which increases their career level and provides salary bonuses.
 *
 * <p>This class provides methods to manage career progression, calculate bonuses,
 * and update career attributes such as title, sector, and salary.</p>
 */
public class Career implements ProgressionInterface {

    /** The sector or industry of the career. */
    private String sector;

    /** The base salary of the career. */
    private int salary;

    /** The current level of the career. */
    private int level;

    /** The accumulated experience points in the career. */
    private int xp;

    /**
     * Constructs a default {@code Career} with:
     * <ul>
     *   <li>Title = "Unemployed"</li>
     *   <li>Sector = "Unemployed"</li>
     *   <li>Salary = 0</li>
     *   <li>Level = 1</li>
     *   <li>XP = 0</li>
     * </ul>
     */
    public Career(){
        sector = "Unemployed";
        salary = 0;
        level = 1;
        xp = 0;
    }

    /**
     * Constructs a {@code Career} with the specified title, sector, and salary.
     * Level is initialized to 1 and XP to 0.
     *
     * @param title  the job title
     * @param sector the career sector
     * @param salary the base salary
     */
    public Career(String title, String sector, int salary) {
        this.sector = sector;
        this.salary = salary;
        level = 1;
        xp = 0;
    }


    /**
     * Returns the current career level.
     *
     * @return the career level
     */
    public int getLevel()
    {
        return level;
    }

    /**
     * Calculates the bonus based on the career level.
     * The bonus is a percentage of the salary equal to {@code level / 100}.
     *
     * @return the salary bonus
     */
    public double getBonus()
    {
        return salary * ((double) level /100);
    }

    /**
     * Increases career XP by 20 points. If XP reaches or exceeds 100,
     * the career level increases by 1 and XP is reset modulo 100.
     */
    public void earnXP()
    {
        xp += 20;
        if(xp >= 100)
        {
            level ++;
            xp %= 100;
        }
    }

    /**
     * Returns the job sectir of the career.
     *
     * @return the career sector
     */
    public String getSector()
    {
        return sector;
    }

    /**
     * Returns the base salary of the career.
     *
     * @return the salary
     */
    public int getSalary() {
        return salary;
    }

    /**
     * Sets the sector of the career.
     *
     * @param sector the new career sector
     */
    public void setSector(String sector)
    {
        this.sector = sector;
    }

    /**
     * Sets the base salary of the career.
     *
     * @param salary the new salary
     */
    public void setSalary(int salary)
    {
        this.salary = salary;
    }
}
