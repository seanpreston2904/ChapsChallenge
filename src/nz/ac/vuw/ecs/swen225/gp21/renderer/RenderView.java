package nz.ac.vuw.ecs.swen225.gp21.renderer;

import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

class RenderView extends JPanel {

    //Frame interval in milliseconds at 60 FPS
    private static final float FPS_60 = 1000.0f/60.0f;

    //Size of a tile in pixels
    private static final int TILE_SIZE = 64;

    //The size of the viewport
    private static final Dimension VIEWPORT_SIZE =
            new Dimension(TILE_SIZE*64, TILE_SIZE*64);

    //Event timer (used to render board)
    private Timer timer;

    //Element -> Animator maps
    private HashMap<Actor, ActorAnimator> actors;
    private HashMap<Item, ItemAnimator> items;
    private HashMap<Tile, TileAnimator> tiles;

    //Top left and bottom right positions on the screen
    private Point topLeft;
    private Point bottomRight;

    //Pixel offsets for camera
    private int viewOffsetX;
    private int viewOffsetY;

    /**
     * RenderView Constructor.
     *
     * @param board board to construct animator data from.
     */
    RenderView(Board board){

        //Forces a fixed size for the panel
        this.setSize(VIEWPORT_SIZE);
        this.setPreferredSize(VIEWPORT_SIZE);
        this.setMaximumSize(VIEWPORT_SIZE);
        this.setMinimumSize(VIEWPORT_SIZE);

        //Construct timer
        timer = new Timer((int)FPS_60, action -> update());

        //TODO: Replace max number with size of board
        for(int row = 0; row < 999; row++){
            for(int col = 0; col < 999; col++){

                //Get the current tile on the board and construct an animator for it
                Tile curr = board.getTile(new Coordinate(col, row));
                tiles.put(curr, new TileAnimator());

                //If the tile has an item, construct an animator for it
                if(curr.getItem() != null){ items.put(curr.getItem(), new ItemAnimator()); }

            }

        }

    }

    /**
     * Internal render method (strictly for drawing).
     *
     * @param g graphics context used to draw graphics onto panel.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    /**
     * Transforms necessary graphics data and draws to panel.
     */
    private void update(){ repaint(); }

    /**
     * Starts the renderer.
     */
    public void startRender(){ timer.start(); }

    /**
     * Stops the renderer.
     */
    public void stopRender(){ timer.stop(); }

}