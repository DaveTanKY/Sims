package sims.tests;

import sims.needs.need;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class NeedTest {

    private need defaultNeed;
    private need customDecayNeed;
    private need fullCustomNeed;

    @Before
    public void setUp() {
        defaultNeed = new need();
        customDecayNeed = new need(0.3);
        fullCustomNeed = new need(50, 1, 20.0);
    }

    @Test
    public void testDefaultConstructor() {
        assertEquals(80.0, defaultNeed.getValue(), 0.001);
        assertEquals(0.5, defaultNeed.getDecayRate(), 0.001);
    }

    @Test
    public void testCustomDecayConstructor() {
        assertEquals(80.0, customDecayNeed.getValue(), 0.001);
        assertEquals(0.3, customDecayNeed.getDecayRate(), 0.001);
    }

    @Test
    public void testFullCustomConstructor() {
        assertEquals(50.0, fullCustomNeed.getValue(), 0.001);
        assertEquals(1.0, fullCustomNeed.getDecayRate(), 0.001);
    }

    @Test
    public void testGetValue() {
        assertEquals(80.0, defaultNeed.getValue(), 0.001);
        assertEquals(80.0, customDecayNeed.getValue(), 0.001);
        assertEquals(50.0, fullCustomNeed.getValue(), 0.001);
    }

    @Test
    public void testGetDecayRate() {
        assertEquals(0.5, defaultNeed.getDecayRate(), 0.001);
        assertEquals(0.3, customDecayNeed.getDecayRate(), 0.001);
        assertEquals(1.0, fullCustomNeed.getDecayRate(), 0.001);
    }

    @Test
    public void testSetValueNormalIncrease() {
        defaultNeed.setValue(10.0);
        assertEquals(90.0, defaultNeed.getValue(), 0.001);
    }

    @Test
    public void testSetValueNormalDecrease() {
        defaultNeed.setValue(-10.0);
        assertEquals(70.0, defaultNeed.getValue(), 0.001);
    }

    @Test
    public void testSetValueClampToMax() {
        defaultNeed.setValue(25.0); // 80 + 25 = 105, should clamp to 100
        assertEquals(100.0, defaultNeed.getValue(), 0.001);
    }

    @Test
    public void testSetValueClampToMin() {
        need lowNeed = new need(10, 1, 20.0);
        lowNeed.setValue(-15.0); // 10 - 15 = -5, should clamp to 0
        assertEquals(0.0, lowNeed.getValue(), 0.001);
    }

    @Test
    public void testSetValueExactMax() {
        need ninetyNeed = new need(90, 1, 20.0);
        ninetyNeed.setValue(10.0); // 90 + 10 = 100, should be exactly 100
        assertEquals(100.0, ninetyNeed.getValue(), 0.001);
    }

    @Test
    public void testSetValueExactMin() {
        need tenNeed = new need(10, 1, 20.0);
        tenNeed.setValue(-10.0); // 10 - 10 = 0, should be exactly 0
        assertEquals(0.0, tenNeed.getValue(), 0.001);
    }

    @Test
    public void testPerformDecayDefault() {
        // Default need: value=80, decayRate=0.5, threshold=30
        boolean isCritical = defaultNeed.performDecay();
        assertEquals(79.5, defaultNeed.getValue(), 0.001); // 80 - 0.5 = 79.5
        assertFalse(isCritical); // 79.5 > 30
    }

    @Test
    public void testPerformDecayCustomRate() {
        // Custom decay need: value=80, decayRate=0.3
        boolean isCritical = customDecayNeed.performDecay();
        assertEquals(79.7, customDecayNeed.getValue(), 0.001); // 80 - 0.3 = 79.7
        assertFalse(isCritical);
    }

    @Test
    public void testPerformDecayBelowThreshold() {
        // Create need close to threshold: value=35, decayRate=10, threshold=30
        need criticalNeed = new need(35, 10, 30.0);
        boolean isCritical = criticalNeed.performDecay();
        assertEquals(25.0, criticalNeed.getValue(), 0.001); // 35 - 10 = 25
        assertTrue(isCritical); // 25 < 30
    }

    @Test
    public void testPerformDecayAtThreshold() {
        // Create need at threshold: value=30, decayRate=0.1, threshold=30
        need atThresholdNeed = new need(30, 1, 30.0);
        boolean isCritical = atThresholdNeed.performDecay();
        assertEquals(29.0, atThresholdNeed.getValue(), 0.001); // 30 - 1 = 29
        assertTrue(isCritical); // 29 < 30
    }

    @Test
    public void testPerformDecayMultipleTimes() {
        need testNeed = new need(50, 5, 30.0);
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

    @Test
    public void testPerformDecayClamping() {
        // Test decay that would go below 0
        need lowNeed = new need(2, 5, 30.0);
        boolean isCritical = lowNeed.performDecay();
        assertEquals(0.0, lowNeed.getValue(), 0.001); // 2 - 5 = -3, clamped to 0
        assertTrue(isCritical); // 0 < 30
    }

    @Test
    public void testZeroDecayRate() {
        need noDecayNeed = new need(50, 0, 30.0);
        boolean isCritical = noDecayNeed.performDecay();
        assertEquals(50.0, noDecayNeed.getValue(), 0.001); // No change
        assertFalse(isCritical);
    }
}