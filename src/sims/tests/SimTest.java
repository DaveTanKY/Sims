package sims.tests;

import sims.entity.Sim;
import sims.gameEngine.SimFactory;
import sims.career.Career;
import sims.needs.Need;
import sims.world.Home;
import sims.world.HomeLocation;
import sims.actions.Activity;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class SimTest {

    private Sim testSim;
    private Sim otherSim;
    private Career it;
    private Career artist;
    private Home newHome;
    private Home otherHome;

    @Before
    public void setUp() {

        // Create a test Sim
        newHome = SimFactory.defaultHome("test");
        it = new Career("IT", 2000);
        testSim = SimFactory.createSim("test", 0, 25, newHome, it);

        // Create another Sim
        otherHome = SimFactory.defaultHome("other");
        artist = new Career("Art", 1500);
        otherSim = SimFactory.createSim("other", 1, 30, otherHome, artist);
    }

    /**
     * Tests the Sim constructor.
     * Verifies that name, UUID, age, activity end time, bank balance, needs, and relationships are initialized correctly.
     */
    @Test
    public void testConstructor() {
        assertEquals("test", testSim.getName());
        assertEquals(0, testSim.getUUID());
        assertEquals(25, testSim.getAge());
        assertEquals(-1, testSim.getActivityEnd());
        assertEquals(500.0, testSim.getBank(), 0.001);
        assertNotNull(testSim.getNeeds()); // Should be initialized as empty HashMap
        assertNotNull(testSim.getRelationshipMap()); // Should be initialized as empty HashMap
    }

    /**
     * Tests the getter methods for Sim properties.
     * Ensures that name, UUID, age, activity end, bank, needs, and relationships are correctly retrieved.
     */
    @Test
    public void testGetters() {
        assertEquals("other", otherSim.getName());
        assertEquals(1, otherSim.getUUID());
        assertEquals(30, otherSim.getAge());
        assertEquals(-1, otherSim.getActivityEnd());
        assertEquals(500.0, otherSim.getBank(), 0.001);
        assertNotNull(otherSim.getNeeds());
        assertNotNull(otherSim.getRelationshipMap());
    }

    /**
     * Tests the setCareer method.
     * Verifies that the Sim's career can be updated correctly.
     */
    @Test
    public void testSetCareer() {
        Career newCareer = new Career("Medical", 8000);
        testSim.setCareer(newCareer);
        assertEquals(8000, testSim.getCareer().getSalary());
    }

    /**
     * Tests the initial bank balance.
     * Ensures that the Sim starts with the default bank balance of 500.
     */
    @Test
    public void testBankInitialization() {
        assertEquals(500.0, testSim.getBank(), 0.001);
    }

    /**
     * Tests the updateBank method.
     * Verifies that bank balance can be decreased (spending) or increased (earning).
     */
    @Test
    public void testUpdateBank() {
        double initialBank = testSim.getBank();
        testSim.updateBank(100.0);
        assertEquals(initialBank - 100.0, testSim.getBank(), 0.001);

        testSim.updateBank(-50.0); // Adding money
        assertEquals(initialBank - 50.0, testSim.getBank(), 0.001);
    }

    /**
     * Tests the updateNeeds method with regular updates.
     * Verifies that need values are updated correctly and capped at 100.
     */
    @Test
    public void testUpdateNeedsRegular() {
        double initialHunger = testSim.getNeeds().get("Hunger").getValue();
        testSim.updateNeeds("Hunger", 10.0);
        assertEquals(Math.min(100.0, initialHunger + 10.0), testSim.getNeeds().get("Hunger").getValue(), 0.001);
    }

    /**
     * Tests the setCurrentLocation method.
     * Ensures that the Sim's current location can be updated.
     */
    @Test
    public void testSetCurrentLocation() {
        HomeLocation newLocation = new HomeLocation("Kitchen", newHome);
        testSim.setCurrentLocation(newLocation);
        assertEquals(newLocation, testSim.getLocation());
    }

    /**
     * Tests the setHome method.
     * Verifies that the Sim's home can be changed.
     */
    @Test
    public void testSetHome() {
        Home newHome = new Home("New Home");
        testSim.setHome(newHome);
        assertEquals(newHome, testSim.getHome());
    }

    /**
     * Tests the initial activity end time.
     * Ensures that activity end is initialized to -1 (no activity).
     */
    @Test
    public void testActivityEndInitialization() {
        assertEquals(-1, testSim.getActivityEnd());
    }

    /**
     * Tests the setActivityEnd method.
     * Verifies that activity end time is calculated correctly as current time plus duration.
     */
    @Test
    public void testSetActivityEnd() {
        testSim.setActivityEnd(60, 100); // Duration 60, current time 100
        assertEquals(160, testSim.getActivityEnd()); // 100 + 60
    }

    /**
     * Tests the performDecay method for the current Sim.
     * Verifies that decay is applied to all needs except the excluded one.
     */
    @Test
    public void testPerformDecayCurrentSim() {
        // For current sim, decay should be applied to all needs except the excluded one
        double initialHunger = testSim.getNeeds().get("Hunger").getValue();
        double initialEnergy = testSim.getNeeds().get("Energy").getValue();

        testSim.performDecay("Hunger", testSim, 100);

        // Hunger should not decay (excluded)
        assertEquals(initialHunger, testSim.getNeeds().get("Hunger").getValue(), 0.001);
        // Energy should decay
        assertTrue(testSim.getNeeds().get("Energy").getValue() < initialEnergy);
    }
}