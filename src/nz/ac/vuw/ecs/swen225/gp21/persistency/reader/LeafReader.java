package nz.ac.vuw.ecs.swen225.gp21.persistency.reader;

import nz.ac.vuw.ecs.swen225.gp21.domain.board.*;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.TileType;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.*;

/**
 * "Leaf".
 * represents leaf objects in the composition.
 * Parse all nodes and initialize the Board.
 * implements all Component methods.
 *
 * @author Rae 300535154
 */
public class LeafReader implements FileReader{
    private static final int HEIGHT = 9;  // the height of the board
    private static final int WIDTH = 11;  // the width of the board
    private Board board;                 // the current board

    private List<Coordinate> bugStartPos = new ArrayList<>();

    /**
     * parse all single nodes from the node list.
     *
     * @param allNodes the single nodes
     */
    public void parseSingleNodes(List<Node> allNodes)  {
        for (Node node : allNodes) {
            Element element = (Element) node;
            String type = element.attributeValue("type");
            String image = element.attributeValue("image");

            // check for info tile
            String message = null;
            if(type.equals("INFO")){
                message = node.selectSingleNode("message").getText();
            }

            String col = null;
            if(type.equals("KEY") || type.equals("LOCK_DOOR")) {
                col = element.attributeValue("col");
            }

            String total_chips = null;
            if(type.equals("LOCK_EXIT") ) {
                total_chips = element.attributeValue("chips");
            }
            // get the location of the tile
            String x = node.selectSingleNode("x").getText();
            String y = node.selectSingleNode("y").getText();
            Coordinate pos = new Coordinate(Integer.parseInt(x), Integer.parseInt(y));

            // set each tile at corresponding position
            setSingleTileOnBoard(type, pos, message, col, total_chips);

        }
    }

    /**
     * parse all repeatedly-formatted wall nodes.
     *
     * @param allNodes all boundary wall nodes
     */
    public void parseWalls(List<Node> allNodes){
        for (Node node : allNodes) {
            Element element = (Element) node;
            String type = element.attributeValue("type");
            String image = element.attributeValue("image");

            // get the start location
            String x_start = node.selectSingleNode("x_start").getText();
            String y_start = node.selectSingleNode("y_start").getText();
            Coordinate startPos = new Coordinate(Integer.parseInt(x_start), Integer.parseInt(y_start));
            // get the end location
            String x_end = node.selectSingleNode("x_end").getText();
            String y_end = node.selectSingleNode("y_end").getText();
            Coordinate endPos = new Coordinate(Integer.parseInt(x_end), Integer.parseInt(y_end));

            //set each wall tile at corresponding position
            setWallOnBoard(startPos, endPos);
        }
    }

    /**
     * parse all randomly formatted nodes.
     *
     * @param nodes all random nodes.
     */
    public void parseRandomNodes(List<Node> nodes) {
        for (Node node : nodes) {
            //System.out.println("\nCurrent Element :" + node.getName());
            Element element = (Element) node;
            String type = element.attributeValue("type");
            String image = element.attributeValue("image");

            String col = null;
            String total_chips = null;
            if(type.equals("KEY") || type.equals("LOCK_DOOR")) {
                col = element.attributeValue("col");
            }

            // get the list of locations
            List<Node> list = node.selectSingleNode("pos").selectNodes("position");
            for (Node n:list){
                Element ele = (Element) n;
                String x = ele.attributeValue("x");
                String y = ele.attributeValue("y");
                //System.out.println("x : " + x + ", y : " + y);
                Coordinate coordinate = new Coordinate(Integer.parseInt(x), Integer.parseInt(y));

                //set list of tiles at corresponding position
                setSingleTileOnBoard(type, coordinate, null, col, null);

            }
        }
    }

    /**
     * initialize the Board with free tiles.
     */
    public void initializeBoard(){
        this.board = new Board(new Tile[WIDTH][HEIGHT]);
        //fill each tile as FREE type
        for (int x = 0; x < WIDTH; x++){
            for (int y = 0; y < HEIGHT; y++){
                this.board.setTile(new Coordinate(x, y), new Tile(new Coordinate(x, y), TileType.FREE,null));
            }
        }
    }

    /**
     * set all boundary walls on the board.
     *
     * @param startPos start coordinate
     * @param endPos end coordinate
     */
    public void setWallOnBoard(Coordinate startPos, Coordinate endPos){
        for (int x = startPos.getX(); x < endPos.getX()+1; x++){
            for (int y = startPos.getY(); y < endPos.getY()+1; y++){
                this.board.setTile(new Coordinate(x, y), new Tile(new Coordinate(x, y), TileType.WALL,null));
            }
        }
    }

    /**
     * set the rest of tiles on the board.
     *
     * @param type tile type
     * @param pos coordinate
     * @param message info msg
     * @param col key/door color
     */
    public void setSingleTileOnBoard(String type, Coordinate pos, String message, String col, String total_chips){
        // set each tile at corresponding position
        switch (type) {
            case "chap":
                board.setPlayerStartPosition(pos);
                break;
            case "WALL":
                board.setTile(pos, new Tile(pos, TileType.WALL, null));
                break;
            case "EXIT":
                board.setTile(pos, new Tile(pos, TileType.EXIT,null));
                break;
            case "INFO":
                board.setTile(pos, new Tile(pos, TileType.FREE, new Item_Info(message)));
                break;
            case "LOCK_EXIT":
                board.setTile(pos, new Tile(pos, TileType.FREE, new Item_Exit(Integer.parseInt(total_chips))));
                break;
            case "TREASURE":
                board.setTile(pos, new Tile(pos, TileType.FREE, new Item_Treasure()));
                break;
            case "KEY":
                board.setTile(pos, new Tile(pos, TileType.FREE, new Item_Key(col)));
                break;
            case "LOCK_DOOR":
                board.setTile(pos, new Tile(pos, TileType.FREE, new Item_Door(col)));
                break;
            case "FREE":
                board.setTile(pos, new Tile(pos, TileType.FREE, null));
                break;
            case "Enemy":
                this.bugStartPos.add(pos);
                break;
            default:
                System.out.println("No match tile found.");
        }

    }

    /**
     * Override method. get the initializeBoard.
     *
     * @param fileName game levels
     * @return board
     */
    @Override
    public Board loadOriginMap(String fileName) { return this.board; }

    /**
     * Override method. get the initializeBoard.
     *
     * @param fName saved game files
     * @return board
     */
    @Override
    public Board loadSavedMap(String fName) {
        return this.board;
    }

    /**
     * Override method.
     *
     * @param fName saved file name
     * @return list
     */
    @Override
    public List<Map<String, String>> loadSavedActions(String fName) {
        return null;
    }

    /**
     * Override method. get the position of all bugs.
     *
     * @return list of pos
     */
    @Override
    public List<Coordinate> getEnemyStartPos() {
        return this.bugStartPos;
    }
}
