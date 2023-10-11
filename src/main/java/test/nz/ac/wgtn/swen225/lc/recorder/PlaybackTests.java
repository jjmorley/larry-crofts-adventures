package nz.ac.wgtn.swen225.lc.recorder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import nz.ac.wgtn.swen225.lc.app.Game;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Direction;
import nz.ac.wgtn.swen225.lc.recorder.Playback;
import nz.ac.wgtn.swen225.lc.recorder.Recorder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for playback class.
 *
 * @author Nate Burt 300605172.
 */
public class PlaybackTests {

  private Game game;
  private Recorder recorder;
  private File testFile;
  private File testFile1;
  private File testFile2;
  private Playback playback;
  private File invalidTestFile; // Create a separate test file for invalid format


  /**
   * Set up game and testing files.
   */
  @Before
  public void setUp() {
    game = new Game(1);
    recorder = new Recorder(1, game); // Initialize Recorder
    testFile = new File("src/main/resources/Recorder/test-playback-game.json");
    testFile1 = new File("src/main/resources/Recorder/test-playback-game1.json");
    testFile2 = new File("src/main/resources/Recorder/test-playback-game2.json");
    // Create a separate test file for invalid format
    invalidTestFile = new File("src/main/resources/Recorder/invalid-playback-game.json");
  }

  /**
   * Test with simple empty file.
   */
  @Test
  public void testPlaybackInitialization() {
    recorder.saveRecordedGameToFile(testFile);
    try {
      playback = new Playback(testFile, game);

      assertEquals(0, playback.getFrame());
      assertEquals(1, playback.getLevel());
      assertFalse(playback.isPlayingBack());
    } catch (IOException e) {
      fail("Exception thrown: " + e.getMessage());
    }
  }

  /**
   * Test loading.
   */
  @Test
  public void testLoadRecordedGameFromFile() {
    // Add moves to recorded file
    recorder.addPlayerMove(Direction.RIGHT);
    recorder.addActorMove();
    recorder.addPlayerMove(Direction.DOWN);
    recorder.saveRecordedGameToFile(testFile);
    try {
      playback = new Playback(testFile, game);

      List<String> playerMoves = playback.getPlayerMoveHistory();
      List<String> actorMoves = playback.getActorMoveHistory();

      // Test that the loaded data matches the expected data
      assertEquals(3, playerMoves.size());
      assertEquals(3, actorMoves.size());
      assertEquals(Direction.RIGHT.toString(), playerMoves.get(0));
      assertEquals("", actorMoves.get(0));
      assertEquals(Direction.DOWN.toString(), playerMoves.get(2));
      assertEquals("ActorMove", actorMoves.get(1));
    } catch (IOException e) {
      fail("Exception thrown: " + e.getMessage());
    }
  }

  /**
   * Test invalid format.
   *
   * @throws IOException thrown if invalid format.
   */
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

  /**
   * Test pausing and playing features.
   */
  @Test
  public void testPauseAndPlay() {
    // Create a test JSON file with recorded moves
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

  /**
   * Test cancelling of playback.
   */
  @Test
  public void testCancelPlayback() {
    // Create a test JSON file with recorded moves
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

  /**
   * Test frame by frame.
   *
   * @throws IOException thrown if loading file encounters error.
   */
  @Test
  public void testPlayNextFrame() throws IOException {
    // Create a Playback object with some player and actor move history
    testFile1 = new File("src/main/resources/Recorder/test-playback-game1.json");
    playback = new Playback(testFile1, game);
    playback.getPlayerMoveHistory().add(Direction.RIGHT.toString());
    playback.getActorMoveHistory().add("");

    System.out.println(playback.getPlayerMoveHistory().size());
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
    // Frame should be incremented to 3
    assertEquals(3, playback.getFrame());

  }

  /**
   * Test autoplay.
   *
   * @throws IOException          thrown if loading file encounters error.
   * @throws InterruptedException if thread error encountered
   */
  @Test
  public void testAutoReplayGame() throws IOException, InterruptedException {

    // Create a Playback object with player and actor move history
    testFile2 = new File("src/main/resources/Recorder/test-playback-game2.json");
    playback = new Playback(testFile2, game);
    playback.getPlayerMoveHistory().add(Direction.RIGHT.toString());
    playback.getActorMoveHistory().add("");
    playback.getPlayerMoveHistory().add(Direction.DOWN.toString());
    playback.getActorMoveHistory().add("ActorMove");
    playback.getPlayerMoveHistory().add(Direction.UP.toString());
    playback.getActorMoveHistory().add("");

    // Set the playback speed
    int speed = 100;
    playback.setSpeed(speed);
    playback.play();

    // Sleep for some time to allow auto-replay to execute
    Thread.sleep(1000); // Sleep for 1 second

    // Get the player's position and compare it with the expected position
    int playerX = game.getDomain().getPlayer().getPosition().x();
    int playerY = game.getDomain().getPlayer().getPosition().y();
    int expectedX = 4;
    int expectedY = 6;
    assertEquals(expectedX, playerX);
    assertEquals(expectedY, playerY);
  }

}
