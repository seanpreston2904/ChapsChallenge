package nz.ac.vuw.ecs.swen225.gp21.renderer.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePaths {

    public static BufferedImage IMG_FLOOR;
    public static BufferedImage IMG_WALL_FULL;
    public static BufferedImage IMG_INFO;
    public static BufferedImage IMG_TREASURE;
    public static BufferedImage IMG_PLAYER;
    public static BufferedImage IMG_EXIT;
    public static BufferedImage IMG_KEY;
    public static BufferedImage IMG_DOOR;

    static {
        try {
            IMG_FLOOR = ImageIO.read(new File("./res/graphics/floor.png"));
            IMG_WALL_FULL = ImageIO.read(new File("./res/graphics/wall_full.png"));
            IMG_INFO = ImageIO.read(new File("./res/graphics/info.png"));
            IMG_TREASURE = ImageIO.read(new File("./res/graphics/treasure.png"));
            IMG_PLAYER = ImageIO.read(new File("./res/graphics/player.png"));
            IMG_EXIT = ImageIO.read(new File("./res/graphics/exit.png"));
            IMG_KEY = ImageIO.read(new File("./res/graphics/key.png"));
            IMG_DOOR = ImageIO.read(new File("./res/graphics/door.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
