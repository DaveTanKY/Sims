package sims.tests;

import sims.career.Career;
import sims.entity.Relationship;
import sims.entity.Sim;
import sims.gameEngine.SimFactory;
import sims.world.Home;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class RelationshipTest {

    private Sim testSim;
    private Sim otherSim;
    Home newHome;
    Home otherHome;
    Career it;
    Career artist;
    private Relationship newRelationship;

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

        // Set up relationship
        newRelationship = new Relationship(testSim, otherSim);
        testSim.addRelationship(otherSim.getUUID(), newRelationship);
        otherSim.addRelationship(testSim.getUUID(), newRelationship);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, newRelationship.getFriendshipLevel());
        assertEquals(testSim, newRelationship.getOtherSim(otherSim));
        assertEquals(otherSim, newRelationship.getOtherSim(testSim));
    }

    @Test
    public void testGetFriendshipLevel() {
        assertEquals(0, newRelationship.getFriendshipLevel());
    }

    @Test
    public void testIncreaseFriendship() {
        // Initial state
        assertEquals(0, newRelationship.getFriendshipLevel());

        // Increase friendship 3 times (75 XP, level still 0)
        newRelationship.increaseFriendship();
        newRelationship.increaseFriendship();
        newRelationship.increaseFriendship();
        assertEquals(0, newRelationship.getFriendshipLevel());

        // 4th increase should level up (100 XP)
        newRelationship.increaseFriendship();
        assertEquals(1, newRelationship.getFriendshipLevel());
    }

    @Test
    public void testIncreaseFriendshipModulo() {
        // Increase friendship multiple times to test modulo
        for (int i = 0; i < 8; i++) {
            newRelationship.increaseFriendship();
        }
        // 8 * 25 = 200 XP
        // Level should increase by 2 (200/100 = 2), XP should be 0 (200 % 100 = 0)
        assertEquals(2, newRelationship.getFriendshipLevel());
    }

    @Test
    public void testGetOtherSim() {
        // Test getting other sim from testSim
        assertEquals(otherSim, newRelationship.getOtherSim(testSim));

        // Test getting other sim from otherSim
        assertEquals(testSim, newRelationship.getOtherSim(otherSim));
    }

    @Test
    public void testIncreaseFriendshipBoundary() {
        // Test exactly at 100 XP
        for (int i = 0; i < 3; i++) {
            newRelationship.increaseFriendship(); // 75 XP, still level 0
        }
        assertEquals(0, newRelationship.getFriendshipLevel());

        newRelationship.increaseFriendship(); // 100 XP, should level up
        assertEquals(1, newRelationship.getFriendshipLevel());
    }

    @Test
    public void testRelationshipSymmetry() {
        // Test that the relationship works both ways
        assertEquals(otherSim, newRelationship.getOtherSim(testSim));
        assertEquals(testSim, newRelationship.getOtherSim(otherSim));

        // Increase friendship and verify level is the same regardless of which sim we ask
        newRelationship.increaseFriendship();
        newRelationship.increaseFriendship();
        newRelationship.increaseFriendship();
        newRelationship.increaseFriendship();
        assertEquals(1, newRelationship.getFriendshipLevel());
    }
}