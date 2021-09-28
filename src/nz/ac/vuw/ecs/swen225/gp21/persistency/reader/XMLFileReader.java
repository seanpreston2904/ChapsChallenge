package nz.ac.vuw.ecs.swen225.gp21.persistency.reader;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Enemy;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.persistency.plugin.LoadEnemyFile;
import org.dom4j.*;
import org.dom4j.io.SAXReader;


/**
 * "Composite".
 *
 * reading map files and saved game files from XML.
 * representing the current game state in order for the player to resume games.
 *
 * @author Rae 300535154
 */
public class XMLFileReader implements FileReader {

    private LeafReader leafReader = new LeafReader();
    private boolean isAction;            // indicates the saved file is a action records or not
    private List<Map<String, String>> actionRecords = new ArrayList<>();// a map of actions with its records
    private Board board;
    private int level;

    private final String[] nodes =    // all XML tilesNodes
            {"tile", "repeatTile", "enemyTile", "treasureTile", "wallTile", "doorTile", "keyTile", "boxTile"};


    /**
     * load the board from the original game levels,
     * which contains all essential information of each tile.
     * Tile type & Tile coordinate & (info message | key/door color)
     *
     * @param fileName game levels
     * @return the completed board
     */
    @Override
    public Board loadOriginMap(String fileName) {
        //parse the game level file
        readGameFile(fileName, true);
        this.board = leafReader.loadOriginMap(fileName);
        return this.board;
    }

    /**
     * load the board from saved game state, with updated tile status.
     *
     * @param fName saved game files
     * @return the saved board
     */
    @Override
    public Board loadSavedMap(String fName) {
        //parse the saved map file
        readGameFile(fName, false);
        this.board = leafReader.loadSavedMap(fName);
        return this.board;
    }

    /**
     * load the saved action records file.
     * With timer, actor, and direction.
     *
     * @param fName saved file name
     * @return a list of actions and records map
     */
    @Override
    public List<Map<String, String>> loadSavedActions(String fName) {
        this.isAction = true;
        readGameFile(fName, false);
        return actionRecords;
    }

    /**
     * get the level of the map.
     * @param fileName file
     * @return level
     */
    public int getLevel(String fileName){
        readGameFile(fileName, false);
        return this.level;
    }

    /**
     * get the starting coordinates of all Enemies.
     *
     * @return a list of coordinates
     */
    @Override
    public List<Coordinate> getEnemyStartPos() {
        return leafReader.getEnemyStartPos();
    }

    /**
     * get the EnemyName.
     *
     * @return name
     */
    public List<String> getEnemyName(){
        return leafReader.getEnemyName();
    }

    /**
     * loads game from XML files.The original game level file & saved game state file.
     *
     * @param fileName input.
     * @param isMap check for map file
     */
    private void readGameFile(String fileName, boolean isMap) {
        try {
            File input = new File(fileName);
            //InputStream inputStream = this.getClass().getResourceAsStream("/level"+level+".xml");
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(input);
            String level =  document.getRootElement().attribute("level").getValue();
            this.level = Integer.parseInt(level);
            // initialize the board with free tiles
            leafReader.initializeBoard();

            //parse the each node in the file
            if (isMap) {
                parseOriginalMap(document);
            }else {
                if(this.isAction) {
                    parseSavedActions(document);
                }else {
                    parseSavedMap(document);
                }
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    /**
     * parse the saved game state file and setup these records for APP.
     * This contains:
     * the time left to play,
     * the current level,
     * keys collected,
     * and the number of treasures that still need to be collected.
     *
     * @param document all nodes
     */
    private void parseSavedActions(Document document){
        List<Node> allNodes = document.selectNodes("/"+document.getRootElement().getName()+"/action");
        //String level =  document.getRootElement().attribute("level").getValue();

        for (Node node : allNodes) {
            Element element_root = (Element) node;
            Iterator<Element> iterator = element_root.elementIterator();

            while (iterator.hasNext()) {
                Element element = iterator.next();
                Map<String, String> tempMap = new HashMap<>();
                tempMap.put(element.getName(), element.attributeValue(element.getName()));
                actionRecords.add(tempMap);
            }
        }
    }

    /**
     * parse the saved game state file.
     *
     * @param document the doc from reader
     */
    private void parseSavedMap(Document document){
        Element root = document.getRootElement();
        for (String node:this.nodes) {
            if (root.elements(node) != null) {
                leafReader.parseSingleNodes(document.selectNodes("/"+document.getRootElement().getName()+"/tile"));
            }
        }
    }

    /**
     * parse the game level file.
     *
     * @param document the doc from reader
     */
    private void parseOriginalMap(Document document){
        Element root = document.getRootElement();

        for (String node:this.nodes){
            if(root.elements(node) != null) {
                switch (node) {
                    case "repeatTile":
                        leafReader.parseWalls(document.selectNodes("/level/repeatTile"));
                        break;
                    case "tile":
                        leafReader.parseSingleNodes(document.selectNodes("/level/tile"));
                        break;
                    default:
                        leafReader.parseRandomNodes(document.selectNodes("/level/"+node+""));
                        break;
                }
            }
        }
    }

}
