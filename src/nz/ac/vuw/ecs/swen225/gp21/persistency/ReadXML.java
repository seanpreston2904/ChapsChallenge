package nz.ac.vuw.ecs.swen225.gp21.persistency;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item_Door;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item_Exit;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item_Info;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item_Key;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item_Treasure;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * reading map files and saved game files from XML.
 * representing the current game state in order for the player to resume games.
 *
 * @author Rae 300535154
 */
public class ReadXML {
    private final int HEIGHT = 9;
    private final int WIDTH = 11;
    private Board board;
    private Coordinate playerStartPos;
    private boolean isMap = true;

    private final String[] nodes = {"tile","repeatTile","movingTile","treasureTile", "wallTile", "doorTile", "keyTile"};
    private List<Coordinate> bugStartPos = new ArrayList<>();

    public static void main(String[] args) {
        ReadXML p = new ReadXML();
        //p.readGameLevel("src/nz/ac/vuw/ecs/swen225/gp21/persistency/levels/level2.xml");
        p.printTheBoard();
    }

    public Board printTheBoard(){
        Board newBoard = new Board(new Tile[WIDTH][HEIGHT]);
        loadGame("src/nz/ac/vuw/ecs/swen225/gp21/persistency/levels/level2.xml");

//        isMap = false;
//        loadGame("src/nz/ac/vuw/ecs/swen225/gp21/persistency/game.XML");

        for (int x = 0; x < WIDTH; x++){
            for (int y = 0; y < HEIGHT; y++){
                newBoard.setTile(new Coordinate(x,y),this.board.getTile(new Coordinate(x,y)));
                Tile tile = board.getTile(new Coordinate(x,y));
                System.out.println(tile.getType() +","+tile.getLocation()+", "+tile.getItem());
            }
        }

        if(!this.bugStartPos.isEmpty()) {
            System.out.println("\n--------------------\nBug starts pos: " + this.bugStartPos);
        }

        return newBoard;
    }

    /**
     * loads game from XML files.
     *
     * The original game level file & saved game state file
     *
     * @param fileName the input file
     */
    public void loadGame(String fileName) {
        try {
            File input = new File(fileName);
            //InputStream inputStream = this.getClass().getResourceAsStream("/level"+level+".xml");
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(input);

            // initialize the board with free tiles
            initializeBoard();

            //parse the each node in the file
            if (isMap) {
                parseMapFile(document);
            }else {
                readGameFromXML(document);
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * parse the saved game state file.
     *
     * @param document the doc from reader
     */
    public void readGameFromXML(Document document){
        Element root = document.getRootElement();
        for (String node:this.nodes) {
            if (root.elements(node) != null) {
                this.parseSingleNodes(document.selectNodes("/game/tile"));
            }
        }
    }

    /**
     * parse the game level file.
     *
     * @param document the doc from reader
     */
    public void parseMapFile(Document document){
        Element root = document.getRootElement();
        for (String node:this.nodes){
            if(root.elements(node) != null) {
                switch (node) {
                    case "repeatTile":
                        this.parseWalls(document.selectNodes("/level/repeatTile"));
                        break;
                    case "tile":
                        this.parseSingleNodes(document.selectNodes("/level/tile"));
                        break;
                    case "movingTile":
                        this.parseRandomNodes(document.selectNodes("/level/movingTile"));
                        break;
                    case "treasureTile":
                        this.parseRandomNodes(document.selectNodes("/level/treasureTile"));
                        break;
                    case "wallTile":
                        this.parseRandomNodes(document.selectNodes("/level/wallTile"));
                        break;
                    case "doorTile":
                        this.parseRandomNodes(document.selectNodes("/level/doorTile"));
                        break;
                    case "keyTile":
                        this.parseRandomNodes(document.selectNodes("/level/keyTile"));
                        break;
                    default:
                        System.out.println("No match node found.");
                }
            }
        }
    }

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
            if(type.equals("info")){
                message = node.selectSingleNode("message").getText();
            }

            // get the location of the tile
            String x = node.selectSingleNode("x").getText();
            String y = node.selectSingleNode("y").getText();
            Coordinate pos = new Coordinate(Integer.parseInt(x), Integer.parseInt(y));

            // set each tile at corresponding position
            setSingleTileOnBoard(type, pos, message,null);
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
    private void parseRandomNodes(List<Node> nodes) {
        for (Node node : nodes) {
            //System.out.println("\nCurrent Element :" + node.getName());
            Element element = (Element) node;
            String type = element.attributeValue("type");
            String image = element.attributeValue("image");
            String col = null;
            if(type.equals("KEY") || type.equals("LOCK_DOOR")) {
                col = element.attributeValue("col");
            }
            //System.out.println("Type : " + type + ", image: " + image + ", "+ col);

            // get the list of locations
            List<Node> list = node.selectSingleNode("pos").selectNodes("position");
            for (Node n:list){
                Element ele = (Element) n;
                String x = ele.attributeValue("x");
                String y = ele.attributeValue("y");
                //System.out.println("x : " + x + ", y : " + y);
                Coordinate coordinate = new Coordinate(Integer.parseInt(x), Integer.parseInt(y));

                //set list of tiles at corresponding position
                setSingleTileOnBoard(type, coordinate, null, col);
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
                this.board.setTile(new Coordinate(x, y), new Tile(new Coordinate(x, y), Tile.TileType.FREE,null));
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
                this.board.setTile(new Coordinate(x, y), new Tile(new Coordinate(x, y), Tile.TileType.WALL,null));
            }
        }
    }

    /**
     * set the reset of tiles on the board.
     *
     * @param type tile type
     * @param pos coordinate
     * @param message info msg
     * @param col key/door color
     */
    public void setSingleTileOnBoard(String type, Coordinate pos, String message, String col){
        // set each tile at corresponding position
        switch (type) {
            case "chap":
                board.setPlayerStartPosition(pos);
                break;
            case "EXIT":
                board.setTile(pos, new Tile(pos, Tile.TileType.EXIT,null));
                break;
            case "INFO":
                board.setTile(pos, new Tile(pos, Tile.TileType.FREE, new Item_Info(message)));
                break;
            case "LOCK_EXIT":
                board.setTile(pos, new Tile(pos, Tile.TileType.FREE, new Item_Exit()));
                break;
            case "WALL":
                board.setTile(pos, new Tile(pos, Tile.TileType.WALL, null));
                break;
            case "TREASURE":
                board.setTile(pos, new Tile(pos, Tile.TileType.FREE, new Item_Treasure()));
                break;
            case "KEY":
                board.setTile(pos, new Tile(pos, Tile.TileType.FREE, new Item_Key(col)));
                break;
            case "LOCK_DOOR":
                board.setTile(pos, new Tile(pos, Tile.TileType.FREE, new Item_Door(col)));
                break;
            case "bug":
                //TODO set BUG tile at corresponding position
                this.bugStartPos.add(pos);
                break;
            case "FREE":
                board.setTile(pos, new Tile(pos, Tile.TileType.FREE, null));
                break;
            default:
                System.out.println("No match tile found.");
        }

    }
}
