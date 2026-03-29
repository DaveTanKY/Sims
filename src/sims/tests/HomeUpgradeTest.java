package sims.tests;

import sims.world.HomeUpgrade;
import sims.actions.Activity;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class HomeUpgradeTest {

    private HomeUpgrade testUpgrade;
    private Activity testActivity;

    @Before
    public void setUp() {
        testActivity = new Activity("Cook", 45, "Fun", 15);
        testUpgrade = new HomeUpgrade("Kitchen", "Stove", 500, testActivity);
    }

    @Test
    public void testConstructor() {
        assertEquals("Kitchen", testUpgrade.getLocation());
        assertEquals("Stove", testUpgrade.getName());
        assertEquals(500, testUpgrade.getPrice());
        assertEquals(testActivity, testUpgrade.getActivity());
        assertFalse(testUpgrade.getUpgrade());
    }

    @Test
    public void testGetUpgrade() {
        assertFalse(testUpgrade.getUpgrade());
    }

    @Test
    public void testGetName() {
        assertEquals("Stove", testUpgrade.getName());
    }

    @Test
    public void testGetPrice() {
        assertEquals(500, testUpgrade.getPrice());
    }

    @Test
    public void testPurchaseUpgrade() {
        assertFalse(testUpgrade.getUpgrade());
        testUpgrade.purchaseUpgrade();
        assertTrue(testUpgrade.getUpgrade());
    }

    @Test
    public void testGetLocation() {
        assertEquals("Kitchen", testUpgrade.getLocation());
    }

    @Test
    public void testGetActivity() {
        assertEquals(testActivity, testUpgrade.getActivity());
    }

    @Test
    public void testMultiplePurchaseCalls() {
        assertFalse(testUpgrade.getUpgrade());

        testUpgrade.purchaseUpgrade();
        assertTrue(testUpgrade.getUpgrade());

        // Calling purchase again should not change state
        testUpgrade.purchaseUpgrade();
        assertTrue(testUpgrade.getUpgrade());
    }
}