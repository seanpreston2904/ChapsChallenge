package nz.ac.vuw.ecs.swen225.gp21.renderer;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Enemy;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.TileType;
import nz.ac.vuw.ecs.swen225.gp21.renderer.util.ImagePaths;

import javax.imageio.ImageIO;
import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.BitSet;
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

    //Reference to game domain
    private Domain game;

    //Element -> Animator maps
    private HashMap<Actor, ActorAnimator> actors;
    private HashMap<Item, ItemAnimator> items;
    private HashMap<Tile, TileAnimator> tiles;

    //Top left and bottom right position on the screen
    private Point topLeft;

    //Pixel offsets for camera
    private int viewOffsetX;
    private int viewOffsetY;

    Timer movePlayer;

    /**
     * RenderView Constructor.
     *
     * @param game game to construct animator data from.
     */
    public RenderView(Domain game){

        //Forces a fixed size for the panel
        this.setSize(VIEWPORT_SIZE);
        this.setPreferredSize(VIEWPORT_SIZE);
        this.setMaximumSize(VIEWPORT_SIZE);
        this.setMinimumSize(VIEWPORT_SIZE);

        //Construct timer
        timer = new Timer((int)FPS_60, action -> update());

        //Set board reference
        this.game = game;

        //Construct animator maps
        this.actors = new HashMap<>();
        this.items = new HashMap<>();
        this.tiles = new HashMap<>();

        //Add player to actor map
        this.actors.put(this.game.getPlayer(), new ActorAnimator(this.game.getPlayer()));

        //Set animator maps
        for(int row = 0; row < this.game.getBoard().getDimension().height; row++){
            for(int col = 0; col < this.game.getBoard().getDimension().width; col++){

                //Get the current tile on the board and construct an animator for it
                Tile curr = this.game.getBoard().getTile(new Coordinate(col, row));
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

        //Get width and height of board
        int boardWidth = this.game.getBoard().getDimension().width;
        int boardHeight = this.game.getBoard().getDimension().height;

        //Get the viewport position
        getViewportDimensions();

        for(int row = 0; row < boardHeight; row++){
            for(int col = 0; col < boardWidth; col++){

                //right, this makes sense
                TileAnimator tile = tiles.get(this.game.getBoard().getTile(new Coordinate(col, row)));

                //Get item on tile
                ItemAnimator item = items.get(this.game.getBoard().getTile(new Coordinate(col, row)).getItem());

                //Calculate the X and Y position of the tile
                int xPos = (tile.getTile().getLocation().getX() - topLeft.x) * TILE_SIZE ;
                int yPos = (tile.getTile().getLocation().getY() - topLeft.y) * TILE_SIZE ;

                //Render appropriate tile graphic
                switch(tile.getTile().getType()){

                    case FREE: g.drawImage(ImagePaths.IMG_FLOOR, xPos, yPos, null); break;
                    case WALL: g.drawImage(ImagePaths.IMG_WALL_FULL, xPos, yPos, null); break;
                    case EXIT: g.drawImage(ImagePaths.IMG_EXIT, xPos, yPos, null); break;

                }

                //If the tile has an item on it, render it.
                if(item != null){

                    //Draw appropriate image
                    switch (tile.getTile().getItem().getType()){

                        case INFO: g.drawImage(ImagePaths.IMG_INFO, xPos, yPos, null); break;
                        case TREASURE: g.drawImage(ImagePaths.IMG_TREASURE, xPos, yPos, null); break;
                        case KEY: g.drawImage(ImagePaths.IMG_KEY, xPos, yPos, null); break;
                        case LOCK_DOOR: g.drawImage(ImagePaths.IMG_DOOR, xPos, yPos, null); break;

                    }

                }

            }

        }

        //For each actor...
        for(ActorAnimator a: actors.values()){

            //Calculate its X and Y positions
            int xPos = (a.actor.getPosition().getX() - topLeft.x) * TILE_SIZE;
            int yPos = (a.actor.getPosition().getY() - topLeft.y) * TILE_SIZE;

            //If the actor is a player, render the player graphic
            if(a.actor instanceof Player){ g.drawImage(ImagePaths.IMG_PLAYER, xPos, yPos, null); }

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

        //Get the player's position
        Coordinate playerPosition = game.getPlayer().getPosition();

        if(playerPosition.getX() >= 4 && playerPosition.getX() < game.getBoard().getDimension().width - 4
        && playerPosition.getY() >= 4 && playerPosition.getY() < game.getBoard().getDimension().height - 4){

            this.topLeft = new Point(playerPosition.getX()-4, playerPosition.getY()-4);

        }

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