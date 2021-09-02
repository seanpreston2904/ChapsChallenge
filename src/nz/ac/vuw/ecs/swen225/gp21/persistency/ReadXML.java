package nz.ac.vuw.ecs.swen225.gp21.persistency;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * The persistence module.
 * It is responsible for reading map files and reading/writing files.
 * representing the current game state (in JSON format) in order for the player to resume games.
 *
 * @author Rae 300535154
 */
public class ReadXML {
    public static void main(String[] args) throws Exception {
        ReadXML p = new ReadXML();
        p.readGameLevel("src/nz/ac/vuw/ecs/swen225/gp21/persistency/levels/level1.xml");
        p.readGameLevel("src/nz/ac/vuw/ecs/swen225/gp21/persistency/levels/level2.xml");
    }

    /**
     * loads game levels from XML files.
     */
    public void readGameLevel(String fileName){
        try {
            File input = new File(fileName);
            //InputStream inputStream = this.getClass().getResourceAsStream("/level"+level+".xml");

            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(input);

            System.out.println("Root element :" + document.getRootElement().getName());

            Element documentRootElement = document.getRootElement();

            // get all single tile
            if(documentRootElement.elements("tile") != null ) {
                List<Node> allSingleNodes = document.selectNodes("/level/tile");
                this.getAllSingleNodes(allSingleNodes);
            }

            // get all repeated wall tile
            if(documentRootElement.elements("repeatTile") != null) {
                List<Node> allRepeatNodes = document.selectNodes("/level/repeatTile");
                this.getAllRepeatNodes(allRepeatNodes);
            }

            // get all bugs tile
            if(fileName.equals("src/nz/ac/vuw/ecs/swen225/gp21/persistency/levels/level2.xml")
                    && documentRootElement.elements("movingTile") != null) {
                List<Node> allMovingNodes = document.selectNodes("/level/movingTile");
                this.getAllMovingNodes(allMovingNodes);
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }



    public void getAllSingleNodes(List<Node> allNodes){
        System.out.println("----------------------------");

        // loop all single nodes
        for (Node node : allNodes) {
            System.out.println("\nCurrent Element :" + node.getName());

            //get the type the corresponding image of the tile
            Element element = (Element) node;
            String type = element.attributeValue("type");
            String image = element.attributeValue("image");
            System.out.println("Type : " + type + ", image: " + image);
            //TODO make Domain Objects

            // check for key or door col
            String col = null;
            if(type.equals("door")||type.equals("key")){
                col = node.selectSingleNode("col").getText();
                System.out.println("color : " + col );
            }

            // get the location of the tile
            String x = node.selectSingleNode("x").getText();
            String y = node.selectSingleNode("y").getText();
            System.out.println("x : " + x + ", y : " + y);


            //TODO set each tile at corresponding position
        }
    }

    public void getAllRepeatNodes(List<Node> allNodes){
        System.out.println("----------------------------");

        // loop all repeat nodes
        for (Node node : allNodes) {
            System.out.println("\nCurrent Element :" + node.getName());

            //get the type the corresponding image of the tile
            Element element = (Element) node;
            String type = element.attributeValue("type");
            String image = element.attributeValue("image");
            System.out.println("Type : " + type + ", image: " + image);
            //TODO make Domain Objects

            // get the start location
            String x_start = node.selectSingleNode("x_start").getText();
            String y_start = node.selectSingleNode("y_start").getText();
            System.out.println("x start: " + x_start + ", y start: " + y_start);

            // get the end location
            String x_end = node.selectSingleNode("x_end").getText();
            String y_end = node.selectSingleNode("y_end").getText();
            System.out.println("x end: " + x_end +", y end: " + y_end );

            //TODO set each tile at corresponding position
        }
    }

    private void getAllMovingNodes(List<Node> allMovingNodes) {
        System.out.println("----------------------------");

        // loop all single nodes
        for (Node node : allMovingNodes) {
            System.out.println("\nCurrent Element :" + node.getName());

            //get the type the corresponding image of the tile
            Element element = (Element) node;
            String type = element.attributeValue("type");
            String image = element.attributeValue("image");
            //String type = node.valueOf("@type");
            System.out.println("Type : " + type + ", image: " + image);
            //TODO make Domain Objects

            // get the list of starting location of each bug
            System.out.println("Bug start positions: ");
            List<Node> list = node.selectSingleNode("bugStartPos").selectNodes("position");
            for (Node n:list){
                Element ele = (Element) n;
                String x = ele.attributeValue("x");
                String y = ele.attributeValue("y");
                System.out.println("x : " + x + ", y : " + y);
                //TODO set each tile at corresponding position
            }

        }
    }
}
