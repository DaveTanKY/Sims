package sims.tests;

import sims.world.OutsideLocation;
import sims.entity.Sim;
import sims.career.Career;
import sims.gameEngine.SimFactory;
import sims.world.Home;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class OutsideLocationTest {

    private OutsideLocation testOutsideLocation;
    private Sim testSim;
    private Home newHome;
    private Career it;

    @Before
    public void setUp() {
        testOutsideLocation = new OutsideLocation("Park", "Sunny", "None");

        // Create a test Sim
        newHome = SimFactory.defaultHome("test");
        it = new Career("Developer", "IT", 2000);
        testSim = SimFactory.createSim("test", 0, 25, newHome, it);
    }

    @Test
    public void testConstructor() {
        assertEquals("Park", testOutsideLocation.getName());
        assertEquals("Sunny", testOutsideLocation.getComparison());
        assertEquals("None", testOutsideLocation.getRequirement());
        assertNotNull(testOutsideLocation.getLocSimList());
        assertTrue(testOutsideLocation.getLocSimList().isEmpty());
    }

    @Test
    public void testGetComparison() {
        assertEquals("Sunny", testOutsideLocation.getComparison());
    }

    @Test
    public void testGetRequirement() {
        assertEquals("None", testOutsideLocation.getRequirement());
    }

    @Test
    public void testMoveTo() {
        // Create initial location for sim
        OutsideLocation initialLocation = new OutsideLocation("Street", "Rainy", "Umbrella");
        testSim.setCurrentLocation(initialLocation);
        initialLocation.addSim(testSim);

        // Move sim to park
        testOutsideLocation.moveTo(testSim);

        assertEquals(testOutsideLocation, testSim.getLocation());
        assertTrue(testOutsideLocation.getLocSimList().contains(testSim));
        assertFalse(initialLocation.getLocSimList().contains(testSim));
    }

    @Test
    public void testMoveToAlreadyInLocation() {
        // Add sim to location first
        testOutsideLocation.addSim(testSim);
        testSim.setCurrentLocation(testOutsideLocation);

        // Move again - should still work
        testOutsideLocation.moveTo(testSim);

        assertEquals(testOutsideLocation, testSim.getLocation());
        assertTrue(testOutsideLocation.getLocSimList().contains(testSim));
    }

    @Test
    public void testInheritsLocFunctionality() {
        // Test inherited activity functionality
        assertNotNull(testOutsideLocation.getActivity());
        assertTrue(testOutsideLocation.getActivity().isEmpty());

        // Test sim management
        testOutsideLocation.addSim(testSim);
        assertTrue(testOutsideLocation.getLocSimList().contains(testSim));

        testOutsideLocation.removeSim(testSim);
        assertFalse(testOutsideLocation.getLocSimList().contains(testSim));
    }

    @Test
    public void testDifferentWeatherConditions() {
        OutsideLocation beach = new OutsideLocation("Beach", "Hot", "Sunscreen");
        OutsideLocation mountain = new OutsideLocation("Mountain", "Cold", "Jacket");

        assertEquals("Hot", beach.getComparison());
        assertEquals("Sunscreen", beach.getRequirement());

        assertEquals("Cold", mountain.getComparison());
        assertEquals("Jacket", mountain.getRequirement());
    }

    @Test
    public void testMoveMultipleSims() {
        OutsideLocation sourceLocation = new OutsideLocation("Home", "Indoors", "None");

        // Create another Sim
        Home otherHome = SimFactory.defaultHome("other");
        Career artist = new Career("Artist", "Art", 1500);
        Sim otherSim = SimFactory.createSim("other", 1, 30, otherHome, artist);

        // Add sims to source location
        sourceLocation.addSim(testSim);
        sourceLocation.addSim(otherSim);
        testSim.setCurrentLocation(sourceLocation);
        otherSim.setCurrentLocation(sourceLocation);

        // Move both sims to outside location
        testOutsideLocation.moveTo(testSim);
        testOutsideLocation.moveTo(otherSim);

        // Check source location is empty
        assertFalse(sourceLocation.getLocSimList().contains(testSim));
        assertFalse(sourceLocation.getLocSimList().contains(otherSim));

        // Check destination has both sims
        assertTrue(testOutsideLocation.getLocSimList().contains(testSim));
        assertTrue(testOutsideLocation.getLocSimList().contains(otherSim));
        assertEquals(testOutsideLocation, testSim.getLocation());
        assertEquals(testOutsideLocation, otherSim.getLocation());
    }

    @Test
    public void testLocationNameChange() {
        testOutsideLocation.setName("Garden");
        assertEquals("Garden", testOutsideLocation.getName());
    }
}