package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class ElementAnimator {

    //List of animation sequences
    private ArrayList<ArrayList<Image>> animationSeqences;

    //Indices for the currently running animation, and current frame
    private int currAnimation;
    private int currFrame;

    /**
     * Adds a sequence of images as an animation sequence to the actor
     *
     * @param sequence images to add to sequence
     */
    public void addAnimationSequence(Image... sequence){ animationSeqences.add(new ArrayList<Image>(Arrays.asList(sequence))); }

}
