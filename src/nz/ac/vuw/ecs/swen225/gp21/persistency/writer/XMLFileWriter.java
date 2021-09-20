package nz.ac.vuw.ecs.swen225.gp21.persistency.writer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

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
public class XMLFileWriter implements FileWriter {
    private static final int HEIGHT = 9;  // the height of the board
    private static final int WIDTH = 11;  // the width of the board

    /**
     * save the board of the current game.
     *
     * @param fName the output file name
     * @param board the current board
     */
    @Override
    public void saveCurrentMap(String fName, Board board){
        String rootName = "savedMap";
        writeGameToXML(fName, rootName, board);
    }

    /**
     *  add the game actions to XML.
     *  This is for Recorder to write each action records to a XML file.
     *
     * @param root the root ele
     * @param timer current time left
     * @param actor current actor
     * @param dir direction actor moved
     */
    @Override
    public void addActionNode(Element root, int timer, String actor, String dir){
        Element element_root = root.addElement("action");
        // time left
        Element element2 = element_root.addElement("timer");
        element2.addAttribute("timer", Integer.toString(timer));

        // current actor
        Element element3 = element_root.addElement("actor");
        element3.addAttribute("actor", actor);

        // direction
        Element element4 = element_root.addElement("direction");
        element4.addAttribute("direction", dir);
    }

    /**
     * save the game state to XML file.
     *
     * @param fName file name to save as
     * @param rootName map or actions
     *
     */
    private void writeGameToXML(String fName, String rootName, Board board){
        try {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement(rootName);

            if(rootName.equals("savedMap")){
                //get all objects from the current game state to create the XML file
                objectsToXML(root, board);

                //TODO add movingBugs

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
     * save the objects on the board to xml.
     *
     * @param root the root ele
     * @param board the current board
     */
    private void objectsToXML(Element root, Board board){
        for (int x = 0; x < WIDTH; x++){
            for (int y = 0; y < HEIGHT; y++){
                Tile tile = board.getTile(new Coordinate(x,y));
                String type =  tile.getType().toString();
                Coordinate pos = tile.getLocation();
                String item = null;
                String col = null;
                String info = null;
                if (tile.getItem() != null){
                    item = tile.getItem().getType().toString();
                    if (item.equals("KEY")){
                        col = ((Item_Key)tile.getItem()).getColor();
                        //System.out.println(tile.getType() +","+tile.getLocation()+", "+ item +" "+ col);
                    }
                    if(item.equals("LOCK_DOOR")){
                        col = ((Item_Door)tile.getItem()).getColor();
                       // System.out.println(tile.getType() +","+tile.getLocation()+", "+ item +" "+ col);
                    }
                    if(item.equals("INFO")){
                        info = ((Item_Info)tile.getItem()).getInfo();
                       // System.out.println(tile.getType() +","+tile.getLocation()+", "+ item +" "+ info);
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
