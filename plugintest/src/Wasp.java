import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Enemy;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A new actor, bug that moves through the maze and tries to eat Chap.
 * The logic of this actor, and how it is rendered is defined.
 *
 * @author Rae 300535154 & Nathanael 300478745
 */
public class Wasp extends Enemy {

    private Coordinate position;
    private String imagePath = "./res/graphics/wasp.png";
    /**
     * default constructor.
     */
    public Wasp() {
    }

    /**
     * Entirely standard constructor.
     *
     * @param initial pos
     *
     */
    public Wasp(Coordinate initial) {
        super(initial);
    }

    /**
     * get the current pos.
     *
     * @return pos
     */
    public Coordinate getPosition() {
        return position;
    }

    /**
     * set the pos for the bug.
     *
     * @param position pos
     */
    public void setPosition(Coordinate position) {
        this.position = position;
    }

    /**
     * get the output string of the pos.
     *
     * @return pos string
     */
    @Override
    public String toString() {
        return "position=" + position ;
    }

    /**
     * This method handles the AI movement mode of the enemy, which is the logic of this actor.
     *
     * NOTE: Mode targets the player.
     *
     * @return - resulting direction.
     *
     */
    @Override
    public Direction movementModeAIDirection(Board board, Player hero) {
        return Direction.facingFromPositionChange(getPosition(), hero.getPosition());
    }

    /**
     * The name is bug, this will load the image, which is used for rendering.
     *
     * @return name
     *
     */
    @Override
    public String getName() {
        return "wasp";
    }

    /**
     * get the image of this Enemy.
     *
     * @return image
     */
    public BufferedImage getImage() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(imagePath));
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
        return "\uD83D\uDC15";
    }
}