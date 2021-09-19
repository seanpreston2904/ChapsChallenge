package nz.ac.vuw.ecs.swen225.gp21.renderer.util;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.board.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.TileType;
import nz.ac.vuw.ecs.swen225.gp21.renderer.RenderView;

import javax.swing.*;
import java.awt.*;

public class TestWindow {

    public static void main(String[] args){

        JFrame window = new JFrame();
        RenderView renderView = new RenderView(new Domain("level1"));

        window.add(renderView);
        window.pack();

        renderView.startRender();

        window.setResizable(false);
        window.setVisible(true);

    }

}
