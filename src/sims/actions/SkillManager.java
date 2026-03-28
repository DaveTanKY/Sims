package sims.actions;

public class SkillManager implements ProgressionInterface {
    int level;
    int exp;

    public SkillManager()
    {
        this.level = 1;
    }

    public int getLevel() {
        return level;
    }

    public void earnXP()
    {
        exp += 25;
        if(exp >= 100)
        {
            exp %= 100;
            level ++;
        }
    }
}
