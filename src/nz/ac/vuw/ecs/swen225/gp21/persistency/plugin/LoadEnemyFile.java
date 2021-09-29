package nz.ac.vuw.ecs.swen225.gp21.persistency.plugin;

import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Enemy;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.persistency.reader.XMLFileReader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

/**
 * implement access to enemy class using a plugin-based design.
 *
 * @author Rae 300535154
 */
public class LoadEnemyFile {

    private List<Enemy> enemyClasses = new ArrayList<>(); // store all bugs


    /**
     * load Enemies from jar file.
     *
     * @param name Enemy name
     * @return - the list
     */
    public List<Enemy> loadEnemyClasses(XMLFileReader reader, List<String> name) {
        try {
            for(int i=0; i< reader.getEnemyStartPos().size();i++) {
                this.setEnemyClasses(reader.getEnemyStartPos().get(i), name.get(i));
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return this.enemyClasses;
    }



    /**
     * Load all enemy from the jar file.
     * construct all enemy objects with associated positions.
     * store them in enemyClasses list.
     *
     * @param pos list of pos
     * @throws ClassNotFoundException msg
     * @throws IllegalAccessException msg
     * @throws InstantiationException msg
     */
    public void setEnemyClasses(Coordinate pos, String name) throws ClassNotFoundException,
            IllegalAccessException, InstantiationException {
        File jarFile = new File("src/nz/ac/vuw/ecs/swen225/gp21/persistency/levels");
        File[] files = jarFile.listFiles(file -> file.getPath().toLowerCase().endsWith(".jar"));

        if (files != null && files.length > 0) {
            Class classToLoad = Class.forName(name, false, getClassLoader(files[0]));

            // set each with object corresponding position
            Object instance = classToLoad.newInstance();
            Enemy act = (Enemy) instance;
            act.setPosition(new Coordinate(pos.getX(), pos.getY()));

            this.enemyClasses.add(act);
        }
    }

    /**
     * get the classloader with Privileged Blocks.
     * 
     * @param file input
     * @return classloader
     */
    private URLClassLoader getClassLoader(File file)  {
        URLClassLoader child = (URLClassLoader) AccessController.doPrivileged(
                new PrivilegedAction() {
                    public Object run() {
                        try {
                            return new URLClassLoader(
                                    new URL[]{file.toURI().toURL()},
                                    Enemy.class.getClassLoader()
                            );
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                });

        return child;
    }
}
