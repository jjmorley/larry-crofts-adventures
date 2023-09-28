package nz.ac.wgtn.swen225.lc.fuzz;

import static org.junit.jupiter.api.Assertions.*;

import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Direction;
import nz.ac.wgtn.swen225.lc.app.*;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * Generate some random input to invoke methods in nz.ac.wgtn.swen225.lc.app
 * Each test will automatically play level 1 (test1) and level 2 (test2) of the game.
 *
 * @author chaisonopp
 */
class FuzzTest {

    // Level 1 test
    @Test
    void test1() {
        assertTimeout(java.time.Duration.ofSeconds(60), () -> {

            /* Init/Assign */
            Game game = new Game(1);
            nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Direction dir = Direction.UP;
            final int MOVE_DURATION = 500;

            /* Move the player up five times (out of top-board), then down 10 times (out of bot-board) */
            for (int i = 0; i < 15; i++) {
                if (i <= 5) { game.movePlayer(Direction.UP); }
                else { game.movePlayer(Direction.DOWN); }
                Thread.sleep(MOVE_DURATION);
            }

            /* Move the player in random directions */
            for (int i = 0; i < 10; i++) {
                // Generate a random number between 1 and 4 (inclusive)
                Random random = new Random();
                int rand = random.nextInt(4) + 1;

                // Correspond the numbers to movements
                switch (rand) {
                    case (1) -> dir = Direction.UP;
                    case (2) -> dir = Direction.DOWN;
                    case (3) -> dir = Direction.LEFT;
                    case (4) -> dir = Direction.RIGHT;
                }

                // Move the player
                game.movePlayer(dir);
                Thread.sleep(MOVE_DURATION);
            }

            /*  */

        });
    }

    /*
    // Level 2 test
    @Test
    void test2() {
        fail("Not yet implemented");
    }
    */

}




