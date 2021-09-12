package nz.ac.vuw.ecs.swen225.gp21.persistency;

import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item_Door;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item_Key;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Tile;
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

    public static void main(String[] args) {
        XMLFileWriter p = new XMLFileWriter();
        p.writeGameToXML();

    }

    /**
     * convert the game objects to XML file.
     * all objects are from the board.
     */
    public void writeGameToXML(){
        try {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("savedGame");

            //get all objects from the current game state to create the XML file
            objectsToXML(root);
            //TODO add movingBugs


            /* set the XML output Format with line change and index */
            OutputFormat XMLFormat = OutputFormat.createPrettyPrint();
            XMLFormat.setEncoding("UTF-8");

            // write the output XML to the path
            Writer writer = new FileWriter("src/nz/ac/vuw/ecs/swen225/gp21/persistency/savedGame.xml");
            XMLWriter XMLWriter = new XMLWriter(writer, XMLFormat);
            XMLWriter.write(document);
            XMLWriter.flush();
            XMLWriter.close();

            System.out.println("\nXML file created!");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void objectsToXML(Element root){
        XMLFileReader p = new XMLFileReader();
        //TODO  get the saved board from APP
        Board b = p.loadOriginGame("src/nz/ac/vuw/ecs/swen225/gp21/persistency/levels/level1.xml");

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
                        //TODO info = ((Item_Info)tile.getItem()).getInfo;
                        info = "testing........";
                    }

                }

                addNodes(root, type, Integer.toString(pos.getX()), Integer.toString(pos.getY()), item, col, info);
            }
        }
    }


    public void addNodes(Element root, String type, String x, String y, String item, String col, String info) {
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


    public void addWalls(Element root){
        Element element2 = root.addElement("repeatTile");
        Element xStartElement = element2.addElement("x_start");
        Element yStartElement = element2.addElement("y_start");
        Element xEndElement = element2.addElement("x_end");
        Element yEndElement = element2.addElement("y_end");
        // add node attribution
        element2.addAttribute("type", "wallTile");
        xStartElement.setText("x1");
        yStartElement.setText("y1");
        xEndElement.setText("x2");
        yEndElement.setText("y2");
    }
}
