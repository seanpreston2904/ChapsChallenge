package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class ElementAnimator {

    //List of animation sequences
    //private ArrayList<ArrayList<Image>> animationSeqences;

    BufferedImage image;

    //Indices for the currently running animation, and current frame
    private int currAnimation;
    private int currFrame;

    ElementAnimator(BufferedImage i){ this.image = i; }

    public BufferedImage getImage(){ return image; }

}
