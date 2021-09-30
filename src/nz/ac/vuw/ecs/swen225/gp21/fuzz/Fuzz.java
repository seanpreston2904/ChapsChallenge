package nz.ac.vuw.ecs.swen225.gp21.fuzz;

import java.util.ArrayList;
import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.ItemType;
import org.junit.jupiter.api.Test;


/**
 * JUnit test cases testing levels 1 & 2.
 *
 * @author harry
 */
public class Fuzz {
  /**
   * AI test completing level 1 of the game.
   */
  @Test
  public void fuzzTest1() {
    Domain domain = new Domain("level1");
    Player player = domain.getPlayer();
    domain.setRunning(true);
    while (domain.isRunning()) {
      domain.moveActor(player, aiHelper(domain));
    }
    System.out.println("is game running: " + domain.isRunning());
    System.out.println(domain.getRemainingChips() + "RemainingChips");
  }

  /**
   * AI test completing level 2 of the game.
   */
  @Test
  public void fuzzTest2() {
    Domain domain = new Domain("level2");
    Player player = domain.getPlayer();
    domain.setRunning(true);

    while (domain.isRunning()) {
      domain.moveActor(player, aiHelper(domain));
    }
    System.out.println("is game running: " + domain.isRunning());
    System.out.println(domain.getRemainingChips() + " RemainingChips");
  }
  /**
  * Checks to see if there is a Enemy
  * next to the player and then returns the recommended
  * Direction to move to.
  *
  * @return Direction
  */

