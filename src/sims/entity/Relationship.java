package sims.entity;

/**
 * Represents a relationship between two Sims.
 * A {@code Relationship} tracks the friendship level and experience points (XP)
 * between two Sims. As friendship XP increases, the friendship level rises.
 *
 * <p>This class provides methods to increase friendship, retrieve friendship
 * details, and determine the other Sim in the relationship.</p>
 */
public class Relationship {

    /** The first Sim in the relationship. */
    private Sim sim1;

    /** The second Sim in the relationship. */
    private Sim sim2;

    /** The current friendship level between the two Sims. */
    private int friendshipLevel;

    /** The accumulated friendship experience points. */
    private int friendshipXP;


    /**
     * Constructs a new {@code Relationship} between two Sims.
     * Friendship level and XP are initialized to 0.
     *
     * @param sim1 the first Sim
     * @param sim2 the second Sim
     */
    public Relationship(Sim sim1 , Sim sim2)
    {
        this.sim1 = sim1;
        this.sim2 = sim2;
        this.friendshipLevel = 0;
        this.friendshipXP = 0;
    }

    /**
     * Returns the current friendship level.
     *
     * @return the friendship level
     */
    public int getFriendshipLevel()
    {
        return friendshipLevel;
    }

    /**
     * Increases friendship XP by 25 points. If XP reaches or exceeds 100,
     * the friendship level increases by 1 and XP is reset modulo 100.
     */
    public void increaseFriendship()
    {
        friendshipXP += 25;
        if(friendshipXP >= 100)
        {
            friendshipLevel ++;
            friendshipXP %= 100;
        }
    }

    /**
     * Returns the other Sim in the relationship given one Sim.
     *
     * @param sim the Sim to compare
     * @return the other Sim in the relationship
     */
    public Sim getOtherSim(Sim sim)
    {
        if(sim == sim1)
        {
            return sim2;
        }
        else
        {
            return sim1;
        }
    }
}
