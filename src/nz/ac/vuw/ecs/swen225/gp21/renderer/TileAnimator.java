package nz.ac.vuw.ecs.swen225.gp21.renderer;

import nz.ac.vuw.ecs.swen225.gp21.domain.board.Tile;

public class TileAnimator extends ElementAnimator {

    //Associated Tile
    private Tile tile;

    TileAnimator(Tile tile){ this.tile = tile; }

    public Tile getTile() {return tile;}
}
