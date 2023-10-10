package nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.domain.Domain;

/**
 * Data packet for easy saving/loading.
 *
 * @author Seb Collis 300603371
 */
public class SaveData {

  private Domain domain;
  private int levelNum;
  private int timeRemaining;

  /**
   * SaveData constructor.
   */
  public SaveData(Domain d, int level, int time) {
    domain = d;
    levelNum = level;
    timeRemaining = time;
  }

  /**
   * Returns domain.
   *
   * @return Domain
   */
  public Domain getDomain() {
    return domain;
  }

  /**
   * Returns level number.
   *
   * @return int
   */
  public int getLevelNum() {
    return levelNum;
  }

  /**
   * Returns time remaining in the level, in seconds.
   *
   * @return int
   */
  public int getTimeRemaining() {
    return timeRemaining;
  }
}
