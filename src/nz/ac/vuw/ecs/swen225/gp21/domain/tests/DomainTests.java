package nz.ac.vuw.ecs.swen225.gp21.domain.tests;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class DomainTests {

    /**
     * Attempt to run the game.
     */
    @Test
    public void gameTest1() {

        Domain d = new Domain("level1");
        d.setRunning(true);

        assertEquals(d.isRunning(), true);
    }
    /**
     * Pickup an item.
     */
    @Test
    public void gameTest2() {

        Domain d = new Domain("level1");
        Actor hero = d.getPlayer();
        d.setRunning(true);
        d.moveActor(hero, Direction.EAST);
        d.moveActor(hero, Direction.EAST);

        assertEquals(d.getPlayer().getInventory().isEmpty(), false);
    }
    /**
     * Go through a door.
     */
    @Test
    public void gameTest3() {

        Domain d = new Domain("level1");
        Actor hero = d.getPlayer();
        d.setRunning(true);
        // grab Red key and go to the Red door at the top right
        d.moveActor(hero, Direction.EAST);
        d.moveActor(hero, Direction.EAST);
        d.moveActor(hero, Direction.NORTH);
        d.moveActor(hero, Direction.EAST);
        d.moveActor(hero, Direction.EAST);
        // pickup a treasure
        d.moveActor(hero, Direction.SOUTH);

        assertEquals(d.getPlayer().getTreasure(), 1);
    }
    /**
     * Err loading game
     */
    @Test
    public void gameTestErr1() {
        try {
            Domain d = new Domain("err.xmlsave");
        } catch (Exception e) {
            assert true;
        }
    }
    /**
     * Save game
     */
    @Test
    public void gameTestSave1() {
        try {
            Domain d = new Domain("save.xmlsave");
        } catch (Exception e) {
            assert true;
        }
    }
    /**
     * Win the game.
     */
    @Test
    public void gameTest4() {

        Domain d = new Domain("level1");
        Actor hero = d.getPlayer();
        d.setRunning(true);
        // grab Red key and go to the Red door at the top right
        d.moveActor(hero, Direction.EAST);
        d.moveActor(hero, Direction.EAST);
        d.moveActor(hero, Direction.NORTH);
        d.moveActor(hero, Direction.EAST);
        d.moveActor(hero, Direction.EAST);
        // pickup a treasure
        d.moveActor(hero, Direction.SOUTH);
        assertEquals(d.getPlayer().getTreasure(), 1);
        d.moveActor(hero, Direction.NORTH);
        d.moveActor(hero, Direction.WEST);
        d.moveActor(hero, Direction.WEST);
        d.moveActor(hero, Direction.SOUTH);
        d.moveActor(hero, Direction.SOUTH);
        // grab another treasure
        d.moveActor(hero, Direction.SOUTH);
        d.moveActor(hero, Direction.SOUTH);
        // turn the corner at the bottom of the board
        d.moveActor(hero, Direction.WEST);
        d.moveActor(hero, Direction.WEST);
        // grab another treasure
        d.moveActor(hero, Direction.WEST);
        d.moveActor(hero, Direction.WEST);
        // pickup the stuff on the left row
        d.moveActor(hero, Direction.NORTH);
        d.moveActor(hero, Direction.NORTH);
        // grab another treasure
        d.moveActor(hero, Direction.NORTH);
        d.moveActor(hero, Direction.NORTH);
        // go through the Green door
        d.moveActor(hero, Direction.NORTH);
        d.moveActor(hero, Direction.NORTH);
        // grab the treasure
        d.moveActor(hero, Direction.WEST);
        assertEquals(d.getPlayer().getPosition().getX(), 2);
        assertEquals(d.getPlayer().getPosition().getY(), 1);
        assertEquals(d.getPlayer().getTreasure(), 5);
        d.moveActor(hero, Direction.EAST);
        d.moveActor(hero, Direction.SOUTH);
        d.moveActor(hero, Direction.SOUTH);
        // go through the Blue door
        d.moveActor(hero, Direction.WEST);
        d.moveActor(hero, Direction.WEST);
        // grab another treasure
        d.moveActor(hero, Direction.SOUTH);
        assertEquals(d.getPlayer().getTreasure(), 6);
        d.moveActor(hero, Direction.NORTH);
        d.moveActor(hero, Direction.EAST);
        d.moveActor(hero, Direction.EAST);
        d.moveActor(hero, Direction.SOUTH);
        d.moveActor(hero, Direction.SOUTH);
        d.moveActor(hero, Direction.SOUTH);
        d.moveActor(hero, Direction.SOUTH);
        // go through the bottom left Red Door
        d.moveActor(hero, Direction.WEST);
        d.moveActor(hero, Direction.WEST);
        d.moveActor(hero, Direction.NORTH);
        assertEquals(d.getPlayer().getPosition().getX(), 1);
        assertEquals(d.getPlayer().getPosition().getY(), 6);
        assertEquals(d.getPlayer().getTreasure(), 7);
        d.moveActor(hero, Direction.SOUTH);
        d.moveActor(hero, Direction.EAST);
        d.moveActor(hero, Direction.EAST);
        d.moveActor(hero, Direction.EAST);
        d.moveActor(hero, Direction.EAST);
        d.moveActor(hero, Direction.EAST);
        d.moveActor(hero, Direction.EAST);
        d.moveActor(hero, Direction.EAST);
        d.moveActor(hero, Direction.EAST);
        d.moveActor(hero, Direction.NORTH);
        // get another treasure
        assertEquals(d.getPlayer().getTreasure(), 8);
        d.moveActor(hero, Direction.SOUTH);
        d.moveActor(hero, Direction.WEST);
        d.moveActor(hero, Direction.WEST);
        d.moveActor(hero, Direction.NORTH);
        d.moveActor(hero, Direction.NORTH);
        d.moveActor(hero, Direction.NORTH);
        d.moveActor(hero, Direction.NORTH);
        d.moveActor(hero, Direction.NORTH);
        d.moveActor(hero, Direction.NORTH);
        d.moveActor(hero, Direction.EAST);
        assertEquals(d.getPlayer().getPosition().getX(), 8);
        assertEquals(d.getPlayer().getPosition().getY(), 1);
        assertEquals(d.getPlayer().getTreasure(), 9);
        d.moveActor(hero, Direction.WEST);
        d.moveActor(hero, Direction.SOUTH);
        d.moveActor(hero, Direction.SOUTH);
        d.moveActor(hero, Direction.WEST);
        d.moveActor(hero, Direction.WEST);
        d.moveActor(hero, Direction.NORTH);
        d.moveActor(hero, Direction.NORTH);
        assertEquals(d.getPlayer().getPosition().getX(), 5);
        assertEquals(d.getPlayer().getPosition().getY(), 1);

        // win the game
        assertEquals(d.isRunning(), false);
    }
    /**
     * Play level 2.
     */
    @Test
    public void gameTest5() {

        Domain d = new Domain("level2");

        assertEquals(d.getPlayer().getPosition().getX(), 9);
        assertEquals(d.getPlayer().getPosition().getY(), 4);
    }
    /**
     * Win level 2.
     */
    @Test
    public void gameTest6() {

        Domain d = new Domain("level2");

        Actor hero = d.getPlayer();

        d.setRunning(true);
        d.moveActor(hero, Direction.NORTH);
        d.moveActor(hero, Direction.NORTH);
        d.moveActor(hero, Direction.NORTH);
        assertEquals(d.getPlayer().getTreasure(), 1);
        d.moveActor(hero, Direction.WEST);
        d.moveActor(hero, Direction.WEST);
        d.moveActor(hero, Direction.WEST);
        assertEquals(d.getPlayer().getTreasure(), 2);

        d.moveActor(hero, Direction.EAST);
        d.moveActor(hero, Direction.EAST);
        d.moveActor(hero, Direction.EAST);

        d.moveActor(hero, Direction.SOUTH);
        d.moveActor(hero, Direction.SOUTH);
        d.moveActor(hero, Direction.SOUTH);
        d.moveActor(hero, Direction.SOUTH);
        d.moveActor(hero, Direction.SOUTH);
        d.moveActor(hero, Direction.SOUTH);
        assertEquals(d.getPlayer().getTreasure(), 3);

        d.moveActor(hero, Direction.WEST);
        d.moveActor(hero, Direction.WEST);
        d.moveActor(hero, Direction.WEST);
        assertEquals(d.getPlayer().getTreasure(), 4);

        d.moveActor(hero, Direction.WEST);
        d.moveActor(hero, Direction.WEST);
        d.moveActor(hero, Direction.WEST);
        d.moveActor(hero, Direction.WEST);
        d.moveActor(hero, Direction.WEST);
        assertEquals(d.getPlayer().getTreasure(), 5);

        d.moveActor(hero, Direction.EAST);
        d.moveActor(hero, Direction.EAST);

        d.moveActor(hero, Direction.NORTH);
        d.moveActor(hero, Direction.NORTH);
        d.moveActor(hero, Direction.NORTH);
        d.moveActor(hero, Direction.NORTH);
        d.moveActor(hero, Direction.NORTH);
        d.moveActor(hero, Direction.NORTH);

        d.moveActor(hero, Direction.WEST);
        d.moveActor(hero, Direction.WEST);
        assertEquals(d.getPlayer().getTreasure(), 6);

        d.moveActor(hero, Direction.EAST);
        d.moveActor(hero, Direction.EAST);

        d.moveActor(hero, Direction.SOUTH);
        d.moveActor(hero, Direction.SOUTH);
        d.moveActor(hero, Direction.SOUTH);

        d.moveActor(hero, Direction.WEST);
        d.moveActor(hero, Direction.WEST);

        assertEquals(d.isRunning(), false);

        assertEquals(d.getPlayer().getPosition().getX(), 1);
        assertEquals(d.getPlayer().getPosition().getY(), 4);
    }
    /**
     * Lose level 2.
     */
    @Test
    public void gameTest10() {

        Domain d = new Domain("level2");
        d.setRunning(true);

        d.moveActor(d.getPlayer(), Direction.NORTH);
        d.moveActor(d.getPlayer(), Direction.NORTH);
        d.moveActor(d.getPlayer(), Direction.WEST);
        d.moveActor(d.getPlayer(), Direction.WEST);
        d.moveActor(d.getPlayer(), Direction.WEST);
        d.moveActor(d.getPlayer(), Direction.WEST);
        d.moveActor(d.getPlayer(), Direction.WEST);

        assertEquals(d.isRunning(), false);
    }
    @Test
    public void gameTest11() {

        Domain d = new Domain("level2");
        d.setRunning(true);

        System.out.print(d.getActors().get(0).getID());
        System.out.print(d.getActors().get(1).getID());
        System.out.print(d.getActors().get(2).getID());
        assertNotEquals(d.getActorByID("enemy46"), null);
        assertNotEquals(d.getActorByID("enemy42"), null);
        assertNotEquals(d.getActorByID("enemy44"), null);

        d.moveActor(d.getPlayer(), Direction.NORTH);
        d.moveActor(d.getPlayer(), Direction.NORTH);
        d.moveActor(d.getPlayer(), Direction.WEST);
        d.moveActor(d.getPlayer(), Direction.WEST);
        d.moveActor(d.getPlayer(), Direction.WEST);
        d.moveActor(d.getPlayer(), Direction.WEST);
        d.moveActor(d.getActorByID("enemy42"), Direction.EAST);

        assertNotEquals(d.getActorByID("enemy42"), null);

        assertEquals(d.getPlayer().getPosition().getX(), 5);
        assertEquals(d.getPlayer().getPosition().getY(), 2);
        assertEquals(d.isRunning(), false);
    }
    /**
     * Push Block Level 2.
     */
    @Test
    public void gameTest12() {

        Domain d = new Domain("level2");
        d.setRunning(true);

        d.moveActor(d.getPlayer(), Direction.NORTH);
        // push a block west a bunch
        d.moveActor(d.getPlayer(), Direction.WEST);
        d.moveActor(d.getPlayer(), Direction.WEST);
        d.moveActor(d.getPlayer(), Direction.WEST);
        d.moveActor(d.getPlayer(), Direction.WEST);
        d.moveActor(d.getPlayer(), Direction.WEST);

        assertEquals(d.getPlayer().getPosition().getX(), 4);
        assertEquals(d.getPlayer().getPosition().getY(), 3);
    }
    @Test
    public void gameTest13() {

        Domain d = new Domain("level2");
        d.setRunning(true);

        // fail to push to blocks west a bunch
        d.moveActor(d.getPlayer(), Direction.WEST);
        d.moveActor(d.getPlayer(), Direction.WEST);
        d.moveActor(d.getPlayer(), Direction.WEST);
        d.moveActor(d.getPlayer(), Direction.WEST);
        d.moveActor(d.getPlayer(), Direction.WEST);

        assertEquals(d.getPlayer().getPosition().getX(), 8);
        assertEquals(d.getPlayer().getPosition().getY(), 4);
    }
}
