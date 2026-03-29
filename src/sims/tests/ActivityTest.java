package sims.tests;

import sims.actions.Activity;
import sims.career.Career;
import sims.entity.Sim;
import sims.gameEngine.SimFactory;
import sims.world.Home;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class ActivityTest {

    private Activity eatNoCost;
    private Activity ShopWithCost;
    private Sim testSim;

    @Before
    public void setUp() {
        // Create activities for testing
        eatNoCost = new Activity("Eat", 30, "Hunger", 20);
        ShopWithCost = new Activity("Shop", 60, "Fun", 10, 50.0);

        // Create a test Sim
        Home newHome = SimFactory.defaultHome("test");
        Career it = new Career("Developer", "IT", 2000);
        testSim = SimFactory.createSim("test", 0, 25, newHome, it);
    }

    @Test
    public void testConstructorNoCost() {
        assertEquals("Eat", eatNoCost.getName());
        assertEquals(30, eatNoCost.getDuration());
        assertEquals("Hunger", eatNoCost.getImpactedNeed());
        assertEquals(20, eatNoCost.getValue());
        assertEquals(0.0, eatNoCost.getCost(), 0.001);
    }

    @Test
    public void testConstructorWithCost() {
        assertEquals("Shop", ShopWithCost.getName());
        assertEquals(60, ShopWithCost.getDuration());
        assertEquals("Fun", ShopWithCost.getImpactedNeed());
        assertEquals(10, ShopWithCost.getValue());
        assertEquals(50.0, ShopWithCost.getCost(), 0.001);
    }

    @Test
    public void testGetName() {
        assertEquals("Eat", eatNoCost.getName());
        assertEquals("Shop", ShopWithCost.getName());
    }

    @Test
    public void testGetDuration() {
        assertEquals(30, eatNoCost.getDuration());
        assertEquals(60, ShopWithCost.getDuration());
    }

    @Test
    public void testGetImpactedNeed() {
        assertEquals("Hunger", eatNoCost.getImpactedNeed());
        assertEquals("Fun", ShopWithCost.getImpactedNeed());
    }

    @Test
    public void testGetValue() {
        assertEquals(20, eatNoCost.getValue());
        assertEquals(10, ShopWithCost.getValue());
    }

    @Test
    public void testGetCost() {
        assertEquals(0.0, eatNoCost.getCost(), 0.001);
        assertEquals(50.0, ShopWithCost.getCost(), 0.001);
    }

    @Test
    public void testPerformActivity() {
        // Initial hunger value should be 80 (default)
        assertEquals(80.0, testSim.getNeeds().get("Hunger").getValue(), 0.001);

        // Perform eating activity (should increase hunger by 20)
        eatNoCost.performActivity(testSim);

        // Hunger should now be 100 (80 + 20, but capped at 100)
        assertEquals(100.0, testSim.getNeeds().get("Hunger").getValue(), 0.001);
    }
}