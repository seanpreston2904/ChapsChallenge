package nz.ac.vuw.ecs.swen225.gp21.renderer.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePaths {

    public static BufferedImage IMG_FLOOR;
    public static BufferedImage IMG_WALL;

    static {
        try {
            IMG_FLOOR = ImageIO.read(new File("./res/graphics/floor.png"));
            //IMG_WALL = ImageIO.read(new File("./res/graphics/wall_floor.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
