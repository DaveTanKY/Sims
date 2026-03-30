package sims.tests;

import sims.needs.Need;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class NeedTest {

    private Need defaultNeed;
    private Need customDecayNeed;
    private Need fullCustomNeed;

    @Before
    public void setUp() {
        defaultNeed = new Need();
        customDecayNeed = new Need(0.3);
        fullCustomNeed = new Need(50, 1, 20.0);
    }

    /**
     * Tests the default constructor.
     * Verifies that the need is initialized with default value of 80 and decay rate of 0.5.
     */
    @Test
    public void testDefaultConstructor() {
        assertEquals(80.0, defaultNeed.getValue(), 0.001);
        assertEquals(0.5, defaultNeed.getDecayRate(), 0.001);
    }

    /**
     * Tests the constructor with custom decay rate.
     * Verifies that the need is initialized with default value of 80 and the specified decay rate.
     */
    @Test
    public void testCustomDecayConstructor() {
        assertEquals(80.0, customDecayNeed.getValue(), 0.001);
        assertEquals(0.3, customDecayNeed.getDecayRate(), 0.001);
    }

    /**
     * Tests the full custom constructor.
     * Verifies that the need is initialized with the specified value, decay rate, and threshold.
     */
    @Test
    public void testFullCustomConstructor() {
        assertEquals(50.0, fullCustomNeed.getValue(), 0.001);
        assertEquals(1.0, fullCustomNeed.getDecayRate(), 0.001);
    }

    /**
     * Tests the getValue method.
     * Ensures that the correct need values are returned for different need instances.
     */
    @Test
    public void testGetValue() {
        assertEquals(80.0, defaultNeed.getValue(), 0.001);
        assertEquals(80.0, customDecayNeed.getValue(), 0.001);
        assertEquals(50.0, fullCustomNeed.getValue(), 0.001);
    }

    /**
     * Tests the getDecayRate method.
     * Ensures that the correct decay rates are returned for different need instances.
     */
    @Test
    public void testGetDecayRate() {
        assertEquals(0.5, defaultNeed.getDecayRate(), 0.001);
        assertEquals(0.3, customDecayNeed.getDecayRate(), 0.001);
        assertEquals(1.0, fullCustomNeed.getDecayRate(), 0.001);
    }

    /**
     * Tests the setValue method with a normal increase.
     * Verifies that adding a positive value increases the need correctly.
     */
    @Test
    public void testSetValueNormalIncrease() {
        defaultNeed.setValue(10.0);
        assertEquals(90.0, defaultNeed.getValue(), 0.001);
    }

    /**
     * Tests the setValue method with a normal decrease.
     * Verifies that subtracting a positive value decreases the need correctly.
     */
    @Test
    public void testSetValueNormalDecrease() {
        defaultNeed.setValue(-10.0);
        assertEquals(70.0, defaultNeed.getValue(), 0.001);
    }

    /**
     * Tests the setValue method clamping to maximum.
     * Verifies that the value is clamped to 100 when it would exceed the maximum.
     */
    @Test
    public void testSetValueClampToMax() {
        defaultNeed.setValue(25.0); // 80 + 25 = 105, should clamp to 100
        assertEquals(100.0, defaultNeed.getValue(), 0.001);
    }

    /**
     * Tests the setValue method clamping to minimum.
     * Verifies that the value is clamped to 0 when it would go below the minimum.
     */
    @Test
    public void testSetValueClampToMin() {
        Need lowNeed = new Need(10, 1, 20.0);
        lowNeed.setValue(-15.0); // 10 - 15 = -5, should clamp to 0
        assertEquals(0.0, lowNeed.getValue(), 0.001);
    }

    /**
     * Tests the setValue method setting to exact maximum.
     * Verifies that setting to exactly 100 is allowed.
     */
    @Test
    public void testSetValueExactMax() {
        Need ninetyNeed = new Need(90, 1, 20.0);
        ninetyNeed.setValue(10.0); // 90 + 10 = 100, should be exactly 100
        assertEquals(100.0, ninetyNeed.getValue(), 0.001);
    }

    /**
     * Tests the setValue method setting to exact minimum.
     * Verifies that setting to exactly 0 is allowed.
     */
    @Test
    public void testSetValueExactMin() {
        Need tenNeed = new Need(10, 1, 20.0);
        tenNeed.setValue(-10.0); // 10 - 10 = 0, should be exactly 0
        assertEquals(0.0, tenNeed.getValue(), 0.001);
    }

    /**
     * Tests the performDecay method with default settings.
     * Verifies that decay reduces the value by the decay rate and returns false for non-critical state.
     */
    @Test
    public void testPerformDecayDefault() {
        // Default need: value=80, decayRate=0.5, threshold=30
        boolean isCritical = defaultNeed.performDecay();
        assertEquals(79.5, defaultNeed.getValue(), 0.001); // 80 - 0.5 = 79.5
        assertFalse(isCritical); // 79.5 > 30
    }

    /**
     * Tests the performDecay method with custom decay rate.
     * Verifies that decay uses the custom rate and returns false for non-critical state.
     */
    @Test
    public void testPerformDecayCustomRate() {
        // Custom decay need: value=80, decayRate=0.3
        boolean isCritical = customDecayNeed.performDecay();
        assertEquals(79.7, customDecayNeed.getValue(), 0.001); // 80 - 0.3 = 79.7
        assertFalse(isCritical);
    }

    /**
     * Tests the performDecay method when value goes below threshold.
     * Verifies that decay reduces value and returns true for critical state.
     */
    @Test
    public void testPerformDecayBelowThreshold() {
        // Create need close to threshold: value=35, decayRate=10, threshold=30
        Need criticalNeed = new Need(35, 10, 30.0);
        boolean isCritical = criticalNeed.performDecay();
        assertEquals(25.0, criticalNeed.getValue(), 0.001); // 35 - 10 = 25
        assertTrue(isCritical); // 25 < 30
    }

    /**
     * Tests the performDecay method when value is at threshold.
     * Verifies that decay reduces value below threshold and returns true for critical state.
     */
    @Test
    public void testPerformDecayAtThreshold() {
        // Create need at threshold: value=30, decayRate=0.1, threshold=30
        Need atThresholdNeed = new Need(30, 1, 30.0);
        boolean isCritical = atThresholdNeed.performDecay();
        assertEquals(29.0, atThresholdNeed.getValue(), 0.001); // 30 - 1 = 29
        assertTrue(isCritical); // 29 < 30
    }

    /**
     * Tests the performDecay method over multiple decays.
     * Verifies progressive decay and the transition to critical state.
     */
    @Test
    public void testPerformDecayMultipleTimes() {
        Need testNeed = new Need(50, 5, 30.0);
        // First decay: 50 - 5 = 45, not critical
        boolean isCritical1 = testNeed.performDecay();
        assertEquals(45.0, testNeed.getValue(), 0.001);
        assertFalse(isCritical1);

        // Second decay: 45 - 5 = 40, not critical
        boolean isCritical2 = testNeed.performDecay();
        assertEquals(40.0, testNeed.getValue(), 0.001);
        assertFalse(isCritical2);

        // Third decay: 40 - 5 = 35, not critical
        boolean isCritical3 = testNeed.performDecay();
        assertEquals(35.0, testNeed.getValue(), 0.001);
        assertFalse(isCritical3);

        // Fourth decay: 35 - 5 = 30, not critical (30 == threshold, but < means below)
        boolean isCritical4 = testNeed.performDecay();
        assertEquals(30.0, testNeed.getValue(), 0.001);
        assertFalse(isCritical4); // 30 is not < 30

        // Fifth decay: 30 - 5 = 25, critical
        boolean isCritical5 = testNeed.performDecay();
        assertEquals(25.0, testNeed.getValue(), 0.001);
        assertTrue(isCritical5);
    }

    /**
     * Tests the performDecay method with clamping to minimum.
     * Verifies that decay clamps the value to 0 and returns true for critical state.
     */
    @Test
    public void testPerformDecayClamping() {
        // Test decay that would go below 0
        Need lowNeed = new Need(2, 5, 30.0);
        boolean isCritical = lowNeed.performDecay();
        assertEquals(0.0, lowNeed.getValue(), 0.001); // 2 - 5 = -3, clamped to 0
        assertTrue(isCritical); // 0 < 30
    }

    /**
     * Tests the performDecay method with zero decay rate.
     * Verifies that no decay occurs and returns false for non-critical state.
     */
    @Test
    public void testZeroDecayRate() {
        Need noDecayNeed = new Need(50, 0, 30.0);
        boolean isCritical = noDecayNeed.performDecay();
        assertEquals(50.0, noDecayNeed.getValue(), 0.001); // No change
        assertFalse(isCritical);
    }
}