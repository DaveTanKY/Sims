package sims.tests;

import sims.entity.Sim;
import sims.gameEngine.SimFactory;
import sims.career.Career;
import sims.needs.need;
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
        it = new Career("Developer", "IT", 2000);
        testSim = SimFactory.createSim("test", 0, 25, newHome, it);

        // Create another Sim
        otherHome = SimFactory.defaultHome("other");
        artist = new Career("Artist", "Art", 1500);
        otherSim = SimFactory.createSim("other", 1, 30, otherHome, artist);
    }

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

    @Test
    public void testSetCareer() {
        Career newCareer = new Career("Doctor", "Medical", 8000);
        testSim.setCareer(newCareer);
        assertEquals("Doctor", testSim.getCareer().getTitle());
        assertEquals(8000, testSim.getCareer().getSalary());
    }

    @Test
    public void testBankInitialization() {
        assertEquals(500.0, testSim.getBank(), 0.001);
    }

    @Test
    public void testUpdateBank() {
        double initialBank = testSim.getBank();
        testSim.updateBank(100.0);
        assertEquals(initialBank - 100.0, testSim.getBank(), 0.001);

        testSim.updateBank(-50.0); // Adding money
        assertEquals(initialBank - 50.0, testSim.getBank(), 0.001);
    }

    @Test
    public void testUpdateNeedsRegular() {
        double initialHunger = testSim.getNeeds().get("Hunger").getValue();
        testSim.updateNeeds("Hunger", 10.0);
        assertEquals(Math.min(100.0, initialHunger + 10.0), testSim.getNeeds().get("Hunger").getValue(), 0.001);
    }

    @Test
    public void testUpdateNeedsSalary() {
        double initialBank = testSim.getBank();
        int initialLevel = it.getLevel();

        testSim.updateNeeds("Salary", 0.0); // Value doesn't matter for salary

        // Bank should increase by salary + bonus
        double expectedIncrease = it.getSalary() + it.getBonus();
        assertEquals(initialBank + expectedIncrease, testSim.getBank(), 0.001);

        // Career should gain XP (level might increase)
        assertTrue(it.getLevel() >= initialLevel);
    }

    @Test
    public void testSetCurrentLocation() {
        HomeLocation newLocation = new HomeLocation("Kitchen", newHome);
        testSim.setCurrentLocation(newLocation);
        assertEquals(newLocation, testSim.getLocation());
    }

    @Test
    public void testSetHome() {
        Home newHome = new Home("New Home");
        testSim.setHome(newHome);
        assertEquals(newHome, testSim.getHome());
    }

    @Test
    public void testActivityEndInitialization() {
        assertEquals(-1, testSim.getActivityEnd());
    }

    @Test
    public void testSetActivityEnd() {
        testSim.setActivityEnd(60, 100); // Duration 60, current time 100
        assertEquals(160, testSim.getActivityEnd()); // 100 + 60
    }

    @Test
    public void testAutoPlay() {
        // Create a mock home with home locations and activities
        Home mockHome = new Home("Mock Home");
        HomeLocation homeLoc = new HomeLocation("Kitchen", mockHome);

        // Create an activity that impacts Hunger
        Activity eatActivity = new Activity("Eat", 30, "Hunger", 20);
        homeLoc.addActivity(eatActivity);

        mockHome.addHomeLocation(homeLoc);

        testSim.setHome(mockHome);

        double initialHunger = testSim.getNeeds().get("Hunger").getValue();
        testSim.autoPlay("Hunger", 50);

        // Hunger should increase by 20
        assertEquals(Math.min(100.0, initialHunger + 20.0), testSim.getNeeds().get("Hunger").getValue(), 0.001);
        // Activity end time should be set
        assertEquals(80, testSim.getActivityEnd()); // 50 + 30
    }

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

    @Test
    public void testPerformDecayOtherSim() {
        // For other sim, decay should trigger autoplay if need is critical and sim is not busy
        Sim otherSim = new Sim("OtherSim", 2, 30);
        Map<String, need> otherNeeds = new HashMap<>();
        need criticalNeed = new need(0.5);
        // Set need to be critical (below threshold)
        criticalNeed.setValue(-50.0); // This will make it critical
        otherNeeds.put("Hunger", criticalNeed);
        otherSim.setNeeds(otherNeeds);

        // Set up home and activity for autoplay
        Home mockHome = new Home("Test Home");
        HomeLocation homeLoc = new HomeLocation("Kitchen", mockHome);
        Activity eatActivity = new Activity("Eat", 30, "Hunger", 20);
        homeLoc.addActivity(eatActivity);
        mockHome.addHomeLocation(homeLoc);
        otherSim.setHome(mockHome);

        otherSim.performDecay("Energy", testSim, 100); // testSim is "current", otherSim is being decayed

        // Since need is critical and time > activityEnd (-1), autoplay should trigger
        // This is complex to test precisely due to the mock setup, but we can verify the method doesn't crash
        assertTrue(otherSim.getActivityEnd() >= 0); // Activity should have been set
    }
}