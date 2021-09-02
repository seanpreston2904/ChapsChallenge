package nz.ac.vuw.ecs.swen225.gp21.persistence;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * responsible for reading map files representing the current game state in order for the player to resume games.
 */
public class ReadXML {
    public static void main(String[] args) {
        new ReadXML();
    }

    /**
     * constructor
     */
    public ReadXML() {
        try {
            File input = new File("src/Persistence/level1.xml");
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(input);

            System.out.println("Root element :" + document.getRootElement().getName());

            Element classElement = document.getRootElement();

            List<Node> allNodes = document.selectNodes("/level/tile" );
            System.out.println("----------------------------");

            for (Node node : allNodes) {
                System.out.println("\nCurrent Element :" + node.getName());
                System.out.println("Type : " + node.valueOf("@type"));

                // check for key or door type
                if(node.valueOf("@type").equals("door")||node.valueOf("@type").equals("key")){
                    System.out.println("color : "
                            + node.selectSingleNode("col").getText() );
                }

                System.out.println("x : "
                        + node.selectSingleNode("x").getText() );
                System.out.println("y : "
                        + node.selectSingleNode("y").getText());

            }


        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}
