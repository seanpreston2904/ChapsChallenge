package test.nz.ac.vuw.ecs.swen225.gp21.persistency;

import nz.ac.vuw.ecs.swen225.gp21.app.App;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Enemy;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item_Door;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item_Key;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.persistency.plugin.LoadEnemyFile;
import nz.ac.vuw.ecs.swen225.gp21.persistency.reader.XMLFileReader;
import nz.ac.vuw.ecs.swen225.gp21.persistency.writer.XMLFileWriter;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;

/**
 * unit tests for Persistence Module.
 */
public class PersistenceTests {
    private String file1 = "src/nz/ac/vuw/ecs/swen225/gp21/persistency/levels/level1.xml"; // game level1 map
    private String file2 = "src/nz/ac/vuw/ecs/swen225/gp21/persistency/levels/level2.xml"; // game level2 map
    private String outAction = "src/test/nz/ac/vuw/ecs/swen225/gp21/persistency/testAction.xml";
    /*---------  Tests for XMLFileReader ----------- */
    private  XMLFileReader reader = new XMLFileReader();
    private Board originalBoard =
            reader.loadOriginMap(file1);
    private Board savedBoard =
            reader.loadSavedMap("src/test/nz/ac/vuw/ecs/swen225/gp21/persistency/savedMap.xml");

    /**
     * test tiles position on map file.
     */
    @Test
    public void test_1() {
        Coordinate p1 = new Coordinate(0,0);
        Tile tile1 = originalBoard.getTile(p1);
        assertEquals("WALL", tile1.getType().toString());

        Coordinate p2 = new Coordinate(5,1);
        Tile tile2 = originalBoard.getTile(p2);
        assertEquals("EXIT", tile2.getType().toString());

        Coordinate p3 = new Coordinate(1,1);
        Tile tile3 = originalBoard.getTile(p3);
        assertEquals("FREE", tile3.getType().toString());
    }

    /**
     * test items position on map file.
     */
    @Test
    public void test_2() {
        Coordinate p1 = new Coordinate(3,2);
        Tile tile1 = originalBoard.getTile(p1);
        assertEquals("LOCK_DOOR", tile1.getItem().getType().toString());
        assertEquals("green", ((Item_Door) tile1.getItem()).getColor());

        Coordinate p2 = new Coordinate(3,4);
        Tile tile2 = originalBoard.getTile(p2);
        assertEquals("KEY", tile2.getItem().getType().toString());
        assertEquals("blue", ((Item_Key) tile2.getItem()).getColor());

        Coordinate p3 = new Coordinate(2,1);
        Tile tile3 = originalBoard.getTile(p3);
        assertEquals("TREASURE", tile3.getItem().getType().toString());
    }

    /**
     * test saved file tiles position.
     */
    @Test
    public void test_3() {
        Coordinate p1 = new Coordinate(1,2);
        Tile tile1 = savedBoard.getTile(p1);
        assertEquals("WALL", tile1.getType().toString());

        Coordinate p2 = new Coordinate(5,1);
        Tile tile2 = savedBoard.getTile(p2);
        assertEquals("EXIT", tile2.getType().toString());

        Coordinate p3 = new Coordinate(3,3);
        Tile tile3 = savedBoard.getTile(p3);
        assertEquals("FREE", tile3.getType().toString());

    }

    /**
     * test saved map file items position.
     */
    @Test
    public void test_4() {
        Coordinate p1 = new Coordinate(5,2);
        Tile tile1 = savedBoard.getTile(p1);
        assertEquals("LOCK_EXIT", tile1.getItem().getType().toString());

        Coordinate p2 = new Coordinate(5,5);
        Tile tile2 = savedBoard.getTile(p2);
        assertEquals("INFO", tile2.getItem().getType().toString());

        Coordinate p3 = new Coordinate(9,6);
        Tile tile3 = savedBoard.getTile(p3);
        assertEquals("TREASURE", tile3.getItem().getType().toString());
    }

    /**
     * test bug position.
     */
    @Test
    public void test_5()  {
        reader.loadOriginMap(file2);
        List<Coordinate> pos = reader.getEnemyStartPos();
        LoadEnemyFile loadEnemyFile = new LoadEnemyFile();
        List<Enemy> lis = loadEnemyFile.loadEnemyClasses(reader, reader.getEnemyName());
        for(int i = 0; i<pos.size(); i++){
            assertEquals(lis.get(i).getPosition().getY(), pos.get(i).getY());
        }

    }

    /**
     * test level.
     */
    @Test
    public void test_6()  {
        assertEquals(reader.getLevel(file2), 2);
        assertEquals(reader.getLevel(file1), 1);
    }


    /*---------  Tests for XMLFileWriter ----------- */
    private XMLFileWriter writer = new XMLFileWriter();

    /**
     * test for map Output file.
     */
    @Test
    public void test_7() {
        writer.saveCurrentMap(
                "src/test/nz/ac/vuw/ecs/swen225/gp21/persistency/testMap.xml",
                new App("level1"));
    }

    /**
     * test for action Output file.
     */
    @Test
    public void test_8() {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("savedAction");

        root.addAttribute("level", "2");

        writer.addActionNode(root, 90, "hero", "NORTH");
        writer.addActionNode(root, 80, "hero", "NORTH");
        writer.addActionNode(root, 70, "hero", "NORTH");
        writer.addActionNode(root, 60, "hero", "NORTH");

        writer.setupXMLOut(outAction, document);

    }


    /**
     * test saved action file.
     */
    @Test
    public void test_9() {
        List<Map<String, String>> savedRecords = reader.loadSavedActions(outAction);

        Map<String, String> timer = savedRecords.get(0);
        Map<String, String> actor = savedRecords.get(1);
        Map<String, String> dir = savedRecords.get(2);

        assertEquals("90", timer.get("timer"));
        assertEquals("hero", actor.get("actor"));
        assertEquals("NORTH", dir.get("direction"));
    }

}
