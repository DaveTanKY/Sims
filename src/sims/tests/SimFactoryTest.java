package sims.tests;

import sims.actions.Activity;
import sims.actions.SkillManager;
import sims.career.Career;
import sims.entity.Sim;
import sims.gameEngine.SimFactory;
import sims.needs.need;
import sims.world.Home;
import sims.world.HomeLocation;
import sims.world.HomeUpgrade;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

public class SimFactoryTest {

    @Test
    public void testDefaultGame() {
        List<Sim> sims = SimFactory.defaultGame();
        assertEquals(7, sims.size());

        // Check first Sim: Dave
        Sim dave = sims.get(0);
        assertEquals("Dave", dave.getName());
        assertEquals(0, dave.getUUID());
        assertEquals(24, dave.getAge());
        assertEquals("IT", dave.getCareer().getSector());
        assertEquals(5000, dave.getCareer().getSalary());
        assertEquals("Dave's Home", dave.getHome().getName());
        assertNotNull(dave.getSkillMap());
        assertNotNull(dave.getNeeds());

        // Check another Sim: Tim
        Sim tim = sims.get(1);
        assertEquals("Tim", tim.getName());
        assertEquals(1, tim.getUUID());
        assertEquals(23, tim.getAge());
        assertEquals("IT", tim.getCareer().getSector());
        assertEquals(4000, tim.getCareer().getSalary());

        // Check Healthcare Sim: Rance
        Sim rance = sims.get(3);
        assertEquals("Rance", rance.getName());
        assertEquals(3, rance.getUUID());
        assertEquals(23, rance.getAge());
        assertEquals("Healthcare", rance.getCareer().getSector());
        assertEquals(4600, rance.getCareer().getSalary());

        // Check Finance Sim: Eliyaz
        Sim eliyaz = sims.get(5);
        assertEquals("Eliyaz", eliyaz.getName());
        assertEquals(5, eliyaz.getUUID());
        assertEquals(25, eliyaz.getAge());
        assertEquals("Finance", eliyaz.getCareer().getSector());
        assertEquals(4200, eliyaz.getCareer().getSalary());
    }

    @Test
    public void testDefaultHome() {
        Home home = SimFactory.defaultHome("TestSim");
        assertEquals("TestSim's Home", home.getName());

        List<HomeLocation> locations = home.getHomeLocation();
        assertEquals(4, locations.size()); // Bathroom, Living Room, Bedroom, Kitchen

        // Check Kitchen
        HomeLocation kitchen = null;
        for (HomeLocation loc : locations) {
            if ("Kitchen".equals(loc.getName())) {
                kitchen = loc;
                break;
            }
        }
        assertNotNull(kitchen);
        List<Activity> kitchenActivities = kitchen.getActivity();
        assertEquals(1, kitchenActivities.size());
        assertEquals("Sandwich", kitchenActivities.get(0).getName());
        assertEquals("Hunger", kitchenActivities.get(0).getImpactedNeed());
        assertEquals(30, kitchenActivities.get(0).getValue());

        // Check upgrades in Kitchen
        List<HomeUpgrade> kitchenUpgrades = kitchen.getUpgradeList();
        assertEquals(2, kitchenUpgrades.size());
        boolean hasOven = false;
        boolean hasCoffeeMachine = false;
        for (HomeUpgrade u : kitchenUpgrades) {
            if ("Oven".equals(u.getName())) {
                hasOven = true;
            }
            if ("Coffee Machine".equals(u.getName())) {
                hasCoffeeMachine = true;
            }
        }
        assertTrue(hasOven);
        assertTrue(hasCoffeeMachine);

        // Check Living Room
        HomeLocation livingRoom = null;
        for (HomeLocation loc : locations) {
            if ("Living Room".equals(loc.getName())) {
                livingRoom = loc;
                break;
            }
        }
        assertNotNull(livingRoom);
        List<Activity> livingActivities = livingRoom.getActivity();
        assertEquals(2, livingActivities.size());
        boolean hasWatchTV = false;
        boolean hasScrollSocial = false;
        for (Activity a : livingActivities) {
            if ("Watch TV".equals(a.getName())) {
                hasWatchTV = true;
            }
            if ("Scroll Social Media".equals(a.getName())) {
                hasScrollSocial = true;
            }
        }
        assertTrue(hasWatchTV);
        assertTrue(hasScrollSocial);

        // Check Bedroom
        HomeLocation bedroom = null;
        for (HomeLocation loc : locations) {
            if ("Bedroom".equals(loc.getName())) {
                bedroom = loc;
                break;
            }
        }
        assertNotNull(bedroom);
        List<Activity> bedroomActivities = bedroom.getActivity();
        assertEquals(1, bedroomActivities.size());
        assertEquals("Sleep", bedroomActivities.get(0).getName());

        // Check Bathroom
        HomeLocation bathroom = null;
        for (HomeLocation loc : locations) {
            if ("Bathroom".equals(loc.getName())) {
                bathroom = loc;
                break;
            }
        }
        assertNotNull(bathroom);
        List<Activity> bathroomActivities = bathroom.getActivity();
        assertEquals(2, bathroomActivities.size());
        boolean hasShower = false;
        boolean hasUseToilet = false;
        for (Activity a : bathroomActivities) {
            if ("Shower".equals(a.getName())) {
                hasShower = true;
            }
            if ("Use Toilet".equals(a.getName())) {
                hasUseToilet = true;
            }
        }
        assertTrue(hasShower);
        assertTrue(hasUseToilet);
    }

