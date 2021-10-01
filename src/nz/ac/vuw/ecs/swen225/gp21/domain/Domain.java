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

    // Board object
    Board board;
    // Player object
    Player hero;
    // Other actors
    ArrayList<Actor> actors;
    // Treasure count
    int treasure;
    // Game state
    boolean running;

    /**
     * The Domain initializes with a board or save game from Persistence, this is the only req. for the Domain to launch.
     *
     * @param levelName - the name of the pathway to a level or save file to load.
     *
     */
    public Domain(String levelName) {
        // load the current level's board from XML via Persistency
        String fname = "src/nz/ac/vuw/ecs/swen225/gp21/persistency/levels/";

        XMLFileReader fileReader = new XMLFileReader();
        LoadEnemyFile loadEnemyFile = new LoadEnemyFile();

        if (levelName.contains("xmlsave")) {
            this.board = fileReader.loadSavedMap(levelName);
            Board alt = fileReader.loadOriginMap(fname + "level" + fileReader.getLevel(levelName) + ".xml");
            this.hero = new Player(alt.getPlayerStartPosition());
        } else {
            this.board = fileReader.loadOriginMap(fname + levelName + ".xml");
            this.hero = new Player(board.getPlayerStartPosition());
        }

        // Initialize the board and starting game features.
        this.treasure = this.board.getTotalTreasures();
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
    }

    //  GAME LOGIC //
    // what events can and cannot happen is controlled by the following methods

    /**
     * Core method that handles the actor movement and checks for exceptions.
     *
     * More formally this moves the given Actor in the given Direction if and only if:
     * The game is running, the input parameters are not null, and the movement specified meets all criteria of the Board.validMove() contract.
     *
     * If this contract is met:
     * - The actor will be moved.
     * - Endgame state will be checked for.
     * - Item interaction will occur, if possible.
     *
     * @param actor - any type of actor enemy or player.
     *
     * @param direction - the way it moves.
     *
     * @throws IllegalStateException - if the game is not running when this move is attempted.
     *
     * @throws IllegalArgumentException - if the specified actor or direction are null.
     *
     */
    public void moveActor(Actor actor, Direction direction) {

        // precondition checks game state
        if (running == false) {
            throw new IllegalStateException("Illegal State Exception. Not running but attempting moveActor().");
        }
        // precondition checks actor
        if (actor == null) {
            throw new IllegalArgumentException("Move Actor is Null");
        }
        // precondition checks direction
        if (direction == null) {
            throw new IllegalArgumentException("Move Direction is Null");
        }

        Coordinate moveToCoordinate = actor.getResultingLocation(direction);

        // checks on the move to location
        if (board.validMove(actors, moveToCoordinate, actor)) {

            // if we get here then we move the actor
            actor.setPosition(moveToCoordinate);
            // clear previous info states
            actor.setInfoMessage(null);

            // check for end game
            checkForEndGame(actor, moveToCoordinate);

            // call the item's interact event
            try {
                interactWithItem(actor);
            } catch (IllegalStateException e) {
                // This is okay, it just means that no item was in this tile. Any other exception is an issue though.
            }

            // NOTE: debugging console version of board for help with marking/testing
            // printCurrentBoard();
        }
    }

    /**
     * Simple utility method to check a location for enemy actors.
     *
     * More formally this will return the first Actor from the list of actors given in the specified coordinate, or return null if none is found.
     *
     *
     * @param moveToCoordinate - the coordinate to check.
     *
     * @return - returns null for no enemies, otherwise returns the enemy.
     *
     * @throws IllegalArgumentException - if the specified actors or coordinate are null.
     *
     */
    public static Actor anotherEnemyInThisSpace(List<Actor> actors, Coordinate moveToCoordinate) {

        if (moveToCoordinate == null) {
            throw new IllegalArgumentException("Move to location is Null");
        }

        if (actors == null) {
            throw new IllegalArgumentException("List of actors is Null");
        }

        // loop through all the enemies
        for (Actor enemy : actors) {
            if (enemy.getPosition().getX() == moveToCoordinate.getX() && enemy.getPosition().getY() == moveToCoordinate.getY()) {
                // note that this enemy is standing here:
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
     * @return - a random Direction coordinate.
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
     * More formally this method will return an Actor from the Domain where the ID parameter equals the ID of the actor. Or return null if none is found.
     *
     * @param ID - string ID.
     *
     * @return - an Actor found or null.
     *
     * @throws IllegalArgumentException - if the specified ID is null.
     *
     */
    public Actor getActorByID(String ID) {

        if (ID == null) {
            throw new IllegalArgumentException("ID given is Null");
        }

        // first check player ID
        if (ID.equals(getPlayer().getID())) {
            return getPlayer();
        }
        // then check enemy ID's
        else {

            for (Actor enemy : getActors()) {
                // check if the ID matches
                if (enemy.getID().equals(ID)) {
                    return enemy;
                }
            }
        }
        return null;
    }


    /**
     * When you step on an item you interact with the item.
     *
     * @param actor - the actor moved.
     *
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
     * When you make a move there is the potential for the game to be over.
     *
     * @param actor - the actor moved.
     *
     * @param moveToCoordinate - the coordinate moved into.
     *
     */
    public void checkForEndGame(Actor actor, Coordinate moveToCoordinate) {
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
     * NOTE: Debugging method to print the board to the console.
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

        // output the board
        System.out.println("Board::: \n\n" + sb.toString());

        // output an info message if found
        if (hero.listenForMessage() != null) {
            System.out.println("INFO: " + hero.listenForMessage());
        }
    }
}
