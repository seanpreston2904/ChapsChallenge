package nz.ac.vuw.ecs.swen225.gp21.persistency.writer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import nz.ac.vuw.ecs.swen225.gp21.app.App;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.*;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.persistency.reader.XMLFileReader;
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
    private LeafWriter leafWriter = new LeafWriter();

    public static void main(String[] args) {
        XMLFileReader reader = new XMLFileReader();
        XMLFileWriter writer = new XMLFileWriter();
        writer.saveCurrentMap("src/nz/ac/vuw/ecs/swen225/gp21/persistency/savedMap.xml",
                new App("level1"));
    }

    /**
     * save the board of the current game.
     *
     * @param fName the output file name
     * @param app the current game
     */
    @Override
    public void saveCurrentMap (String fName, App app){
        String rootName = "savedMap";
        writeGameToXML(fName, rootName, app);
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
        leafWriter.addActionNode(root, timer, actor, dir);
    }

    /**
     * save the game state to XML file.
     *
     * @param fName file name to save as
     * @param rootName map or actions
     *
     */
    private void writeGameToXML(String fName, String rootName, App app){
        try {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement(rootName);

            if(rootName.equals("savedMap")){
                //get all objects from the current game state to create the XML file
                objectsToXML(root, app);

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
     * @param app the current game
     */
    private void objectsToXML(Element root, App app){
        Board board = app.getCurrentBoard();
        for (int x = 0; x < WIDTH; x++){
            for (int y = 0; y < HEIGHT; y++){
                Tile tile = board.getTile(new Coordinate(x,y));
                String type =  tile.getType().toString();
                Coordinate pos = tile.getLocation();
                String item = null;
                String col = null;
                String info = null;
                String chips = null;
                if (tile.getItem() != null){
                    item = tile.getItem().getType().toString();
                    switch (item) {
                        case "KEY":
                            col = ((Item_Key) tile.getItem()).getColor();
                            //System.out.println(tile.getType() +","+tile.getLocation()+", "+ item +" "+ col);
                            break;
                        case "LOCK_DOOR":
                            col = ((Item_Door) tile.getItem()).getColor();
                            // System.out.println(tile.getType() +","+tile.getLocation()+", "+ item +" "+ col);
                            break;
                        case "INFO":
                            info = ((Item_Info) tile.getItem()).getInfo();
                            // System.out.println(tile.getType() +","+tile.getLocation()+", "+ item +" "+ info);
                            break;
                        case "LOCK_EXIT":
                            chips = Integer.toString(((Item_Exit) tile.getItem()).getTreasure());
                            System.out.println(tile.getType() +","+tile.getLocation()+", "+ item +" "+ chips);
                            break;
                    }
                }
                // add all nodes to the file
                leafWriter.addNodes(root, type, Integer.toString(pos.getX()),
                        Integer.toString(pos.getY()), item, col, info, chips);
            }
        }
    }




}
