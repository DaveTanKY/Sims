package sims.tests;

import sims.world.Home;
import sims.world.HomeLocation;
import sims.world.HomeUpgrade;
import sims.actions.Activity;
import sims.entity.Sim;
import sims.career.Career;
import sims.gameEngine.SimFactory;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.List;

public class HomeLocationTest {

    private Home testHome;
    private HomeLocation testHomeLocation;
    private HomeUpgrade testUpgrade;
    private Activity testActivity;
    private Sim testSim;

    @Before
    public void setUp() {
        testHome = new Home("Test Home");
        testHomeLocation = new HomeLocation("Kitchen", testHome);
        testActivity = new Activity("Cook", 45, "Fun", 15);
        testUpgrade = new HomeUpgrade("Kitchen", "Stove", 500, testActivity);

        // Create a test Sim
        Home newHome = SimFactory.defaultHome("test");
        Career it = new Career("Developer", "IT", 2000);
        testSim = SimFactory.createSim("test", 0, 25, newHome, it);
    }

    @Test
    public void testConstructor() {
        assertEquals("Kitchen", testHomeLocation.getName());
        assertEquals(testHome, testHomeLocation.getHome());
        assertNotNull(testHomeLocation.getUpgradeList());
        assertTrue(testHomeLocation.getUpgradeList().isEmpty());
    }

    @Test
    public void testAddUpgrade() {
        testHomeLocation.addUpgrade(testUpgrade);
        List<HomeUpgrade> upgrades = testHomeLocation.getUpgradeList();
        assertEquals(1, upgrades.size());
        assertEquals(testUpgrade, upgrades.get(0));
    }

    @Test
    public void testGetUpgradeList() {
        assertNotNull(testHomeLocation.getUpgradeList());

        testHomeLocation.addUpgrade(testUpgrade);
        assertEquals(1, testHomeLocation.getUpgradeList().size());
    }

    @Test
    public void testGetHome() {
        assertEquals(testHome, testHomeLocation.getHome());
    }

    @Test
    public void testGetLocSimListDelegatesToHome() {
        // HomeLocation.getLocSimList() delegates to home.getLocSimList()
        assertEquals(testHome.getLocSimList(), testHomeLocation.getLocSimList());

        testHome.addSim(testSim);
        assertTrue(testHomeLocation.getLocSimList().contains(testSim));
    }

    @Test
    public void testRemoveSimDelegatesToHome() {
        testHome.addSim(testSim);
        assertTrue(testHomeLocation.getLocSimList().contains(testSim));

        // HomeLocation.removeSim calls home.removeSim
        testHomeLocation.removeSim(testSim);
        assertFalse(testHomeLocation.getLocSimList().contains(testSim));
    }

    @Test
    public void testSetHome() {
        Home newHome = new Home("New Home");
        testHomeLocation.setHome(newHome);
        assertEquals(newHome, testHomeLocation.getHome());
    }

    @Test
    public void testInheritsLocFunctionality() {
        // Test inherited activity functionality
        assertNotNull(testHomeLocation.getActivity());
        assertTrue(testHomeLocation.getActivity().isEmpty());

        testHomeLocation.addActivity(testActivity);
        assertEquals(1, testHomeLocation.getActivity().size());
        assertEquals(testActivity, testHomeLocation.getActivity().get(0));
    }

    @Test
    public void testMultipleUpgrades() {
        HomeUpgrade upgrade1 = new HomeUpgrade("Kitchen", "Fridge", 300, new Activity("Eat", 30, "Hunger", 20));
        HomeUpgrade upgrade2 = new HomeUpgrade("Kitchen", "Microwave", 200, new Activity("Heat", 15, "Hunger", 10));

        testHomeLocation.addUpgrade(testUpgrade);
        testHomeLocation.addUpgrade(upgrade1);
        testHomeLocation.addUpgrade(upgrade2);

        List<HomeUpgrade> upgrades = testHomeLocation.getUpgradeList();
        assertEquals(3, upgrades.size());
        assertTrue(upgrades.contains(testUpgrade));
        assertTrue(upgrades.contains(upgrade1));
        assertTrue(upgrades.contains(upgrade2));
    }

    @Test
    public void testUpgradeDetails() {
        testHomeLocation.addUpgrade(testUpgrade);

        HomeUpgrade upgrade = testHomeLocation.getUpgradeList().get(0);
        assertEquals("Kitchen", upgrade.getLocation());
        assertEquals("Stove", upgrade.getName());
        assertEquals(500, upgrade.getPrice());
        assertEquals(testActivity, upgrade.getActivity());
        assertFalse(upgrade.getUpgrade());
    }
}