  public Direction aiHelper(Domain domain) {
    //Players X pos
    int playerX = domain.getPlayer().getPosition().getX();
    //Players Y pos
    int playerY = domain.getPlayer().getPosition().getY();
    ArrayList<Actor> actors = domain.getActors();
    //Coordinate to get Tile left of player
    Coordinate corrLeft = new Coordinate(playerX - 1, playerY);
    //Coordinate to get Tile right of player
    Coordinate corrRight = new Coordinate(playerX + 1, playerY);
    //Coordinate to get Tile Top of player
    Coordinate corrTop = new Coordinate(playerX, playerY - 1);
    //Coordinate to get Tile below of player
    Coordinate corrBelow = new Coordinate(playerX, playerY + 1);
    Direction d = Direction.SOUTH;
    //If the tiles are empty around the player then move in a random direction
    if (domain.anotherEnemyInThisSpace(actors, corrRight) == null && domain.anotherEnemyInThisSpace(actors,
            corrLeft) == null && domain.anotherEnemyInThisSpace(actors, corrTop) == null
            && domain.anotherEnemyInThisSpace(actors, corrBelow) == null) {
      d = domain.randomlyMoveActor();
    }
    //Checking if there is an Enemy North, East, South, or West of player and move away from enemy
    if (domain.anotherEnemyInThisSpace(actors, corrRight) != null && domain.anotherEnemyInThisSpace(actors,
            corrLeft) == null) {
      d = Direction.WEST;
    } else if (domain.anotherEnemyInThisSpace(actors, corrRight) != null && domain.anotherEnemyInThisSpace(actors,
            corrTop) == null) {
      System.out.println("1");
      d = Direction.NORTH;
    } else if (domain.anotherEnemyInThisSpace(actors, corrRight) != null && domain.anotherEnemyInThisSpace(actors,
            corrBelow) == null) {
      d = Direction.SOUTH;

    } else if (domain.anotherEnemyInThisSpace(actors, corrRight) == null && domain.anotherEnemyInThisSpace(actors,
            corrLeft) != null) {
      d = Direction.EAST;

    } else if (domain.anotherEnemyInThisSpace(actors, corrRight) == null && domain.anotherEnemyInThisSpace(actors,
             corrBelow) != null) {
      d = Direction.SOUTH;
    } else if (domain.anotherEnemyInThisSpace(actors, corrRight) == null && domain.anotherEnemyInThisSpace(actors,
            corrTop) != null) {
      d = Direction.NORTH;

    } else if (domain.anotherEnemyInThisSpace(actors, corrLeft) != null && domain.anotherEnemyInThisSpace(actors,
             corrRight) == null) {
      System.out.println("2");
      d = Direction.EAST;

    } else if (domain.anotherEnemyInThisSpace(actors, corrLeft) != null && domain.anotherEnemyInThisSpace(actors,
              corrTop) == null) {
      d = Direction.NORTH;

    } else if (domain.anotherEnemyInThisSpace(actors, corrLeft) != null && domain.anotherEnemyInThisSpace(actors,
              corrBelow) == null) {
      d = Direction.SOUTH;

    } else if (domain.anotherEnemyInThisSpace(actors, corrLeft) == null && domain.anotherEnemyInThisSpace(actors,
              corrRight) != null) {
      d = Direction.WEST;

    } else if (domain.anotherEnemyInThisSpace(actors, corrLeft) == null && domain.anotherEnemyInThisSpace(actors,
              corrBelow) != null) {
      d = Direction.SOUTH;

    } else if (domain.anotherEnemyInThisSpace(actors, corrLeft) == null && domain.anotherEnemyInThisSpace(actors,
              corrTop) != null) {
      d = Direction.NORTH;

    } else if (domain.anotherEnemyInThisSpace(actors, corrBelow) != null && domain.anotherEnemyInThisSpace(actors,
              corrTop) != null && domain.anotherEnemyInThisSpace(actors, corrRight) == null) {
      d = Direction.EAST;
    } else if (domain.anotherEnemyInThisSpace(actors, corrBelow) != null && domain.anotherEnemyInThisSpace(actors,
            corrTop) == null) {
      d = Direction.NORTH;

    } else if (domain.anotherEnemyInThisSpace(actors, corrBelow) != null && domain.anotherEnemyInThisSpace(actors,
            corrRight) == null) {
      d = Direction.EAST;
    } else if (domain.anotherEnemyInThisSpace(actors, corrBelow) != null && domain.anotherEnemyInThisSpace(actors,
            corrLeft) == null && !domain.getBoard().getTile(corrLeft).getItem().getType().equals(ItemType.PUSH_BLOCK)) {
      d = Direction.WEST;

    } else if (domain.anotherEnemyInThisSpace(actors, corrBelow) == null && domain.anotherEnemyInThisSpace(actors,
                corrTop) != null) {
      d = Direction.SOUTH;

    } else if (domain.anotherEnemyInThisSpace(actors, corrBelow) == null && domain.anotherEnemyInThisSpace(actors,
                corrLeft) != null) {
      d = Direction.SOUTH;

    } else if (domain.anotherEnemyInThisSpace(actors, corrTop) == null && domain.anotherEnemyInThisSpace(actors,
                corrBelow) != null) {
      d = Direction.WEST;

    } else if (domain.anotherEnemyInThisSpace(actors, corrTop) != null && domain.anotherEnemyInThisSpace(actors,
                corrBelow) == null) {
      d = Direction.SOUTH;

    } else if (domain.anotherEnemyInThisSpace(actors, corrTop) != null && domain.anotherEnemyInThisSpace(actors,
                corrLeft) == null) {
      d = Direction.WEST;

    } else if (domain.anotherEnemyInThisSpace(actors, corrTop) != null && domain.anotherEnemyInThisSpace(actors,
                corrRight) == null) {
      d = Direction.EAST;

    } else if (domain.anotherEnemyInThisSpace(actors, corrTop) != null && domain.anotherEnemyInThisSpace(actors,
                corrBelow) == null) {
      d = Direction.SOUTH;

    } else if (domain.anotherEnemyInThisSpace(actors, corrLeft) != null) {
      d = Direction.EAST;

    } else if (domain.anotherEnemyInThisSpace(actors, corrRight) != null) {
      d = Direction.WEST;
    } else if (domain.anotherEnemyInThisSpace(actors, corrTop) != null) {
      d = Direction.SOUTH;
    }
    return d;
  }
}






