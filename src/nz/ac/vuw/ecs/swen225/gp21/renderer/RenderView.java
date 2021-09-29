package nz.ac.vuw.ecs.swen225.gp21.renderer;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Enemy;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.*;
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
    private Coordinate topLeft;

    //Pixel offsets for camera
    private int viewOffsetX;
    private int viewOffsetY;

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

        this.topLeft = getViewportDimensions();

        //Construct animator maps
        this.actors = new HashMap<>();
        this.items = new HashMap<>();
        this.tiles = new HashMap<>();

        //Add actors to actor map
        for(Actor a: this.game.getActors()){
            actors.put(a, new ActorAnimator(loadImage("./res/graphics/"+a.getName()+".png")));
        }

        actors.put(this.game.getPlayer(), new ActorAnimator(loadImage("./res/graphics/player.png")));

        //Set animator maps
        for(int row = 0; row < this.game.getBoard().getDimension().height; row++){
            for(int col = 0; col < this.game.getBoard().getDimension().width; col++){

                //Get the current tile on the board and construct an animator for it
                Tile curr = this.game.getBoard().getTile(new Coordinate(col, row));

                //Select appropriate tile image
                BufferedImage tileImage;
                switch(curr.getType()){

                    case WALL: tileImage = loadImage("./res/graphics/wall_full.png"); break;
                    case FREE: tileImage = loadImage("./res/graphics/floor.png"); break;
                    case EXIT: tileImage = loadImage("./res/graphics/exit.png"); break;
                    default: tileImage = loadImage("./res/graphics/missing_texture.png");

                }

                tiles.put(curr, new TileAnimator(tileImage));

                //If the tile has an item, construct an animator for it
                if(curr.getItem() != null){

                    Item currItem = curr.getItem();

                    //Select appropriate item image
                    BufferedImage itemImage;
                    switch(curr.getItem().getType()){

                        case PUSH_BLOCK: itemImage = loadImage("./res/graphics/pushable_block.png"); break;
                        case LOCK_EXIT: itemImage = loadImage("./res/graphics/exit_door.png"); break;
                        case LOCK_DOOR: itemImage = loadImage("./res/graphics/door_"+((Item_Door) currItem).getColor()+".png"); break;
                        case TREASURE: itemImage = loadImage("./res/graphics/treasure.png"); break;
                        case INFO: itemImage = loadImage("./res/graphics/info.png"); break;
                        case KEY: itemImage = loadImage("./res/graphics/key_"+((Item_Key) currItem).getColor()+".png"); break;
                        default: itemImage = loadImage("./res/graphics/missing_texture.png"); break;

                    }

                    items.put(currItem, new ItemAnimator(itemImage));

                }

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
        this.topLeft = getViewportDimensions();

        for(int row = 0; row < boardHeight; row++){
            for(int col = 0; col < boardWidth; col++){

                //Get tile at row, col on board.
                Tile tile = this.game.getBoard().getTile(new Coordinate(col, row));

                //Get item on tile.
                Item item = this.game.getBoard().getTile(new Coordinate(col, row)).getItem();

                //Calculate the X and Y position of the tile.
                int xPos = (tile.getLocation().getX() - topLeft.getX()) * TILE_SIZE ;
                int yPos = (tile.getLocation().getY() - topLeft.getY()) * TILE_SIZE ;

                //Render tile graphic.
                g.drawImage(tiles.get(tile).getImage(), xPos, yPos, null);

                //If the tile has an item on it, render it.
                if(item != null){

                    //Render image graphic
                    g.drawImage(items.get(item).getImage(), xPos, yPos, null);

                }

            }

        }

        //For each actor...
        for(Actor a: actors.keySet()){

            //Calculate its X and Y positions
            int xPos = (a.getPosition().getX() - topLeft.getX()) * TILE_SIZE;
            int yPos = (a.getPosition().getY() - topLeft.getY()) * TILE_SIZE;

            //Render actor graphic
           g.drawImage(actors.get(a).getImage(), xPos, yPos, null);

        }

    }

    /**
     * Transforms necessary graphics data and draws to panel.
     */
    private void update(){ repaint(); }

    /**
     * Gets the appropriate viewport dimensions
     */
    private Coordinate getViewportDimensions(){

        //Get the player's position and the current viewport
        Coordinate playerPosition = game.getPlayer().getPosition();
        Coordinate viewportTopLeft = new Coordinate(-1, -1);

        //If the player's X position is within the size of the board - 4 on both edges, center the
        //viewport on the player.
        if(playerPosition.getX() >= 4
                && playerPosition.getX() < game.getBoard().getDimension().width - 4){

            viewportTopLeft.setX(playerPosition.getX()-4);

        } else {

            //If the player is on the left side of the board, set the viewport position to the left edge
            if(playerPosition.getX() < 4){ viewportTopLeft.setX(0); }

            //Otherwise, the player is on the right edge: set the viewport position to the right edge
            else{ viewportTopLeft.setX(game.getBoard().getDimension().width - 9); }

        }

        //If the player's Y position is within the size of the board - 4 on both edges, center the
        //viewport on the player.
        if(playerPosition.getY() >= 4
                && playerPosition.getY() < game.getBoard().getDimension().height - 4){

            viewportTopLeft.setY(playerPosition.getY()-4);

        }else {

            //If the player is on the top side of the board, set the viewport position to the top edge
            if(playerPosition.getY() < 4){ viewportTopLeft.setY(0); }

            //Otherwise, the player is on the bottom edge: set the viewport position to the bottom edge
            else{viewportTopLeft.setY(game.getBoard().getDimension().height - 9);}

        }

        return viewportTopLeft;

    }

    /**
     * Starts the renderer.
     */
    public void startRender(){ timer.start(); }

    /**
     * Stops the renderer.
     */
    public void stopRender(){ timer.stop(); }

    /**
     * Loads an image from a specified directory
     *
     * @param dir Location of image on disk.
     * @return a BufferedImage loaded with image from specified location.
     */
    private BufferedImage loadImage(String dir){

        try{

            return ImageIO.read(new File(dir));

        }catch(IOException e){

            System.err.println("Image at: \"" + dir + "\" was not found!\n"+e.getMessage());

        }

        return null;

    }

}