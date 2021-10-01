package nz.ac.vuw.ecs.swen225.gp21.domain.tests;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
}
