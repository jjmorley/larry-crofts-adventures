package nz.ac.wgtn.swen225.lc.fuzz;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import nz.ac.wgtn.swen225.lc.app.*;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Direction;
import org.junit.jupiter.api.Test;

/**
 * Generate some random input to invoke methods in nz.ac.wgtn.swen225.lc.app
 * Each test will automatically play level 1 (test1) and level 2 (test2) of the game.
 *
 * @author chaisonopp
 */
class FuzzTest {
    // Initialise/Assignment

    private nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Direction dir;
    private final int TIMEOUT = 250;


    // Level 1 test
    @Test
    void test1() {
        assertTimeout(java.time.Duration.ofSeconds(60), () -> {
            // Initialise Game()
            Game game = new Game(1);

            // Try to move the player out of bounds in primary directions (UP, DOWN, LEFT, RIGHT)
            for (int i = 0; i < 35; i++) {
                if (i <= 5) {
                    game.movePlayer(Direction.UP);
                } else if (i <= 15) {
                    game.movePlayer(Direction.DOWN);
                } else if (i <= 17) {
                    game.movePlayer(Direction.UP);
                } else if (i <= 22) {
                    game.movePlayer(Direction.LEFT);
                } else if (i <= 32) {
                    game.movePlayer(Direction.RIGHT);
                } else {
                    game.movePlayer(Direction.LEFT);
                }

                Thread.sleep(TIMEOUT);
            }

            // Move the player in random directions
            for (int i = 0; i < 100; i++) {
                // Generate a random number between 1 and 4 (inclusive)
                Random random = new Random();
                int rand = random.nextInt(4) + 1;

                // Correspond the numbers to movements
                switch (rand) {
                    default  -> dir = Direction.UP;
                    case (2) -> dir = Direction.DOWN;
                    case (3) -> dir = Direction.LEFT;
                    case (4) -> dir = Direction.RIGHT;
                }

                // Move the player
                game.movePlayer(dir);
                Thread.sleep(TIMEOUT);
            }
        });
    }



    // Level 2 test
    @Test
    void test2() {
        assertTimeout(java.time.Duration.ofSeconds(60), () -> {
            // Initialise Game()
            Game game = new Game(2);

            // Move the player in random directions
            for (int i = 0; i < 100; i++) {
                // Generate a random number between 1 and 4 (inclusive)
                Random random = new Random();
                int rand = random.nextInt(4) + 1;

                // Correspond the numbers to movements
                switch (rand) {
                    default  -> dir = Direction.UP;
                    case (2) -> dir = Direction.DOWN;
                    case (3) -> dir = Direction.LEFT;
                    case (4) -> dir = Direction.RIGHT;
                }

                // Move the player
                game.movePlayer(dir);
                Thread.sleep(TIMEOUT);
            }
        });
    }

}




