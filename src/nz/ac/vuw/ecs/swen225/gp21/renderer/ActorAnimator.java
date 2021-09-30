package nz.ac.vuw.ecs.swen225.gp21.renderer;

import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;

import java.awt.image.BufferedImage;

public class ActorAnimator extends Animator {

    Coordinate animationOffset;         //Animation Offset (used to determine top left position of sprite mid-animation)

    /**
     * Actor Animator Constructor
     * @param i Image of actor to be displayed at runtime.
     */
    ActorAnimator(BufferedImage i){

        super(i);

        animationOffset = new Coordinate(0, 0);
    }

    /**
     * Move the animator in a specified direction
     * @param d direction to move animator in.
     */
    public void move(Direction d){

        //TODO: update the animation offset (maybe on a separate thread? (maybe use timer? (this is gonna be interesting ¯\_(ツ)_/¯)))
        //Assuming this method is called AFTER moving an actor, we can set the offset position such that the actor
        //appears in their original position. We can then gradually ease it back to 0 (player input should not be
        //possible during this time).

    }

}
