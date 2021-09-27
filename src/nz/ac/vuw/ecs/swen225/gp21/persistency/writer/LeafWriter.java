package nz.ac.vuw.ecs.swen225.gp21.persistency.writer;

import nz.ac.vuw.ecs.swen225.gp21.app.App;
import org.dom4j.Element;

/**
 * "Leaf".
 * represents leaf objects in the composition.
 * Add all nodes to root element.
 * implements all Component methods.
 *
 * @author Rae 300535154
 */
public class LeafWriter implements FileWriter{


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
    public void addNodes(Element root, String type, String x, String y,
                          String item, String col, String info, String chips) {
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
            if(chips !=null){
                element1.addAttribute("chips", chips);
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

    /**
     *  add all the game actions to root.
     *
     * @param root the root ele
     * @param timer current time left
     * @param actor current actor
     * @param dir direction actor moved
     */
    @Override
    public void addActionNode(Element root, int timer, String actor, String dir) {
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


    @Override
    public void saveCurrentMap(String fName, App app) {

    }


}
