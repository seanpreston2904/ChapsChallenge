package nz.ac.vuw.ecs.swen225.gp21.persistency;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import nz.ac.vuw.ecs.swen225.gp21.app.App;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item_Door;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item_Info;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item_Key;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import org.dom4j.Element;
import org.dom4j.DocumentHelper;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;




/**
 * writing the Java objects to XML files.
 * to save the current game state in order for the player to resume games.
 *
 * @author Rae 300535154
 */
public class XMLFileWriter {
    private static final int HEIGHT = 9;  // the height of the board
    private static final int WIDTH = 11;  // the width of the board

    /*----------------The debug function--------------------------------------------------------*/
    public static void main(String[] args) {
        XMLFileWriter p = new XMLFileWriter();
        p.saveCurrentMap("src/nz/ac/vuw/ecs/swen225/gp21/persistency/savedMap.xml", new App("level1"));
        p.saveCurrentActions("src/nz/ac/vuw/ecs/swen225/gp21/persistency/savedAction.xml", new App("level1"));
    }
    /* ------------------------------------------------------------------------------------------ */

    /**
     * save the current board of the game.
     *
     * @param fName the output file name
     * @param app the app
     */
    public void saveCurrentMap(String fName, App app){
        String rootName = "savedMap";
        writeGameToXML(fName, rootName, app);
    }

    /**
     * save the current action records of the game.
     *
     * @param fName the output file name
     * @param app the app
     */
    public void saveCurrentActions(String fName, App app){
        String rootName = "savedAction";
        writeGameToXML(fName, rootName, app);
    }

    /**
     * save the game state to XML file.
     *
     * @param fName file name to save as
     * @param rootName map or actions
     * @param app the app
     */
    private void writeGameToXML(String fName, String rootName, App app){
        try {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement(rootName);

            if(rootName.equals("savedMap")){
                //get all objects from the current game state to create the XML file
                objectsToXML(root, app);

                //TODO add movingBugs

            }else {
                gameActionsToXML(root, app);
            }

            /* set the XML output Format with line change and index */
            OutputFormat XMLFormat = OutputFormat.createPrettyPrint();
            XMLFormat.setEncoding("UTF-8");

            // write the output XML to the path
            OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream(fName), StandardCharsets.UTF_8);
            XMLWriter XMLWriter = new XMLWriter(writer, XMLFormat);
            XMLWriter.write(document);
            XMLWriter.flush();
            XMLWriter.close();

            System.out.println("\nXML file created!");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *  save the game actions to XML.
     *
     * @param root the root ele
     * @param app the app
     */
    private void gameActionsToXML(Element root, App app){

        // time left
        int timer = app.getTimer();
        Element element2 = root.addElement("timer");
        element2.addAttribute("timeLeft", Integer.toString(timer));

        // current level
        int level = app.getLevel();
        Element element3 = root.addElement("currentLevel");
        element3.addAttribute("level", Integer.toString(level));

        // keys collected  //TODO  get the saved actions from APP
        int keys = 0;/*app.keys_BCollected();*/
        Element element4 = root.addElement("keysCollected");
        element4.addAttribute("keys", Integer.toString(keys));

        // treasures left
        int treasures = app.getRemainingTreasures();
        Element element5 = root.addElement("treasuresLeft");
        element5.addAttribute("treasures", Integer.toString(treasures));

        //        // current player pos
//        Coordinate pos = new Coordinate(3,3) /*App.getPlayerCurrentPos*/;
//        Element element1 = root.addElement("currentPos");
//        element1.addAttribute("x", Integer.toString(pos.getX()));
//        element1.addAttribute("y", Integer.toString(pos.getY()));
    }

    /**
     * save the objects on the board to xml.
     *
     * @param root the root ele
     * @param app the app
     */
    private void objectsToXML(Element root, App app){
        XMLFileReader p = new XMLFileReader();
        Board b = p.loadOriginMap("src/nz/ac/vuw/ecs/swen225/gp21/persistency/levels/level1.xml");
        //TODO  get the current board from APP
        //Board b = app.getCurrentBoard();
        for (int x = 0; x < WIDTH; x++){
            for (int y = 0; y < HEIGHT; y++){
                Tile tile = b.getTile(new Coordinate(x,y));
                String type =  tile.getType().toString();
                Coordinate pos = tile.getLocation();
                String item = null;
                String col = null;
                String info = null;
                if (tile.getItem() != null){
                    item = tile.getItem().getType().toString();
                    if (item.equals("KEY")){
                        col = ((Item_Key)tile.getItem()).getColor();
                        System.out.println(tile.getType() +","+tile.getLocation()+", "+ item +" "+ col);
                    }
                    if(item.equals("LOCK_DOOR")){
                        col = ((Item_Door)tile.getItem()).getColor();
                        System.out.println(tile.getType() +","+tile.getLocation()+", "+ item +" "+ col);
                    }
                    if(item.equals("INFO")){
                        info = ((Item_Info)tile.getItem()).getInfo();
                        System.out.println(tile.getType() +","+tile.getLocation()+", "+ item +" "+ info);
                    }

                }

                addNodes(root, type, Integer.toString(pos.getX()), Integer.toString(pos.getY()), item, col, info);
            }
        }
    }

    /**
     * add all nodes to the file.
     *
     * @param root element root
     * @param type tile type
     * @param x x coordinate
     * @param y y coordinate
     * @param item item on tile
     * @param col key/door col
     * @param info message tile
     */
    private void addNodes(Element root, String type, String x, String y, String item, String col, String info) {
        Element element1 = root.addElement("tile");
        Element XElement = element1.addElement("x");
        Element YElement = element1.addElement("y");

        if(info != null){
            Element infoElement = element1.addElement("message");
            infoElement.setText(info);
        }

        //add the attributes
        if (item != null) {
            element1.addAttribute("type", item);
            if (col != null){
                element1.addAttribute("col", col);
            }
        }else {
            element1.addAttribute("type", type);
        }

        //TODO add the image icon attribute
        //element1.addAttribute("image", icon);

        //set the value
        XElement.setText(x);
        YElement.setText(y);
    }

}
