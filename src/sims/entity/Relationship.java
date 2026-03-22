package sims.entity;

public class Relationship {
    private Sim sim1;
    private Sim sim2;
    private int friendshipLevel;
    private int romanceLevel;

    public Relationship(Sim sim1 , Sim sim2)
    {
        this.sim1 = sim1;
        this.sim2 = sim2;
        this.friendshipLevel = 0;
        this.romanceLevel = 0;
    }

    public void increaseFriendship(int value)
    {
        friendshipLevel += value;
    }

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
