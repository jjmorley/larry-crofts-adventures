package nz.ac.wgtn.swen225.lc.app;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

/**
 * A game timer, times out after a given period of time then calls a callback.
 *
 * @author Trent Shailer 300602354.
 */
public class GameTimer {
  private final Runnable onTimeout;
  private final Consumer<Integer> onTimerUpdate;
  private int timeRemaining;
  private Timer currentTimer;
  private boolean isPaused = false;


  /**
   * Creates a new game timer with a given amount of time remaining.
   *
   * @param startingTime The time that the timer should start with.
   * @param onTimeout    The function to be called when the timer ends.
   */
  public GameTimer(int startingTime, Runnable onTimeout, Consumer<Integer> onTimerUpdate) {
    timeRemaining = startingTime;
    this.onTimeout = onTimeout;
    this.onTimerUpdate = onTimerUpdate;

    decrementTimerAfterOneSec();
  }

  private void decrementTimerAfterOneSec() {
    TimerTask task = new TimerTask() {
      public void run() {
        decrementTimer();
      }
    };
    currentTimer = new Timer("GameTimer");

    long delay = 1000L;
    currentTimer.schedule(task, delay);
  }

  private void decrementTimer() {
    timeRemaining--;

    onTimerUpdate.accept(timeRemaining);

    if (timeRemaining > 0) {
      decrementTimerAfterOneSec();
    } else {
      onTimeout.run();
    }
  }

  /**
   * Stops the timer from decrementing.
   */
  public void pauseTimer() {
    if (currentTimer != null) {
      currentTimer.cancel();
    }
  }

  /**
   * Resumes the timer decrementing.
   */
  public void resumeTimer() {
    decrementTimerAfterOneSec();
  }

  public int getTimeRemaining() {
    return timeRemaining;
  }

  /**
   * Set the paused state of the timer.
   *
   * @param paused the new state.
   * */
  public void setPaused(boolean paused) {
    if (currentTimer != null) {
      currentTimer.cancel();
    }

    // If resuming
    if (!paused) {
      // Start timer
      decrementTimerAfterOneSec();
    }


    isPaused = paused;
  }
}
