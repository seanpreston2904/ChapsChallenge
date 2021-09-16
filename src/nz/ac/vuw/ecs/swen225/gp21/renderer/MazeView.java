package nz.ac.vuw.ecs.swen225.gp21.renderer;

import javax.swing.*;
import java.awt.*;

public class MazeView extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {

        //Clear Background
        super.paintComponent(g);

        //Draw Red Background
        g.setColor(Color.RED);
        g.fillRect(0, 0, 640, 480);

    }

}
