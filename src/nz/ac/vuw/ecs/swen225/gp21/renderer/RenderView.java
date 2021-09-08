package nz.ac.vuw.ecs.swen225.gp21.renderer;

import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.*;
import java.sql.Array;
import java.util.ArrayList;

class RenderView extends JPanel {

    private final float FPS_60 = 1000.0f/60.0f;     //Frame interval in milliseconds

    private Timer timer;                            //Timer for even frame intervals

    private ArrayList<ActorAnimator> actors;        //Actors (Players, enemies, etc.) on the board
    private ArrayList<ItemAnimator> items;          //Items (Chips, Keys, etc.) on board
    private TileAnimator[][] tiles;                 //Tiles (Floor, Walls, Water, etc.) on board

    private TileAnimator topLeft;                   //Top left tile in view of the camera
    private TileAnimator bottomRight;               //Bottom right tile in view of the camera

    private int viewOffsetX;                        //Offset in pixels on X axis (used for camera transitions)
    private int viewOffsetY;                        //Offset in pixels on Y axis (used for camera transitions)

    /**
     * RenderView Constructor
     *
     * @param board board to construct animator data from
     */
    RenderView(Board board){

        //TODO: Literally everything

    }

    /**
     * Internal render method (strictly for drawing)
     *
     * @param g graphics context used to draw graphics onto panel
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    /**
     * Transforms necessary graphics data and draws to panel
     */
    private void update(){ repaint(); }

}