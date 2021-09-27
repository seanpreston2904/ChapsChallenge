package nz.ac.vuw.ecs.swen225.gp21.fuzz;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.actor.Player;
import org.junit.jupiter.api.Test;

/**
 * JUnit test cases
 *
 * @author harry
 *
 */
public class Fuzz {
    @Test
    public void fuzzTest1() {
        System.out.println("Fuzz test 1");
        Domain domain = new Domain("level1");
        Player player = domain.getPlayer();

        while(domain.isRunning()) {
            domain.randomlyMoveActor(player);
            System.out.println("RemainingChips : "+domain.getRemainingChips());
        }
        System.out.println(domain.getRemainingChips());

    }
}
