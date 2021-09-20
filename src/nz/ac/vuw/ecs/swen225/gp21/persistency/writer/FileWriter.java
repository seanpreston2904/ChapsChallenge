package nz.ac.vuw.ecs.swen225.gp21.persistency.writer;

import nz.ac.vuw.ecs.swen225.gp21.app.App;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import org.dom4j.Element;

/**
 * An FileWriter interface to achieve the following abstraction.
 * 1. save the current game map.
 * 2. save the current game records in order for the player to resume games.
 *
 * @author Rae 300535154
 */

public interface FileWriter {

    /**
     * save the board of the current game.
     *  @param fName the output file name
     * @param app the current game
     */
    void saveCurrentMap(String fName, App app);

    /**
     *  add the game actions to XML.
     *  This is for Recorder to write each action records to a XML file.
     *
     * @param root the root ele
     * @param timer current time left
     * @param actor current actor
     * @param dir direction actor moved
     */
    void addActionNode(Element root, int timer, String actor, String dir);
}
