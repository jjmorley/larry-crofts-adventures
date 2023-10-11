package nz.ac.wgtn.swen225.lc.recorder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import nz.ac.wgtn.swen225.lc.app.Game;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Direction;




/**
 * This class takes care of playing back a recorded game from a file in multiple ways.
 *
 * @author Nate Burt 300605172.
 */
public class Playback {
  private Game game;
  private int frame = 0;
  private int level;
  private static int playSpeed = 0;
  private boolean playingBack = false;
  private List<String> playerMoveHistory = new ArrayList<>();
  private List<String> actorMoveHistory = new ArrayList<>();
  private Timer timer;
  private TimerTask replayTask;

  /**
   * Creates new Playback instance.
   * Calls loading method of provided file.
   *
   * @param file File of recorded game.
   * @param game Specific separate playback game.
   * @throws IOException If an I/O error occurs while processing the file.
   */
  public Playback(File file, Game game) throws IOException {
    this.game = game;
    loadRecordedGameFromFile(file);
    timer = new Timer();
    replayTask = autoReplayGame();
  }

  /**
   * Loads player and actor moves and leve number into fields.
   *
   * @param file File of recorded game.
   * @throws IOException If an I/O error occurs while processing the file from incorrect formatting.
   */
  public void loadRecordedGameFromFile(File file) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    // For loading
    TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String, Object>>() {
    };
    Map<String, Object> recordedGameData = objectMapper.readValue(file, typeReference);
    System.out.println("Loaded Recorded Game Data from: " + file);

    // Check formatting
    if (!recordedGameData.containsKey("playerMoveHistory")
            || !recordedGameData.containsKey("actorMoveHistory")
            || !recordedGameData.containsKey("level")) {
      throw new IOException("Invalid file format: Missing required fields");
    }

    // Load fields
    this.playerMoveHistory = (List<String>) recordedGameData.get("playerMoveHistory");
    this.actorMoveHistory = (List<String>) recordedGameData.get("actorMoveHistory");
    this.level = (int) recordedGameData.get("level");
  }

  /**
   * Start the recording playback.
   */
  public void initiateRecordingPlayback() {
    game.startRecordingPlayback(level, this);
  }

  /**
   * Game uses this method to set auto playback speed.
   * Also restarts frame play timer to make sure replaying at right speed.
   *
   * @param speed specified replay speed from user.
   */
  public void setSpeed(int speed) {
    if (speed <= 0) {
      throw new IllegalArgumentException("Speed must be a positive value.");
    }

    playSpeed = speed;

    // If playback is currently active, restart the task with the new speed
    if (playingBack) {
      startAutoReplayTimer();
    }
  }

  /**
   * Start timer to run through frames at specified speed.
   */
  public void startAutoReplayTimer() {
    // Check if the task is already running and cancel it if necessary
    if (replayTask != null) {
      replayTask.cancel();
    }

    if (timer == null) {
      timer = new Timer();
    }

    // Schedule the timer task with the updated speed
    replayTask = autoReplayGame();
    timer.scheduleAtFixedRate(replayTask, 50, playSpeed);
  }

  /**
   * Create a TimerTask for replaying the game frame by frame.
   *
   * @return The TimerTask instance.
   */
  private TimerTask autoReplayGame() {
    return new TimerTask() {
      @Override
      public void run() {
        // If playback stopped or end of recorded game
        if (!playingBack || frame == playerMoveHistory.size()) {
          replayTask.cancel();
          pause();
        } else {
          if (actorMoveHistory.get(frame).isEmpty()) {
            game.movePlayer(Direction.valueOf(playerMoveHistory.get(frame)));
          } else {
            game.updateActors();
          }
          frame++;
        }
      }
    };
  }

  /**
   * Game uses this method to move step-by-step through recorded game.
   */
  public void playNextFrame() {
    pause();

    if (frame == playerMoveHistory.size() - 1) {
      game.stopRecordingPlayback(this);
    } else {
      // Check whether next move is an actor move or player move
      if (actorMoveHistory.get(frame).isEmpty()) {
        game.movePlayer(Direction.valueOf(playerMoveHistory.get(frame)));
      } else {
        game.updateActors();
      }
      frame++;
    }

  }

  /**
   * Pause playback.
   */
  public void pause() {
    playingBack = false;
  }

  /**
   * Resume or start auto playback.
   */
  public void play() {
    playingBack = true;
    startAutoReplayTimer();
  }

  /**
   * Cancel auto playback indefinitely.
   * Game calls this method for an indefinite stop to the game.
   */
  public void cancelPlayback() {
    playingBack = false;
    timer.cancel();
  }

  /**
   * Getter for player move history.
   *
   * @return player movements
   */
  public List<String> getPlayerMoveHistory() {
    return playerMoveHistory;
  }

  /**
   * Getter for actor move history.
   *
   * @return actors moving.
   */
  public List<String> getActorMoveHistory() {
    return actorMoveHistory;
  }

  /**
   * Getter for recorded game level.
   *
   * @return level of the game.
   */
  public int getLevel() {
    return level;
  }

  /**
   * Getter for current recorded game frame.
   *
   * @return current game frame.
   */
  public int getFrame() {
    return frame;
  }

  /**
   * Getter for if the recorded game is being auto-played.
   *
   * @return if game is playing back.
   */
  public boolean isPlayingBack() {
    return playingBack;
  }
}
