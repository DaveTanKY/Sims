package sims.needs;

public class need {
    private int value;
    private int decayRate;

    //default
    public need()
    {
        this.value = 80;
        this.decayRate = 10;
    }

    //specific value
    public need(int value, int decayRate)
    {
        this.value = value;
        this.decayRate = decayRate;
    }

    public int getValue() {
        return value;
    }

    public int getDecayRate() {
        return decayRate;
    }

    public void setValue(int modify) {
        if (modify + this.value >= 100)
        {
            this.value = 100;
        }
        else if(modify+this.value <= 0)
        {
            this.value = 0;
        }
        else
        {
            this.value += modify;
        }
    }
}
