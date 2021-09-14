package nz.ac.vuw.ecs.swen225.gp21.domain.board;


import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Actor;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.TileType;

import java.awt.*;
import java.util.Arrays;

/**
 * Board class represents the map of tiles and items that the player navigates.
 */
public class Board {

    Tile[][] board;

    Coordinate playerStartPosition;

    /**
     * Constructor.
     */
    public Board(Tile[][] board) {
        this.board = board;
    }

    /**
     * Get where the player begins the game.
     */
    public Coordinate getPlayerStartPosition() {
        return playerStartPosition;
    }

    /**
     * Set where the player begins the game.
     */
    public void setPlayerStartPosition(Coordinate playerStartPosition) {
        this.playerStartPosition = playerStartPosition;
    }

    /**
     * Get a tile from the board.
     *
     * @param coordinate - input coordinate.
     *
     * @return - output tile.
     *
     */
    public Tile getTile(Coordinate coordinate) {
        return board[coordinate.getX()][coordinate.getY()];
    }

    /**
     * Set a tile on the board.
     *
     * @param coordinate - input coordinate.
     *
     * @param tile - tile to set.
     *
     */
    public void setTile(Coordinate coordinate, Tile tile) {
        board[coordinate.getX()][coordinate.getY()] = tile;
    }


    /**
     * Utility method to check for walls, doors and other impassable.
     *
     * @param moveTo - the place to check.
     *
     * @return - TRUE if it is a valid move.
     *
     */
    public boolean validMove(Coordinate moveTo, Actor actor) {
        // is it null
        if (moveTo == null) {
            throw new NullPointerException("Illegal moveTo location");
        }

        // is it a wall
        if (board[moveTo.getX()][moveTo.getY()].getType().equals(TileType.WALL)) {
            return false;
        }

        // check for impassible items
        // check for items that interaction will allow us to pass through
        Item item = board[moveTo.getX()][moveTo.getY()].getItem();
        if (item.isImpassable()) {

            // attempt to interact with
            if (item instanceof PreMove) {
                return ((PreMove)item).preInteract(actor);
            }

            return false;
        }

        // otherwise return true
        return true;
    }

    @Override
    public String toString() {
        return "Board{" +
                "board=" + Arrays.toString(board) +
                '}';
    }
}
