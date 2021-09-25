package nz.ac.vuw.ecs.swen225.gp21.domain;

import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Enemy;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.TileType;
import nz.ac.vuw.ecs.swen225.gp21.persistency.plugin.LoadEnemyFile;
import nz.ac.vuw.ecs.swen225.gp21.persistency.reader.XMLFileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The effective game class.
 */
public class Domain {


    //   BOARD   //
    // Board object
    Board board;
    // Player object
    Player hero;
    // Other actors
    ArrayList<Actor> actors;
    // Treasure count
    int treasure;

    // game state
    boolean running;

    /**
     * The Domain initializes with a board from Persistence, this is the only req. for the Domain to launch.
     */
    public Domain(String levelName) {
        // load the current level's board from XML via Persistency
        String fname = "src/nz/ac/vuw/ecs/swen225/gp21/persistency/levels/" + levelName + ".xml";

        XMLFileReader fileReader = new XMLFileReader();
        LoadEnemyFile loadEnemyFile = new LoadEnemyFile();

        if (levelName.contains("xmlsave")) {
            this.board = fileReader.loadSavedMap(levelName);
        } else {
            this.board = fileReader.loadOriginMap(fname);
        }

        // Initialize the board and starting game features.
        this.treasure = this.board.getTotalTreasures();
        this.hero = new Player(board.getPlayerStartPosition());
        this.actors = new ArrayList<>();

        // load any and all enemies
        for (Enemy e : loadEnemyFile.loadEnemyClasses(fileReader, fileReader.getEnemyName())) {
            actors.add(e);
        }

        // start off not running
        this.running = false;

        // TODO remove debugging print board
        printCurrentBoard();
    }

    /**
     * Getter for the Board.
     */
    public Board getBoard() {
        return this.board;
    }
    
    /**
     * Getter for the Hero character.
     */
    public Player getPlayer() {
        return this.hero;
    }

    /**
     * Getter for the remaining treasures on the board.
     */
    public int getRemainingChips() {
        return treasure;
    }

    /**
     * Getter for the running state of the game.
     *
     * @return
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Setter for the running state of the game.
     *
     * @param running
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    //  GAME LOGIC //
    // what events can and cannot happen is controlled by the following methods

    /**
     * Core method that handles the actor movement and checks for exceptions.
     *
     * @param actor
     * @param direction
     */
    public void moveActor(Actor actor, Direction direction) {

        if (running == false) {
            throw new IllegalStateException("Illegal State Exception. Not running but attempting moveActor().");
        }

        // precondition checks
        if (direction == null) {
            throw new IllegalArgumentException("Move Actor Direction is Null");
        }

        Coordinate moveToCoordinate = actor.getResultingLocation(direction);

        // checks on the move to location
        if (!board.validMove(moveToCoordinate, actor)) {
            //throw new IllegalArgumentException("chap cannot be moved into this position");
        } else {

            // if we get here then we move the actor
            actor.setPosition(moveToCoordinate);
            // clear previous info states
            actor.setInfoMessage(null);

            // check for exit
            if (board.getTile(moveToCoordinate).getType() == TileType.EXIT) {

                // end the game
                running = false;

                return;
            }

            // call the item's interact event
            try {
                interactWithItem(actor);
            } catch (IllegalStateException e) {
                // This is okay, it just means that no item was in this tile. Any other exception is an issue though.
            }

            // TODO remove debugging console version of board
            printCurrentBoard();
        }
    }

    /**
     * Side method that handles random movement.
     *
     * This is called by a tick event in app for other actors/enemies.
     *
     */
    public static Direction randomlyMoveActor() {

        // generate a direction
        Direction dir = Direction.values()[
                new Random().nextInt(Direction.values().length) // randomly choose a direction
                ];

        return dir;
    }



    // INVENTORY //
    // - treasure
    // - keys
    // - other ... ?

    /**
     * When you step on an item you interact with the item.
     */
    public void interactWithItem(Actor actor) {
        Coordinate current = actor.getPosition();

        // boundary checks on Coordinate
        if (current == null) {
            throw new IllegalArgumentException("Illegal Hero State");
        }

        Tile currentTile = board.getTile(current);

        // boundary checks on Tile
        if (currentTile == null) {
            throw new IllegalArgumentException("Illegal Tile");
        }
        // boundary checks on Item
        if (currentTile.getItem() == null) {
            throw new IllegalStateException("Invalid Retrieve Item State");
        }

        // the hero uses the item
        currentTile.getItem().interact(actor);

        // if it has no repeat uses, remove it
        if (currentTile.getItem().isOneTimeUse()) {
            currentTile.setItem(null);
        }

        this.treasure = board.getTotalTreasures();
    }


    // --------- EXTRA METHODS ------------

    /**
     * TODO: Debugging method to print the board to the console.
     */
    public void printCurrentBoard() {

        StringBuilder sb = new StringBuilder();

        // loop through each tile to construct the board
        for (int y = 0; y < board.getDimension().height; y++) {
            for (int x = 0; x < board.getDimension().width; x++) {

                // when we get to the hero we draw it instead of a tile
                if (hero.getPosition().getX() == x && hero.getPosition().getY() == y) {
                    sb.append(hero.consoleString());
                }
                // otherwise draw a tile
                else {
                    Tile t = board.getTile(new Coordinate(x, y));
                    sb.append(t.consoleString());
                }

            }
            sb.append("\n");
        }

        System.out.println("Board::: \n\n" + sb.toString());

        if (hero.listenForMessage() != null) {
            System.out.println("INFO: " + hero.listenForMessage());
        }
    }

}
