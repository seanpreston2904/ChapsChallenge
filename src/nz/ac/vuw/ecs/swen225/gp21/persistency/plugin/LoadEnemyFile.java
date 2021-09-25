package nz.ac.vuw.ecs.swen225.gp21.persistency.plugin;

import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Enemy;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;

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
     * get all the Enemy classes from jar.
     *
     * @return a list of classes
     */
    public List<Enemy> getEnemyClasses(){
        return this.enemyClasses;
    }

    /**
     * Load all enemy from the jar file.
     * construct all enemy objects with associated positions.
     * store them in enemyClasses list.
     *
     * @param pos list of pos
     * @throws MalformedURLException msg
     * @throws ClassNotFoundException msg
     * @throws IllegalAccessException msg
     * @throws InstantiationException msg
     */
    public void setEnemyClasses(List<Coordinate> pos, String name) throws MalformedURLException,
            ClassNotFoundException, IllegalAccessException, InstantiationException {
        File jarFile = new File("src/nz/ac/vuw/ecs/swen225/gp21/persistency/levels");
        File[] files = jarFile.listFiles(file -> file.getPath().toLowerCase().endsWith(".jar"));

        assert files != null;

        Class classToLoad = Class.forName(name, false, getClassLoader(files[0]));

        // set each with object corresponding position
        for (Coordinate po : pos) {
            Object instance = classToLoad.newInstance();
            Enemy act = (Enemy) instance;
            act.setPosition(new Coordinate(po.getX(), po.getY()));
            this.enemyClasses.add(act);
        }

    }

    /**
     * get the classloader with Privileged Blocks.
     * @param file input
     * @return classloader
     */
    public URLClassLoader getClassLoader(File file)  {
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
