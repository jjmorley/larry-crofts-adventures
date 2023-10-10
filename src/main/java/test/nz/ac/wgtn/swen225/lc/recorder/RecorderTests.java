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

public class RecorderTests {

    private Recorder recorder;
    private File testFile;
    private Game game;

    @Before
    public void setUp() {
        this.game = new Game(0);
        recorder = new Recorder(1, game); // Initialize Recorder
        testFile = new File("src/main/resources/Recorder/test-playback-game.json");
    }

    @Test
    public void testSave1() {
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

    @Test
    public void testSave2() {
        // Add a different sequence of player moves to the recorder
        recorder.addPlayerMove(Direction.UP);
        recorder.addPlayerMove(Direction.LEFT);
        recorder.addActorMove();
        recorder.addPlayerMove(Direction.UP);

        // Save the recorded game data to a unique test file
        File uniqueTestFile = new File("src/main/resources/Recorder/unique-test-recorded-game.json");
        recorder.saveRecordedGameToFile(uniqueTestFile);

        try {
            // Construct a Playback object from the unique test file and catch IOException
            Playback loadedRecorder = new Playback(uniqueTestFile, this.game);

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