package nz.ac.vuw.ecs.swen225.gp21.persistency.reader;

import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;

import java.util.List;
import java.util.Map;

/**
 *  "Component" .
 *
 * An FileReader interface to achieve the following abstraction.
 * 1. load the board from the original game levels.
 * 2. load the board from saved game map.
 * 3. load the saved action records file.
 * 4. get the starting coordinates of all bugs.
 *
 * @author Rae 300535154
 */

public interface FileReader {

    /**
     * load the board from the original game levels,
     * which contains all essential information of each tile.
     * Tile type & Tile coordinate & (info message | key/door color)
     *
     * @param fileName game levels
     * @return the completed board
     */
     Board loadOriginMap(String fileName);


    /**
     * load the board from saved game state, with updated tile status.
     *
     * @param fName saved game files
     * @return the saved board
     */
    Board loadSavedMap(String fName);

    /**
     * load the saved action records file.
     * With timer, actor, and direction.
     *
     * @param fName saved file name
     * @return a list of actions and records map
     */
    List<Map<String, String>> loadSavedActions(String fName);

    /**
     * get the starting coordinates of all bugs.
     *
     * @return a list of coordinates
     */
    List<Coordinate> getBugStartPos();
}
