package nz.ac.vuw.ecs.swen225.gp21.domain.actor;

import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Actor is the abstract class that movable actors in the levels will inherit.
 *
 * Note: Actor is part of the domain. I understand this is not exactly what is anticipated but spoke with Jens
 * and he said it was fine as long as sub-actors where loaded by the Persistence for plugin features.
 *
 * See Persistency/levels/level2.jar for plugin features.
 *
 */
public abstract class Actor {

    private Coordinate position;

    private Direction facingDirection;

    private String infoMessage;

    private String pathName;

    /**
     * Default constructor with coordinates.
     *
     * @param initial
     *
     */
    public Actor(Coordinate initial) {
        this.position = initial;
        this.pathName = "abstract";
    }

    /**
     * Default constructor no parameters.
     */
    public Actor() {
        this.position = new Coordinate(-1,-1);
    }

    /**
     * Utility method to move the actor a certain direction and return its resulting position.
     *
     * @param direction the direction to move to
     *
     * @return
     *
     */
    public Coordinate getResultingLocation(Direction direction) {
        return new Coordinate(this.getPosition().getX(), this.getPosition().getY()).getResultingLocation(direction);
    }

    /**
     * setter for the object's location.
     *
     * @param position
     */
    public void setPosition(Coordinate position) {
        // change the actor's facing
        if (Direction.facingFromPositionChange(getPosition(), position) != null) {
            this.facingDirection = Direction.facingFromPositionChange(getPosition(), position);
        }

        this.position = position;
    }

    /**
     * getter for the object's location.
     *
     * @return position
     */
    public Coordinate getPosition() {
        return this.position;
    }

    /**
     * getter for the object's facing direction.
     *
     * @return facing direction
     */
    public Direction getFacing() {
        return this.facingDirection;
    }

    /**
     * setter for the object's facing direction.
     *
     * @param facing
     *
     */
    public void setFacing(Direction facing) {
        this.facingDirection = facing;
    }

    /**
     * Getter to return the current info message to be shown.
     *
     * @return
     */
    public String listenForMessage() {
        return infoMessage;
    }

    /**
     * Setter for info message used by the tile.
     *
     * @param infoMessage
     */
    public void setInfoMessage(String infoMessage) {
        this.infoMessage = infoMessage;
    }

    /**
     * Parse a string into an actor.
     *
     * @param actor
     *
     * @return
     *
     */
    public static Actor parseActor(String actor) {
        if (actor.toLowerCase().equals("hero")) {

            return new Player(new Coordinate(0,0));

        } else if (actor.toLowerCase().equals("enemy")) {

            return new Enemy(new Coordinate(0,0)) {};
        }
        else {
        	
            return null;
        }
    }

    /**
     * Getter for the actor's name.
     */
    public String getName() {
        return "abstract";
    }

    /**
     * Getter for the actor's ID.
     */
    public String getID() {
        return "abstract";
    }

    /**
     * Get the image of this Enemy.
     *
     * @return image
     *
     */
    public BufferedImage getImage() {
        BufferedImage image = null;
        try {
            // pathname instantiated by subclasses
            image = ImageIO.read(new File(pathName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * Return a string for the console based version of this board.
     *
     * @return
     */
    public String consoleString() {
        return "\uD83D\uDC64";
    }
}
