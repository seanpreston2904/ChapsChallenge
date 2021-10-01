package nz.ac.vuw.ecs.swen225.gp21.domain.tests;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * JUnit test cases
 *
 * @author harry
 *
 */
public class UtilTests {

    /**
     * Test Direction.facingFromPositionChange method
     */
    @Test
    public void directionTest1() {
        Direction d = Direction.SOUTH;
        Coordinate a = new Coordinate(1,1);
        Coordinate b = new Coordinate(1, 2);

        assertEquals(Direction.facingFromPositionChange(a, b), d);
    }
    @Test
    public void directionTest2() {
        Direction d = Direction.SOUTH;
        Coordinate a = new Coordinate(1,1);
        Coordinate b = new Coordinate(1, 3);

        assertEquals(Direction.facingFromPositionChange(a, b), d);
    }
    @Test
    public void directionTest3() {
        Direction d = Direction.SOUTH;
        Coordinate a = new Coordinate(1,1);
        Coordinate b = new Coordinate(1, 8);

        assertEquals(Direction.facingFromPositionChange(a, b), d);
    }
    @Test
    public void directionTest4() {
        Direction d = Direction.NORTH;
        Coordinate a = new Coordinate(1,2);
        Coordinate b = new Coordinate(1, 1);

        assertEquals(Direction.facingFromPositionChange(a, b), d);
    }
    @Test
    public void directionTest5() {
        Direction d = Direction.NORTH;
        Coordinate a = new Coordinate(1,5);
        Coordinate b = new Coordinate(1, 0);

        assertEquals(Direction.facingFromPositionChange(a, b), d);
    }
    @Test
    public void directionTest6() {
        Direction d = Direction.NORTH;
        Coordinate a = new Coordinate(1,8);
        Coordinate b = new Coordinate(1, -1);

        assertEquals(Direction.facingFromPositionChange(a, b), d);
    }

    /**
     * Now we test in the X axis
     */
    @Test
    public void directionTestX1() {
        Direction d = Direction.WEST;
        Coordinate a = new Coordinate(1,1);
        Coordinate b = new Coordinate(0, 1);

        assertEquals(Direction.facingFromPositionChange(a, b), d);
    }
    @Test
    public void directionTestX2() {
        Direction d = Direction.WEST;
        Coordinate a = new Coordinate(3,1);
        Coordinate b = new Coordinate(0, 1);

        assertEquals(Direction.facingFromPositionChange(a, b), d);
    }
    @Test
    public void directionTestX3() {
        Direction d = Direction.WEST;
        Coordinate a = new Coordinate(4,1);
        Coordinate b = new Coordinate(-1, 1);

        assertEquals(Direction.facingFromPositionChange(a, b), d);
    }
    /**
     * Now we test in the X axis the other facing
     */
    @Test
    public void directionTestX4() {
        Direction d = Direction.EAST;
        Coordinate a = new Coordinate(0,1);
        Coordinate b = new Coordinate(1, 1);

        assertEquals(Direction.facingFromPositionChange(a, b), d);
    }
    @Test
    public void directionTestX5() {
        Direction d = Direction.EAST;
        Coordinate a = new Coordinate(2,1);
        Coordinate b = new Coordinate(5, 1);

        assertEquals(Direction.facingFromPositionChange(a, b), d);
    }
    @Test
    public void directionTestX6() {
        Direction d = Direction.EAST;
        Coordinate a = new Coordinate(-1,1);
        Coordinate b = new Coordinate(0, 1);

        assertEquals(Direction.facingFromPositionChange(a, b), d);
    }

    /**
     * Now we add more complicated tests in two axes.
     */
    @Test
    public void directionTestBoth1() {
        // Y AXIS has the greater change and therefore overrides
        Direction d = Direction.SOUTH;
        Coordinate a = new Coordinate(-1,1);
        Coordinate b = new Coordinate(0, 5);

        assertEquals(Direction.facingFromPositionChange(a, b), d);
    }
    @Test
    public void directionTestBoth2() {
        // X AXIS has the greater change and therefore overrides
        Direction d = Direction.EAST;
        Coordinate a = new Coordinate(-1,1);
        Coordinate b = new Coordinate(111, 5);

        assertEquals(Direction.facingFromPositionChange(a, b), d);
    }
    @Test
    public void directionTestBoth3() {
        // X AXIS has the greater change and therefore overrides
        Direction d = Direction.WEST;
        Coordinate a = new Coordinate(-1,0);
        Coordinate b = new Coordinate(-10, 5);

        assertEquals(Direction.facingFromPositionChange(a, b), d);
    }
    @Test
    public void directionTestBoth4() {
        // Y AXIS has the greater change and therefore overrides
        Direction d = Direction.NORTH;
        Coordinate a = new Coordinate(-1,12);
        Coordinate b = new Coordinate(1, 5);

        assertEquals(Direction.facingFromPositionChange(a, b), d);
    }

    /**
     * Now for the most complicated tests. What happens when the changes are equal? Let us prioritize the compass X axis changes over Y axis (i.e. N,S E,W).
     */
    @Test
    public void directionTestComplication1() {
        // Y AXIS has the greater change and therefore overrides
        Direction d = Direction.SOUTH;
        Coordinate a = new Coordinate(-1,-1);
        Coordinate b = new Coordinate(1, 1);

        assertEquals(Direction.facingFromPositionChange(a, b), d);
    }
    @Test
    public void directionTestComplication2() {
        // Y AXIS has the greater change and therefore overrides
        Direction d = Direction.NORTH;
        Coordinate a = new Coordinate(-1,1);
        Coordinate b = new Coordinate(1, -1);

        assertEquals(Direction.facingFromPositionChange(a, b), d);
    }
    @Test
    public void directionTestComplication3() {
        // Y AXIS has the greater change and therefore overrides
        Direction d = Direction.NORTH;
        Coordinate a = new Coordinate(1,0);
        Coordinate b = new Coordinate(0, -1);

        assertEquals(Direction.facingFromPositionChange(a, b), d);
    }


    /**
     * Now we test the specific use case of lvl 2
     */
    @Test
    public void lvlBasedDirectionTest1() {
        // Y AXIS has the greater change and therefore overrides
        Direction d = Direction.NORTH;
        Coordinate a = new Coordinate(8,4);
        Coordinate b = new Coordinate(8, 3);

        assertEquals(Direction.facingFromPositionChange(a, b), d);
    }
}
