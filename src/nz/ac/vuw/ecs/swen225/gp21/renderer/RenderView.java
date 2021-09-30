package nz.ac.vuw.ecs.swen225.gp21.renderer;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item_Door;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item_Key;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.ItemType;
import nz.ac.vuw.ecs.swen225.gp21.renderer.audio.AudioEngine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RenderView extends JPanel {

    //Frame interval in milliseconds at 60 FPS
    private static final float FPS_60 = 1000.0f / 60.0f;

    //Size of a tile in pixels
    private static final int TILE_SIZE = 64;

    //The size of the viewport
    private static final Dimension VIEWPORT_SIZE =
            new Dimension(TILE_SIZE * 9, TILE_SIZE * 9);

    //Audio engine
    AudioEngine audioEngine;
    HashMap<Coordinate, Item_Door> doorObserver;

    //Event timer (used to render board)
    private final Timer timer;

    //Reference to game domain
    private final Domain game;

    //Element -> Animator maps
    private final HashMap<Actor, ActorAnimator> actors;
    private final HashMap<Item, ItemAnimator> items;
    private final HashMap<Tile, TileAnimator> tiles;

    //"Out of Time" observers for playing sounds
    private ArrayList<Item> inventoryObserver;
    private final Coordinate playerPosObserver;
    private int treasureObserver;

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
    public RenderView(Domain game) {

        //Forces a fixed size for the panel
        this.setSize(VIEWPORT_SIZE);
        this.setPreferredSize(VIEWPORT_SIZE);
        this.setMaximumSize(VIEWPORT_SIZE);
        this.setMinimumSize(VIEWPORT_SIZE);

        //Construct timer
        timer = new Timer((int) FPS_60, action -> update());

        //Construct audio engine
        this.audioEngine = new AudioEngine();

        //Set board reference
        this.game = game;

        //Calculate initial viewport dimensions
        this.topLeft = getViewportDimensions();

        //Construct animator maps
        this.actors = new HashMap<>();
        this.items = new HashMap<>();
        this.tiles = new HashMap<>();

        //Add actors to actor map
        for (Actor a : this.game.getActors()) {
            actors.put(a, new ActorAnimator(loadImage("./res/graphics/" + a.getName().toUpperCase() + ".png")));
        }

        actors.put(this.game.getPlayer(), new ActorAnimator(loadImage("./res/graphics/player.png")));

        //Set animator maps
        for (int row = 0; row < this.game.getBoard().getDimension().height; row++) {
            for (int col = 0; col < this.game.getBoard().getDimension().width; col++) {

                //Get the current tile on the board and construct an animator for it
                Tile curr = this.game.getBoard().getTile(new Coordinate(col, row));
                tiles.put(curr, new TileAnimator(loadImage("./res/graphics/" + curr.getType().toString() + ".png")));

                //If the tile has an item, construct an animator for it
                if (curr.getItem() != null) {

                    Item currItem = curr.getItem();

                    if (currItem.getType() == ItemType.LOCK_DOOR) {
                        items.put(currItem, new ItemAnimator(
                                loadImage("./res/graphics/"
                                        + currItem.getType() + "_" + ((Item_Door) currItem).getColor().toUpperCase()
                                        + ".png")));
                    } else if (currItem.getType() == ItemType.KEY) {
                        items.put(currItem, new ItemAnimator(
                                loadImage("./res/graphics/"
                                        + currItem.getType() + "_" + ((Item_Key) currItem).getColor().toUpperCase()
                                        + ".png")));
                    } else {
                        items.put(currItem, new ItemAnimator(loadImage(
                                "./res/graphics/"
                                        + currItem.getType().toString() +
                                        ".png")));
                    }

                }

            }

        }

        //Construct observer variables
        this.playerPosObserver = game.getPlayer().getPosition();
        this.treasureObserver = game.getPlayer().getTreasure();
        this.doorObserver = new HashMap<>();

        //Get all doors on the board and add them to the doorObserver
        for (int x = 0; x < game.getBoard().getDimension().getWidth(); x++) {
            for (int y = 0; y < game.getBoard().getDimension().getHeight(); y++) {

                Tile t = game.getBoard().getTile(new Coordinate(x, y));

                if (t.getItem() != null && t.getItem().getType() == ItemType.LOCK_DOOR)
                    doorObserver.put(new Coordinate(x, y), (Item_Door) t.getItem());

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

        for (int row = 0; row < boardHeight; row++) {
            for (int col = 0; col < boardWidth; col++) {

                //Get tile at row, col on board.
                Tile tile = this.game.getBoard().getTile(new Coordinate(col, row));

                //Get item on tile.
                Item item = this.game.getBoard().getTile(new Coordinate(col, row)).getItem();

                //Calculate the X and Y position of the tile.
                int xPos = (tile.getLocation().getX() - topLeft.getX()) * TILE_SIZE + viewOffsetX;
                int yPos = (tile.getLocation().getY() - topLeft.getY()) * TILE_SIZE + viewOffsetY;

                //Render tile graphic.
                g.drawImage(tiles.get(tile).getImage(), xPos, yPos, null);

                //If the tile has an item on it, render it.
                if (item != null) {

                    //Render image graphic
                    g.drawImage(items.get(item).getImage(), xPos, yPos, null);

                }

            }

        }

        //For each actor...
        for (Map.Entry<Actor, ActorAnimator> entry : actors.entrySet()) {

            //Calculate its X and Y positions
            int xPos = (entry.getKey().getPosition().getX() - topLeft.getX()) * TILE_SIZE + viewOffsetX + entry.getValue().animationOffset.getX();
            int yPos = (entry.getKey().getPosition().getY() - topLeft.getY()) * TILE_SIZE + viewOffsetY  + entry.getValue().animationOffset.getY();

            //Render actor graphic
            g.drawImage(entry.getValue().getImage(), xPos, yPos, null);

        }

    }

    /**
     * Transforms necessary graphics data, updates observers and draws to panel.
     */
    private void update() {

        //Repaint the screen
        repaint();

        if(viewOffsetX < 0){ viewOffsetX+=16; }
        if(viewOffsetX > 0){ viewOffsetX-=16; }
        if(viewOffsetX < 0){ viewOffsetX+=16; }
        if(viewOffsetX > 0){ viewOffsetX-=16; }

        //Process actor animations
        for(Map.Entry<Actor, ActorAnimator> entry : actors.entrySet()){ entry.getValue().tick(); }

        //Get player position (for comparison with observer)
        Coordinate playerPos = game.getPlayer().getPosition();

        //Get the dimensions of the board
        Dimension boardSize = this.game.getBoard().getDimension();

        //Get all items the player may have picked up
        ArrayList<Item> newItems = game.getPlayer().getInventory().stream()
                .filter(i -> !inventoryObserver.contains(i))
                .collect(Collectors.toCollection(ArrayList::new));

        //If the player has picked up new items, play the new item sound
        if (!newItems.isEmpty() || treasureObserver != game.getPlayer().getTreasure()) {
            audioEngine.playItemSound();
        }

        //If the player has moved, play movement sound
        if (playerPosObserver.getX() != playerPos.getX() || playerPosObserver.getY() != playerPos.getY()) {

            audioEngine.playMoveSound();

            //If the player moved to the left, shift the view offset back to the right
            if(playerPosObserver.getX() > playerPos.getX()){
                if(playerPos.getX() > 3 && playerPos.getX() < boardSize.getWidth() - 5) this.viewOffsetX = -64;
                actors.get(game.getPlayer()).setAnimationOffset(Direction.WEST);
            }

            //If the player moved to the right, shift the view offset back to the left
            else if(playerPosObserver.getX() < playerPos.getX()){
                if(playerPos.getX() > 4 && playerPos.getX() < boardSize.getWidth() - 4) this.viewOffsetX = 64;
                actors.get(game.getPlayer()).setAnimationOffset(Direction.EAST);
            }

            //If the player moved down, shift the view offset back upwards
            else if(playerPosObserver.getY() > playerPos.getY()){
                if(playerPos.getY() > 3 && playerPos.getY() < boardSize.getHeight() - 5) this.viewOffsetY = -64;
                actors.get(game.getPlayer()).setAnimationOffset(Direction.SOUTH);
            }

            //If the player moved down, shift the view offset back upwards
            else if(playerPosObserver.getY() < playerPos.getY()){
                if(playerPos.getY() > 4 && playerPos.getY() < boardSize.getHeight() - 4) this.viewOffsetY = 64;
                actors.get(game.getPlayer()).setAnimationOffset(Direction.NORTH);
            }

        }

        //If a door has been opened, play door open sound and flag it for removal from the door observer.
        Coordinate toRemove = null;

        for (Coordinate c : doorObserver.keySet()) {
            if (game.getBoard().getTile(c).getItem() == null) {
                audioEngine.playDoorSound();
                toRemove = c;
            }
        }

        //Update observers.
        inventoryObserver = new ArrayList<>(game.getPlayer().getInventory());

        playerPosObserver.setX(playerPos.getX());
        playerPosObserver.setY(playerPos.getY());

        treasureObserver = game.getPlayer().getTreasure();

        //If a door needs to be removed from the observer list, remove it.
        if (toRemove != null) {
            doorObserver.remove(toRemove);
        }

    }

    /**
     * Gets the appropriate viewport dimensions
     * @return the top left corner of the viewport
     */
    private Coordinate getViewportDimensions() {

        //Get the player's position and the current viewport
        Coordinate playerPosition = game.getPlayer().getPosition();
        Coordinate viewportTopLeft = new Coordinate(-1, -1);

        //If the player's X position is within the size of the board - 4 on both edges, center the
        //viewport on the player.
        if (playerPosition.getX() >= 4
                && playerPosition.getX() < game.getBoard().getDimension().width - 4) {

            viewportTopLeft.setX(playerPosition.getX() - 4);

        } else {

            //If the player is on the left side of the board, set the viewport position to the left edge
            if (playerPosition.getX() < 4) {
                viewportTopLeft.setX(0);
            }

            //Otherwise, the player is on the right edge: set the viewport position to the right edge
            else {
                viewportTopLeft.setX(game.getBoard().getDimension().width - 9);
            }

        }

        //If the player's Y position is within the size of the board - 4 on both edges, center the
        //viewport on the player.
        if (playerPosition.getY() >= 4
                && playerPosition.getY() < game.getBoard().getDimension().height - 4) {

            viewportTopLeft.setY(playerPosition.getY() - 4);

        } else {

            //If the player is on the top side of the board, set the viewport position to the top edge
            if (playerPosition.getY() < 4) {
                viewportTopLeft.setY(0);
            }

            //Otherwise, the player is on the bottom edge: set the viewport position to the bottom edge
            else {
                viewportTopLeft.setY(game.getBoard().getDimension().height - 9);
            }

        }

        return viewportTopLeft;

    }

    /**
     * Starts the renderer.
     */
    public void startRender() {
        timer.start();
    }

    /**
     * Stops the renderer.
     */
    public void stopRender() {
        timer.stop();
    }

    /**
     * Loads an image from a specified directory
     *
     * @param dir Location of image on disk.
     * @return a BufferedImage loaded with image from specified location.
     */
    private BufferedImage loadImage(String dir) {

        try {

            //Read file from provided directory
            return ImageIO.read(new File(dir));

        } catch (IOException e) {

            //Return error message
            System.err.println("Image at: \"" + dir + "\" was not found!\n" + e.getMessage());

            //Return the missing texture file
            try {
                return ImageIO.read(new File("./res/graphics/MISSING_TEXTURE.png"));
            } catch (IOException f) {
                System.err.println("If you can read this something is horribly wrong");
            }

        }

        return null;

    }

}