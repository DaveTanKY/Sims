package sims.needs;


/**
 * Represents a basic need in the Sims world, such as hunger, energy, or fun.
 * A {@code need} has a value, a decay rate, and a threshold that determines
 * when the need becomes critical.
 *
 * <p>This class provides methods to adjust the need's value, apply decay over time,
 * and check whether the need has fallen below its threshold.</p>
 */
public class need {
    /** The current value of the need (0–100). */
    private double value;

    /** The rate at which the need decays over time. */
    private double decayRate;

    /** The threshold below which the need is considered critical. */
    private double threshold;

    /**
     * Constructs a default {@code need} with:
     * <ul>
     *   <li>Initial value = 80</li>
     *   <li>Decay rate = 0.5</li>
     *   <li>Threshold = 30</li>
     * </ul>
     */
    public need()
    {
        this.value = 80;
        this.decayRate = 0.5;
        this.threshold = 30;
    }

    /**
     * Constructs a {@code need} with a custom decay rate.
     * The initial value is set to 80 and the threshold to 30.
     *
     * @param rate the decay rate
     */
    public need(double rate)
    {
        value = 80;
        decayRate = rate;
        threshold = 30;
    }

    /**
     * Constructs a {@code need} with specific values.
     *
     * @param value      the initial value of the need
     * @param decayRate  the rate at which the need decays
     * @param threshold  the threshold below which the need is critical
     */
    public need(int value, int decayRate, double threshold)
    {
        this.value = value;
        this.decayRate = decayRate;
        this.threshold = threshold;
    }

    /**
     * Returns the current value of the need.
     *
     * @return the need value
     */
    public double getValue() {
        return value;
    }

    /**
     * Returns the decay rate of the need.
     *
     * @return the decay rate
     */
    public double getDecayRate() {
        return decayRate;
    }


    /**
     * Modifies the value of the need by the specified amount.
     * The value is clamped between 0 and 100.
     *
     * @param modify the amount to adjust the need value
     */
    public void setValue(Double modify) {
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

    /**
     * Applies decay to the need by reducing its value according to the decay rate.
     * Returns whether the need has fallen below its threshold.
     *
     * @return {@code true} if the need value is below the threshold, {@code false} otherwise
     */
    public boolean performDecay()
    {
        setValue(-(decayRate));
        return value < threshold;
    }
}
