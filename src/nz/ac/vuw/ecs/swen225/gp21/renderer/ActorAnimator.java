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
    public void setAnimationOffset(Direction d){

        //Set the actor's animation offset based on the direction of movement
        switch (d){

            case NORTH: animationOffset.setY(-64); break;
            case SOUTH: animationOffset.setY(64); break;
            case EAST: animationOffset.setX(-64); break;
            case WEST: animationOffset.setX(64); break;

        }

    }

    /**
     * Method to move one "tick" through the transition (who's current position is determined by the animation offset)
     */
    public void tick(){

        //Increment or decrement the animation offset on the X axis, based on its current value
        if(animationOffset.getX() > 0){ animationOffset.setX(animationOffset.getX() - 16); }
        else if(animationOffset.getX() < 0){ animationOffset.setX(animationOffset.getX() + 16); }

        //Increment or decrement the animation offset on the Y axis, based on its current value
        if(animationOffset.getY() > 0){ animationOffset.setY(animationOffset.getY() - 16); }
        else if(animationOffset.getY() < 0){ animationOffset.setY(animationOffset.getY() + 16); }

    }

}
