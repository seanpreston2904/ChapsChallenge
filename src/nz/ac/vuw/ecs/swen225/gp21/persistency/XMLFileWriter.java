package nz.ac.vuw.ecs.swen225.gp21.persistency;

import nz.ac.vuw.ecs.swen225.gp21.domain.board.*;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * writing the Java objects to XML files to save the current game state in order for the player to resume games.
 *
 * @author Rae 300535154
 */
public class XMLFileWriter {
    private final int HEIGHT = 9;  // the height of the board
    private final int WIDTH = 11;  // the width of the board

    /*----------------The debug function--------------------------------------------------------*/
    public static void main(String[] args) {
        XMLFileWriter p = new XMLFileWriter();
        p.saveCurrentMap("src/nz/ac/vuw/ecs/swen225/gp21/persistency/savedMap.xml");
        p.saveCurrentActions("src/nz/ac/vuw/ecs/swen225/gp21/persistency/savedAction.xml");
    }
    /* ------------------------------------------------------------------------------------------ */

    /**
     * save the current board of the game.
     *
     * @param fName the output file name
     */
    public void saveCurrentMap(String fName){
        String rootName = "savedMap";
        writeGameToXML(fName, rootName);
    }

    /**
     * save the current action records of the game.
     *
     * @param fName output file name
     */
    public void saveCurrentActions(String fName){
        String rootName = "savedAction";
        writeGameToXML(fName, rootName);
    }

    /**
     * save the game state to XML file.
     *
     * @param fName file name to save as
     * @param rootName map or actions
     */
    public void writeGameToXML(String fName, String rootName){
        try {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement(rootName);

            if(rootName.equals("savedMap")){
                //get all objects from the current game state to create the XML file
                objectsToXML(root);

                //TODO add movingBugs

            }else {
                gameActionsToXML(root);
            }

            /* set the XML output Format with line change and index */
            OutputFormat XMLFormat = OutputFormat.createPrettyPrint();
            XMLFormat.setEncoding("UTF-8");

            // write the output XML to the path
            Writer writer = new FileWriter(fName);
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
     * save the game actions to XML.
     *
     * @param root the root ele
     */
    private void gameActionsToXML(Element root){
        //TODO  get the saved actions from APP

//        // current player pos
//        Coordinate pos = new Coordinate(3,3) /*App.getPlayerCurrentPos*/;
//        Element element1 = root.addElement("currentPos");
//        element1.addAttribute("x", Integer.toString(pos.getX()));
//        element1.addAttribute("y", Integer.toString(pos.getY()));

        // time left
        int timer = 0 /*App.getTimer*/;
        Element element2 = root.addElement("timer");
        element2.addAttribute("timeLeft", Integer.toString(timer));

        // current level
        int level = 0 /*App.getLevel*/;
        Element element3 = root.addElement("currentLevel");
        element3.addAttribute("level", Integer.toString(level));

        // keys collected
        int keys_B = 0 /*App.keysCollected*/;
        int keys_R = 0;
        int keys_G = 0;
        Element element4 = root.addElement("keysCollected");
        element4.addAttribute("keys_B", Integer.toString(keys_B));
        element4.addAttribute("keys_R", Integer.toString(keys_R));
        element4.addAttribute("keys_G", Integer.toString(keys_G));

        // treasures left
        int treasures = 0 /*App.treasuresLeft*/;
        Element element5 = root.addElement("treasuresLeft");
        element5.addAttribute("treasures", Integer.toString(treasures));

    }

    /**
     * save the objects on the board to xml.
     *
     * @param root the root ele
     */
    private void objectsToXML(Element root){
        XMLFileReader p = new XMLFileReader();
        //TODO  get the current board from APP
        Board b = p.loadOriginMap("src/nz/ac/vuw/ecs/swen225/gp21/persistency/levels/level1.xml");

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
