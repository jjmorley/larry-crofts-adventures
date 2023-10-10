package test.nz.ac.wgtn.swen225.lc.recorder;


import nz.ac.wgtn.swen225.lc.app.Game;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Direction;
import nz.ac.wgtn.swen225.lc.recorder.Playback;
import nz.ac.wgtn.swen225.lc.recorder.Recorder;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

public class PlaybackTests {

    private Game game;
    private Recorder recorder;
    private File testFile;
    private Playback playback;
    private File invalidTestFile; // Create a separate test file for invalid format


    @Before
    public void setUp() {
        // Initialize the game or any required objects
        game = new Game(1); // You may need to adjust the game initialization based on your actual setup
        recorder = new Recorder(1, game); // Initialize Recorder
        testFile = new File("test-playback-game.json"); // Create a test file
        invalidTestFile = new File("invalid-playback-game.json"); // Create a separate test file for invalid format
    }

    @Test
    public void testPlaybackInitialization() {
        recorder.saveRecordedGameToFile(testFile);
        try {
            playback = new Playback(testFile, game);

            assertEquals(0, playback.getFrame());
            assertEquals(1, playback.getLevel());
            assertFalse(playback.isPlayingBack());
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testLoadRecordedGameFromFile() {
        recorder.addPlayerMove(Direction.RIGHT);
        recorder.addActorMove();
        recorder.addPlayerMove(Direction.DOWN);
        recorder.saveRecordedGameToFile(testFile);
        try {
            playback = new Playback(testFile, game);

            List<String> playerMoves = playback.getPlayerMoveHistory();
            List<String> actorMoves = playback.getActorMoveHistory();

            // Test that the loaded data matches the expected data (you may need to adjust these values)
            assertEquals(3, playerMoves.size());
            assertEquals(3, actorMoves.size());
            assertEquals(Direction.RIGHT.toString(), playerMoves.get(0));
            assertEquals("", actorMoves.get(0));
            assertEquals(Direction.DOWN.toString(), playerMoves.get(2));
            assertEquals("ActorMove", actorMoves.get(1));
        } catch (IOException e) {
            e.printStackTrace();
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testInvalidFileFormatMissingFields() throws IOException {
        // Create an invalid JSON file with missing required fields
        String invalidJson = "{ \"playerMoveHistory\": [\"UP\", \"DOWN\"], \"level\": 1 }";

        // Write the invalid JSON string to the test file
        Files.write(Paths.get(invalidTestFile.toURI()), invalidJson.getBytes());

        try {
            playback = new Playback(invalidTestFile, game);

            // The test should not reach here, so it's an error
            fail("Expected IOException due to missing required fields.");
        } catch (IOException e) {
            // Check if the exception message contains the expected error message
            assertTrue(e.getMessage().contains("Invalid file format: Missing required fields"));
        }
    }
    @Test
    public void testPauseAndPlay() {
        // Create a test JSON file with recorded moves
        // This assumes that you have a Recorder class to create the JSON file
        Recorder recorder = new Recorder(1, game);
        recorder.addPlayerMove(Direction.RIGHT);
        recorder.saveRecordedGameToFile(testFile);

        try {
            // Initialize Playback with the test file
            playback = new Playback(testFile, game);
            playback.setSpeed(2000);
            // Start playback
            playback.play();

            // Ensure that playback is playing
            assertTrue(playback.isPlayingBack());

            // Pause playback
            playback.pause();

            // Ensure that playback is paused
            assertFalse(playback.isPlayingBack());

            // Resume playback
            playback.play();

            // Ensure that playback is playing again
            assertTrue(playback.isPlayingBack());
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testCancelPlayback() {
        // Create a test JSON file with recorded moves
        // This assumes that you have a Recorder class to create the JSON file
        Recorder recorder = new Recorder(1, game);
        recorder.addPlayerMove(Direction.RIGHT);
        recorder.saveRecordedGameToFile(testFile);

        try {
            // Initialize Playback with the test file
            playback = new Playback(testFile, game);

            playback.setSpeed(2000);
            // Start playback
            playback.play();

            // Cancel playback
            playback.cancelPlayback();

            // Ensure that playback is canceled
            assertFalse(playback.isPlayingBack());
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testPlayNextFrame() throws IOException {
        // Create a Playback object with some player and actor move history
        File testFile = new File("test-playback-game.json");
        playback = new Playback(testFile, game);
        playback.getPlayerMoveHistory().add(Direction.RIGHT.toString());
        playback.getActorMoveHistory().add("");
        playback.getPlayerMoveHistory().add(Direction.DOWN.toString());
        playback.getActorMoveHistory().add("ActorMove");
        playback.getPlayerMoveHistory().add(Direction.UP.toString());
        playback.getActorMoveHistory().add("");

        // Initially, frame should be 0
        assertEquals(0, playback.getFrame());

        // Call playNextFrame to move to the next frame
        playback.playNextFrame();

        // Frame should be incremented to 1
        assertEquals(1, playback.getFrame());

        playback.playNextFrame();

        // Frame should be incremented to 2
        assertEquals(2, playback.getFrame());

        playback.playNextFrame();

        // Frame should be incremented to3
        assertEquals(3, playback.getFrame());


    }

    @Test
    public void testAutoReplayGame() throws IOException, InterruptedException {

        // Create a Playback object with some player and actor move history
        File testFile = new File("test-playback-game.json");
        playback = new Playback(testFile, game);
        playback.getPlayerMoveHistory().add(Direction.RIGHT.toString());
        playback.getActorMoveHistory().add("");
        playback.getPlayerMoveHistory().add(Direction.DOWN.toString());
        playback.getActorMoveHistory().add("ActorMove");
        playback.getPlayerMoveHistory().add(Direction.UP.toString());
        playback.getActorMoveHistory().add("");

        // Set the playback speed (e.g., 100 milliseconds between task executions)
        int speed = 100;

        // Start auto-replay with the specified speed
        playback.autoReplayGame(speed);

        // Sleep for some time to allow auto-replay to execute
        Thread.sleep(1000); // Sleep for 1 second

        // Get the player's position and compare it with the expected position
        int playerX = game.getDomain().getPlayer().getPosition().x();
        int playerY = game.getDomain().getPlayer().getPosition().y();
        assertEquals(4, playerX); // Replace expectedX with the expected X-coordinate
        assertEquals(6, playerY); // Replace expectedY with the expected Y-coordinate
    }

}
