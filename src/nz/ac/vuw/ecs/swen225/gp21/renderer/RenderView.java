package nz.ac.vuw.ecs.swen225.gp21.renderer;

import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.renderer.util.ImagePaths;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class RenderView extends JPanel {

    //Frame interval in milliseconds at 60 FPS
    private static final float FPS_60 = 1000.0f/60.0f;

    //Size of a tile in pixels
    private static final int TILE_SIZE = 64;

    //The size of the viewport
    private static final Dimension VIEWPORT_SIZE =
            new Dimension(TILE_SIZE*9, TILE_SIZE*9);

    //Event timer (used to render board)
    private Timer timer;

    //Reference to game board
    private Board board;

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
    public RenderView(Board board){

        //Forces a fixed size for the panel
        this.setSize(VIEWPORT_SIZE);
        this.setPreferredSize(VIEWPORT_SIZE);
        this.setMaximumSize(VIEWPORT_SIZE);
        this.setMinimumSize(VIEWPORT_SIZE);

        //Construct timer
        timer = new Timer((int)FPS_60, action -> update());

        //Set board reference
        this.board = board;

        //Construct animator maps
        this.actors = new HashMap<>();
        this.items = new HashMap<>();
        this.tiles = new HashMap<>();

        //Set animator maps
        for(int row = 0; row < this.board.getDimension().height; row++){
            for(int col = 0; col < this.board.getDimension().width; col++){

                //Get the current tile on the board and construct an animator for it
                Tile curr = this.board.getTile(new Coordinate(col, row));
                tiles.put(curr, new TileAnimator(curr));

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

        //Draw Background
        super.paintComponent(g);

        //STEPS FOR RENDERING BOARD
        //1. Find Viewport Dimensions
        //2. Render Tiles to Screen
        //3. Render Items to Screen
        //4. Render Actors to Screen

        for(int row = 0; row < board.getDimension().height; row++){
            for(int col = 0; col < board.getDimension().width; col++){

                //Get the associated animator for the tile
                TileAnimator t = tiles.get(board.getTile(new Coordinate(col, row)));

                //Calculate it's X and Y positions
                int xPos = t.getTile().getLocation().getX()*TILE_SIZE;
                int yPos = t.getTile().getLocation().getY()*TILE_SIZE;

                //Draw tile onto screen
                g.drawImage(ImagePaths.IMG_FLOOR, xPos, yPos, null);

            }
        }

    }

    /**
     * Transforms necessary graphics data and draws to panel.
     */
    private void update(){ repaint(); }

    /**
     * Gets the appropriate viewport dimensions
     */
    private void getViewportDimensions(){

        return;

    }

    /**
     * Starts the renderer.
     */
    public void startRender(){ timer.start(); }

    /**
     * Stops the renderer.
     */
    public void stopRender(){ timer.stop(); }

}