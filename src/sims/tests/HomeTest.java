package sims.tests;

import sims.world.Home;
import sims.world.HomeLocation;
import sims.career.Career;
import sims.entity.Sim;
import sims.gameEngine.SimFactory;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.List;

public class HomeTest {

    private Home testHome;
    private HomeLocation kitchen;
    private Sim testSim;

    @Before
    public void setUp() {
        testHome = new Home("Test Home");
        kitchen = new HomeLocation("Kitchen", testHome);

        // Create a test Sim
        Home newHome = SimFactory.defaultHome("test");
        Career it = new Career("Developer", "IT", 2000);
        testSim = SimFactory.createSim("test", 0, 25, newHome, it);
    }

    @Test
    public void testConstructor() {
        assertEquals("Test Home", testHome.getName());
        assertNotNull(testHome.getHomeLocation());
        assertTrue(testHome.getHomeLocation().isEmpty());
        assertNotNull(testHome.getLocSimList());
        assertTrue(testHome.getLocSimList().isEmpty());
    }

    @Test
    public void testGetHomeLocation() {
        List<HomeLocation> locations = testHome.getHomeLocation();
        assertNotNull(locations);
        assertTrue(locations.isEmpty());
    }

    @Test
    public void testAddHomeLocation() {
        HomeLocation bedroom = new HomeLocation("Bedroom", testHome);
        testHome.addHomeLocation(kitchen);
        testHome.addHomeLocation(bedroom);

        List<HomeLocation> locations = testHome.getHomeLocation();
        assertEquals(2, locations.size());
        assertTrue(locations.contains(kitchen));
        assertTrue(locations.contains(bedroom));

        // Check that the home reference was set
        assertEquals(testHome, kitchen.getHome());
        assertEquals(testHome, bedroom.getHome());
    }

    @Test
    public void testMoveToWithMultipleLocations() {
        HomeLocation kitchen = new HomeLocation("Kitchen", testHome);
        HomeLocation bedroom = new HomeLocation("Bedroom", testHome);

        testHome.addHomeLocation(kitchen);
        testHome.addHomeLocation(bedroom);

        testSim.setCurrentLocation(bedroom);
        bedroom.addSim(testSim);

        // Move sim to kitchen
        kitchen.moveTo(testSim);

        assertEquals(kitchen, testSim.getLocation());
        assertTrue(testHome.getLocSimList().contains(testSim));
    }

    @Test
    public void testHomeInheritsLocFunctionality() {
        // Test that Home inherits activity functionality from Loc
        assertNotNull(testHome.getActivity());
        assertTrue(testHome.getActivity().isEmpty());

        // Test sim management
        testHome.addSim(testSim);
        assertTrue(testHome.getLocSimList().contains(testSim));

        testHome.removeSim(testSim);
        assertFalse(testHome.getLocSimList().contains(testSim));
    }
}