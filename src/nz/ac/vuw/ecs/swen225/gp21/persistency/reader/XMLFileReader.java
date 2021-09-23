package nz.ac.vuw.ecs.swen225.gp21.persistency.reader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item_Door;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item_Exit;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item_Info;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item_Key;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item_Treasure;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.TileType;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;


/**
 * "Composite".
 *
 * reading map files and saved game files from XML.
 * representing the current game state in order for the player to resume games.
 *
 * @author Rae 300535154
 */
public class XMLFileReader implements FileReader {
    private static final int HEIGHT = 9;  // the height of the board
    private static final int WIDTH = 11;  // the width of the board
    private final String[] nodes =
            {"tile", "repeatTile", "movingTile", "treasureTile",
                    "wallTile", "doorTile", "keyTile"}; // all XML tilesNodes
    private LeafReader leafReader = new LeafReader();
    private boolean isAction;            // indicates the saved file is a action records or not
    private List<Map<String, String>> actionRecords = new ArrayList<>();// a map of actions with its records
    private Board board;



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
        this.board = leafReader.loadSavedMap(fileName);
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
     * get the starting coordinates of all bugs.
     *
     * @return a list of coordinates
     */
    @Override
    public List<Coordinate> getBugStartPos() {
        assert this.board != null;
        return leafReader.getBugStartPos();
    }

    /*----------------The debug function--------------------------------------------------------*/

    /**
     * print the board with all tiles.
     */
    public void printBoard() {
        String file1 = "src/nz/ac/vuw/ecs/swen225/gp21/persistency/levels/level1.xml";
        String file2 = "src/nz/ac/vuw/ecs/swen225/gp21/persistency/levels/level2.xml";
        Board board = loadOriginMap(file1);
        //Board board =loadSavedMap("src/nz/ac/vuw/ecs/swen225/gp21/persistency/tests/savedMap.xml");

        for (int x = 0; x < WIDTH; x++){
            for (int y = 0; y < HEIGHT; y++){
                Tile tile = board.getTile(new Coordinate(x,y));
                System.out.println(tile.getType() +", "+tile.getLocation()+", "+tile.getItem());
            }
        }
        System.out.println("\n--------------------\nBug starts pos: "
                + getBugStartPos()
                +"\n--------------------\n");

    }

    public static void main(String[] args) {
        XMLFileReader p = new XMLFileReader();
        p.printBoard();


        System.out.println("Records: " +
                p.loadSavedActions("src/nz/ac/vuw/ecs/swen225/gp21/persistency/tests/testAction.xml"));

    }
    /* ------------------------------------------------------------------------------------------ */

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

            // initialize the board with free tiles
            leafReader.initializeBoard();

            //parse the each node in the file
            if (isMap) {
                parseOriginalMap(document);
                System.out.println("Game level loaded.");
            }else {
                if(this.isAction) {
                    System.out.println("NB: This is a copy of the saved actions.");
                    parseSavedActions(document);
                }else {
                    System.out.println("NB: This is a copy of the saved game.");
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
