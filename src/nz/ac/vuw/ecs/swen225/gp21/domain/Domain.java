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
 * The game class that handles the backend.
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
     *
     * @param - the name of the pathway to a level or save file to load.
     *
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
        try {
            for (Enemy e : loadEnemyFile.loadEnemyClasses(fileReader, fileReader.getEnemyName())) {
                actors.add(e);
                // initialize ID
                e.setID("" + e.getPosition().getX() + e.getPosition().getY());

            }
        } catch (Exception e) {
            // ... no plugins to load, that's fine
        }

        // start off not running
        this.running = false;

        // TODO remove debugging print board
        printCurrentBoard();
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
        if (!board.validMove(actors, moveToCoordinate, actor)) {
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

            // POST MOVE CHECKS for the player stepping on an enemy of the enemy stepping on a player
            if (actor instanceof Player) {
                if (anotherEnemyInThisSpace(actors, moveToCoordinate) != null) {

                    // end the game
                    running = false;

                    return;
                }
            }
            // B) if an enemy attempts to move
            else {
                // check if its a player it just stepped on
                ArrayList<Actor> heroList = new ArrayList<>(); heroList.add(hero);

                if (anotherEnemyInThisSpace(heroList, moveToCoordinate) != null) {

                    // end the game
                    running = false;

                    return;
                }
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
     * Simple utility method to check a location for enemy actors.
     *
     * @param moveToCoordinate
     *
     * @return - returns null for no enemies, otherwise returns the enemy.
     *
     */
    public static Actor anotherEnemyInThisSpace(List<Actor> actors, Coordinate moveToCoordinate) {

        if (moveToCoordinate == null) {
            throw new IllegalArgumentException("Move to location is Null");
        }

        // loop through all the enemies
        for (Actor enemy : actors) {
            if (enemy.getPosition().getX() == moveToCoordinate.getX() && enemy.getPosition().getY() == moveToCoordinate.getY()) {
                // note that this dude is standing here:
                return enemy;
            }
        }
        return null;
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


    /**
     * Get an actor from the board (hero or enemy) by ID.
     *
     * @param ID - string ID.
     *
     * @return
     *
     */
    public Actor getActorByID(String ID) {

        // first check player ID
        if (ID.equals(getPlayer().getID())) {
            return getPlayer();
        }
        // then check enemy ID's
        else {

            for (Actor enemy : getActors()) {
                System.out.print("ENEMY: " + enemy.getID());
                if (enemy.getID().equals(ID)) {
                    return enemy;
                }
            }
        }
        return null;
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
        if (currentTile.getItem().isOneTimeUse() && actor instanceof Player) {
            currentTile.setItem(null);
        }

        this.treasure = board.getTotalTreasures();
    }

    /**
     * Getter for the enemy actor list.
     *
     * @return
     *
     */
    public ArrayList<Actor> getActors() {
        return actors;
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
                List<Actor> tempActors = (List<Actor>)actors.clone();
                tempActors.add(hero);

                boolean actorInThisSpace = false;

                // check for actors to draw instead
                for (Actor a : tempActors) {
                    if (a.getPosition().getX() == x && a.getPosition().getY() == y) {
                        sb.append(a.consoleString());
                        actorInThisSpace = true;
                    }
                }
                // otherwise draw a tile
                if (!actorInThisSpace) {
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
