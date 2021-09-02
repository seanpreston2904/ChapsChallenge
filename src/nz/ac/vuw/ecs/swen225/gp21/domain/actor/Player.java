package nz.ac.vuw.ecs.swen225.gp21.domain.actor;

import nz.ac.vuw.ecs.swen225.gp21.domain.board.Item;
import nz.ac.vuw.ecs.swen225.gp21.domain.utils.Coordinate;

import java.util.ArrayList;

public class Player extends Actor {

    private ArrayList<Item> inventory;

    public Player(Coordinate initial) {
        super(initial);
    }

}
