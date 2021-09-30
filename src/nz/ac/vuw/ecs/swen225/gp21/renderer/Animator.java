package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Animator {

    //Image to draw on screen when element is rendered
    BufferedImage image;

    /**
     * Constructor for element animator.
     *
     * @param i The elements image that will be rendered at runtime.
     */
    Animator(BufferedImage i){ this.image = i; }

    /**
     * Get the image associated with the animator.
     *
     * @return The BufferedImage containing the animator's image data.
     */
    public BufferedImage getImage(){ return image; }

}
