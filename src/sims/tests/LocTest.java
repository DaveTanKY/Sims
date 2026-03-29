package sims.tests;

import sims.world.Loc;
import sims.world.Home;
import sims.world.HomeLocation;
import sims.actions.Activity;
import sims.career.Career;
import sims.entity.Sim;
import sims.gameEngine.SimFactory;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.List;

public class LocTest {

    private HomeLocation homeLocation;
    private Activity testActivity;
    private Sim testSim;
    private Home home;

    @Before
    public void setUp() {
        home = new Home("Test Home");
        homeLocation = new HomeLocation("Kitchen", home);
        testActivity = new Activity("Test Activity", 30, "Hunger", 10);

        // Create a test Sim
        Home newHome = SimFactory.defaultHome("test");
        Career it = new Career("Developer", "IT", 2000);
        testSim = SimFactory.createSim("test", 0, 25, newHome, it);
    }

    @Test
    public void testLocConstructor() {
        assertEquals("Kitchen", homeLocation.getName());
        assertNotNull(homeLocation.getActivity());
        assertNotNull(homeLocation.getLocSimList());
        assertTrue(homeLocation.getActivity().isEmpty());
        assertTrue(homeLocation.getLocSimList().isEmpty());
    }

    @Test
    public void testAddAndGetActivity() {
        homeLocation.addActivity(testActivity);
        List<Activity> activities = homeLocation.getActivity();
        assertEquals(1, activities.size());
        assertEquals(testActivity, activities.get(0));
    }

    @Test
    public void testAddAndRemoveSim() {
        homeLocation.getHome().addSim(testSim);
        assertTrue(homeLocation.getHome().getLocSimList().contains(testSim));

        homeLocation.removeSim(testSim);
        assertFalse(homeLocation.getHome().getLocSimList().contains(testSim));
    }

    @Test
    public void testGetName() {
        assertEquals("Kitchen", homeLocation.getName());
    }

    @Test
    public void testSetName() {
        homeLocation.setName("Bedroom");
        assertEquals("Bedroom", homeLocation.getName());
    }

    @Test
    public void testMultipleActivities() {
        Activity activity1 = new Activity("Eat", 30, "Hunger", 20);
        Activity activity2 = new Activity("Cook", 45, "Fun", 15);

        homeLocation.addActivity(activity1);
        homeLocation.addActivity(activity2);

        List<Activity> activities = homeLocation.getActivity();
        assertEquals(2, activities.size());
        assertTrue(activities.contains(activity1));
        assertTrue(activities.contains(activity2));
    }

    @Test
    public void testMultipleSims() {
        // Create another Sim
        Home otherHome = SimFactory.defaultHome("other");
        Career artist = new Career("Artist", "Art", 1500);
        Sim otherSim = SimFactory.createSim("other", 1, 30, otherHome, artist);

        homeLocation.getHome().addSim(testSim);
        homeLocation.getHome().addSim(otherSim);

        List<Sim> sims = homeLocation.getHome().getLocSimList();
        assertEquals(2, sims.size());
        assertTrue(sims.contains(testSim));
        assertTrue(sims.contains(otherSim));
    }
}