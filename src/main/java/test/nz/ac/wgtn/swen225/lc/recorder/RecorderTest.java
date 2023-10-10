package test.nz.ac.wgtn.swen225.lc.recorder;

import nz.ac.wgtn.swen225.lc.app.Game;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Direction;
import nz.ac.wgtn.swen225.lc.recorder.Playback;
import nz.ac.wgtn.swen225.lc.recorder.Recorder;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class RecorderTest {

    private Recorder recorder;
    private File testFile;
    private Game game;

    @Before
    public void setUp() {
        this.game = new Game(0);
        recorder = new Recorder(1, game); // Initialize Recorder
        testFile = new File("test-recorded-game.json"); // Create a test file
    }

    @Test
    public void testSave() {
        // Add some moves to the recorder
        recorder.addPlayerMove(Direction.RIGHT);
        recorder.addActorMove();
        recorder.addPlayerMove(Direction.DOWN);

        // Save the recorded game data to a test file
        recorder.saveRecordedGameToFile(testFile);

        try {

            // Construct a Playback object from the test file and catch IOException
            Playback loadedRecorder = new Playback(testFile, this.game);

            // Check if loadedRecorder contains the same moves as the original recorder
            assertEquals(recorder.getPlayerMoveHistory(), loadedRecorder.getPlayerMoveHistory());
            assertEquals(recorder.getActorMoveHistory(), loadedRecorder.getActorMoveHistory());
            assertEquals(recorder.getLevel(), loadedRecorder.getLevel());
        } catch (IOException e) {
            // Handle IOException if it occurs during file operations
            fail("IOException occurred: " + e.getMessage());
        }
    }
}