    @Test
    public void testCreateSim() {
        Career career = new Career("TestCareer", 3000);
        Home home = new Home("Test Home");
        HomeLocation loc = new HomeLocation("Test Loc", home);
        home.addHomeLocation(loc);

        Sim sim = SimFactory.createSim("TestSim", 42, 30, home, career);

        assertEquals("TestSim", sim.getName());
        assertEquals(42, sim.getUUID());
        assertEquals(30, sim.getAge());
        assertEquals(home, sim.getHome());
        assertEquals(loc, sim.getLocation()); // Should be set to first location
        assertEquals(career, sim.getCareer());
        assertNotNull(sim.getSkillMap());
        assertNotNull(sim.getNeeds());
    }

    @Test
    public void testCreateSkills() {
        Map<String, SkillManager> skills = SimFactory.createSkills();
        assertEquals(6, skills.size());
        assertTrue(skills.containsKey("Hunger"));
        assertTrue(skills.containsKey("Hygiene"));
        assertTrue(skills.containsKey("Energy"));
        assertTrue(skills.containsKey("Bladder"));
        assertTrue(skills.containsKey("Fun"));
        assertTrue(skills.containsKey("Social"));

        // Check that each is a SkillManager instance
        for (SkillManager skill : skills.values()) {
            assertNotNull(skill);
            assertEquals(1, skill.getLevel()); // Default level
        }
    }

    @Test
    public void testCreateNeeds() {
        Map<String, need> needs = SimFactory.createNeeds();
        assertEquals(6, needs.size());
        assertTrue(needs.containsKey("Hunger"));
        assertTrue(needs.containsKey("Hygiene"));
        assertTrue(needs.containsKey("Energy"));
        assertTrue(needs.containsKey("Bladder"));
        assertTrue(needs.containsKey("Fun"));
        assertTrue(needs.containsKey("Social"));

        // Check decay rates
        assertEquals(0.2, needs.get("Hunger").getDecayRate(), 0.001);
        assertEquals(0.01, needs.get("Hygiene").getDecayRate(), 0.001);
        assertEquals(0.3, needs.get("Energy").getDecayRate(), 0.001);
        assertEquals(0.02, needs.get("Bladder").getDecayRate(), 0.001);
        assertEquals(0.005, needs.get("Fun").getDecayRate(), 0.001);
        assertEquals(0.003, needs.get("Social").getDecayRate(), 0.001);

        // Check values are between 50 and 100
        for (need n : needs.values()) {
            assertTrue(n.getValue() >= 50 && n.getValue() <= 100);
        }
    }
}