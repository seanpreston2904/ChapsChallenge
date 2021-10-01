package nz.ac.vuw.ecs.swen225.gp21.domain.tests;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Enemy;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.*;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.ItemType;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.TileType;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class FunctionTests {

    // testing getter methods
    @Test
    public void functionTest1() {
        Domain d = new Domain("level1");

        assertEquals(d.getActors(), new ArrayList<>());
    }
    @Test
    public void functionTest2() {
        Domain d = new Domain("level1");

        assertEquals(d.getRemainingChips(), 9);
    }
    @Test
    public void functionTest3() {
        Domain d = new Domain("level1");

        assertEquals(d.getPlayer().getPosition().getX(), 5);
        assertEquals(d.getPlayer().getPosition().getY(), 4);
    }
    @Test
    public void functionTest4() {
        Domain d = new Domain("level1");

        assertEquals(d.getActorByID("hero").getID(), "hero");
    }

    // testing moveActor
    @Test
    public void functionTest5() {
        Domain d = new Domain("level1");

        try {
            d.moveActor(null, Direction.NORTH);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }
    @Test
    public void functionTest6() {
        Domain d = new Domain("level1");

        try {
            d.moveActor(d.getPlayer(), null);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    // randomly move enemy
    @Test
    public void functionTest7() {
        Direction direction = Domain.randomlyMoveActor();
        assertNotEquals(direction, null);
    }

    // get actor by ID
    @Test
    public void functionTest8() {
        Domain d = new Domain("level2");

        try {
            Actor a = d.getActorByID(null);
        } catch (IllegalArgumentException e) {
            assert true;
        }
    }
    @Test
    public void functionTest9() {
        Domain d = new Domain("level2");

        Actor a = d.getActorByID("enemy42");

        assertEquals(a.getID(), "enemy42");
    }
    @Test
    public void functionTest10() {
        Domain d = new Domain("level2");

        Actor a = d.getActorByID("zebulac");

        assertEquals(a, null);
    }

    // actor tests
    @Test
    public void functionTestActor1() {
        Player a = new Player(new Coordinate(0,0));

        assertEquals(a.removeFromInventory(null), false);
    }
    @Test
    public void functionTestActor2() {
        Player a = new Player(new Coordinate(0,0));

        assertEquals(a.getName(), "hero");
    }
    @Test
    public void functionTestActor3() {
        Player a = new Player(new Coordinate(0,0));

        assertEquals(a.getTreasure(), 0);
    }
    // enemy
    @Test
    public void functionTestActor4() {
        Enemy a = new Enemy(new Coordinate(0,0)) {};

        assertEquals(a.getName(), "abstract_enemy");
    }
    @Test
    public void functionTestActor5() {
        Enemy a = new Enemy(new Coordinate(0,0)) {};

        assertEquals(a.movementModeAIDirection(null, null), null);
    }
    @Test
    public void functionTestActor6() {
        Enemy a = new Enemy(new Coordinate(0,0)) {};

        assertEquals(a.getID(), "enemy00");
    }
    @Test
    public void functionTestActor7() {
        Enemy a = new Enemy(new Coordinate(0,0)) {};

        assertEquals(a.consoleString(), "\uD83D\uDC1B");
    }
    // abstract actor
    @Test
    public void functionTestActor8() {
        Actor a = new Actor(new Coordinate(0,0)) {};

        assertEquals(a.getName(), "abstract");
    }
    @Test
    public void functionTestActor9() {
        assertEquals(Actor.parseActor("sdsds"), null);
        assertEquals(Actor.parseActor("hero").getName(), new Player(new Coordinate(0,0)).getName());
        assertEquals(Actor.parseActor("enemy").getName(), new Enemy(new Coordinate(0,0)) {}.getName());
    }
    @Test
    public void functionTestActor10() {
        Actor a = new Actor(new Coordinate(0,0)) {};

        assertEquals(a.getID(), "abstract");
    }
    @Test
    public void functionTestActor11() {
        Actor a = new Actor(new Coordinate(0,0)) {};

        assertEquals(a.consoleString(), "\uD83D\uDC64");
    }
    @Test
    public void functionTestActor12() {
        Actor a = new Actor(new Coordinate(0,0)) {};

        assertEquals(a.listenForMessage(), null);
    }
    @Test
    public void functionTestActor13() {
        Actor a = new Actor(new Coordinate(0,0)) {};
        a.setFacing(Direction.parseDirection("north"));

        assertEquals(a.getFacing(), Direction.NORTH);
    }
    @Test
    public void functionTestActor14() {
        Actor a = new Actor(new Coordinate(0,0)) {};

        // this should throw an error and be null
        assertEquals(a.getImage(), null);
    }

    // testing items
    @Test
    public void functionTestItem1() {
        Item i = new Item(ItemType.KEY, false) {
            @Override
            public void interact(Actor actor) {

            }
        };

        assertEquals(i.getId(), "ID: " +  ItemType.KEY);
        assertEquals(i.toString(), "Item{type=KEY}");
        assertEquals(i.consoleString(), "");
    }
    @Test
    public void functionTestItem2() {
        Item_Door i = new Item_Door("Blue");

        assertEquals(i.getColor(), "Blue");
        assertEquals(i.toString(), "Item{type=LOCK_DOOR,Blue}");
        assertEquals(i.consoleString(), "\uD83D\uDEAA|");
        assertEquals(i.preInteract(null, null, null), false);
    }
    @Test
    public void functionTestItem3() {
        Item_Key i = new Item_Key("Blue");

        assertEquals(i.getColor(), "Blue");
        assertEquals(i.toString(), "Item{type=KEY,Blue}");
        assertEquals(i.consoleString(), "♥|");
    }
    @Test
    public void functionTestItem4() {
        Item_Push_Block i = new Item_Push_Block();
        i.interact(null);

        assertEquals(i.preInteract(null, null, null), false);
        assertEquals(i.toString(), "Item{type=PUSH_BLOCK}");
        assertEquals(i.consoleString(), "❒|");
    }
    @Test
    public void functionTestItem5() {
        Item_Info i = new Item_Info("Hi");
        Player me = new Player(new Coordinate(0,0));
        i.interact(me);

        assertEquals(me.listenForMessage(), "Hi");
        assertEquals(i.getInfo(), "Hi");
        assertEquals(i.consoleString(), "i|");
    }

    // Tile tests
    @Test
    public void functionTestTile1() {
        Tile t1 = new Tile(new Coordinate(0,0), TileType.FREE);
        Tile t2 = new Tile(new Coordinate(0,0), TileType.WALL);
        Tile t3 = new Tile(new Coordinate(0,0), TileType.EXIT);
        Tile t4 = new Tile(new Coordinate(0,0), TileType.WALL);
        t4.setItem(new Item_Key("Red"));

        assertEquals(t1.consoleString(), "_|");
        assertEquals(t2.consoleString(), "#|");
        assertEquals(t3.consoleString(), "!|");
        assertEquals(t4.consoleString(), "♥|");
        assertEquals(t4.toString(), "Tile{{0, 0}, WALL}");
    }
    @Test
    public void functionTestTile2() {
        Tile t1 = new Tile(new Coordinate(0, 0), TileType.FREE);

        t1.setType(TileType.WALL);
        t1.setLocation(new Coordinate(1,1));

        assertEquals(t1.getType(), TileType.WALL);
        assertEquals(t1.getLocation().getX(), 1);
        assertEquals(t1.getLocation().getY(), 1);
    }
}
