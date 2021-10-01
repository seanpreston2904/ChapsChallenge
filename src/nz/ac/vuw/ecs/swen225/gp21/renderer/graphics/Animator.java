package nz.ac.vuw.ecs.swen225.gp21.renderer.graphics;

import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class Animator {

    //Image to draw on screen when element is rendered
    BufferedImage image;

    //Animation Offset (used to determine top left position of sprite mid-animation)
    Coordinate animationOffset;

    /**
     * Constructor for element animator.
     *
     * @param i The elements image that will be rendered at runtime.
     */
    Animator(BufferedImage i){

        this.image = i;
        this.animationOffset = new Coordinate(0, 0);

    }

    /**
     * Get the image associated with the animator.
     *
     * @return The BufferedImage containing the animator's image data.
     */
    public BufferedImage getImage(){ return image; }

    /**
     * Move the animator in a specified direction
     * @param d direction to move animator in.
     */
    public void setAnimationOffset(Direction d){

        //Set the actor's animation offset based on the direction of movement
        switch (d){

            case NORTH: animationOffset.setY(64); break;
            case SOUTH: animationOffset.setY(-64); break;
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

