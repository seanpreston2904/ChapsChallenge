package nz.ac.vuw.ecs.swen225.gp21.domain.board;


import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;

import java.awt.*;
import java.util.Arrays;

public class Board {

    Tile[][] board;

    Coordinate playerStartPosition;

    Player player;

    /**
     * Constructor
     */
    public Board(Tile[][] board) {
        this.board = board;
    }

    /**
     * get where the player begins the game
     */
    public Coordinate getPlayerStartPosition() {
        return playerStartPosition;
    }

    /**
     * set where the player begins the game
     */
    public void setPlayerStartPosition(Coordinate playerStartPosition) {
        this.playerStartPosition = playerStartPosition;
    }

    /**
     * Get a tile from the board
     *
     * @param p
     * @return
     */
    public Tile getTile(Coordinate p) {
        return board[p.getX()][p.getY()];
    }

    /**
     * Set a tile on the board
     *
     * @param p
     * @param tile
     */
    public void setTile(Coordinate p, Tile tile) {
        board[p.getX()][p.getY()] = tile;
    }


    /**
     * Utility method to check for walls, doors and other impassable
     *
     * @param moveTo - the place to check
     * @return
     */
    public boolean validMove(Coordinate moveTo) {
        // is it null
        if (moveTo == null) {
            throw new NullPointerException("Illegal moveTo location");
        }

        // is it a wall
        if (board[moveTo.getX()][moveTo.getY()].getType().equals(Tile.TileType.WALL)) {
            return false;
        }

        // check for impassible items
        // check for items that interaction will allow us to pass through
        Item item = board[moveTo.getX()][moveTo.getY()].getItem();
        if (item.isImpassable()) {

            // attempt to interact with
            if (item instanceof PreMove) {
                return ((PreMove)item).preInteract(player);
            }

            return false;
        }

        // otherwise return true
        return true;
    }


    /**
     * @return the board
     */
    @Override
    public String toString() {
        return "Board{" +
                "board=" + Arrays.toString(board) +
                '}';
    }
}
