package nz.ac.vuw.ecs.swen225.gp21.domain.board;


import java.awt.*;
import java.util.Arrays;

public class Board {

    Tile[][] board;

    /**
     * Constructor
     */
    public Board(Tile[][] board) {
        this.board = board;
    }

    /**
     * Get a tile from the board
     *
     * @param p
     * @return
     */
    public Tile getTile(Dimension p) {
        return board[p.width][p.height];
    }

    /**
     * Set a tile on the board
     *
     * @param p
     * @param tile
     */
    public void setTile(Dimension p, Tile tile) {
        board[p.width][p.height] = tile;
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
